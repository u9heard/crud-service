package org.example.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exceptions.EmptyJsonException;
import org.example.exceptions.MethodNotAllowedException;
import org.example.exceptions.ModelNotFullException;
import org.example.exceptions.ResponseWriterException;
import org.example.exceptions.parsers.JsonParseException;
import org.example.exceptions.parsers.ParametersParseException;
import org.example.exceptions.parsers.PathParseException;
import org.example.exceptions.database.access.DatabaseDeleteException;
import org.example.exceptions.database.access.DatabaseReadException;
import org.example.exceptions.database.access.DatabaseSaveException;
import org.example.exceptions.database.access.DatabaseUpdateException;
import org.example.exceptions.database.service.ModelConflictException;
import org.example.exceptions.database.service.ModelNotFoundException;
import org.example.responses.JsonMessageResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ExceptionsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = null;
        if(servletResponse instanceof HttpServletResponse){
            response = (HttpServletResponse) servletResponse;
            response.setContentType("application/json");
        }
        else{
            filterChain.doFilter(servletRequest, servletResponse);
        }

        PrintWriter writer = response.getWriter();

        try{
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ResponseWriterException | ServletException | IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (JsonParseException | ModelNotFullException | EmptyJsonException | ParametersParseException | PathParseException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(JsonMessageResponse.getJsonMessage(e.getMessage()));
        } catch (ModelConflictException e){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            writer.write(JsonMessageResponse.getJsonMessage(e.getMessage()));
        } catch (ModelNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            writer.write(JsonMessageResponse.getJsonMessage(e.getMessage()));
        } catch (MethodNotAllowedException e){
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            writer.write(JsonMessageResponse.getJsonMessage(e.getMessage()));
        } catch (DatabaseSaveException | DatabaseReadException | DatabaseDeleteException | DatabaseUpdateException e){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println(e.getMessage());
            writer.write(JsonMessageResponse.getJsonMessage("Internal server error"));
        } finally {
            writer.close();
        }
    }
}
