package org.example.servlets.carvolume;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.database.DatabaseConnector;
import org.example.interfaces.CrudRepository;
import org.example.interfaces.ModelParser;
import org.example.interfaces.ModelValidator;
import org.example.interfaces.QuerySpecification;
import org.example.models.CarColor;
import org.example.models.CarVolume;
import org.example.parsers.JsonModelParser;
import org.example.parsers.PathParser;
import org.example.parsers.RequestBodyParser;
import org.example.repositories.CarVolumeSQLRepository;
import org.example.repositories.CatalogSQLRepository;
import org.example.repositories.VolumeSQLRepository;
import org.example.responses.ErrorJsonResponse;
import org.example.services.CarVolumeService;
import org.example.specifications.carcolor.CarColorByCarIdSpecification;
import org.example.specifications.carcolor.CarColorByIdsSpecification;
import org.example.specifications.carvolume.CarVolumeByIdsSpecification;
import org.example.validators.CarVolumeValidator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CarVolumeServlet extends HttpServlet {

    CarVolumeService carVolumeService;
    ModelValidator<CarVolume> carVolumeValidator;
    ModelParser<CarVolume> carVolumeParser;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        this.carVolumeService = (CarVolumeService) getServletContext().getAttribute("carvolumeService");
        this.carVolumeParser = new JsonModelParser<>(CarVolume.class);
        this.carVolumeValidator = new CarVolumeValidator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        PathParser parser = new PathParser(req.getPathInfo());
        PrintWriter writer = resp.getWriter();
        Long id = parser.parseLong(1);
        if(id == null){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(ErrorJsonResponse.CHECK_INPUT_DATA.getJsonMessage());
            writer.close();
            return;
        }

        QuerySpecification specification = new CarColorByCarIdSpecification(id);

        List<CarVolume> result = carVolumeService.get(specification);
        if(result.isEmpty()){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            writer.write(ErrorJsonResponse.NOT_FOUND.getJsonMessage());
            writer.close();
            return;
        }


        writer.write(mapper.writeValueAsString(result));
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String requestBody = RequestBodyParser.readBody(req);
        CarVolume newCarVolume = carVolumeParser.parse(requestBody);
        PrintWriter writer = resp.getWriter();

        if(!carVolumeValidator.validate(newCarVolume)){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(ErrorJsonResponse.MODEL_NOT_FULL.getJsonMessage());
            writer.close();
            return;
        }

        if(!carVolumeService.add(newCarVolume)){
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            writer.write(ErrorJsonResponse.ENTITY_MISSING.getJsonMessage());
            writer.close();
            return;
        }

        resp.setStatus(HttpServletResponse.SC_CREATED);
        writer.close();
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
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter writer = resp.getWriter();
            writer.write(ErrorJsonResponse.CHECK_INPUT_DATA.getJsonMessage());
            writer.close();
            return;
        }

        QuerySpecification specification = new CarVolumeByIdsSpecification(id_car, id_volume);

        carVolumeService.delete(specification);
    }
}
