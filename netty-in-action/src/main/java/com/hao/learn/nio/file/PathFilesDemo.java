package com.hao.learn.nio.file;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.SneakyThrows;

public class PathFilesDemo {

    @SneakyThrows
    public static void main(String[] args) {
        Path source = Paths.get("src/main/java/../resources/data.txt");
        System.out.println(source);
        System.out.println(source.normalize());  // 规范化路径：去除掉 ../. 之类的东西
        System.out.println(source.toAbsolutePath());  // 转化为绝对路径模式
        System.out.println(Files.exists(source)); // 判断路径是否存在

        Path dir = Paths.get("src/main/resources/dir");
        if (!Files.exists(dir)) {
            Files.createDirectory(dir);
        }

        Path target = dir.resolve("data.txt");
        if (Files.exists(target)) {
            Files.delete(target);
        }
        Files.copy(source, target);
    }

}
