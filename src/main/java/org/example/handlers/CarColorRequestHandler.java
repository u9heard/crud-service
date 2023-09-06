package org.example.handlers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.exceptions.RequestParametersException;
import org.example.interfaces.ConvertModelStrategy;
import org.example.interfaces.ModelParser;
import org.example.interfaces.ModelValidator;
import org.example.models.CarColor;
import org.example.models.Catalog;
import org.example.parsers.LongPathParser;
import org.example.parsers.StringPathParser;
import org.example.services.CarColorService;
import org.example.services.CatalogService;
import org.example.services.StorageService;

import java.util.List;

// carcolor/car/<id_car>
public class CarColorRequestHandler extends RequestHandler<CarColor>{
    public CarColorRequestHandler(StorageService<CarColor> storageService, ModelParser<CarColor> modelParser, ModelValidator<CarColor> modelValidator, String MODEL_NAME, ConvertModelStrategy<CarColor> convertModelStrategy) {
        super(storageService, modelParser, modelValidator, MODEL_NAME, convertModelStrategy);
    }

    @Override
    protected List<CarColor> getModelByRequest(HttpServletRequest req) {
        LongPathParser pathParser = new LongPathParser();
        Long id = pathParser.parsePath(req.getPathInfo(), 1);
        List<CarColor> result = ((CarColorService) this.storageService).getByCarId(id);
        return result;
    }

}
