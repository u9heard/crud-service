package org.example.servlets.order;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interfaces.ModelParser;
import org.example.interfaces.ModelValidator;
import org.example.interfaces.QuerySpecification;
import org.example.models.Order;
import org.example.parsers.JsonModelParser;
import org.example.parsers.PathParser;
import org.example.parsers.RequestBodyParser;
import org.example.responses.ErrorJsonResponse;
import org.example.services.OrderService;
import org.example.specifications.order.OrderByIdSpecification;
import org.example.validators.OrderValidator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderServlet extends HttpServlet {

    OrderService orderService;
    ModelParser<Order> orderParser;
    ModelValidator<Order> orderValidator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        this.orderService = (OrderService) getServletContext().getAttribute("orderService");
        this.orderParser = new JsonModelParser<>(Order.class);
        this.orderValidator = new OrderValidator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PathParser parser = new PathParser(req.getPathInfo());
        PrintWriter writer = resp.getWriter();

        Long id = parser.parseLong(1);

        if(id == null){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(ErrorJsonResponse.CHECK_INPUT_DATA.getJsonMessage());
            writer.close();
            return;
        }

        List<Order> result = orderService.get(new OrderByIdSpecification(id));
        if(result.isEmpty()){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            writer.write(ErrorJsonResponse.NOT_FOUND.getJsonMessage());
            writer.close();
            return;
        }

        Map<String, List<Order>> resultMap = new HashMap<>();
        resultMap.put("orders", result);

        writer.write(orderParser.toJSON(resultMap));
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String requestBody = RequestBodyParser.readBody(req);
        Order newOrder = orderParser.toModel(requestBody);
        PrintWriter writer = resp.getWriter();

        if(!orderValidator.validate(newOrder)){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(ErrorJsonResponse.MODEL_NOT_FULL.getJsonMessage());
            writer.close();
            return;
        }

        if (!orderService.add(newOrder)){
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            writer.write(ErrorJsonResponse.ENTITY_MISSING.getJsonMessage());
            writer.close();
            return;
        }
        resp.setStatus(HttpServletResponse.SC_CREATED);
        writer.close();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String requestBody = RequestBodyParser.readBody(req);
        Order updateOrder = orderParser.toModel(requestBody);
        PrintWriter writer = resp.getWriter();

        if(!orderValidator.validate(updateOrder)){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(ErrorJsonResponse.MODEL_NOT_FULL.getJsonMessage());
            writer.close();
            return;
        }

        if(!orderService.update(updateOrder)){
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            writer.write(ErrorJsonResponse.ENTITY_MISSING.getJsonMessage());
            writer.close();
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

        QuerySpecification specification = new OrderByIdSpecification(id);
        orderService.delete(specification);
    }
}
