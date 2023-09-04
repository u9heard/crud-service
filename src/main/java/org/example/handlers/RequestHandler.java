package org.example.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interfaces.ModelParser;
import org.example.interfaces.ModelValidator;
import org.example.interfaces.PathParser;
import org.example.interfaces.StorageService;
import org.example.parsers.LongPathParser;
import org.example.parsers.RequestBodyParser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHandler<T> {

    protected final StorageService<T> storageService;
    protected final ModelParser<T> modelParser;
    protected final ModelValidator<T> modelValidator;

    protected final String MODEL_NAME;

    public RequestHandler(StorageService<T> storageService, ModelParser<T> modelParser, ModelValidator<T> modelValidator, String MODEL_NAME) {
        this.storageService = storageService;
        this.modelParser = modelParser;
        this.modelValidator = modelValidator;
        this.MODEL_NAME = MODEL_NAME;
    }

    public void handleGet(HttpServletRequest req, HttpServletResponse resp){
        resp.setContentType("application/json");
        System.out.println("fff");
        List<T> resultList = getModelByRequest(req);
        System.out.println("ddd");
        String jsonResponse = getJson(resultList);
        try(PrintWriter writer = resp.getWriter()) {
            writer.write(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handlePost(HttpServletRequest req, HttpServletResponse resp){
        resp.setContentType("application/json");
        T model = getModelFromRequestBody(req);
        modelValidator.validate(model);
        storageService.add(model);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    public void handlePut(HttpServletRequest req, HttpServletResponse resp){
        resp.setContentType("application/json");
        T model = getModelFromRequestBody(req);
        modelValidator.validate(model);
        storageService.update(model);
    }

    public void handleDelete(HttpServletRequest req, HttpServletResponse resp){
        resp.setContentType("application/json");
        deleteModelByRequest(req);
    }

    protected void deleteModelByRequest(HttpServletRequest req){
        LongPathParser pathParser = new LongPathParser();
        Long id = pathParser.parsePath(req.getPathInfo(), 1);
        this.storageService.deleteById(id);
    }

    protected T getModelFromRequestBody(HttpServletRequest req){
        String requestBody = RequestBodyParser.readBody(req);
        T model = modelParser.toModel(requestBody);
        return model;
    }

    protected List<T> getModelByRequest(HttpServletRequest req){
        LongPathParser pathParser = new LongPathParser();
        Long id = pathParser.parsePath(req.getPathInfo(), 1);
        List<T> result = new ArrayList<>();
        result.add(this.storageService.getById(id));
        return result;
    }

    protected String getJson(List<T> resultList){
        Map<String, T> resultMap = new HashMap<>();
        resultMap.put(MODEL_NAME, resultList.get(0));
        return modelParser.toJSON(resultMap);
    }
}
