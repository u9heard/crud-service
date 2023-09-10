package org.example.servlets.color;

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
import org.example.models.Color;
import org.example.services.ColorService;
import org.example.validators.ColorValidator;

import java.io.IOException;

public class ColorServlet extends HttpServlet {
    private StorageService<Color> colorStorageService;
    private ModelParser<Color> colorModelParser;
    private ModelValidator<Color> colorModelValidator;
    private String MODEL_NAME;
    private RequestHandler<Color> requestHandler;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.colorModelValidator = (ColorValidator) getServletContext().getAttribute("colorValidator");
        this.colorModelParser = (ModelParser<Color>) getServletContext().getAttribute("colorParser");
        this.colorStorageService = (ColorService) getServletContext().getAttribute("colorService");
        this.MODEL_NAME = "colors";
        this.requestHandler = new RequestHandler<>(colorStorageService, colorModelParser, colorModelValidator, MODEL_NAME);
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
