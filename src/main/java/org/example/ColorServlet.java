package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.database.DatabaseService;
import org.example.interfaces.ModelParser;
import org.example.models.Color;
import org.example.models.User;
import org.example.models.Volume;
import org.example.parsers.JsonModelParser;
import org.example.parsers.PathParser;
import org.example.parsers.RequestBodyParser;
import org.example.repositories.ColorRepository;
import org.example.validators.ColorValidator;
import org.example.validators.VolumeValidator;

import java.io.IOException;
import java.io.PrintWriter;

public class ColorServlet extends HttpServlet {

    private ColorRepository colorRepository;
    private ModelParser<Color> colorParser;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.colorRepository = new ColorRepository((DatabaseService) getServletContext().getAttribute("dbService"));
        this.colorParser = new JsonModelParser<>(Color.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        PathParser parser = new PathParser(req.getPathInfo());
        Long id = parser.parseLong(1);
        if(id == null){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Color result = colorRepository.getById(id);
        if(result == null){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        PrintWriter writer = resp.getWriter();
        writer.write(mapper.writeValueAsString(result));
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestBody = RequestBodyParser.readBody(req);
        Color newColor = colorParser.parse(requestBody);
        ColorValidator validator = new ColorValidator();

        if(!validator.validate(newColor)){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        colorRepository.save(newColor);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestBody = RequestBodyParser.readBody(req);
        Color updateColor = colorParser.parse(requestBody);
        ColorValidator validator = new ColorValidator();

        if(!validator.validate(updateColor)){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        colorRepository.update(updateColor);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PathParser pathParser = new PathParser(req.getPathInfo());
        Long id = pathParser.parseLong(1);
        if(id == null){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        colorRepository.delete(id);
    }
}
