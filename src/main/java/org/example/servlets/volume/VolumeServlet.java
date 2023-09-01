package org.example.servlets.volume;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import org.example.models.Volume;
import org.example.parsers.JsonModelParser;
import org.example.parsers.LongPathParser;
import org.example.services.VolumeService;
import org.example.servlets.BaseServlet;
import org.example.specifications.DefaultByIdSpecification;
import org.example.validators.VolumeValidator;

import java.util.List;

public class VolumeServlet extends BaseServlet<Volume, Long> {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.modelService = (VolumeService) getServletContext().getAttribute("volumeService");
        this.modelParser = new JsonModelParser<>(Volume.class);
        this.modelValidator = new VolumeValidator();
        this.pathParser = new LongPathParser();
        this.modelName = "volume";
    }

    @Override
    protected List<Volume> getModelFromDatabase(Long id) {
        return this.modelService.query(new DefaultByIdSpecification(id));
    }

    @Override
    protected void deleteModelFromDatabase(Long id) {
        this.modelService.deleteById(id);
    }
}
