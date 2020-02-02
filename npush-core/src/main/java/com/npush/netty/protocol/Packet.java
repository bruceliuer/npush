package com.npush.netty.protocol;

import java.io.Serializable;
import java.util.Arrays;

public class Packet implements Serializable {

    public byte command;
    public byte version;
    public byte flags;
    public int msgId;
    public byte[] body;


    public int getBodyLength() {
        return body == null ? 0 : body.length;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "command=" + command +
                ", version=" + version +
                ", flags=" + flags +
                ", msgId=" + msgId +
                ", body=" + Arrays.toString(body) +
                '}';
    }
}
