package pl.kmejka.test.jmsTunnel.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.activemq.broker.BrokerService;

/**
 * Created by kmejka on 20.05.14.
 */
public class QueueBroker {

    private static final Logger LOG = LoggerFactory.getLogger(QueueBroker.class);

    final BrokerService broker;

    public QueueBroker() {
        this.broker = new BrokerService();
    }

    public void startQueue(final String queueName, final String queueAddress) {
        LOG.debug("Starting queue broker with name: {} and queueAddress {}", queueName, queueAddress);
        broker.setBrokerName(queueName);
        try {
            broker.addConnector(queueAddress);
            broker.setPersistent(false);
            broker.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOG.debug("Queue broker started");
    }

    public void stopQueue() {
        LOG.debug("Stopping queue broker");
        try {
            this.broker.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOG.debug("Queue broker stopped");
    }

}
