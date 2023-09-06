package org.example.handlers.strategy;

import org.example.interfaces.ConvertModelStrategy;
import org.example.interfaces.ModelParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManyModelsToJson<T> implements ConvertModelStrategy<T> {
    @Override
    public String execute(List<T> modelList, String MODEL_NAME, ModelParser<T> modelParser) {
        Map<String, List<T>> resultMap = new HashMap<>();
        resultMap.put(MODEL_NAME, modelList);
        return modelParser.toJSON(resultMap);
    }
}
