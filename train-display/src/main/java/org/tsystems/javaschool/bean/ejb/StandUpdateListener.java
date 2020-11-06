package org.tsystems.javaschool.bean.ejb;

import org.tsystems.javaschool.dto.StandUpdateDto;

import javax.enterprise.context.Dependent;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author Trofim Kremen
 */
@Dependent
public class StandUpdateListener implements MessageListener {

    @Push(channel = "standUpdate")
    private PushContext context;

    @Inject
    private StandBean standBean;

    @Override
    public void onMessage(Message message) {
        try {
            processMessage(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void processMessage(final Message message) throws JMSException {
        StandUpdateDto standDto = message.getBody(StandUpdateDto.class);
        standBean.updateStand(standDto);
        context.send("Stand updated, nice!");
    }
}
