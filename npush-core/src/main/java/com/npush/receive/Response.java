package com.npush.receive;

import com.npush.connection.Connection;
import com.npush.netty.protocol.Packet;

public class Response {
    private final Packet packet;
    private final Connection connection;

    public Response(Packet packet, Connection connection) {
        this.packet = packet;
        this.connection = connection;
    }

    public void send(byte[] body) {
        packet.body = body;
        connection.send(packet);
    }


    public void sendError(byte[] reson) {

    }
}