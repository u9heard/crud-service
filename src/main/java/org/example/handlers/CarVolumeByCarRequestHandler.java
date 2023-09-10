package org.example.handlers;

import jakarta.servlet.http.HttpServletRequest;
import org.example.interfaces.ConvertModelStrategy;
import org.example.interfaces.ModelParser;
import org.example.interfaces.ModelValidator;
import org.example.models.CarVolume;
import org.example.parsers.LongPathParser;
import org.example.services.CarVolumeService;
import org.example.services.StorageService;

import java.util.List;

// /carvolume/car/<id_car>
public class CarVolumeByCarRequestHandler extends RequestHandler<CarVolume>{
    public CarVolumeByCarRequestHandler(StorageService<CarVolume> storageService, ModelParser<CarVolume> modelParser, ModelValidator<CarVolume> modelValidator, String MODEL_NAME) {
        super(storageService, modelParser, modelValidator, MODEL_NAME);
    }

    @Override
    protected List<CarVolume> getModelByRequest(HttpServletRequest req) {
        LongPathParser pathParser = new LongPathParser();
        Long id = pathParser.parsePath(req.getPathInfo(), 1);
        List<CarVolume> result = ((CarVolumeService) this.storageService).getByCarId(id);
        return result;
    }
}
