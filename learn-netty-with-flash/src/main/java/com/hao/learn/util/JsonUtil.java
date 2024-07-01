package com.hao.learn.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class JsonUtil {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * 将对象转换为 JSON 字符串
     * @param obj 要转换的对象
     * @return JSON 字符串
     */
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * 将 JSON 字符串转换为对象
     * @param json JSON 字符串
     * @param clazz 要转换的对象类
     * @param <T> 对象类型
     * @return 转换后的对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    /**
     * 将 JSON 字符串转换为对象
     * @param json JSON 字符串
     * @param type 要转换的对象类型
     * @param <T> 对象类型
     * @return 转换后的对象
     */
    public static <T> T fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }

    /**
     * 将 JSON 字符串转换为 List
     * @param json JSON 字符串
     * @param clazz List 中对象的类
     * @param <T> List 中对象的类型
     * @return 转换后的 List
     */
    public static <T> List<T> fromJsonToList(String json, Class<T> clazz) {
        Type type = TypeToken.getParameterized(List.class, clazz).getType();
        return gson.fromJson(json, type);
    }

    /**
     * 将 JSON 字符串转换为 Map
     * @param json JSON 字符串
     * @param keyClazz Map 中 key 的类
     * @param valueClazz Map 中 value 的类
     * @param <K> key 的类型
     * @param <V> value 的类型
     * @return 转换后的 Map
     */
    public static <K, V> Map<K, V> fromJsonToMap(String json, Class<K> keyClazz, Class<V> valueClazz) {
        Type type = TypeToken.getParameterized(Map.class, keyClazz, valueClazz).getType();
        return gson.fromJson(json, type);
    }

    /**
     * 将 JSON 字符串格式化
     * @param json JSON 字符串
     * @return 格式化后的 JSON 字符串
     */
    public static String formatJson(String json) {
        JsonElement jsonElement = JsonParser.parseString(json);
        return gson.toJson(jsonElement);
    }

    /**
     * 将格式化的 JSON 字符串转换为紧凑格式
     * @param json JSON 字符串
     * @return 紧凑格式的 JSON 字符串
     */
    public static String compactJson(String json) {
        JsonElement jsonElement = JsonParser.parseString(json);
        Gson compactGson = new Gson();
        return compactGson.toJson(jsonElement);
    }

    /**
     * 将对象转换为 JSON 字符串的字节数组
     * @param obj 要转换的对象
     * @return JSON 字符串的字节数组
     */
    public static byte[] toJsonBytes(Object obj) {
        String json = gson.toJson(obj);
        return json.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 将 JSON 字符串的字节数组转换为对象
     * @param bytes JSON 字符串的字节数组
     * @param clazz 要转换的对象类
     * @param <T> 对象类型
     * @return 转换后的对象
     */
    public static <T> T fromJsonBytes(byte[] bytes, Class<T> clazz) {
        String json = new String(bytes, StandardCharsets.UTF_8);
        return gson.fromJson(json, clazz);
    }
}

