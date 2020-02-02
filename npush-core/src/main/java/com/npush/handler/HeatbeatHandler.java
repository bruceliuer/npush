package com.npush.handler;

import com.alibaba.fastjson.JSON;
import com.npush.netty.protocol.Packet;
import com.npush.receive.Request;

public class HeatbeatHandler implements MessageHandler {
    @Override
    public void handle(Request request) {
        Packet packet = request.getMessage();
        System.out.println(" heatbeat msg: " + JSON.toJSONString(packet));

    }
}
