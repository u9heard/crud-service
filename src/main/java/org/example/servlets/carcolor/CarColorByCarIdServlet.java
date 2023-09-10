package org.example.servlets.carcolor;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exceptions.MethodNotAllowedException;
import org.example.handlers.CarColorByCarRequestHandler;
import org.example.handlers.RequestHandler;
import org.example.handlers.strategy.ManyModelsToJson;
import org.example.interfaces.ModelParser;
import org.example.interfaces.ModelValidator;
import org.example.models.CarColor;
import org.example.services.CarColorService;
import org.example.services.StorageService;
import org.example.validators.CarColorValidator;

import java.io.IOException;

public class CarColorByCarIdServlet extends HttpServlet {
    private StorageService<CarColor> carColorStorageService;
    private ModelParser<CarColor> carColorModelParser;
    private ModelValidator<CarColor> carColorModelValidator;
    private String MODEL_NAME;

    private RequestHandler<CarColor> requestHandler;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.carColorModelValidator = (CarColorValidator) getServletContext().getAttribute("carcolorValidator");
        this.carColorModelParser = (ModelParser<CarColor>) getServletContext().getAttribute("carcolorParser");
        this.carColorStorageService = (CarColorService) getServletContext().getAttribute("carcolorService");
        this.MODEL_NAME = "colors";
        this.requestHandler = new CarColorByCarRequestHandler(carColorStorageService, carColorModelParser, carColorModelValidator, MODEL_NAME);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.requestHandler.handleGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new MethodNotAllowedException(String.format("Method %s not allowed", req.getMethod()));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new MethodNotAllowedException(String.format("Method %s not allowed", req.getMethod()));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new MethodNotAllowedException(String.format("Method %s not allowed", req.getMethod()));
    }
}
