package pl.kmejka.test.jmsTunnel.gateway.http;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kmejka on 22.05.14.
 */
public class GatewayHttpMessageSender {
    private static final Logger LOG = LoggerFactory.getLogger(GatewayHttpMessageSender.class);

    //    private Connection connection;
//    private Session session;
//    private MessageProducer producer;
    private final CloseableHttpClient httpClient;
    private final String sendToEndpoint;

    public GatewayHttpMessageSender(final String sendToEndpoint) {
        LOG.debug("Starting gateway response sender with sendToEndpoint: {}", sendToEndpoint);

        httpClient = HttpClients.createDefault();
        this.sendToEndpoint = sendToEndpoint;
//
//        try {
//            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(sendToQueueAddress);
//            this.connection = connectionFactory.createConnection();
//            connection.start();
//
//            this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            Destination destination = session.createQueue(sendToQueueName);
//
//            this.producer = session.createProducer(destination);
//
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
    }

    public void destroyResponseSender() {
        LOG.debug("Closing gateway response sender");
        try {
            if (httpClient != null) {
                httpClient.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(final String textMessage) {
        LOG.debug("Gateway sending message: {}", textMessage);
        HttpPost post = new HttpPost(sendToEndpoint);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("msg", textMessage));
        CloseableHttpResponse response = null;
        try {
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpClient.execute(post);
            LOG.debug("Gateway received response for sending message, status: {}", response.getStatusLine());
            EntityUtils.consume(response.getEntity());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        try {
//            TextMessage message = session.createTextMessage();
//            message.setText(textMessage);
//            this.producer.send(message);
//        } catch (JMSException e) {
//            e.printStackTrace();
//        }
    }

}
