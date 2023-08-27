package org.example.servlets.volume;

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
import org.example.models.Volume;
import org.example.parsers.JsonModelParser;
import org.example.parsers.PathParser;
import org.example.parsers.RequestBodyParser;
import org.example.repositories.VolumeSQLRepository;
import org.example.responses.ErrorJsonResponse;
import org.example.services.UserService;
import org.example.services.VolumeService;
import org.example.specifications.volume.VolumeByIdSpecification;
import org.example.validators.VolumeValidator;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class VolumeServlet extends HttpServlet {

    private VolumeService volumeService;
    private ModelParser<Volume> volumeParser;
    private ModelValidator<Volume> volumeValidator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        this.volumeService = (VolumeService) getServletContext().getAttribute("volumeService");
        this.volumeParser = new JsonModelParser<>(Volume.class);
        this.volumeValidator = new VolumeValidator();
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

        QuerySpecification specification = new VolumeByIdSpecification(id);

        List<Volume> result = volumeService.get(specification);
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
        Volume newVolume = volumeParser.parse(requestBody);
        PrintWriter writer = resp.getWriter();

        if(!volumeValidator.validate(newVolume)){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(ErrorJsonResponse.MODEL_NOT_FULL.getJsonMessage());
            writer.close();
            return;
        }

        if(!volumeService.add(newVolume)){
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
        Volume updateVolume = volumeParser.parse(requestBody);
        PrintWriter writer = resp.getWriter();

        if(!volumeValidator.validate(updateVolume)){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(ErrorJsonResponse.MODEL_NOT_FULL.getJsonMessage());
            writer.close();
            return;
        }

        if(!volumeService.update(updateVolume)){
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

        QuerySpecification specification = new VolumeByIdSpecification(id);

        volumeService.delete(specification);
    }
}
