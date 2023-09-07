package org.example.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exceptions.ModelNotFullException;
import org.example.interfaces.ConvertModelStrategy;
import org.example.interfaces.ModelParser;
import org.example.interfaces.ModelValidator;
import org.example.services.StorageService;
import org.example.parsers.LongPathParser;
import org.example.parsers.RequestBodyParser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class RequestHandler<T> {

    protected final StorageService<T> storageService;
    protected final ModelParser<T> modelParser;
    protected final ModelValidator<T> modelValidator;
    protected final String MODEL_NAME;
    private final ConvertModelStrategy<T> convertModelStrategy;


    public RequestHandler(StorageService<T> storageService, ModelParser<T> modelParser, ModelValidator<T> modelValidator, String MODEL_NAME, ConvertModelStrategy<T> convertModelStrategy) {
        this.storageService = storageService;
        this.modelParser = modelParser;
        this.modelValidator = modelValidator;
        this.MODEL_NAME = MODEL_NAME;
        this.convertModelStrategy = convertModelStrategy;
    }

    public void handleGet(HttpServletRequest req, HttpServletResponse resp){
        resp.setContentType("application/json");
        List<T> resultList = getModelByRequest(req);
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
        if(!modelValidator.validateOnInsert(model)){
            throw new ModelNotFullException("Model not full");
        }
        storageService.add(model);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    public void handlePut(HttpServletRequest req, HttpServletResponse resp){
        resp.setContentType("application/json");
        T model = getModelFromRequestBody(req);
        if(!modelValidator.validateOnUpdate(model)){
            throw new ModelNotFullException("Model not full");
        }
        storageService.update(model);
    }

    public void handleDelete(HttpServletRequest req, HttpServletResponse resp){
        resp.setContentType("application/json");
        deleteModelByRequest(req);
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    protected void deleteModelByRequest(HttpServletRequest req){
        LongPathParser pathParser = new LongPathParser();
        Long id = pathParser.parsePath(req.getPathInfo(), 1);
        this.storageService.deleteById(id);
    }

    protected T getModelFromRequestBody(HttpServletRequest req){
        String requestBody = RequestBodyParser.readBody(req);
        return modelParser.toModel(requestBody);
    }

    protected List<T> getModelByRequest(HttpServletRequest req){
        LongPathParser pathParser = new LongPathParser();
        Long id = pathParser.parsePath(req.getPathInfo(), 1);
        List<T> result = new ArrayList<>();
        result.add(this.storageService.getById(id));
        return result;
    }

    protected String getJson(List<T> resultList){
        return convertModelStrategy.execute(resultList, MODEL_NAME, modelParser);
    }
}
