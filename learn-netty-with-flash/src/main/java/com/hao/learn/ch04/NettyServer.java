package com.hao.learn.ch04;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServer {

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(); // 监听端口，接收新连接的线程
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(); // 负责数据读写的线程

        ServerBootstrap serverBootstrap = new ServerBootstrap(); // 创建配置引导类，支持链式编程
        serverBootstrap.group(bossGroup, workerGroup) // 配置两大线程组
                .channel(NioServerSocketChannel.class) // 指定为 NIO 模型
                .childHandler(new ChannelInitializer<NioSocketChannel>() { // 定义每个连接数据读写的处理逻辑
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {

                    }
                });
        bind(serverBootstrap, 5432); // 绑定端口
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    log.info("端口[{}]绑定成功", port);
                } else {
                    log.error("端口[{}]绑定失败", port);
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }

}
