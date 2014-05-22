package pl.kmejka.test.jmsTunnel.runner;

import pl.kmejka.test.jmsTunnel.consumer.ServiceConsumer;
import pl.kmejka.test.jmsTunnel.producer.ServiceProducer;
import pl.kmejka.test.jmsTunnel.gateway.Gateway;

/**
 * Created by kmejka on 20.05.14.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        String serviceProducerRequestQueueName = "requestQueue";
        String serviceProducerRequestQueueAddress = "tcp://localhost:11111";
        String serviceProducerResponseQueueName = "responseQueue";
        String serviceProducerResponseQueueAddress = "tcp://localhost:11112";

        ServiceProducer producer = new ServiceProducer(serviceProducerRequestQueueName, serviceProducerRequestQueueAddress, serviceProducerResponseQueueName, serviceProducerResponseQueueAddress);

        String proxyListenToQueueName = "requestQueue";
        String proxyListenToQueueAddress = "tcp://localhost:11111";
        String proxySendToQueueName = "responseQueue";
        String proxySendToQueueAddress = "tcp://localhost:11112";

        String gatewayListenToQueueName = "requestQueue";
        String gatewayListenToQueueAddress = "tcp://localhost:11111";
        String gatewaySendToEndpoint = "http://localhost:8080";

        Gateway gateway = new Gateway(8080, gatewayListenToQueueName, gatewayListenToQueueAddress,  gatewaySendToEndpoint);


        String serviceConsumerRequestQueueName = "requestQueue";
        String serviceConsumerRequestQueueAddress = "tcp://localhost:11111";
        String serviceConsumerResponseQueueName = "responseQueue";
        String serviceConsumerResponseQueueAddress = "tcp://localhost:11112";

        ServiceConsumer consumer = new ServiceConsumer(serviceConsumerRequestQueueName, serviceConsumerRequestQueueAddress, serviceConsumerResponseQueueName, serviceConsumerResponseQueueAddress);
        consumer.sendMessage("Request");

    }
}
