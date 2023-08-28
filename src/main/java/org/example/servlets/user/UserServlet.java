package org.example.servlets.user;


import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interfaces.ModelParser;
import org.example.interfaces.ModelValidator;
import org.example.interfaces.QuerySpecification;
import org.example.models.User;
import org.example.parsers.JsonModelParser;
import org.example.parsers.PathParser;
import org.example.parsers.RequestBodyParser;
import org.example.responses.ErrorJsonResponse;
import org.example.services.UserService;
import org.example.specifications.user.UserByIdSpecification;
import org.example.validators.UserValidator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServlet extends HttpServlet {

    private UserService userService;
    private ModelParser<User> userParser;
    private ModelValidator<User> userValidator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.userService = (UserService) getServletContext().getAttribute("userService");
        this.userParser = new JsonModelParser<>(User.class);
        this.userValidator = new UserValidator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PathParser parser = new PathParser(req.getPathInfo());
        PrintWriter writer = resp.getWriter();

        Long id = parser.parseLong(1);
        if(id == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(ErrorJsonResponse.CHECK_INPUT_DATA.getJsonMessage());
            writer.close();
            return;
        }

        QuerySpecification specification = new UserByIdSpecification(id);
        List<User> resultList = userService.get(specification);

        if(resultList.isEmpty()){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            writer.write(ErrorJsonResponse.NOT_FOUND.getJsonMessage());
            writer.close();
            return;
        }

        Map<String, List<User>> resultMap = new HashMap<>();
        resultMap.put("users", resultList);

        writer.write(userParser.toJSON(resultMap));
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String requestBody = RequestBodyParser.readBody(req);
        User newUser = userParser.toModel(requestBody);

        if(!userValidator.validate(newUser)){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter writer = resp.getWriter();
            writer.write(ErrorJsonResponse.MODEL_NOT_FULL.getJsonMessage());
            writer.close();
            return;
        }

        userService.add(newUser);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String requestBody = RequestBodyParser.readBody(req);
        User updateUser = userParser.toModel(requestBody);

        if(!userValidator.validate(updateUser)){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter writer = resp.getWriter();
            writer.write(ErrorJsonResponse.MODEL_NOT_FULL.getJsonMessage());
            writer.close();
            return;
        }

        userService.update(updateUser);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PathParser parser = new PathParser(req.getPathInfo());
        Long id = parser.parseLong(1);

        if(id == null){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter writer = resp.getWriter();
            writer.write(ErrorJsonResponse.CHECK_INPUT_DATA.getJsonMessage());
            writer.close();
            return;
        }

        QuerySpecification specification = new UserByIdSpecification(id);

        userService.delete(specification);
    }
}