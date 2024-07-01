package com.hao.learn.ch08;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister.Pack;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class PacketCodeC {

    private static final int MAGIC_NUMBER = 0x12345678;

    private static final Serializer SERIALIZER = Serializer.DEFAULT;

    public static final PacketCodeC INSTANCE = new PacketCodeC();

    public ByteBuf encode(Packet packet) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        byte[] bytes = SERIALIZER.serialize(packet); // 协议的数据部分

        // 实际编码过程：根据协议设置内容
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(SERIALIZER.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public ByteBuf encode(ByteBufAllocator bufAllocator, Packet packet) {
        ByteBuf byteBuf = bufAllocator.buffer();
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        byteBuf.writeInt(MAGIC_NUMBER); // 写入魔数
        byteBuf.writeByte(packet.getVersion()); // 写入协议版本
        byteBuf.writeByte(SERIALIZER.getSerializerAlgorithm()); // 写入序列化算法标识
        byteBuf.writeByte(packet.getCommand()); // 写入指令
        byteBuf.writeInt(bytes.length); // 写入数据长度
        byteBuf.writeBytes(bytes); // 写入具体数据

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        byteBuf.skipBytes(4); // 跳过魔数
        byteBuf.skipBytes(1); // 跳过版本号
        byte serializeAlgorithm = byteBuf.readByte(); // 序列化算法标识
        byte command = byteBuf.readByte(); // 指令
        int length = byteBuf.readInt(); // 数据包长度
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = Command.getRequestType(command);
        Serializer serializer = SerializerAlgorithm.getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }



}
