package com.hao.learn.ch08;


public interface SerializerAlgorithm {

    /**
     * JSON 序列化标识
     */
    byte JSON = 1;

    static Serializer getSerializer(byte serializeAlgorithm) {
        if (SerializerAlgorithm.JSON == serializeAlgorithm) {
            return new JsonSerializer(); // 应该用一个 ConcurrentHashMap 缓存起来，但这里就直接 new 了
        }

        return null;
    }


}
