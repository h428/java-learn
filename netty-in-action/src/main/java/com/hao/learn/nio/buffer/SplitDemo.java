package com.hao.learn.nio.buffer;

import static com.hao.learn.util.ByteBufferUtil.debugAll;

import java.nio.ByteBuffer;

/**
 * 处理粘包问题 demo
 */
public class SplitDemo {

    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("Hello,world\nI'm Michael\nHo\n".getBytes());
        split(source);
    }

    private static void split(ByteBuffer source) {
        source.flip();
        int oldLimit = source.limit();
        for (int i = 0; i < oldLimit; i++) {
            if (source.get(i) == '\n') {
                System.out.println(i);
                ByteBuffer target = ByteBuffer.allocate(i + 1 - source.position());
                source.limit(i + 1);
                target.put(source);
                debugAll(target);
                source.limit(oldLimit);
            }
        }
        source.compact();
    }

}
