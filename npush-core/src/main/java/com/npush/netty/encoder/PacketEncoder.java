package com.npush.netty.encoder;

import com.npush.netty.protocol.Packet;
import com.npush.util.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 自定义
 * magic(2)+length(4)+cmd(1)+version(1)+flags(1)+msgId(4)+body(n)
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    public static final PacketEncoder INSTANCE = new PacketEncoder();

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf out) throws Exception {
        out.writeByte(Constants.MAGIC_NUM1);
        out.writeByte(Constants.MAGIC_NUM2);
        out.writeInt(packet.getBodyLength());
        out.writeByte(packet.command);
        out.writeByte(packet.flags);
        out.writeByte(packet.version);
        out.writeInt(packet.msgId);
        if (packet.getBodyLength() > 0) {
            out.writeBytes(packet.body);
        }
    }
}
