package org.tsystems.javaschool.bean;

import lombok.extern.slf4j.Slf4j;
import org.tsystems.javaschool.dto.StandUpdateDto;
import org.tsystems.javaschool.util.JsonParser;

import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author Trofim Kremen
 */
@Dependent
@Slf4j
public class StandUpdateListener implements MessageListener {

    @Inject
    private StandNamedBean standNamedBean;

    @EJB
    private JsonParser jsonParser;

    @Override
    public void onMessage(Message message) {
        try {
            log.info("Message " + message.getJMSMessageID() + " recieved");
            processMessage(message);
        } catch (JMSException e) {
            log.error("Error processing the message", e);
        }
    }

    private void processMessage(Message message) throws JMSException {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            StandUpdateDto standUpdateDto = jsonParser.readStandDtoJSON(text);
            standNamedBean.updateStand(standUpdateDto);
            log.info("Message " + message.getJMSMessageID() + " has been processed");
        }
    }
}
