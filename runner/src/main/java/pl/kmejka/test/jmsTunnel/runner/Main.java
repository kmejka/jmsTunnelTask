package pl.kmejka.test.jmsTunnel.runner;

import org.apache.activemq.benchmark.Producer;
import pl.kmejka.test.jmsTunnel.consumer.ServiceConsumer;
import pl.kmejka.test.jmsTunnel.producer.ServiceProducer;
import pl.kmejka.test.jmsTunnel.producerGateway.ProducerGateway;

/**
 * Created by kmejka on 20.05.14.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        ProducerGateway producerGateway = new ProducerGateway(8080);

//        String serviceProducerRequestQueueName = "requestQueue";
//        String serviceProducerRequestQueueAddress = "tcp://localhost:11111";
//        String serviceProducerResponseQueueName = "responseQueue";
//        String serviceProducerResponseQueueAddress = "tcp://localhost:11112";
//
//        ServiceProducer producer = new ServiceProducer(serviceProducerRequestQueueName, serviceProducerRequestQueueAddress, serviceProducerResponseQueueName, serviceProducerResponseQueueAddress);
//
//
//        String serviceConsumerRequestQueueName = "requestQueue";
//        String serviceConsumerRequestQueueAddress = "tcp://localhost:11111";
//        String serviceConsumerResponseQueueName = "responseQueue";
//        String serviceConsumerResponseQueueAddress = "tcp://localhost:11112";
//
//        ServiceConsumer consumer = new ServiceConsumer(serviceConsumerRequestQueueName, serviceConsumerRequestQueueAddress, serviceConsumerResponseQueueName, serviceConsumerResponseQueueAddress);
//        consumer.sendMessage("Request");


    }
}
