package org.example.servlets.volume;

import jakarta.servlet.http.HttpServlet;
import org.example.models.Volume;
import org.example.servlets.BaseServlet;
import org.example.specifications.DefaultByIdSpecification;

import java.util.List;

public class VolumeServlet extends BaseServlet<Volume, Long> {



    @Override
    protected List<Volume> getModelFromDatabase(Long id) {
        return this.modelService.query(new DefaultByIdSpecification(id));
    }

    @Override
    protected void deleteModelFromDatabase(Long id) {

    }
}
