package pl.kmejka.test.jmsTunnel.proxy;

import org.apache.activemq.broker.BrokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kmejka.test.jmsTunnel.proxy.endpoint.ProxyHttpEndpoint;
import pl.kmejka.test.jmsTunnel.proxy.endpoint.ProxyHttpMessageHandler;
import pl.kmejka.test.jmsTunnel.proxy.endpoint.ProxyHttpMessageSender;
import pl.kmejka.test.jmsTunnel.proxy.jms.ProxyJmsMessageConsumer;
import pl.kmejka.test.jmsTunnel.proxy.jms.ProxyJmsMessageListener;
import pl.kmejka.test.jmsTunnel.proxy.jms.ProxyJmsMessageProducer;

/**
 * Created by kmejka on 20.05.14.
 */
public class ServiceProxy {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceProxy.class);

    public static void main(String[] args) throws Exception {
        final int proxyEndpointPort = 8081;
        final String proxyRequestQueueName = "proxyRequestQueue";
        final String proxyRequestQueueAddress = "tcp://localhost:20000";
        final String proxyResponseQueueName = "proxyResponseQueue";
        final String proxyResponseQueueAddress = "tcp://localhost:20001";
        final String forwardToEndpoint = "http://localhost:8080";

        final BrokerService proxyRequestQueueBroker = new BrokerService();
        final BrokerService proxyResponseQueueBroker = new BrokerService();
        constructAndStartQueue(proxyRequestQueueBroker, proxyRequestQueueName, proxyRequestQueueAddress);
        constructAndStartQueue(proxyResponseQueueBroker, proxyResponseQueueName, proxyResponseQueueAddress);

        ProxyHttpMessageSender proxyHttpMessageSender = new ProxyHttpMessageSender(forwardToEndpoint);
        ProxyJmsMessageListener proxyJmsMessageListener = new ProxyJmsMessageListener(proxyHttpMessageSender);
        ProxyJmsMessageConsumer proxyJmsMessageConsumer = new ProxyJmsMessageConsumer(proxyRequestQueueName, proxyRequestQueueAddress, proxyJmsMessageListener);

        ProxyJmsMessageProducer proxyJmsResponseMessageProducer = new ProxyJmsMessageProducer(proxyResponseQueueName, proxyResponseQueueAddress);
        ProxyHttpMessageHandler proxyHttpMessageHandler = new ProxyHttpMessageHandler(proxyJmsResponseMessageProducer);

        ProxyHttpEndpoint gatewayHttpEndpoint = new ProxyHttpEndpoint(proxyEndpointPort, proxyHttpMessageHandler);
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
