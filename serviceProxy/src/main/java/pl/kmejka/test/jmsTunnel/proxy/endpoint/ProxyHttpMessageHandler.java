package pl.kmejka.test.jmsTunnel.proxy.endpoint;

import com.google.common.net.MediaType;
import org.apache.commons.httpclient.HttpStatus;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.kmejka.test.jmsTunnel.proxy.jms.ProxyJmsMessageProducer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by kmejka on 22.05.14.
 */
public class ProxyHttpMessageHandler extends AbstractHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ProxyHttpMessageHandler.class);

    private final ProxyJmsMessageProducer proxyJmsMessageProducer;

    public ProxyHttpMessageHandler(final ProxyJmsMessageProducer proxyJmsMessageProducer) {
        this.proxyJmsMessageProducer = proxyJmsMessageProducer;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOG.debug("Handling message");
        String inputMessage = request.getParameter("msg");
        LOG.debug("Proxy received http message with content {}", inputMessage);
        baseRequest.setHandled(true);

        response.setContentType(MediaType.HTML_UTF_8.toString());
        response.setStatus(HttpStatus.SC_ACCEPTED);
        response.getWriter().println("");
        LOG.debug("Gateway responded with status: {}", HttpStatus.SC_ACCEPTED);

        LOG.debug("Forwarding message");
        this.proxyJmsMessageProducer.sendMessage(inputMessage + "\tPROXY FORWARDING TO JMS\t");
    }
}
