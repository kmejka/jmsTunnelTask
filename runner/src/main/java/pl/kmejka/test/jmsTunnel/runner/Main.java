package pl.kmejka.test.jmsTunnel.runner;

import pl.kmejka.test.jmsTunnel.consumer.MsgConsumer;
import pl.kmejka.test.jmsTunnel.producer.MsgProducer;
import pl.kmejka.test.jmsTunnel.server.QueueBroker;

/**
 * Created by kmejka on 20.05.14.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        String queueAddressServer = "tcp://localhost:12345";
        String queueAddressConsumers = "http://localhost:12345";
        String queueName = "fred";

        QueueBroker queueBroker = new QueueBroker();
        queueBroker.startQueue(queueName, queueAddressServer);

        MsgProducer msgProducer = new MsgProducer();
        msgProducer.sendMessage("Chcieliście wydymać freda, to teraz fred wydyma was", queueName, queueAddressConsumers);

        MsgConsumer msgConsumer = new MsgConsumer();
        msgConsumer.startMessageConsumer(1000, queueName, queueAddressConsumers);

        queueBroker.stopQueue();
    }
}
