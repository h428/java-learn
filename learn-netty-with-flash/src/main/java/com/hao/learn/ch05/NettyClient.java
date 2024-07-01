package com.hao.learn.ch05;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyClient {

    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(group) // 指定线程模型
                .channel(NioSocketChannel.class) // 指定 IO 类型为 NIO
                .handler(new ChannelInitializer<NioSocketChannel>() { // 定义 IO 处理业务逻辑
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {

                    }
                });

        // 建立连接
//        bootstrap.connect("baidu.com", 80).addListener(future -> {
//            if (future.isSuccess()) {
//                log.info("连接成功");
//            } else {
//                log.error("连接失败");
//            }
//        });

//        connect(bootstrap, "baidu.com", 80);
        connect(bootstrap, "localhost", 80, 1);
    }

    private static final int MAX_RETRY = 10;

    // 失败重连逻辑：指数退避算法
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("连接成功");
            } else if (retry == MAX_RETRY) {
                log.error("重连次数已经用完，放弃连接！");
            } else {
                int delay = 1 << (retry - 1); // 本次重连间隔
                log.info("连接失败，休息 {} 秒后准备第 {} 次重连...", delay, retry);
                bootstrap.config().group()
                        .schedule(() -> connect(bootstrap, host, port, retry + 1), delay, TimeUnit.SECONDS);
            }
        });
    }

}
