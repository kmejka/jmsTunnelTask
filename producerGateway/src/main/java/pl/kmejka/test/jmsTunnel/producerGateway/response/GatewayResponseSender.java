package pl.kmejka.test.jmsTunnel.producerGateway.response;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by kmejka on 22.05.14.
 */
public class GatewayResponseSender {
    private static final Logger LOG = LoggerFactory.getLogger(GatewayResponseSender.class);

    private Connection connection;
    private Session session;
    private MessageProducer producer;

    public GatewayResponseSender(final String sendToQueueName, final String sendToQueueAddress) {
        LOG.debug("Starting gateway response sender with sendToQueueName: {} and sendToQueueAddress: {}", sendToQueueName, sendToQueueAddress);
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(sendToQueueAddress);
            this.connection = connectionFactory.createConnection();
            connection.start();

            this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(sendToQueueName);

            this.producer = session.createProducer(destination);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void destroyResponseSender() {
        LOG.debug("Closing gateway response sender");
        try {
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
            if (producer != null) {
                producer.close();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(final String textMessage) {
        LOG.debug("Gateway sending message: {}", textMessage);
        try {
            TextMessage message = session.createTextMessage();
            message.setText(textMessage);
            this.producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
