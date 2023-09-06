package org.example.servlets.users;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.handlers.strategy.OneModelToJson;
import org.example.interfaces.ModelParser;
import org.example.interfaces.ModelValidator;
import org.example.services.StorageService;
import org.example.models.User;
import org.example.services.UserService;
import org.example.handlers.RequestHandler;
import org.example.validators.UserValidator;

import java.io.IOException;

public class UserServlet extends HttpServlet {

    private StorageService<User> userStorageService;
    private ModelParser<User> userModelParser;
    private ModelValidator<User> userModelValidator;
    private String MODEL_NAME;

    private RequestHandler<User> requestHandler;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        this.userModelValidator = (UserValidator) getServletContext().getAttribute("userValidator");
        this.userModelParser = (ModelParser<User>) getServletContext().getAttribute("userParser");
        this.userStorageService = (UserService) getServletContext().getAttribute("userService");
        this.MODEL_NAME = "user";
        this.requestHandler = new RequestHandler<>(userStorageService, userModelParser, userModelValidator, MODEL_NAME, new OneModelToJson<>());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        requestHandler.handleGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        requestHandler.handlePost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        requestHandler.handlePut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        requestHandler.handleDelete(req, resp);
    }
}
