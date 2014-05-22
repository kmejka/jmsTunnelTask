package pl.kmejka.test.jmsTunnel.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kmejka.test.jmsTunnel.gateway.http.GatewayHttpEndpoint;
import pl.kmejka.test.jmsTunnel.gateway.http.GatewayHttpMessageHandler;
import pl.kmejka.test.jmsTunnel.gateway.jms.GatewayJmsMessageConsumer;
import pl.kmejka.test.jmsTunnel.gateway.jms.GatewayJmsMessageProducer;

/**
 * Created by kmejka on 20.05.14.
 */
public class Gateway {

    private static final Logger LOG = LoggerFactory.getLogger(Gateway.class);

    public static void main(String[] args) throws Exception {
        LOG.debug("Starting gateway");
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
}
