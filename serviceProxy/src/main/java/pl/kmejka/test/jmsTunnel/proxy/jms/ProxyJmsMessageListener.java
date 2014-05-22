package pl.kmejka.test.jmsTunnel.proxy.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kmejka.test.jmsTunnel.proxy.endpoint.ProxyHttpMessageSender;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by kmejka on 21.05.14.
 */
public class ProxyJmsMessageListener implements MessageListener {

    private static final Logger LOG = LoggerFactory.getLogger(ProxyJmsMessageListener.class);
    private final ProxyHttpMessageSender proxyHttpMessageSender;

    public ProxyJmsMessageListener(final ProxyHttpMessageSender proxyHttpMessageSender) {
        this.proxyHttpMessageSender = proxyHttpMessageSender;
    }

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
            LOG.debug("Forwarding");
            proxyHttpMessageSender.sendMessage(text + "\tPROXY FORWARDING MESSAGE\t");
        } else {
            LOG.debug("Received message, NOT instance of TestMessage");
            LOG.debug("Received: " + message);
        }
    }
}
