package org.example.servlets.color;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import org.example.models.Color;
import org.example.services.ColorService;
import org.example.servlets.BaseServlet;

public class ColorServlet extends BaseServlet<Color, Long> {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.modelService = (ColorService) getServletContext().getAttribute("colorService");

    }

    @Override
    protected Color getModelFromDatabase(Long id) {
        return this.modelService.getById(id);
    }

    @Override
    protected void deleteModelFromDatabase(Long id) {
        this.modelService.deleteById(id);
    }
}
