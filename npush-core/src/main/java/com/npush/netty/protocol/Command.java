package com.npush.netty.protocol;

public enum Command {
    Heartbeat(1),
    Handshake(2),
    Login(3),
    Logout(4),
    Bind(5),
    Unbind(6),
    Kick(7),
    Unknown(-1);

    Command(int cmd) {
        this.cmd = (byte) cmd;
    }

    public final byte cmd;

    public static Command toCMD(byte b) {
        for (Command command : values()) {
            if (command.cmd == b) {
                return command;
            }
        }
        //if (b > 0 && b < values().length - 1) return values()[b - 1];
        return Unknown;
    }
}