package org.example.servlets.carvolume;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exceptions.MethodNotAllowedException;
import org.example.exceptions.ParametersParseException;
import org.example.interfaces.PathParser;
import org.example.interfaces.QuerySpecification;
import org.example.models.CarVolume;
import org.example.parsers.JsonModelParser;
import org.example.parsers.LongPathParser;
import org.example.services.CarVolumeService;
import org.example.servlets.BaseServlet;
import org.example.specifications.carvolume.CarVolumeByIdCarSpecification;
import org.example.specifications.carvolume.CarVolumeByIdsSpecification;
import org.example.validators.CarVolumeValidator;

import java.io.IOException;
import java.util.List;

public class CarVolumeServlet extends BaseServlet<CarVolume, Long> {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.modelService = (CarVolumeService) getServletContext().getAttribute("carvolumeService");
        this.modelParser = new JsonModelParser<>(CarVolume.class);
        this.modelValidator = new CarVolumeValidator();
        this.pathParser = new LongPathParser();
        this.modelName = "volumes";
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new MethodNotAllowedException(String.format("Method %s not allowed", req.getMethod()));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Long id_car = null;
        Long id_volume = null;

        try {
            id_car = Long.parseLong(req.getParameter("idCar"));
            id_volume = Long.parseLong(req.getParameter("idVolume"));
        } catch (NumberFormatException ignored) {}

        if(id_car == null || id_volume == null){
            throw new ParametersParseException("Check input data");
        }

        QuerySpecification specification = new CarVolumeByIdsSpecification(id_car, id_volume);

        modelService.deleteBySpecification(specification);
    }

    @Override
    protected List<CarVolume> getModelFromDatabase(Long id) {
        return this.modelService.query(new CarVolumeByIdCarSpecification(id));
    }

    @Override
    protected void deleteModelFromDatabase(Long aLong) {

    }
}
