package com.npush.connection;

import io.netty.channel.Channel;
import io.netty.util.internal.chmv8.ConcurrentHashMapV8;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

public class ConnectionManager {
    public static final ConnectionManager INSTANCE = new ConnectionManager();

    //可能会有20w的链接数
    private final ConcurrentMap<String, Connection> connections = new ConcurrentHashMapV8<String, Connection>();


    public void add(Connection connection) {
        connections.putIfAbsent(connection.getId(), connection);
    }

    public void remove(Connection connection) {
        connections.remove(connection.getId());
    }

    public void remove(Channel channel){
        connections.remove(channel.id().asLongText());
    }

    public Connection get(final Channel channel){
        return connections.get(channel.id().asLongText());
    }

    public Connection get(final String channelId) throws ExecutionException {
        return connections.get(channelId);
    }

    public List<String> getConnectionIds(){
        return new ArrayList<String>(connections.keySet());
    }

    public List<Connection> getConnections(){
        return new ArrayList<Connection>(connections.values());
    }
}
