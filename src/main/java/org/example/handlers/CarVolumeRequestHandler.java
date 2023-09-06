package org.example.handlers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.exceptions.RequestParametersException;
import org.example.interfaces.ConvertModelStrategy;
import org.example.interfaces.ModelParser;
import org.example.interfaces.ModelValidator;
import org.example.models.CarColor;
import org.example.models.CarVolume;
import org.example.parsers.LongPathParser;
import org.example.services.CarColorService;
import org.example.services.CarVolumeService;
import org.example.services.StorageService;

import java.util.List;

// /carvolume/car/<id_car>
public class CarVolumeRequestHandler extends RequestHandler<CarVolume>{
    public CarVolumeRequestHandler(StorageService<CarVolume> storageService, ModelParser<CarVolume> modelParser, ModelValidator<CarVolume> modelValidator, String MODEL_NAME, ConvertModelStrategy<CarVolume> convertModelStrategy) {
        super(storageService, modelParser, modelValidator, MODEL_NAME, convertModelStrategy);
    }

    @Override
    protected List<CarVolume> getModelByRequest(HttpServletRequest req) {
        LongPathParser pathParser = new LongPathParser();
        Long id = pathParser.parsePath(req.getPathInfo(), 1);
        List<CarVolume> result = ((CarVolumeService) this.storageService).getByCarId(id);
        return result;
    }
}
