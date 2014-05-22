package pl.kmejka.test.jmsTunnel.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.kmejka.test.jmsTunnel.gateway.http.GatewayHttpEndpoint;
import pl.kmejka.test.jmsTunnel.gateway.http.GatewayHttpMessageHandler;
import pl.kmejka.test.jmsTunnel.gateway.jms.GatewayJmsMessageProducer;
import pl.kmejka.test.jmsTunnel.gateway.jms.GatewayJmsMessageConsumer;

/**
 * Created by kmejka on 20.05.14.
 */
public class Gateway {

    private static final Logger LOG = LoggerFactory.getLogger(Gateway.class);
//
//    final GatewayEndpoint http;
//    private final GatewayResponseConsumer responseConsumer;
//
//    public Gateway(final int gatewayEndpointPort, final String listenToQueueName, final String listenToQueueAddress, final String sendToEndpoint) throws Exception {
//        this.http = new GatewayEndpoint(gatewayEndpointPort);
//        this.responseConsumer = new GatewayResponseConsumer(listenToQueueName, listenToQueueAddress, sendToEndpoint);
//    }

    public static void main(String[] args) throws Exception {
        final int gatewayEndpointPort = 8080;
        final String forwardToQueueName = "producerRequestQueue";
        final String forwardToQueueAddress = "tcp://localhost:10000";
        final String listenToQueueName = "producerResponseQueue";
        final String listenToQueueAddress = "tcp://localhost:10001";
        final String forwardToEndpoint = "http://localhost:8081";

        GatewayJmsMessageProducer gatewayJmsMessageProducer = new GatewayJmsMessageProducer(forwardToQueueName, forwardToQueueAddress);
        GatewayHttpMessageHandler gatewayHttpMessageHandler = new GatewayHttpMessageHandler(gatewayJmsMessageProducer);

        GatewayJmsMessageConsumer gatewayJmsMessageConsumer = new GatewayJmsMessageConsumer(listenToQueueName, listenToQueueAddress, forwardToEndpoint);
        GatewayHttpEndpoint gatewayHttpEndpoint = new GatewayHttpEndpoint(gatewayEndpointPort, gatewayHttpMessageHandler);

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
