package com.hao.learn.ch07;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ByteBufTest {

    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);

        print("allocate ByteBuf(9, 100", buffer);

        // write 方法改变写指针，写完之后指针未到 capacity 的时候，buffer 仍然可写
        buffer.writeBytes(new byte[] {1, 2, 3, 4});
        print("writeBytes(1, 2, 3, 4)", buffer);

        // int 4 个字节
        buffer.writeInt(12);
        print("writeInt(12)", buffer);

        // 再写一个字节，buffer不可写
        buffer.writeBytes(new byte[]{5});
        print("writeBytes(5)", buffer);

        // 再写，可以扩容，则扩容
        buffer.writeBytes(new byte[]{6});
        print("writeBytes(6)", buffer);

        // get 不改变读写指针
        System.out.println("getByte(3) return " + buffer.getByte(3));
        System.out.println("getShort(3) return " + buffer.getShort(3));
        System.out.println("getInt(3) return " + buffer.getInt(3));
        print("getByte()", buffer);

        // set 不改变读写指针
        buffer.setByte(buffer.readableBytes() - 1, 0);
        print("setByte()", buffer);

        // read方法改变读写指针
        byte[] dst = new byte[buffer.readableBytes()];
        buffer.readBytes(dst);
        print("readBytes(" + dst.length + ")", buffer);

        // 打印读取的数据
        for (byte b : dst) {
            System.out.println(b);
        }

    }

    private static void print(String action, ByteBuf buffer) {
        System.out.println("after ===========" + action + "============");
        System.out.println("capacity(): " + buffer.capacity());
        System.out.println("maxCapacity(): " + buffer.maxCapacity());
        System.out.println("readerIndex(): " + buffer.readerIndex());
        System.out.println("readableBytes(): " + buffer.readableBytes());
        System.out.println("isReadable(): " + buffer.isReadable());
        System.out.println("writerIndex(): " + buffer.writerIndex());
        System.out.println("writableBytes(): " + buffer.writableBytes());
        System.out.println("isWritable(): " + buffer.isWritable());
        System.out.println("maxWritableBytes(): " + buffer.maxWritableBytes());
        System.out.println();
    }

}
