package org.example.servlets.catalog;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.interfaces.ModelParser;
import org.example.interfaces.ModelValidator;
import org.example.interfaces.QuerySpecification;
import org.example.models.Catalog;
import org.example.parsers.JsonModelParser;
import org.example.parsers.PathParser;
import org.example.parsers.RequestBodyParser;
import org.example.responses.ErrorJsonResponse;
import org.example.services.CatalogService;
import org.example.specifications.catalog.CatalogByIdSpecification;
import org.example.validators.CatalogValidator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatalogServlet extends HttpServlet {

    private CatalogService catalogService;
    private ModelParser<Catalog> catalogParser;
    private ModelValidator<Catalog> catalogValidator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        this.catalogService = (CatalogService) getServletContext().getAttribute("catalogService");
        this.catalogParser = new JsonModelParser<>(Catalog.class);
        this.catalogValidator = new CatalogValidator();
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

        List<Catalog> result = catalogService.get(new CatalogByIdSpecification(id));
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String requestBody = RequestBodyParser.readBody(req);
        Catalog newCatalog = catalogParser.toModel(requestBody);
        PrintWriter writer = resp.getWriter();

        if(!catalogValidator.validate(newCatalog)){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(ErrorJsonResponse.MODEL_NOT_FULL.getJsonMessage());
            writer.close();
            return;
        }

        if(!catalogService.add(newCatalog)){
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            writer.write(ErrorJsonResponse.UNIQUE_CHECK_ERROR.getJsonMessage());
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
        Catalog updateCatalog = catalogParser.toModel(requestBody);
        PrintWriter writer = resp.getWriter();

        if(!catalogValidator.validate(updateCatalog)){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(ErrorJsonResponse.MODEL_NOT_FULL.getJsonMessage());
            writer.close();
            return;
        }

        if(!catalogService.update(updateCatalog)){
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            writer.write(ErrorJsonResponse.UNIQUE_CHECK_ERROR.getJsonMessage());
            writer.close();
            return;
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

        QuerySpecification specification = new CatalogByIdSpecification(id);
        catalogService.delete(specification);
    }
}
