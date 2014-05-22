package pl.kmejka.test.jmsTunnel.producerGateway.response;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kmejka.test.jmsTunnel.producerGateway.response.listener.GatewayResponseListener;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

/**
 * Created by kmejka on 22.05.14.
 */
public class GatewayResponseConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(GatewayResponseConsumer.class);

    private Connection connection;
    private Session session;
    private MessageConsumer consumer;

    public GatewayResponseConsumer(final String listenToQueueName, final String listenToQueueAddress, final String sendToQueueName, final String sendToQueueAddress) {
        LOG.debug("Starting gateway request message consumer with listenToQueueName: {} and listenToQueueAddress: {}", listenToQueueName, listenToQueueAddress);
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(listenToQueueAddress);
            this.connection = connectionFactory.createConnection();
            connection.start();

            this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(listenToQueueName);

            this.consumer = session.createConsumer(destination);
            consumer.setMessageListener(new GatewayResponseListener(new GatewayResponseSender(sendToQueueName, sendToQueueAddress)));

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
