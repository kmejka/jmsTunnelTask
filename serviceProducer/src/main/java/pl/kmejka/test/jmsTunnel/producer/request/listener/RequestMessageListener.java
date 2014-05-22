package pl.kmejka.test.jmsTunnel.producer.request.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kmejka.test.jmsTunnel.producer.response.ResponseSender;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by kmejka on 21.05.14.
 */
public class RequestMessageListener implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger(RequestMessageListener.class);

    private final ResponseSender responseSender;

    public RequestMessageListener(final ResponseSender responseSender) {
        LOG.debug("Constructing producer request message listener");
        this.responseSender = responseSender;
    }

    @Override
    public void onMessage(Message message) {
        LOG.debug("OnMessage in producer RequestMessageListener");
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
            LOG.debug("Responding");
            responseSender.sendMessage(text + "\t~~PRODUCER RESPONDING~~\t");
        } else {
            LOG.debug("Received message, NOT instance of TestMessage");
            LOG.debug("Received: " + message);
        }
    }
}
