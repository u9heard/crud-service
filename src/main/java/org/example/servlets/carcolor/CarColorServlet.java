package org.example.servlets.carcolor;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exceptions.MethodNotAllowedException;
import org.example.exceptions.ParametersParseException;
import org.example.interfaces.QuerySpecification;
import org.example.models.CarColor;
import org.example.parsers.JsonModelParser;
import org.example.parsers.LongPathParser;
import org.example.services.CarColorService;
import org.example.servlets.BaseServlet;
import org.example.specifications.carcolor.CarColorByCarIdSpecification;
import org.example.specifications.carcolor.CarColorByIdsSpecification;
import org.example.validators.CarColorValidator;

import java.io.IOException;
import java.util.List;

public class CarColorServlet extends BaseServlet<CarColor, Long> {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.modelService = (CarColorService) getServletContext().getAttribute("carcolorService");
        this.modelParser = new JsonModelParser<>(CarColor.class);
        this.modelValidator = new CarColorValidator();
        this.pathParser = new LongPathParser();
        this.modelName = "colors";
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        throw new MethodNotAllowedException(String.format("Method %s not allowed", req.getMethod()));
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
            throw new ParametersParseException("Check input data");
        }

        QuerySpecification specification = new CarColorByIdsSpecification(id_car, id_color);
        this.modelService.deleteBySpecification(specification);
    }

    @Override
    protected List<CarColor> getModelFromDatabase(Long id) {
        return this.modelService.query(new CarColorByCarIdSpecification(id));
    }

    @Override
    protected void deleteModelFromDatabase(Long id) {

    }
}
