package org.example.parsers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.interfaces.ModelParser;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class JsonModelParser<T> implements ModelParser<T> {

    private final ObjectMapper objectMapper;
    private final Class<T> tClass;

    public JsonModelParser(Class<T> tClass) {
        this.objectMapper = new ObjectMapper();
        this.tClass = tClass;
    }


    public T parse(String json) {
        try {
            if(!json.isEmpty()){
                return objectMapper.readValue(json, tClass);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
