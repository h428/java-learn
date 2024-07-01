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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioServerWithThreadPool {
    private static final int PORT = 8080;
    private static final int BUFFER_SIZE = 1024;
    private static final int THREAD_POOL_SIZE = 10;

    public static void main(String[] args) throws IOException {
        // 创建 ServerSocketChannel 并绑定端口
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(PORT));
        serverChannel.configureBlocking(false);

        // 创建 Selector 并将 ServerSocketChannel 注册到 Selector 上，监听 OP_ACCEPT 事件
        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 创建线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        while (true) {
            // 阻塞直到有事件发生
            selector.select();

            // 获取所有已准备的选择键
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();

                if (key.isAcceptable()) {
                    // 处理新连接
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel clientChannel = server.accept();
                    clientChannel.configureBlocking(false);
                    clientChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("Accepted new connection from " + clientChannel.getRemoteAddress());
                } else if (key.isReadable()) {
                    // 将读事件交给线程池中的线程处理
                    threadPool.submit(new ClientHandler(key));
                }

                // 移除已处理的键
                keyIterator.remove();
            }
        }
    }

    private static class ClientHandler implements Runnable {
        private final SelectionKey key;

        public ClientHandler(SelectionKey key) {
            this.key = key;
        }

        @Override
        public void run() {
            try {
                SocketChannel clientChannel = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
                int bytesRead = clientChannel.read(buffer);
                String name = Thread.currentThread().getName();

                if (bytesRead == -1) {
                    clientChannel.close();
                } else {
                    buffer.flip();
                    String receivedData = new String(buffer.array(), 0, buffer.limit());
                    System.out.println(name + " received: " + receivedData);

                    // Echo the data back to the client
                    buffer.clear();
                    buffer.put(("Echo: " + receivedData).getBytes());
                    buffer.flip();
                    clientChannel.write(buffer);
                }
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    key.channel().close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
