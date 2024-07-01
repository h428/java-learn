package com.hao.learn.nio.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileChannelTransferDemo {

    public static void main(String[] args) {
        try (
                FileChannel from = new FileInputStream("src/main/resources/data.txt").getChannel();
                FileChannel to = new FileOutputStream("src/main/resources/to.txt").getChannel()
        ) {
            long size = from.size();
            for (long left = size; left > 0; ) {
                System.out.println("position: " + (size - left) + " left:" + left);
                left -= from.transferTo((size - left), left, to);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
