package org.example.handlers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.interfaces.ConvertModelStrategy;
import org.example.interfaces.ModelParser;
import org.example.interfaces.ModelValidator;
import org.example.services.CatalogService;
import org.example.services.StorageService;
import org.example.models.Catalog;
import org.example.parsers.StringPathParser;

import java.util.List;

public class CatalogByBrandRequestHandler extends RequestHandler<Catalog> {
    public CatalogByBrandRequestHandler(StorageService<Catalog> storageService, ModelParser<Catalog> modelParser, ModelValidator<Catalog> modelValidator, String MODEL_NAME) {
        super(storageService, modelParser, modelValidator, MODEL_NAME);
    }

    @Override
    protected List<Catalog> getModelByRequest(HttpServletRequest req) {
        StringPathParser pathParser = new StringPathParser();
        String brand = pathParser.parsePath(req.getPathInfo(), 1);
        List<Catalog> result = ((CatalogService) this.storageService).getByBrand(brand);
        return result;
    }
}
