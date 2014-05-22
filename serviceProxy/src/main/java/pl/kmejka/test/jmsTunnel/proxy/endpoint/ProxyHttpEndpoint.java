package pl.kmejka.test.jmsTunnel.proxy.endpoint;

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
