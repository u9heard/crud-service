package org.example.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exceptions.PathParsingException;
import org.example.exceptions.database.service.DatabaseServiceException;
import org.example.exceptions.database.service.ModelConflictException;
import org.example.exceptions.database.service.ModelNotFoundException;
import org.example.responses.JsonMessageResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class ExceptionsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response;
        PrintWriter writer;
        if(servletResponse instanceof HttpServletResponse){
            response = (HttpServletResponse) servletResponse;
            response.setContentType("application/json");
        }
        else{
            throw new RuntimeException();
        }

        writer = response.getWriter();

        try{
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ServletException | IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (PathParsingException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(JsonMessageResponse.getJsonMessage(e.getMessage()));
        } catch (DatabaseServiceException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(JsonMessageResponse.getJsonMessage(e.getMessage()));
        } catch (ModelConflictException e){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            writer.write(JsonMessageResponse.getJsonMessage(e.getMessage()));
        } catch (ModelNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            writer.write(JsonMessageResponse.getJsonMessage(e.getMessage()));
        } finally {
            writer.close();
        }

    }
}
