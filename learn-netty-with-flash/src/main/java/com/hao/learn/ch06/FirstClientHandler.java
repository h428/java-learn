package com.hao.learn.ch06;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 获取数据
        ByteBuf buffer = getByteBuf(ctx);
        log.info("客户端成功连接到服务器，准备写出数据：{}", buffer.toString(Charset.defaultCharset()));

        // 写数据
        ctx.channel().writeAndFlush(buffer);
    }

    // 准备一次数据
    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        // 分配一个 ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();
        // 准备数据，字符串以 UTF-8 编码的字节数组
        byte[] bytes = "你好，Netty".getBytes(Charset.defaultCharset());
        // 填充到 ByteBuf
        buffer.writeBytes(bytes);

        return buffer;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        log.info("客户端读取到服务端响应的数据：{}", byteBuf.toString(Charset.defaultCharset()));
    }
}
