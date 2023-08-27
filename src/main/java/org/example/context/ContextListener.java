package org.example.context;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.example.database.DatabaseConnector;
import org.example.models.CarColor;
import org.example.models.CarVolume;
import org.example.repositories.*;
import org.example.services.*;

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

        UserSQLRepository userSQLRepository = new UserSQLRepository(databaseConnector);
        OrderSQLRepository orderSQLRepository = new OrderSQLRepository(databaseConnector);
        CatalogSQLRepository catalogSQLRepository = new CatalogSQLRepository(databaseConnector);
        ColorSQLRepository colorSQLRepository = new ColorSQLRepository(databaseConnector);
        VolumeSQLRepository volumeSQLRepository = new VolumeSQLRepository(databaseConnector);
        CarColorSQLRepository carColorSQLRepository = new CarColorSQLRepository(databaseConnector);
        CarVolumeSQLRepository carVolumeSQLRepository = new CarVolumeSQLRepository(databaseConnector);

        UserService userService = new UserService(userSQLRepository);
        CatalogService catalogService = new CatalogService(catalogSQLRepository);
        OrderService orderService = new OrderService(
                userSQLRepository,
                catalogSQLRepository,
                carColorSQLRepository,
                carVolumeSQLRepository,
                orderSQLRepository
        );
        VolumeService volumeService = new VolumeService(volumeSQLRepository);
        ColorService colorService = new ColorService(colorSQLRepository);
        CarColorService carColorService = new CarColorService(catalogSQLRepository, colorSQLRepository, carColorSQLRepository);
        CarVolumeService carVolumeService = new CarVolumeService(catalogSQLRepository, volumeSQLRepository, carVolumeSQLRepository);


        servletContext.setAttribute("userService", userService);
        servletContext.setAttribute("orderService", orderService);
        servletContext.setAttribute("catalogService", catalogService);
        servletContext.setAttribute("colorService", colorService);
        servletContext.setAttribute("volumeService", volumeService);
        servletContext.setAttribute("carcolorService", carColorService);
        servletContext.setAttribute("carvolumeService", carVolumeService);
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}
