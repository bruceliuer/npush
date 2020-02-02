package com.npush.netty;

import com.npush.connection.Connection;
import com.npush.connection.ConnectionManager;
import com.npush.connection.NettyConnection;
import com.npush.netty.protocol.Packet;
import com.npush.receive.MessageReceiver;
import com.npush.receive.Request;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 连接处理
 */
public class ConnectionHandler extends ChannelHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionHandler.class);
    private MessageReceiver receiver = new MessageReceiver();


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ConnectionManager.INSTANCE.remove(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info(ctx.channel().remoteAddress()+",  channelActive");
        super.channelActive(ctx);

        Connection connection = new NettyConnection();
        connection.init(ctx.channel());
        ConnectionManager.INSTANCE.add(connection);
        System.out.println(ConnectionManager.INSTANCE.getConnectionIds());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info(ctx.channel().remoteAddress()+",  channelInactive");
        super.channelInactive(ctx);
    }

    /**
     * 收到消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info(ctx.channel().remoteAddress()+",  channelRead");

        Connection connection = ConnectionManager.INSTANCE.get(ctx.channel());
        receiver.onMessage(new Request((Packet) msg, connection));
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        logger.info(ctx.channel().remoteAddress()+",  disconnect");

        super.disconnect(ctx, promise);
        ConnectionManager.INSTANCE.remove(ctx.channel());
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        logger.info(ctx.channel().remoteAddress()+",  close");

        super.close(ctx, promise);
        ConnectionManager.INSTANCE.remove(ctx.channel());
    }
}
