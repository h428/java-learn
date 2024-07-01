package com.hao.learn.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {
        ByteBuf buf = Unpooled.buffer(10, 20); // 创建一个初始容量为10字节，最大容量为20字节的缓冲区
        System.out.println("Max writable bytes: " + buf.maxWritableBytes()); // 输出20
        buf.writeBytes(new byte[5]); // 写入5字节
        System.out.println("Max writable bytes: " + buf.maxWritableBytes()); // 输出15
    }

}
