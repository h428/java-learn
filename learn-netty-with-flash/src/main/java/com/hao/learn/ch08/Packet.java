package com.hao.learn.ch08;

import lombok.Data;

@Data
public abstract class Packet {

    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 指令
     * @return 指令
     */
    public abstract Byte getCommand();

}
