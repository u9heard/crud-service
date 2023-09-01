package org.example.servlets.color;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import org.example.models.Color;
import org.example.parsers.JsonModelParser;
import org.example.parsers.LongPathParser;
import org.example.services.ColorService;
import org.example.servlets.BaseServlet;
import org.example.specifications.DefaultByIdSpecification;
import org.example.validators.ColorValidator;

import java.util.List;

public class ColorServlet extends BaseServlet<Color, Long> {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.modelService = (ColorService) getServletContext().getAttribute("colorService");
        this.modelParser = new JsonModelParser<>(Color.class);
        this.modelValidator = new ColorValidator();
        this.pathParser = new LongPathParser();
        this.modelName = "color";
    }

    @Override
    protected List<Color> getModelFromDatabase(Long id) {
        return this.modelService.query(new DefaultByIdSpecification(id));
    }

    @Override
    protected void deleteModelFromDatabase(Long id) {
        this.modelService.deleteById(id);
    }
}
