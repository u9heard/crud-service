package org.example.listeners;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.example.database.DatabaseConnector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        Context context;
        DataSource dataSource;
        try {
            context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/database");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

        DatabaseConnector databaseConnector = new DatabaseConnector(dataSource);

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("dbService", databaseConnector);
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}
