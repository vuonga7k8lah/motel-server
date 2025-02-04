package com.vuongkma.blogs.helpers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseFormat {
    public static Map<String, String> build(String message) {
        Map<String, String> map = new HashMap<>();
        map.put("message", message);

        return map;
    }

    public static <T> Map<String, Object> build(T data, String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("info", data);

        return map;
    }


    public static Map<String, Object> build(Long id, String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("message", message);

        return map;
    }

    public static <T> Map<String, Object> build(List<T> data, String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("info", data);
        map.put("message", message);

        return map;
    }
}
