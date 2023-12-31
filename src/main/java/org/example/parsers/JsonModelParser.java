package org.example.parsers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exceptions.parsers.EmptyJsonException;
import org.example.exceptions.parsers.JsonParseException;
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
            else {
                throw new EmptyJsonException("Json is empty");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new JsonParseException("Json parsing failed, check input data");
        }
    }

    public String toJSON(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new JsonParseException("Unable to convert Object -> JSON");
        }
    }
}
