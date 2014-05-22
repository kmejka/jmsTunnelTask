package pl.kmejka.test.jmsTunnel.producerGateway.endpoint;

import com.google.common.net.MediaType;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by kmejka on 22.05.14.
 */
public class GatewayEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(GatewayEndpoint.class);

    private final Server server;

    public GatewayEndpoint(final int port) throws Exception {
        LOG.debug("Constructing new gateway endpoint on port {}", port);

        server = new Server(port);
        server.setHandler(new AbstractHandler() {

            @Override
            public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
                LOG.debug("Handling message");
                StringWriter writer = new StringWriter();
                IOUtils.copy(request.getInputStream(), writer);
                LOG.debug("Gateway received http message with content {}", writer.toString());
                baseRequest.setHandled(true);
                response.setContentType(MediaType.HTML_UTF_8.toString());
                response.setStatus(HttpStatus.SC_ACCEPTED);
                response.getWriter().println("<h1>Hello World</h1>");
                LOG.debug("Gateway responded with status: {}", HttpStatus.SC_ACCEPTED);
            }
        });

        server.start();
        server.join();
    }

    public void destroyEndpoint() {
        LOG.debug("Destroying gateway endpoint");
        try {
            this.server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
