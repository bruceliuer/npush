package com.npush.connection;

public class ConnectionInfo {
    public final String os;
    public final String clientVer;
    public final String deviceId;
    public final String desKey;

    public ConnectionInfo(String os, String clientVer, String deviceId, String desKey) {
        this.os = os;
        this.clientVer = clientVer;
        this.deviceId = deviceId;
        this.desKey = desKey;
    }
}