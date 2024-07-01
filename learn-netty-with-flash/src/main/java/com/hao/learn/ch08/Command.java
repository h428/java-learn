package com.hao.learn.ch08;

import com.hao.learn.ch09.packet.LoginResponsePacket;

public interface Command {

    Byte LOGIN_REQUEST = 1;
    Byte LOGIN_RESPONSE = 2;

    static Class<? extends Packet> getRequestType(byte command) {
        if (Command.LOGIN_REQUEST.equals(command)) {
            return LoginRequestPacket.class;
        }

        if (Command.LOGIN_RESPONSE.equals(command)) {
            return LoginResponsePacket.class;
        }

        return null;
    }

}
