package org.example.servlets.volume;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.handlers.RequestHandler;
import org.example.handlers.strategy.OneModelToJson;
import org.example.interfaces.ModelParser;
import org.example.interfaces.ModelValidator;
import org.example.services.StorageService;
import org.example.models.Volume;
import org.example.services.VolumeService;
import org.example.validators.VolumeValidator;

import java.io.IOException;

public class VolumeServlet extends HttpServlet {
    private StorageService<Volume> volumeStorageService;
    private ModelParser<Volume> volumeModelParser;
    private ModelValidator<Volume> volumeModelValidator;
    private String MODEL_NAME;
    private RequestHandler<Volume> requestHandler;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.volumeModelValidator = (VolumeValidator) getServletContext().getAttribute("volumeValidator");
        this.volumeModelParser = (ModelParser<Volume>) getServletContext().getAttribute("volumeParser");
        this.volumeStorageService = (VolumeService) getServletContext().getAttribute("volumeService");
        this.MODEL_NAME = "volumes";
        this.requestHandler = new RequestHandler<>(volumeStorageService, volumeModelParser, volumeModelValidator, MODEL_NAME);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.requestHandler.handleGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.requestHandler.handlePost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.requestHandler.handlePut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.requestHandler.handleDelete(req, resp);
    }
}
