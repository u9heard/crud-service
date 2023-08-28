package org.example.servlets.carcolor;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interfaces.ModelParser;
import org.example.interfaces.ModelValidator;
import org.example.interfaces.QuerySpecification;
import org.example.models.CarColor;
import org.example.parsers.JsonModelParser;
import org.example.parsers.PathParser;
import org.example.parsers.RequestBodyParser;
import org.example.responses.ErrorJsonResponse;
import org.example.services.CarColorService;
import org.example.specifications.carcolor.CarColorByCarIdSpecification;
import org.example.specifications.carcolor.CarColorByIdsSpecification;
import org.example.validators.CarColorValidator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarColorServlet extends HttpServlet {

    CarColorService carColorService;
    ModelParser<CarColor> carColorParser;
    ModelValidator<CarColor> carColorValidator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.carColorService = (CarColorService) getServletContext().getAttribute("carcolorService");
        this.carColorParser = new JsonModelParser<>(CarColor.class);
        this.carColorValidator = new CarColorValidator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
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

        Map<String, List<CarColor>> resultMap = new HashMap<>();
        resultMap.put("colors", result);

        writer.write(carColorParser.toJSON(resultMap));
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String requestBody = RequestBodyParser.readBody(req);
        CarColor newCarColor = carColorParser.toModel(requestBody);
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
        Long id_car = null;
        Long id_color = null;

        try{
            id_car = Long.parseLong(req.getParameter("idCar"));
            id_color = Long.parseLong(req.getParameter("idColor"));
        }
        catch (NumberFormatException ignored){}

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
