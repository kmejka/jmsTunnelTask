package pl.kmejka.test.jmsTunnel.gateway.http;

import com.google.common.net.MediaType;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kmejka.test.jmsTunnel.gateway.jms.GatewayJmsMessageProducer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by kmejka on 22.05.14.
 */
public class GatewayHttpMessageHandler extends AbstractHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GatewayHttpMessageHandler.class);

    private final GatewayJmsMessageProducer gatewayJmsMessageProducer;

    public GatewayHttpMessageHandler(final GatewayJmsMessageProducer gatewayJmsMessageProducer) {
        this.gatewayJmsMessageProducer = gatewayJmsMessageProducer;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("Handling message");
        StringWriter writer = new StringWriter();
        IOUtils.copy(request.getInputStream(), writer);
        String inputMessage = writer.toString();
        LOG.debug("Gateway received http message with content {}", inputMessage);
        baseRequest.setHandled(true);

        response.setContentType(MediaType.HTML_UTF_8.toString());
        response.setStatus(HttpStatus.SC_ACCEPTED);
        response.getWriter().println("");
        LOG.debug("Gateway responded with status: {}", HttpStatus.SC_ACCEPTED);

        LOG.debug("Forwarding message");
        this.gatewayJmsMessageProducer.sendMessage(inputMessage + "\tGATEWAY FORWARDING TO JMS\t");
    }
}
