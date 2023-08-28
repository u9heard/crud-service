package org.example.servlets.catalog;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.models.Catalog;
import org.example.parsers.JsonModelParser;
import org.example.parsers.PathParser;
import org.example.responses.ErrorJsonResponse;
import org.example.services.CatalogService;
import org.example.specifications.catalog.CatalogByBrandSpecification;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatalogByBrandServlet extends HttpServlet {

    private CatalogService catalogService;
    private JsonModelParser<Catalog> catalogParser;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.catalogService = (CatalogService) getServletContext().getAttribute("catalogService");
        this.catalogParser = new JsonModelParser<>(Catalog.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PathParser parser = new PathParser(req.getPathInfo());
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

        Map<String, List<Catalog>> resultMap = new HashMap<>();
        resultMap.put("catalog", result);

        writer.write(catalogParser.toJSON(resultMap));
        writer.close();
    }
}
