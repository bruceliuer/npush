package com.npush.handler;

import com.alibaba.fastjson.JSON;
import com.npush.netty.protocol.Command;
import com.npush.netty.protocol.Packet;
import com.npush.receive.Request;
import com.npush.router.RouterCenter;
import com.npush.util.Constants;

public class BindHandler implements MessageHandler {
    @Override
    public void handle(Request request) {
        Packet packet = request.getMessage();
        System.out.println("receive client msg: " + JSON.toJSONString(packet));

        byte[] body = packet.body;
        String userIdStr = new String(body, Constants.UTF_8);
        long userId = Long.parseLong(userIdStr);

        boolean success = false;
        if (packet.command == Command.Bind.cmd) {
            success = RouterCenter.INSTANCE.publish(userId, request.getConnection());
        } else {
            success = RouterCenter.INSTANCE.unPublish(userId);
        }
        request.getResponse().send(new byte[]{success ? (byte) 1 : (byte) 0});
    }
}
