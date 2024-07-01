package com.hao.learn.ch08;

public interface Serializer {

    Serializer DEFAULT = new JsonSerializer();

    /**
     * 序列化算法
     *
     * @return 返回序列化算法
     */
    byte getSerializerAlgorithm();

    /**
     * Java 对象转化成二进制数据
     *
     * @param object 对象
     * @return 字节数组
     */
    byte[] serialize(Object object);

    /**
     * 二进制数据转化为 Java 对象
     *
     * @param clazz 类型
     * @param bytes 数据
     * @param <T>   泛型
     * @return 对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);


}
