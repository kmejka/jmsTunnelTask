package pl.kmejka.test.jmsTunnel.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kmejka.test.jmsTunnel.consumer.listener.MsgListener;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by kmejka on 20.05.14.
 */
public class MsgConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(MsgConsumer.class);

    public void startMessageConsumer(final int timeoutMillis, final String queueName, final String queueAddress) {
        Connection connection = null;
        Session session = null;
        MessageConsumer consumer = null;
        try {
            LOG.debug("Starting message consumer with queueName: {} and queueAddress: {}", queueName, queueAddress);
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(queueAddress);
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queueName);
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(new MsgListener());

        } catch (javax.jms.JMSException e) {
            e.printStackTrace();
        }
        finally {
            LOG.debug("Closing message consumer");
            try {
                if (session != null) {
                    session.close();
                }
                if (connection != null) {
                    connection.close();
                }
                if (consumer != null) {
                    consumer.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
