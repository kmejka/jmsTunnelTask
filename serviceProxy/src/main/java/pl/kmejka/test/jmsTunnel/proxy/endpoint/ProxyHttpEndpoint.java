package pl.kmejka.test.jmsTunnel.proxy.endpoint;

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
public class ProxyHttpEndpoint {
    private static final Logger LOG = LoggerFactory.getLogger(ProxyHttpEndpoint.class);

    private final Server server;

    public ProxyHttpEndpoint(final int port, final ProxyHttpMessageHandler messageHandler) throws Exception {
        LOG.debug("Constructing new proxy http endpoint on port {}", port);

        server = new Server(port);
        server.setHandler(messageHandler);

        server.start();
        server.join();
    }

    public void destroyEndpoint() {
        LOG.debug("Destroying proxy http");
        try {
            this.server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
