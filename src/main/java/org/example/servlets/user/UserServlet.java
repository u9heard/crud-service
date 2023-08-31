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
import org.example.parsers.LongPathParser;
import org.example.parsers.RequestBodyParser;
import org.example.responses.ErrorJsonResponse;
import org.example.services.UserService;
import org.example.servlets.BaseServlet;
import org.example.specifications.user.UserByIdSpecification;
import org.example.validators.UserValidator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServlet extends BaseServlet<User, Long> {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.modelParser = new JsonModelParser<>(User.class);
        this.modelService = (UserService) getServletContext().getAttribute("userService");
        this.pathParser = new LongPathParser();
        this.modelValidator = new UserValidator();
        this.modelName = "user";
    }


    @Override
    protected String getJson(User model) {
        Map<String, User> resultMap = new HashMap<>();
        resultMap.put("user", model);
        return modelParser.toJSON(resultMap);
    }

    @Override
    protected User getModelFromDatabase(Long id) {
        return this.modelService.getById(id);
    }

    @Override
    protected void deleteModelFromDatabase(Long id) {
        this.modelService.deleteById(id);
    }
}