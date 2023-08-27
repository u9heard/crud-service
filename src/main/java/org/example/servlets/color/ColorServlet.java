package org.example.servlets.color;

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
import org.example.models.Color;
import org.example.parsers.JsonModelParser;
import org.example.parsers.PathParser;
import org.example.parsers.RequestBodyParser;
import org.example.repositories.ColorSQLRepository;
import org.example.responses.ErrorJsonResponse;
import org.example.services.ColorService;
import org.example.specifications.color.ColorByIdSpecification;
import org.example.validators.ColorValidator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ColorServlet extends HttpServlet {

    private ColorService colorService;
    private ModelParser<Color> colorParser;
    private ModelValidator<Color> colorValidator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.colorService = (ColorService) getServletContext().getAttribute("colorService");
        this.colorParser = new JsonModelParser<>(Color.class);
        this.colorValidator = new ColorValidator();
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

        QuerySpecification specification = new ColorByIdSpecification(id);

        List<Color> result = colorService.get(specification);
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
        Color newColor = colorParser.parse(requestBody);
        PrintWriter writer = resp.getWriter();

        if(!colorValidator.validate(newColor)){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(ErrorJsonResponse.MODEL_NOT_FULL.getJsonMessage());
            writer.close();
            return;
        }

        if(!colorService.add(newColor)){
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            writer.write(ErrorJsonResponse.UNIQUE_CHECK_ERROR.getJsonMessage());
            writer.close();
            return;
        }

        writer.close();
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String requestBody = RequestBodyParser.readBody(req);
        Color updateColor = colorParser.parse(requestBody);
        PrintWriter writer = resp.getWriter();

        if(!colorValidator.validate(updateColor)){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(ErrorJsonResponse.MODEL_NOT_FULL.getJsonMessage());
            writer.close();
            return;
        }

        if(!colorService.update(updateColor)){
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            writer.write(ErrorJsonResponse.UNIQUE_CHECK_ERROR.getJsonMessage());
            writer.close();
            return;
        }

        writer.close();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PathParser pathParser = new PathParser(req.getPathInfo());
        Long id = pathParser.parseLong(1);
        if(id == null){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter writer = resp.getWriter();
            writer.write(ErrorJsonResponse.CHECK_INPUT_DATA.getJsonMessage());
            writer.close();
            return;
        }

        QuerySpecification specification = new ColorByIdSpecification(id);

        colorService.delete(specification);
    }
}
