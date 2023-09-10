package org.example.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exceptions.ModelNotFullException;
import org.example.exceptions.ResponseWriterException;
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
        List<T> resultList = getModelByRequest(req);
        String jsonResponse = getJsonOnGet(resultList);
        setResponse(resp, jsonResponse, HttpServletResponse.SC_OK);
    }

    public void handlePost(HttpServletRequest req, HttpServletResponse resp){
        resp.setContentType("application/json");
        T model = getModelFromRequestBody(req);
        modelValidator.validateOnInsert(model);
        storageService.add(model);
        String respJson = getJsonOnUpdate(model);
        setResponse(resp, respJson, HttpServletResponse.SC_CREATED);
    }

    public void handlePut(HttpServletRequest req, HttpServletResponse resp){
        resp.setContentType("application/json");
        T model = getModelFromRequestBody(req);
        modelValidator.validateOnUpdate(model);
        String respJson = getJsonOnUpdate(model);
        storageService.update(model);
        setResponse(resp, respJson, HttpServletResponse.SC_OK);
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
        if(req.getPathInfo() != null) {
            Long id = pathParser.parsePath(req.getPathInfo(), 1);
            List<T> result = new ArrayList<>();
            result.add(this.storageService.getById(id));
            return result;
        }
        else{
            return this.storageService.getAll();
        }

    }

    protected String getJsonOnUpdate(T model){
        return modelParser.toJSON(Map.of(MODEL_NAME, model));
    }

    protected String getJsonOnGet(List<T> resultList){
        return modelParser.toJSON(Map.of(MODEL_NAME, resultList));
    }

    protected void setResponse(HttpServletResponse response, String message, int responseCode){
        try(PrintWriter writer = response.getWriter()){
            response.setStatus(responseCode);
            writer.write(message);
        } catch (IOException e) {
            throw new ResponseWriterException("Unable to get Writer");
        }
    }
}
