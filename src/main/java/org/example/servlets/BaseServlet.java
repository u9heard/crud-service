package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interfaces.ModelParser;
import org.example.interfaces.ModelValidator;
import org.example.interfaces.PathParser;
import org.example.interfaces.StorageService;
import org.example.parsers.RequestBodyParser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseServlet<T, PathParam> extends HttpServlet {
    protected StorageService<T> modelService;
    protected ModelParser<T> modelParser;
    protected ModelValidator<T> modelValidator;
    protected PathParser<PathParam> pathParser;
    protected String modelName;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PathParam id = parseIdFromPath(req.getPathInfo());
        T model = getModelFromDatabase(id);
        String jsonResponse = getJson(model);
        PrintWriter writer = resp.getWriter();
        writer.write(jsonResponse);
        writer.close();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String requestBody = RequestBodyParser.readBody(req);
        T model = modelParser.toModel(requestBody);
        modelValidator.validate(model);
        modelService.add(model);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String requestBody = RequestBodyParser.readBody(req);
        T model = modelParser.toModel(requestBody);
        modelValidator.validate(model);
        modelService.update(model);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PathParam id = parseIdFromPath(req.getPathInfo());
        deleteModelFromDatabase(id);
    }

    protected String getJson(T model)
    {
        Map<String, T> resultMap = new HashMap<>();
        resultMap.put(modelName, model);
        return modelParser.toJSON(resultMap);
    }

    protected abstract T getModelFromDatabase(PathParam id);
    protected abstract void deleteModelFromDatabase(PathParam id);

    private PathParam parseIdFromPath(String pathInfo)  {
        return pathParser.parsePath(pathInfo,1);
    }
}
