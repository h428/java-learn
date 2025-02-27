package com.hao.learn.ch02;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IOClient {

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                while (true) {
                    try {
                        byte[] bytes = (new Date() + ": hello world").getBytes();
                        socket.getOutputStream().write(bytes);
                        Thread.sleep(2000);
                    } catch (Exception e) {

                    }
                }
            } catch (IOException e) {

            }
        }).start();
    }

}
