package pl.kmejka.test.jmsTunnel.gateway.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kmejka.test.jmsTunnel.gateway.http.GatewayHttpMessageSender;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

/**
 * Created by kmejka on 22.05.14.
 */
public class GatewayJmsMessageConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(GatewayJmsMessageConsumer.class);

    private Connection connection;
    private Session session;
    private MessageConsumer consumer;

    public GatewayJmsMessageConsumer(final String listenToQueueName, final String listenToQueueAddress, final String forwardToEndpoint) {
        LOG.debug("Starting gateway request message consumer with listenToQueueName: {} and listenToQueueAddress: {} and forwardToEndpoint {}", listenToQueueName, listenToQueueAddress, forwardToEndpoint);
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(listenToQueueAddress);
            this.connection = connectionFactory.createConnection();
            connection.start();

            this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(listenToQueueName);

            this.consumer = session.createConsumer(destination);
            consumer.setMessageListener(new GatewayJmsMessageListener(new GatewayHttpMessageSender(forwardToEndpoint)));

        } catch (javax.jms.JMSException e) {
            e.printStackTrace();
        }
    }

    public void destroyGatewayResponseMessageConsumer() {
        LOG.debug("Closing gateway response message consumer");
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
