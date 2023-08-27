package org.example.servlets.catalog;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.database.DatabaseConnector;
import org.example.interfaces.CrudRepository;
import org.example.models.Catalog;
import org.example.parsers.PathParser;
import org.example.repositories.CatalogSQLRepository;
import org.example.responses.ErrorJsonResponse;
import org.example.services.CatalogService;
import org.example.specifications.catalog.CatalogByBrandSpecification;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CatalogByBrandServlet extends HttpServlet {

    private CatalogService catalogService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.catalogService = (CatalogService) getServletContext().getAttribute("catalogService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PathParser parser = new PathParser(req.getPathInfo());
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();

        String brand = parser.parseString(1);

        if(brand == null){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(ErrorJsonResponse.CHECK_INPUT_DATA.getJsonMessage());
            writer.close();
            return;
        }

        List<Catalog> result = catalogService.get(new CatalogByBrandSpecification(brand));
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
