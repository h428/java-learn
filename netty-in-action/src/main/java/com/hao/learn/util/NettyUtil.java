package com.hao.learn.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyUtil {

    public static void printReceiveMessage(ByteBuf byteBuf) {
        log.info("接收到数据 {}", byteBuf.toString(StandardCharsets.UTF_8));
    }

    public static void incrementAndWrite(ByteBuf byteBuf, ChannelHandlerContext context) {
        String str = byteBuf.toString(StandardCharsets.UTF_8);
        int num = Integer.parseInt(str) + 1;
        byte[] bytes = Integer.toString(num).getBytes(StandardCharsets.UTF_8);
        ByteBuf buffer = context.alloc().buffer();
        log.info("接收到 {}，休眠一秒后，准备回写 {}", num - 1, num);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        buffer.writeBytes(bytes);
        context.channel().writeAndFlush(buffer);
    }

    public static void main(String[] args) {
        log.info("hello");
    }

}
