package com.npush.server;

import com.npush.netty.server.ConnectionServer;
import org.junit.Test;

public class ConnectionServerTest {

    @Test
    public void testStop() throws Exception {

    }

    @Test
    public void testStart() throws Exception {
        ConnectionServer server = new ConnectionServer(3000);
        server.start();
    }
}