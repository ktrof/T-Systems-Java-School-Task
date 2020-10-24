package org.tsystems.javaschool.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.tsystems.javaschool.model.dto.StandRowDto;

import javax.jms.Topic;

/**
 * @author Trofim Kremen
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SendMessage {

    private final JmsTemplate jmsTemplate;
    private final Topic topic;

    public void sendMessage(StandRowDto standRowDto) {
        Runnable runnable =
                () -> {
                    try {
                        jmsTemplate.convertAndSend(topic, standRowDto);
                    }
                    catch (Exception e) {
                        log.error("Error sending message", e);
                    }
                };
        new Thread(runnable).start();
    }

}
