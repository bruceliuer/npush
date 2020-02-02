package com.npush.util;

import java.nio.charset.Charset;

public interface Constants {
    Charset UTF_8 = Charset.forName("UTF-8");
    int MAX_PACKET_SIZE = 1024;
    int HEADER_LEN = 13;
    byte MAGIC_NUM1 = (byte) 33;
    byte MAGIC_NUM2 = (byte) 99;
    long TIME_DELAY = 58L;
    
    String JVM_LOG_PATH = "/opt/";
    
    int THREAD_QUEUE_SIZE = 10000;
    int MIN_POOL_SIZE = 50;
    int MAX_POOL_SIZE = 500;
}