package com.hao.learn.fire;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("接入新的传感器");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;

        if (buf.readableBytes() < 16) {
            log.warn("接收到的数据长度不足");
            return;
        }

        // 读取各字段数据
        int magicNumber = buf.readUnsignedShort();
        int nodeId = buf.readUnsignedShort();
        int co2Concentration = buf.readUnsignedShort();
        int temperature = buf.readUnsignedShort();
        int smokeConcentration = buf.readUnsignedShort();
        int x = buf.readUnsignedShort();
        int y = buf.readUnsignedShort();
        int z = buf.readUnsignedShort();

        // 整合日志信息到一行
        log.info("读取到数据: 魔数: {} 节点编号: {} CO2 浓度: {} 温度: {} 烟雾浓度: {} 位置: (x: {}, y: {}, z: {})",
                magicNumber, nodeId, co2Concentration, temperature, smokeConcentration, x, y, z);

        // Create a CSV formatted line
        String csvLine = String.format("%d,%d,%d,%d,%d,%d,%d",
                nodeId, co2Concentration, temperature, smokeConcentration, x, y, z);

        // Write the CSV line to the fi
        try {
            FileWriterManager.writeData(csvLine);
        } catch (IOException e) {
            log.error("Error writing data to file", e);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("Error occurred during processing", cause);
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }
}
