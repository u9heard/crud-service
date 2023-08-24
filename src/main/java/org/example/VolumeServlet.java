package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.database.DatabaseService;
import org.example.interfaces.ModelParser;
import org.example.models.Volume;
import org.example.parsers.JsonModelParser;
import org.example.parsers.PathParser;
import org.example.parsers.RequestBodyParser;
import org.example.repositories.VolumeRepository;
import org.example.validators.VolumeValidator;

import java.io.IOException;
import java.io.PrintWriter;

public class VolumeServlet extends HttpServlet {

    private VolumeRepository volumeRepository;
    private ModelParser<Volume> volumeParser;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        this.volumeRepository = new VolumeRepository((DatabaseService) getServletContext().getAttribute("dbService"));
        this.volumeParser = new JsonModelParser<>(Volume.class);
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

        Volume result = volumeRepository.getById(id);
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
        Volume newVolume = volumeParser.parse(requestBody);
        VolumeValidator validator = new VolumeValidator();

        if(!validator.validate(newVolume)){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        volumeRepository.save(newVolume);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestBody = RequestBodyParser.readBody(req);
        Volume updateVolume = volumeParser.parse(requestBody);
        VolumeValidator validator = new VolumeValidator();

        if(!validator.validate(updateVolume)){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        volumeRepository.update(updateVolume);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PathParser pathParser = new PathParser(req.getPathInfo());
        Long id = pathParser.parseLong(1);
        if(id == null){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        volumeRepository.delete(id);
    }
}
