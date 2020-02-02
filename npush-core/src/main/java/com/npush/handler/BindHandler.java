package com.npush.handler;

import com.alibaba.fastjson.JSON;
import com.npush.netty.protocol.Packet;
import com.npush.receive.Request;

public class BindHandler implements MessageHandler {
    @Override
    public void handle(Request request) {
        Packet packet = request.getMessage();
        System.out.println("receive client msg: " + JSON.toJSONString(request));

    }
}
