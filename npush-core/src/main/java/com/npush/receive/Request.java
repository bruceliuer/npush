package com.npush.receive;

import com.npush.connection.Connection;
import com.npush.netty.protocol.Command;
import com.npush.netty.protocol.Packet;

public class Request {
    private final Command command;
    private final Packet message;
    private final Connection connection;

    public Request(Packet message, Connection connection) {
        this.message = message;
        this.connection = connection;
        this.command = Command.toCMD(message.command);
    }

    public Command getCommand() {
        return command;
    }

    public Packet getMessage() {
        return message;
    }

    public Connection getConnection() {
        return connection;
    }

    public Response getResponse() {
        Packet packet = new Packet();
        packet.command = message.command;
        packet.msgId = message.msgId;
        packet.version = message.version;
        return new Response(packet, connection);
    }
}