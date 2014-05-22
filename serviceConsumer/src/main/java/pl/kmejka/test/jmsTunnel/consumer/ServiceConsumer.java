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

    public static void main(String[] args) {
        LOG.debug("Starting service consumer");

        final String requestQueueName = "proxyRequestQueue";
        final String requestQueueAddress = "tcp://localhost:20000";
        final String responseQueueName = "proxyResponseQueue";
        final String responseQueueAddress = "tcp://localhost:20001";

        ResponseMessageConsumer responseMessageConsumer = new ResponseMessageConsumer(responseQueueName, responseQueueAddress);
        RequestSender requestSender = new RequestSender(requestQueueName, requestQueueAddress);

        requestSender.sendMessage("\t~~ORIGINAL MESSAGE FROM SERVICE CONSUMER~~\t");
    }
}
