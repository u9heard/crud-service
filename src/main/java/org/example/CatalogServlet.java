package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.database.DatabaseService;
import org.example.interfaces.ModelParser;
import org.example.interfaces.ModelValidator;
import org.example.models.Catalog;
import org.example.parsers.JsonModelParser;
import org.example.parsers.PathParser;
import org.example.parsers.RequestBodyParser;
import org.example.repositories.CatalogRepository;
import org.example.validators.CatalogValidator;

import java.io.IOException;
import java.io.PrintWriter;

public class CatalogServlet extends HttpServlet {

    private CatalogRepository catalogRepository;
    private ModelParser<Catalog> catalogParser;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.catalogRepository = new CatalogRepository(
                (DatabaseService) getServletContext().getAttribute("dbService")
        );
        this.catalogParser = new JsonModelParser<>(Catalog.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PathParser parser = new PathParser(req.getPathInfo());
        ObjectMapper mapper = new ObjectMapper();

        Long id = parser.parseLong(1);
        if(id == null){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Catalog result = catalogRepository.getById(id);
        if(result == null){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        PrintWriter writer = resp.getWriter();
        writer.write(mapper.writeValueAsString(result));
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestBody = RequestBodyParser.readBody(req);
        Catalog newCatalog = catalogParser.parse(requestBody);
        ModelValidator<Catalog> validator = new CatalogValidator();

        if(!validator.validate(newCatalog)){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        catalogRepository.save(newCatalog);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestBody = RequestBodyParser.readBody(req);
        Catalog updateCatalog = catalogParser.parse(requestBody);
        ModelValidator<Catalog> validator = new CatalogValidator();

        if(!validator.validate(updateCatalog)){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        catalogRepository.update(updateCatalog);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PathParser pathParser = new PathParser(req.getPathInfo());
        Long id = pathParser.parseLong(1);

        if(id == null){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        catalogRepository.delete(id);
    }
}
