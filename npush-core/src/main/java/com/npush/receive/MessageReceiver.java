package com.npush.receive;

import com.npush.handler.BindHandler;
import com.npush.handler.HeatbeatHandler;
import com.npush.handler.MessageHandler;

public class MessageReceiver {

    public static final MessageHandler BIND_HANDLER = new BindHandler();

    public static final MessageHandler HEATBEAT_HANDLER = new HeatbeatHandler();

    public void onMessage(Request request) {
        switch (request.getCommand()) {
            case Heartbeat:
                HEATBEAT_HANDLER.handle(request);
                break;
            case Handshake:
                break;
            case Login:
                break;
            case Bind:
                BIND_HANDLER.handle(request);
                break;
            case Kick:
                break;
            case Unknown:
                break;
        }
    }
}