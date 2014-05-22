package pl.kmejka.test.jmsTunnel.producer.request;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kmejka.test.jmsTunnel.producer.request.listener.RequestMessageListener;
import pl.kmejka.test.jmsTunnel.producer.response.ResponseSender;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

/**
 * Created by kmejka on 21.05.14.
 */
public class RequestMessageConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(RequestMessageConsumer.class);
    private final ResponseSender responseSender;
    private Connection connection;
    private Session session;
    private MessageConsumer consumer;

    public RequestMessageConsumer(final String queueName, final String queueAddress, final ResponseSender responseSender) {
        LOG.debug("Starting request message consumer with queueName: {} and queueAddress: {}", queueName, queueAddress);
        this.responseSender = responseSender;
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(queueAddress);
            this.connection = connectionFactory.createConnection();
            connection.start();

            this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queueName);

            this.consumer = session.createConsumer(destination);
            consumer.setMessageListener(new RequestMessageListener(responseSender));

        } catch (javax.jms.JMSException e) {
            e.printStackTrace();
        }
    }

    public void destroyRequestMessageConsumer() {
        LOG.debug("Closing request message consumer");
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
