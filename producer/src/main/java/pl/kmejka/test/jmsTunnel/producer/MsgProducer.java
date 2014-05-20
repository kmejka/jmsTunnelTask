package pl.kmejka.test.jmsTunnel.producer;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by kmejka on 20.05.14.
 */
public class MsgProducer {

    private static final Logger LOG = LoggerFactory.getLogger(MsgProducer.class);


    public void sendMessage(final String messageText, final String queueName, final String queueAddress) {
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        try {
            LOG.debug("Starting message producer with queueName: {} and queueAddress: {}", queueName, queueAddress);
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(queueAddress);
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queueName);

            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            LOG.debug("Sending message");
            TextMessage message = session.createTextMessage();
            message.setText(messageText);

            producer.send(message);

            LOG.debug("Message sent, closing");

        } catch (JMSException e) {

            e.printStackTrace();
        } finally {
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
    }
}
