package org.tsystems.javaschool.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.tsystems.javaschool.model.dto.StandDto;
import org.tsystems.javaschool.model.dto.StandRowDto;

import javax.jms.Topic;

/**
 * @author Trofim Kremen
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final JmsTemplate jmsTemplate;
    private final Topic topic;

    public void sendMessage(StandDto standDto) {
        Runnable runnable =
                () -> {
                    try {
                        jmsTemplate.convertAndSend(topic, standDto);
                    }
                    catch (Exception e) {
                        log.error("Error sending message", e);
                    }
                };
        new Thread(runnable).start();
    }

}
