package pl.kmejka.test.jmsTunnel.consumer.response.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by kmejka on 21.05.14.
 */
public class ResponseMessageListener implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger(ResponseMessageListener.class);

    @Override
    public void onMessage(Message message) {
        LOG.debug("OnMessage in consumer ResponseMessageListener");
        if (message instanceof TextMessage) {
            LOG.debug("Received message, instance of TestMessage");
            TextMessage textMessage = (TextMessage) message;
            String text = null;
            try {
                text = textMessage.getText();
            } catch (JMSException e) {
                e.printStackTrace();
            }
            LOG.debug("Received: " + text);
        } else {
            LOG.debug("Received message, NOT instance of TestMessage");
            LOG.debug("Received: " + message);
        }
    }
}
