package pl.kmejka.test.jmsTunnel.runner;

import pl.kmejka.test.jmsTunnel.producerGateway.Gateway;

/**
 * Created by kmejka on 20.05.14.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        String serviceConsumerRequestQueueName = "requestQueue";
        String serviceConsumerRequestQueueAddress = "tcp://localhost:11111";
        String serviceConsumerResponseQueueName = "responseQueue";
        String serviceConsumerResponseQueueAddress = "tcp://localhost:11112";
//
//        ServiceConsumer consumer = new ServiceConsumer(serviceConsumerRequestQueueName, serviceConsumerRequestQueueAddress, serviceConsumerResponseQueueName, serviceConsumerResponseQueueAddress);
//        consumer.sendMessage("Request");

        String proxyListenToQueueName = "requestQueue";
        String proxyListenToQueueAddress = "tcp://localhost:11111";
        String proxySendToQueueName = "responseQueue";
        String proxySendToQueueAddress = "tcp://localhost:11112";

        String gatewayListenToQueueName = "requestQueue";
        String gatewayListenToQueueAddress = "tcp://localhost:11111";
        String gatewaySendToQueueName = "responseQueue";
        String gatewaySendToQueueAddress = "tcp://localhost:11112";

        Gateway gateway = new Gateway(8080, gatewayListenToQueueName, gatewayListenToQueueAddress, gatewaySendToQueueName, gatewaySendToQueueAddress);

        String serviceProducerRequestQueueName = "requestQueue";
        String serviceProducerRequestQueueAddress = "tcp://localhost:11111";
        String serviceProducerResponseQueueName = "responseQueue";
        String serviceProducerResponseQueueAddress = "tcp://localhost:11112";
//
//        ServiceProducer producer = new ServiceProducer(serviceProducerRequestQueueName, serviceProducerRequestQueueAddress, serviceProducerResponseQueueName, serviceProducerResponseQueueAddress);
//
//


    }
}
