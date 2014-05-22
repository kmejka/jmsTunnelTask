package pl.kmejka.test.jmsTunnel.producer;

import org.apache.activemq.broker.BrokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kmejka.test.jmsTunnel.producer.request.RequestMessageConsumer;
import pl.kmejka.test.jmsTunnel.producer.response.ResponseSender;

/**
 * Created by kmejka on 21.05.14.
 */
public class ServiceProducer {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceProducer.class);

    public static void main(String[] args) {
        LOG.debug("Starting service producer");
        final String requestQueueName = "producerRequestQueue";
        final String requestQueueAddress = "tcp://localhost:10000";
        final String responseQueueName = "producerResponseQueue";
        final String responseQueueAddress = "tcp://localhost:10001";

        final BrokerService producerRequestQueueBroker = new BrokerService();
        final BrokerService producerResponseQueueBroker = new BrokerService();

        constructAndStartQueue(producerRequestQueueBroker, requestQueueName, requestQueueAddress);
        constructAndStartQueue(producerResponseQueueBroker, responseQueueName, responseQueueAddress);

        LOG.debug("Creating response sender");
        ResponseSender responseSender = new ResponseSender(responseQueueName, responseQueueAddress);
        final RequestMessageConsumer requestMessageConsumer = new RequestMessageConsumer(requestQueueName, requestQueueAddress, responseSender);
    }

    private static void constructAndStartQueue(final BrokerService queueBrokerServiceRef, final String queueName, final String queueAddress) {
        LOG.debug("Starting producer queue broker with name: {} and queueAddress {}", queueName, queueAddress);
        queueBrokerServiceRef.setBrokerName(queueName);
        try {
            queueBrokerServiceRef.addConnector(queueAddress);
            queueBrokerServiceRef.setPersistent(false);
            queueBrokerServiceRef.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOG.debug("Queue broker started");
    }
}
