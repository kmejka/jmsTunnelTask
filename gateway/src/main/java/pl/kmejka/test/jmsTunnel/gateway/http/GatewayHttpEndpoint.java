package pl.kmejka.test.jmsTunnel.gateway.http;

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kmejka on 22.05.14.
 */
public class GatewayHttpEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(GatewayHttpEndpoint.class);

    private final Server server;

    public GatewayHttpEndpoint(final int port, final GatewayHttpMessageHandler msgHandler) throws Exception {
        LOG.debug("Constructing new gateway http on port {}", port);

        server = new Server(port);
        server.setHandler(msgHandler);

        server.start();
        server.join();
    }

    public void destroyEndpoint() {
        LOG.debug("Destroying gateway http");
        try {
            this.server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
