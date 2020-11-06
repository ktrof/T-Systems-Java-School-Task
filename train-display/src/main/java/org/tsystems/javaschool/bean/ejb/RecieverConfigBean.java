package org.tsystems.javaschool.bean.ejb;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageConsumer;

import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;

/**
 * @author Trofim Kremen
 */
@Singleton
@Slf4j
public class RecieverConfigBean {

    private static final String QUEUE_NAME = "QUEUE";

    @Inject
    private StandUpdateListener listener;
    private Connection connection;
    private Session session;

    public void openConnection() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:61616");
        try {
            connection = connectionFactory.createQueueConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(QUEUE_NAME);

            ActiveMQMessageConsumer messageConsumer = (ActiveMQMessageConsumer) session.createConsumer(queue);
            messageConsumer.setMessageListener(listener);
        } catch (JMSException e) {
            log.error("Error opening JMS connection", e);
        }
    }

    @PreDestroy
    public void closeConnection() {
        try {
            session.close();
            connection.close();
        } catch (JMSException e) {
            log.error("Error closing JMS connection", e);
        }
    }

}
