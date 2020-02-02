package com.npush.connection;

import com.npush.netty.protocol.Packet;
import io.netty.channel.Channel;

public interface Connection {

    String getId();

    void send(Packet packet);

    boolean isClosed();

    boolean isOpen();
    
    int getHbTimes();
    
    void close();

	boolean isConnected();

	boolean isEnable();

	void init(Channel channel);
    
}