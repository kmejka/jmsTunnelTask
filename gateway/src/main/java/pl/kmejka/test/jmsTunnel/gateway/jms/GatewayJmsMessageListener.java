package pl.kmejka.test.jmsTunnel.gateway.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kmejka.test.jmsTunnel.gateway.http.GatewayHttpMessageSender;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by kmejka on 22.05.14.
 */
public class GatewayJmsMessageListener implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger(GatewayJmsMessageListener.class);

    private final GatewayHttpMessageSender sender;

    public GatewayJmsMessageListener(GatewayHttpMessageSender sender) {
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
            sender.sendMessage(text+"\tGATEWAY FORWARDING MESSAGE HTTP\t");
        } else {
            LOG.debug("Received message, NOT instance of TestMessage");
            LOG.debug("Received: " + message);
        }
    }
}
