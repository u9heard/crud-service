package org.example.servlets.order;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.handlers.RequestHandler;
import org.example.handlers.strategy.OneModelToJson;
import org.example.interfaces.ModelParser;
import org.example.interfaces.ModelValidator;
import org.example.services.StorageService;
import org.example.models.Order;
import org.example.services.OrderService;
import org.example.validators.OrderValidator;

import java.io.IOException;

public class OrderServlet extends HttpServlet {
    private StorageService<Order> orderStorageService;
    private ModelParser<Order> orderModelParser;
    private ModelValidator<Order> orderModelValidator;
    private String MODEL_NAME;

    private RequestHandler<Order> requestHandler;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.orderModelValidator = (OrderValidator) getServletContext().getAttribute("orderValidator");
        this.orderModelParser = (ModelParser<Order>) getServletContext().getAttribute("orderParser");
        this.orderStorageService = (OrderService) getServletContext().getAttribute("orderService");
        this.MODEL_NAME = "orders";
        this.requestHandler = new RequestHandler<>(orderStorageService, orderModelParser, orderModelValidator, MODEL_NAME);
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
