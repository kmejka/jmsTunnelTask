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

    private final BrokerService producerRequestQueueBroker;
    private final BrokerService producerResponseQueueBroker;

    private final RequestMessageConsumer requestMessageConsumer;

    public ServiceProducer(final String requestQueueName, final String requestQueueAddress, final String responseQueueName, final String responseQueueAddress) {
        LOG.debug("Initializing service producer");
        this.producerRequestQueueBroker = new BrokerService();
        this.producerResponseQueueBroker = new BrokerService();
        this.constructAndStartQueue(producerRequestQueueBroker, requestQueueName, requestQueueAddress);
        this.constructAndStartQueue(producerResponseQueueBroker, responseQueueName, responseQueueAddress);

        LOG.debug("Creating response sender");
        ResponseSender responseSender = new ResponseSender(responseQueueName, responseQueueAddress);
        requestMessageConsumer = new RequestMessageConsumer(requestQueueName, requestQueueAddress, responseSender);

    }

    public void destroyServiceProducer() {
        stopQueue(producerRequestQueueBroker);
        stopQueue(producerResponseQueueBroker);
        this.requestMessageConsumer.destroyResponseMessageConsumer();
    }

    private void constructAndStartQueue(final BrokerService queueBrokerServiceRef, final String queueName, final String queueAddress) {
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

    private void stopQueue(final BrokerService queueBrokerServiceRef) {
        try {
            queueBrokerServiceRef.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
