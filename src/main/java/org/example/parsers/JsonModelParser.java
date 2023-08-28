package org.example.parsers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.interfaces.ModelParser;

public class
JsonModelParser<T> implements ModelParser<T> {

    private final ObjectMapper objectMapper;
    private final Class<T> tClass;

    public JsonModelParser(Class<T> tClass) {
        this.objectMapper = new ObjectMapper();
        this.tClass = tClass;
        this.objectMapper.findAndRegisterModules();
    }


    public T toModel(String json) {
        try {
            if(!json.isEmpty()){
                return objectMapper.readValue(json, tClass);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String toJSON(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
