package org.example.servlets.carvolume;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exceptions.MethodNotAllowedException;
import org.example.handlers.RequestHandler;
import org.example.handlers.strategy.ManyModelsToJson;
import org.example.interfaces.ModelParser;
import org.example.interfaces.ModelValidator;
import org.example.services.StorageService;
import org.example.models.CarVolume;
import org.example.services.CarVolumeService;
import org.example.validators.CarVolumeValidator;

import java.io.IOException;

public class CarVolumeServlet extends HttpServlet {
    private StorageService<CarVolume> carVolumeStorageService;
    private ModelParser<CarVolume> carVolumeModelParser;
    private ModelValidator<CarVolume> carVolumeModelValidator;
    private String MODEL_NAME;
    private RequestHandler<CarVolume> carVolumeRequestHandler;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.carVolumeModelValidator = (CarVolumeValidator) getServletContext().getAttribute("carvolumeValidator");
        this.carVolumeModelParser = (ModelParser<CarVolume>) getServletContext().getAttribute("carvolumeParser");
        this.carVolumeStorageService = (CarVolumeService) getServletContext().getAttribute("carvolumeService");
        this.MODEL_NAME = "volumes";
        this.carVolumeRequestHandler = new RequestHandler<>(carVolumeStorageService, carVolumeModelParser, carVolumeModelValidator, MODEL_NAME);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.carVolumeRequestHandler.handleGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.carVolumeRequestHandler.handlePost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new MethodNotAllowedException(String.format("Method %s not allowed", req.getMethod()));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.carVolumeRequestHandler.handleDelete(req, resp);
    }
}
