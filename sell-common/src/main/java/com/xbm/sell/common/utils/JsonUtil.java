package com.xbm.sell.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();


    public static String toJson(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object fromJson(String jsonString,Class classType){
        try {
            return objectMapper.readValue(jsonString,classType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object fromJson(String jsonString, TypeReference typeReference){
        try {
            return objectMapper.readValue(jsonString,typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
