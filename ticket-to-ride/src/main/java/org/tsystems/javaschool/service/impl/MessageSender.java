package org.tsystems.javaschool.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.tsystems.javaschool.model.dto.stand.StandUpdateDto;
import org.tsystems.javaschool.util.JsonParser;

import javax.jms.*;

/**
 * @author Trofim Kremen
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MessageSender {

    private final ActiveMQConnectionFactory connectionFactory;
    private final Environment environment;
    private final JsonParser jsonParser;

    public void sendMessage(StandUpdateDto standDto) {
        try {
            Connection connection = connectionFactory.createQueueConnection();
            connection.start();
            System.out.println("Successfully connected");
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            System.out.println("Session created");
            Queue queue = session.createQueue(environment.getProperty("queue.name"));
            System.out.println("Queue created");

            MessageProducer messageProducer = session.createProducer(queue);
            messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

            ActiveMQTextMessage mqTextMessage = (ActiveMQTextMessage) session.createTextMessage();
            String textMessage = jsonParser.writeToJSON(standDto);
            mqTextMessage.setText(textMessage);
            messageProducer.send(mqTextMessage);
            System.out.println("Message sent");
            session.close();
            connection.close();
        }
        catch (Exception e) {
            log.error("Error sending message", e);
        }
    }

}
