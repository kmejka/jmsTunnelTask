package pl.kmejka.test.jmsTunnel.producerGateway.response.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kmejka.test.jmsTunnel.producerGateway.response.GatewayResponseSender;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by kmejka on 22.05.14.
 */
public class GatewayResponseListener implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger(GatewayResponseListener.class);

    private final GatewayResponseSender sender;

    public GatewayResponseListener(GatewayResponseSender sender) {
        this.sender = sender;
    }

    @Override
    public void onMessage(Message message) {
        LOG.debug("OnMessage in gateway GatewayResponseListener");
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
            sender.sendMessage("Forwarding message with text: '"+text+"'");
        } else {
            LOG.debug("Received message, NOT instance of TestMessage");
            LOG.debug("Received: " + message);
        }
    }
}
