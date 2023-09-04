package org.example.handlers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.criteria.SearchCriteria;
import org.example.criteria.SearchOperator;
import org.example.interfaces.ModelParser;
import org.example.interfaces.ModelValidator;
import org.example.interfaces.StorageService;
import org.example.models.Catalog;
import org.example.parsers.StringPathParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatalogByBrandRequestHandler extends RequestHandler<Catalog> {
    public CatalogByBrandRequestHandler(StorageService<Catalog> storageService, ModelParser<Catalog> modelParser, ModelValidator<Catalog> modelValidator, String MODEL_NAME) {
        super(storageService, modelParser, modelValidator, MODEL_NAME);
    }

    @Override
    protected List<Catalog> getModelByRequest(HttpServletRequest req) {
        StringPathParser pathParser = new StringPathParser();
        String brand = pathParser.parsePath(req.getPathInfo(), 1);
        List<Catalog> result = (this.storageService.query(List.of(new SearchCriteria("brand", SearchOperator.EQUALS, brand))));
        return result;
    }

    @Override
    protected String getJson(List<Catalog> resultList){
        Map<String, List<Catalog>> resultMap = new HashMap<>();
        resultMap.put(MODEL_NAME, resultList);
        return modelParser.toJSON(resultMap);
    }
}
