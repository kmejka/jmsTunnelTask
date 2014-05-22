package pl.kmejka.test.jmsTunnel.proxy.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

/**
 * Created by kmejka on 20.05.14.
 */
public class ProxyJmsMessageConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(ProxyJmsMessageConsumer.class);

    private Connection connection;
    private Session session;
    private MessageConsumer consumer;

    public ProxyJmsMessageConsumer(final String queueName, final String queueAddress, final ProxyJmsMessageListener proxyJmsMessageListener) {
        LOG.debug("Starting response message consumer with queueName: {} and queueAddress: {}", queueName, queueAddress);
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(queueAddress);
            this.connection = connectionFactory.createConnection();
            connection.start();

            this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queueName);

            this.consumer = session.createConsumer(destination);
            consumer.setMessageListener(proxyJmsMessageListener);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void destroyResponseMessageConsumer() {
        LOG.debug("Closing response message consumer");
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
