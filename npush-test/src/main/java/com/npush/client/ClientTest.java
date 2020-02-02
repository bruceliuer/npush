package com.npush.client;

import com.npush.connection.ConnectionManager;
import com.npush.netty.encoder.PacketDecoder;
import com.npush.netty.encoder.PacketEncoder;
import com.npush.netty.protocol.Command;
import com.npush.netty.protocol.Packet;
import com.npush.task.NettySharedHolder;
import com.npush.util.Constants;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by ohun on 2015/12/24.
 */
public class ClientTest {

    private static final Logger log = LoggerFactory.getLogger(ClientTest.class);

    @Test
    public void testClient() {
        String host = "127.0.0.1";
        int port = 3000;
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new PacketDecoder());
                    ch.pipeline().addLast(PacketEncoder.INSTANCE);
                    ch.pipeline().addLast(new ClientHandler());
                }
            });
            ChannelFuture future = b.connect(host, port).sync(); // (5)
            if (future.awaitUninterruptibly(4000) && future.isSuccess()) {
                startHeartBeat(future.channel());
                future.channel().closeFuture().sync();
            } else {
                future.cancel(true);
                future.channel().close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

    public void startHeartBeat(final Channel channel) {
        NettySharedHolder.timer.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                try {
                    final Packet packet = buildHeartBeat();
                    ChannelFuture channelFuture = channel.writeAndFlush(packet);
                    channelFuture.addListener(new ChannelFutureListener() {

                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            if (!future.isSuccess()) {
                                if (!channel.isActive()) {
                                    log.warn("client send msg false:" + channel.remoteAddress().toString() + "," + packet + ",channel is not active");
                                    ConnectionManager.INSTANCE.remove(channel);
                                }
                                log.warn("client send msg false:" + channel.remoteAddress().toString() + "," + packet);
                            } else {
                                log.debug("client send msg success:" + channel.remoteAddress().toString() + "," + packet);
                            }
                        }
                    });
                } finally {
                    if (channel.isActive()) {
                        NettySharedHolder.timer.newTimeout(this, Constants.TIME_DELAY, TimeUnit.SECONDS);
                    }
                }
            }
        }, Constants.TIME_DELAY, TimeUnit.SECONDS);
    }

    private static Packet buildHeartBeat() {
        Packet packet = new Packet();
        packet.command = Command.Heartbeat.cmd;
        return packet;
    }

}