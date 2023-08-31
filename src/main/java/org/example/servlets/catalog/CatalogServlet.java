package org.example.servlets.catalog;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import org.example.interfaces.StorageService;
import org.example.models.Catalog;
import org.example.models.User;
import org.example.parsers.JsonModelParser;
import org.example.parsers.LongPathParser;
import org.example.services.CatalogService;
import org.example.servlets.BaseServlet;
import org.example.validators.CatalogValidator;

import java.util.HashMap;
import java.util.Map;

public class CatalogServlet extends BaseServlet<Catalog, Long> {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.modelService = (CatalogService) getServletContext().getAttribute("catalogService");
        this.modelParser = new JsonModelParser<>(Catalog.class);
        this.modelValidator = new CatalogValidator();
        this.pathParser = new LongPathParser();
        this.modelName = "catalog";
    }


    @Override
    protected Catalog getModelFromDatabase(Long id) {
        return this.modelService.getById(id);
    }

    @Override
    protected void deleteModelFromDatabase(Long id) {
        this.modelService.deleteById(id);
    }
}
