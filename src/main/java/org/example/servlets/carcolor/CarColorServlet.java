package org.example.servlets.carcolor;

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
import org.example.parsers.JsonModelParser;
import org.example.parsers.PathParser;
import org.example.parsers.RequestBodyParser;
import org.example.repositories.CarColorSQLRepository;
import org.example.repositories.CatalogSQLRepository;
import org.example.repositories.ColorSQLRepository;
import org.example.responses.ErrorJsonResponse;
import org.example.services.CarColorService;
import org.example.specifications.carcolor.CarColorByCarIdSpecification;
import org.example.specifications.carcolor.CarColorByIdsSpecification;
import org.example.validators.CarColorValidator;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CarColorServlet extends HttpServlet {

    CarColorService carColorService;
    ModelParser<CarColor> carColorParser;
    ModelValidator<CarColor> carColorValidator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        DatabaseConnector connector = (DatabaseConnector) getServletContext().getAttribute("dbService");
        this.carColorService = new CarColorService(new CatalogSQLRepository(connector), new ColorSQLRepository(connector), new CarColorSQLRepository(connector));
        this.carColorParser = new JsonModelParser<>(CarColor.class);
        this.carColorValidator = new CarColorValidator();
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

        List<CarColor> result = carColorService.get(specification);
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
        CarColor newCarColor = carColorParser.parse(requestBody);
        PrintWriter writer = resp.getWriter();

        if(!carColorValidator.validate(newCarColor)){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(ErrorJsonResponse.MODEL_NOT_FULL.getJsonMessage());
            writer.close();
            return;
        }

        if(!carColorService.add(newCarColor)){
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
        PathParser pathParser = new PathParser(req.getPathInfo());
        Long id_car = Long.getLong(req.getParameter("idCar"));
        Long id_color = Long.getLong(req.getParameter("idColor"));

        if(id_car == null || id_color == null){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter writer = resp.getWriter();
            writer.write(ErrorJsonResponse.CHECK_INPUT_DATA.getJsonMessage());
            writer.close();
            return;
        }

        QuerySpecification specification = new CarColorByIdsSpecification(id_car, id_color);

        carColorService.delete(specification);
    }
}
