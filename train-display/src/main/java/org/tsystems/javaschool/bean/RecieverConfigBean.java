package org.tsystems.javaschool.bean;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageConsumer;
import org.tsystems.javaschool.util.PropertiesBean;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import java.util.Objects;

/**
 * @author Trofim Kremen
 */
@Singleton
@Slf4j
public class RecieverConfigBean {

    @Inject
    private StandUpdateListener listener;

    @EJB
    private PropertiesBean propertiesBean;
    private Connection connection = null;
    private Session session = null;

    public void openConnection() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(propertiesBean.getBrokerUrl());
        try {
            connection = connectionFactory.createQueueConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(propertiesBean.getDestinationName());

            ActiveMQMessageConsumer messageConsumer = (ActiveMQMessageConsumer) session.createConsumer(queue);
            messageConsumer.setMessageListener(listener);
        } catch (JMSException e) {
            log.error("Error opening JMS connection", e);
        }
    }

    @PreDestroy
    public void closeConnection() {
        try {
            if (Objects.nonNull(connection) || Objects.nonNull(session))
            session.close();
            connection.close();
        } catch (JMSException e) {
            log.error("Error closing JMS connection", e);
        }
    }

}
