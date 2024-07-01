package com.hao.learn.ch09.packet;

import com.hao.learn.ch08.Command;
import com.hao.learn.ch08.Packet;
import lombok.Data;

@Data
public class LoginResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
