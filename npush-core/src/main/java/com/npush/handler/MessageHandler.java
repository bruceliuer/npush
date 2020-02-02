package com.npush.handler;

import com.npush.receive.Request;

public interface MessageHandler {
    void handle(Request request);
}