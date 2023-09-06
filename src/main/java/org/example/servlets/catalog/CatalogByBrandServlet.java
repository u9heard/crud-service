package org.example.servlets.catalog;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exceptions.MethodNotAllowedException;
import org.example.handlers.CatalogByBrandRequestHandler;
import org.example.handlers.strategy.ManyModelsToJson;
import org.example.interfaces.ModelParser;
import org.example.interfaces.ModelValidator;
import org.example.services.StorageService;
import org.example.models.Catalog;
import org.example.services.CatalogService;
import org.example.handlers.RequestHandler;
import org.example.validators.CatalogValidator;

import java.io.IOException;

public class CatalogByBrandServlet extends HttpServlet {

    private StorageService<Catalog> catalogStorageService;
    private ModelParser<Catalog> catalogModelParser;
    private ModelValidator<Catalog> catalogModelValidator;
    private String MODEL_NAME;
    private RequestHandler<Catalog> catalogRequestHandler;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.catalogModelValidator = (CatalogValidator) getServletContext().getAttribute("catalogValidator");
        this.catalogModelParser = (ModelParser<Catalog>) getServletContext().getAttribute("catalogParser");
        this.catalogStorageService = (CatalogService) getServletContext().getAttribute("catalogService");
        this.MODEL_NAME = "catalog";
        this.catalogRequestHandler = new CatalogByBrandRequestHandler(catalogStorageService, catalogModelParser, catalogModelValidator, MODEL_NAME, new ManyModelsToJson<>());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.catalogRequestHandler.handleGet(req, resp);
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
