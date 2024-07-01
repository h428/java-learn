package com.hao.learn.nio.buffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * 利用 ByteBuffer 从文件读取数据
 */
@Slf4j
public class ReadFileDemo {


    public static void main(String[] args) {
        try (RandomAccessFile file = new RandomAccessFile("src/main/resources/data.txt", "rw")){
            FileChannel channel = file.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(10);

            do {
                int len = channel.read(buffer);
                log.debug("read bytes: {}", len);
                if (len == -1) {
                    break;
                }
                buffer.flip();
                while (buffer.hasRemaining()) {
                    log.debug("{}", (char)buffer.get());
                }
                buffer.clear();
            } while (true);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
