package org.example.interfaces;

import org.example.interfaces.ModelParser;

import java.util.List;

public interface ConvertModelStrategy<T> {
    String execute(List<T> modelList, String MODEL_NAME, ModelParser<T> modelParser);
}
