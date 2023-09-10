package org.example.handlers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.interfaces.ConvertModelStrategy;
import org.example.interfaces.ModelParser;
import org.example.interfaces.ModelValidator;
import org.example.models.Catalog;
import org.example.models.Order;
import org.example.parsers.LongPathParser;
import org.example.parsers.StringPathParser;
import org.example.services.CatalogService;
import org.example.services.OrderService;
import org.example.services.StorageService;

import java.util.List;

public class OrderByUserRequestHandler extends RequestHandler<Order>{
    public OrderByUserRequestHandler(StorageService<Order> storageService, ModelParser<Order> modelParser, ModelValidator<Order> modelValidator, String MODEL_NAME) {
        super(storageService, modelParser, modelValidator, MODEL_NAME);
    }

    @Override
    protected List<Order> getModelByRequest(HttpServletRequest req) {
        LongPathParser pathParser = new LongPathParser();
        Long id = pathParser.parsePath(req.getPathInfo(), 1);
        List<Order> result = ((OrderService) this.storageService).getByUserId(id);
        return result;
    }
}
