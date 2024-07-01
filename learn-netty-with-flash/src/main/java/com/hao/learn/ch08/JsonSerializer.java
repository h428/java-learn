package com.hao.learn.ch08;

import com.hao.learn.util.JsonUtil;

public class JsonSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JsonUtil.toJsonBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JsonUtil.fromJsonBytes(bytes, clazz);
    }
}
