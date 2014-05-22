package pl.kmejka.test.jmsTunnel.runner;

import pl.kmejka.test.jmsTunnel.consumer.ServiceConsumer;
import pl.kmejka.test.jmsTunnel.producer.ServiceProducer;

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


        String serviceConsumerRequestQueueName = "requestQueue";
        String serviceConsumerRequestQueueAddress = "tcp://localhost:11111";
        String serviceConsumerResponseQueueName = "responseQueue";
        String serviceConsumerResponseQueueAddress = "tcp://localhost:11112";

        ServiceConsumer consumer = new ServiceConsumer(serviceConsumerRequestQueueName, serviceConsumerRequestQueueAddress, serviceConsumerResponseQueueName, serviceConsumerResponseQueueAddress);
        consumer.sendMessage("Request");


//        consumer.destroyServiceConsumer();
//        producer.destroyServiceProducer();
//        String queueAddressServer = "http://localhost:12345";
//        String queueAddressProducer = "http://localhost:12345";
//        String queueAddressConsumer = "http://localhost:12345";
//        String queueName = "fred";
//
//        QueueBroker queueBroker = new QueueBroker();
//        queueBroker.startQueue(queueName, queueAddressServer);
//
//        MsgProducer msgProducer = new MsgProducer();
//        msgProducer.sendMessage("Chcieliście wydymać freda, to teraz fred wydyma was", queueName, queueAddressProducer);
//
//        MsgConsumer msgConsumer = new MsgConsumer();
//        msgConsumer.startMessageConsumer(1000, queueName, queueAddressConsumer);
//
//        queueBroker.stopQueue();
    }
}
