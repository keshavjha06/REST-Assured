package com.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FetchKeyValue {
    private static final List<String> keys = new ArrayList<>();
    private static final List<Object> values = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        File file = new File("src/test/resources/JsonSample.json");
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> array = mapper.readValue(file, new TypeReference<List<Map<String, Object>>>(){});
        for (Map<String, Object> treeMap : array) {
            find(treeMap);
        }
        System.out.println(keys);
        System.out.println(values);
    }

    private static void find(Map<String, Object> treeMap) {
        treeMap.forEach((key, value) -> {
            if (value instanceof LinkedHashMap) {
                find((LinkedHashMap) value);
            } else {
                values.add(value);
            }
            keys.add(key);
        });
    }

}
