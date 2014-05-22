package pl.kmejka.test.jmsTunnel.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kmejka.test.jmsTunnel.consumer.request.RequestSender;
import pl.kmejka.test.jmsTunnel.consumer.response.ResponseMessageConsumer;

/**
 * Created by kmejka on 21.05.14.
 */
public class ServiceConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(ServiceConsumer.class);

    private final ResponseMessageConsumer responseMessageConsumer;
    private final RequestSender requestSender;

    public ServiceConsumer(final String requestQueueName, final String requestQueueAddress, final String responseQueueName, final String responseQueueAddress) {
        LOG.debug("Initializing service consumer");
        this.requestSender = new RequestSender(requestQueueName, requestQueueAddress);
        this.responseMessageConsumer = new ResponseMessageConsumer(responseQueueName, responseQueueAddress);
    }

    public void sendMessage(final String textMessage) {
        this.requestSender.sendMessage(textMessage);
    }

    public void destroyServiceConsumer() {
        LOG.debug("Destroying service consumer");
        this.requestSender.destroyRequestSender();
        this.responseMessageConsumer.destroyResponseMessageConsumer();
    }
}
