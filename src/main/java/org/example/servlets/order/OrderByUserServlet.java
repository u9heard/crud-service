package org.example.servlets.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.database.DatabaseConnector;
import org.example.models.Order;
import org.example.parsers.PathParser;
import org.example.repositories.*;
import org.example.responses.ErrorJsonResponse;
import org.example.services.OrderService;
import org.example.specifications.order.OrderByIdSpecification;
import org.example.specifications.order.OrderByUserSpecification;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class OrderByUserServlet extends HttpServlet {

    private OrderService orderService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        this.orderService = (OrderService) getServletContext().getAttribute("orderService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PathParser parser = new PathParser(req.getPathInfo());
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();

        Long id = parser.parseLong(1);

        if(id == null){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(ErrorJsonResponse.CHECK_INPUT_DATA.getJsonMessage());
            writer.close();
            return;
        }

        List<Order> result = orderService.get(new OrderByUserSpecification(id));
        if(result.isEmpty()){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            writer.write(ErrorJsonResponse.NOT_FOUND.getJsonMessage());
            writer.close();
            return;
        }

        writer.write(mapper.writeValueAsString(result));
        writer.close();
    }
}
