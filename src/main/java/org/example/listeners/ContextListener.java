package org.example.listeners;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import liquibase.Liquibase;
import liquibase.command.CommandScope;
import liquibase.command.core.UpdateCommandStep;
import liquibase.command.core.helpers.DbUrlConnectionCommandStep;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.example.database.DatabaseService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;

import static javax.naming.spi.NamingManager.getInitialContext;

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

        DatabaseService databaseService = new DatabaseService(dataSource);

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("dbService", databaseService);
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}
