package com.hao.learn.test;


import com.hao.learn.util.NettyUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws InterruptedException {
        int num = 0;
        log.info("成功连接服务器，准备发送心跳 {}", num);
        // 1. 获取数据
        ByteBuf buffer = getByteBuf(ctx, num);
        // 2. 写数据
        ctx.channel().writeAndFlush(buffer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // incrementAndWrite((ByteBuf) msg, ctx);
        NettyUtil.incrementAndWrite((ByteBuf) msg, ctx);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx, int num) {
        // 1. 获取二进制抽象 ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();

        // 2. 准备数据，指定字符串字符集为 UTF-8
        byte[] bytes = String.valueOf(num).getBytes(StandardCharsets.UTF_8);

        // 3. 填充数据到 ByteBuf
        buffer.writeBytes(bytes);

        return buffer;
    }
}
