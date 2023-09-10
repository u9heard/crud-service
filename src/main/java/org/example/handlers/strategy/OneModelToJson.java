package org.example.handlers.strategy;

import org.example.interfaces.ConvertModelStrategy;
import org.example.interfaces.ModelParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OneModelToJson<T> implements ConvertModelStrategy<T> {
    @Override
    public String execute(List<T> modelList, String MODEL_NAME, ModelParser<T> modelParser) {
        return modelParser.toJSON(Map.of(MODEL_NAME, modelList.get(0)));
    }
}
