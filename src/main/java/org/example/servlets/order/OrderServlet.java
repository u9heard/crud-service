package org.example.servlets.order;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import org.example.models.Order;
import org.example.parsers.JsonModelParser;
import org.example.parsers.LongPathParser;
import org.example.services.OrderService;
import org.example.servlets.BaseServlet;
import org.example.specifications.DefaultByIdSpecification;
import org.example.validators.OrderValidator;

import java.util.List;

public class OrderServlet extends BaseServlet<Order, Long> {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.modelService = (OrderService) getServletContext().getAttribute("orderService");
        this.modelParser = new JsonModelParser<>(Order.class);
        this.modelValidator = new OrderValidator();
        this.pathParser = new LongPathParser();
        this.modelName = "order";
    }

    @Override
    protected List<Order> getModelFromDatabase(Long id) {
        return this.modelService.query(new DefaultByIdSpecification(id));
    }

    @Override
    protected void deleteModelFromDatabase(Long id) {
        this.modelService.deleteById(id);
    }
}
