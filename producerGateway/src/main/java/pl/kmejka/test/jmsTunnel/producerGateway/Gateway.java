package pl.kmejka.test.jmsTunnel.producerGateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.kmejka.test.jmsTunnel.producerGateway.endpoint.GatewayEndpoint;
import pl.kmejka.test.jmsTunnel.producerGateway.response.GatewayResponseConsumer;

/**
 * Created by kmejka on 20.05.14.
 */
public class Gateway {

    private static final Logger LOG = LoggerFactory.getLogger(Gateway.class);

    final GatewayEndpoint endpoint;
    private final GatewayResponseConsumer responseConsumer;

    public Gateway(final int gatewayEndpointPort, final String listenToQueueName, final String listenToQueueAddress, final String sendToQueueName, final String sendToQueueAddress) throws Exception {
        this.endpoint = new GatewayEndpoint(gatewayEndpointPort);
        this.responseConsumer = new GatewayResponseConsumer(listenToQueueName, listenToQueueAddress, sendToQueueName, sendToQueueAddress);
    }

//    public void startQueue(final String queueName, final String queueAddress) {
//        LOG.debug("Starting queue broker with name: {} and queueAddress {}", queueName, queueAddress);
//        broker.setBrokerName(queueName);
//        try {
//            broker.addConnector(queueAddress);
//            broker.setPersistent(false);
//            broker.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        LOG.debug("Queue broker started");
//    }
//
//    public void stopQueue() {
//        LOG.debug("Stopping queue broker");
//        try {
//            this.broker.stop();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        LOG.debug("Queue broker stopped");
//    }

}
