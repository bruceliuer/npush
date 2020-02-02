package com.npush.client;

import com.npush.netty.protocol.Command;
import com.npush.netty.protocol.Packet;
import com.npush.util.Constants;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientHandler extends ChannelHandlerAdapter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        Packet packet = new Packet();
        packet.command = Command.Bind.cmd;
        packet.version = 0;
        packet.flags = 0;
        packet.msgId = 1;
        packet.body = "hello word".getBytes(Constants.UTF_8);
        ctx.writeAndFlush(packet);
        System.out.println("client connect");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("client channelInactive");
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        System.out.println(msg);
        logger.debug("channelRead msg=" + msg);
    }
}