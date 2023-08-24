package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.database.DatabaseService;
import org.example.interfaces.ModelParser;
import org.example.interfaces.ModelValidator;
import org.example.models.Catalog;
import org.example.models.User;
import org.example.parsers.JsonModelParser;
import org.example.parsers.PathParser;
import org.example.parsers.RequestBodyParser;
import org.example.repositories.UserRepository;
import org.example.validators.UserValidator;
import org.postgresql.util.PSQLException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class UserServlet extends HttpServlet {

    private UserRepository userRepository;
    private ModelParser<User> userParser;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        DatabaseService databaseService = (DatabaseService) getServletContext().getAttribute("dbService");
        this.userRepository = new UserRepository(databaseService);
        this.userParser = new JsonModelParser<>(User.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        ObjectMapper objectMapper = new ObjectMapper();
        PathParser parser = new PathParser(req.getPathInfo());

        Long id = parser.parseLong(1);
        if(id == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        User user = userRepository.getById(id);
        if(user == null){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        PrintWriter writer = resp.getWriter();
        writer.write(objectMapper.writeValueAsString(user));
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestBody = RequestBodyParser.readBody(req);
        User newUser = userParser.parse(requestBody);
        ModelValidator<User> validator = new UserValidator();

        if(!validator.validate(newUser)){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        userRepository.save(newUser);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestBody = RequestBodyParser.readBody(req);
        User updateUser = userParser.parse(requestBody);
        ModelValidator<User> validator = new UserValidator();

        if(!validator.validate(updateUser)){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        userRepository.update(updateUser);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PathParser parser = new PathParser(req.getPathInfo());
        Long parsedId = parser.parseLong(1);

        if(parsedId == null){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        userRepository.delete(parsedId);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
