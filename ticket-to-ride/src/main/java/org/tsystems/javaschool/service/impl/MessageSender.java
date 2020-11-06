package org.tsystems.javaschool.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.tsystems.javaschool.model.dto.stand.StandUpdateDto;

import javax.jms.Connection;
import javax.jms.Queue;
import javax.jms.Session;
import java.util.Objects;

/**
 * @author Trofim Kremen
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MessageSender {

    private final JmsTemplate jmsTemplate;

    public void sendMessage(StandUpdateDto standDto) {
        Runnable runnable =
                () -> {
                    try {
                        Connection connection = Objects.requireNonNull(jmsTemplate.getConnectionFactory())
                                .createConnection();
                        connection.start();
                        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                        Queue queue = session.createQueue(jmsTemplate.getDefaultDestinationName());
                        jmsTemplate.convertAndSend(queue, standDto);
                        session.close();
                        connection.close();
                    }
                    catch (Exception e) {
                        log.error("Error sending message", e);
                    }
                };
        new Thread(runnable).start();
    }

}
