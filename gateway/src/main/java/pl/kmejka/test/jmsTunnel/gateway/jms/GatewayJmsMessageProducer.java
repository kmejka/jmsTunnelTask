package pl.kmejka.test.jmsTunnel.gateway.jms;

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
public class GatewayJmsMessageProducer {


    private static final Logger LOG = LoggerFactory.getLogger(GatewayJmsMessageProducer.class);

    private Connection connection;
    private Session session;
    private MessageProducer producer;

    public GatewayJmsMessageProducer(final String queueName, final String queueAddress) {
        LOG.debug("Starting jms producer with queueName: {} and queueAddress: {}", queueName, queueAddress);
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(queueAddress);
            this.connection = connectionFactory.createConnection();
            connection.start();

            this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queueName);

            this.producer = session.createProducer(destination);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void destroyResponseSender() {
        LOG.debug("Closing jms producer");
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
        LOG.debug("Received order to send message: {}", textMessage);
        try {
            TextMessage message = session.createTextMessage();
            message.setText(textMessage);
            this.producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
