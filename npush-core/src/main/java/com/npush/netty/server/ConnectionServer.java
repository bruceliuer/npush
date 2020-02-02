package com.npush.netty.server;

import com.npush.netty.ConnectionHandler;
import com.npush.netty.encoder.PacketDecoder;
import com.npush.netty.encoder.PacketEncoder;
import com.npush.receive.MessageReceiver;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 长连接服务
 */
public class ConnectionServer {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionServer.class);

    private int port;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private final AtomicBoolean startFlag = new AtomicBoolean(false);


    public ConnectionServer(int port) {
        this.port = port;
    }

    public void start() {
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();

        try {

            /**
             * ServerBootstrap 是一个启动NIO服务的辅助启动类
             * 你可以在这个服务中直接使用Channel
             */
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);

            MessageReceiver receiver = new MessageReceiver();

            final ConnectionHandler connectionHandler = new ConnectionHandler(receiver);

            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new PacketDecoder());
                    ch.pipeline().addLast(PacketEncoder.INSTANCE);
                    ch.pipeline().addLast(connectionHandler);
                }
            });

            /***
             * 绑定端口并启动去接收进来的连接
             */
            ChannelFuture f = b.bind(port).sync();

            logger.info("server start ok on:"+port);

            /**
             * 这里会一直等待，直到socket被关闭
             */
            f.channel().closeFuture().sync();

        } catch (Exception e) {
            stop();
        }

    }



    public void stop() {
        logger.info("netty server stop now");
        this.startFlag.set(false);
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
    }
}
