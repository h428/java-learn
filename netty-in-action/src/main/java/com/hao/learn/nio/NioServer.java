package com.hao.learn.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) throws IOException {
        // 创建选择器
        Selector selector = Selector.open();

        // 打开服务器通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8080));
        serverSocketChannel.configureBlocking(false);

        // 将服务器通道注册到选择器上，监听接受连接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("服务器启动，监听端口: 8080");

        while (true) {
            // 阻塞直到有事件发生
            selector.select();

            // 获取所有触发事件的 SelectionKey 实例
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();

                try {
                    if (key.isAcceptable()) {
                        // 处理接受连接事件
                        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                        SocketChannel clientChannel = serverChannel.accept();
                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector, SelectionKey.OP_READ, new StringBuilder());
                        System.out.println("接受新连接: " + clientChannel.getRemoteAddress());
                    } else if (key.isReadable()) {
                        // 处理读事件
                        SocketChannel clientChannel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int bytesRead = clientChannel.read(buffer);

                        if (bytesRead == -1) {
                            clientChannel.close();
                            System.out.println("客户端关闭连接");
                        } else if (bytesRead > 0) {
                            buffer.flip();
                            StringBuilder sb = (StringBuilder) key.attachment();
                            while (buffer.hasRemaining()) {
                                char c = (char) buffer.get();
                                if (c == '\n') {
                                    System.out.println("接收到数据: " + sb.toString());
                                    clientChannel.write(ByteBuffer.wrap((sb.toString() + "\n").getBytes()));
                                    sb.setLength(0);
                                } else {
                                    sb.append(c);
                                }
                            }
                            buffer.clear();
                        }
                    }
                } catch (IOException e) {
                    key.cancel();
                    key.channel().close();
                    e.printStackTrace();
                }
                // 从已选择键集合中移除当前的 key
                iterator.remove();
            }
        }
    }
}
