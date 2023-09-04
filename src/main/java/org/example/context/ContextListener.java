package org.example.context;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.example.database.DatabaseConnector;
import org.example.interfaces.ModelParser;
import org.example.interfaces.ModelValidator;
import org.example.models.*;
import org.example.parsers.JsonModelParser;
import org.example.repositories.*;
import org.example.services.*;
import org.example.validators.*;

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

        ModelParser<User> userModelParser = new JsonModelParser<>(User.class);
        ModelParser<Catalog> catalogModelParser = new JsonModelParser<>(Catalog.class);
        ModelParser<Order> orderModelParser = new JsonModelParser<>(Order.class);
        ModelParser<Color> colorModelParser = new JsonModelParser<>(Color.class);
        ModelParser<Volume> volumeModelParser = new JsonModelParser<>(Volume.class);
        ModelParser<CarColor> carColorModelParser = new JsonModelParser<>(CarColor.class);
        ModelParser<CarVolume> carVolumeModelParser = new JsonModelParser<>(CarVolume.class);

        UserValidator userValidator = new UserValidator();
        CatalogValidator catalogValidator = new CatalogValidator();
        OrderValidator orderValidator = new OrderValidator();
        ColorValidator colorValidator = new ColorValidator();
        VolumeValidator volumeValidator = new VolumeValidator();
        CarColorValidator carColorValidator = new CarColorValidator();
        CarVolumeValidator carVolumeValidator = new CarVolumeValidator();

        servletContext.setAttribute("userService", userService);
        servletContext.setAttribute("orderService", orderService);
        servletContext.setAttribute("catalogService", catalogService);
        servletContext.setAttribute("colorService", colorService);
        servletContext.setAttribute("volumeService", volumeService);
        servletContext.setAttribute("carcolorService", carColorService);
        servletContext.setAttribute("carvolumeService", carVolumeService);

        servletContext.setAttribute("userParser", userModelParser);
        servletContext.setAttribute("catalogParser", catalogModelParser);
        servletContext.setAttribute("orderParser", orderModelParser);
        servletContext.setAttribute("colorParser", colorModelParser);
        servletContext.setAttribute("volumeParser", volumeModelParser);
        servletContext.setAttribute("carcolorParser", carColorModelParser);
        servletContext.setAttribute("carvolumeParser", carVolumeModelParser);

        servletContext.setAttribute("userValidator", userValidator);
        servletContext.setAttribute("catalogValidator", catalogValidator);
        servletContext.setAttribute("orderValidator", orderValidator);
        servletContext.setAttribute("colorValidator", colorValidator);
        servletContext.setAttribute("volumeValidator", volumeValidator);
        servletContext.setAttribute("carcolorValidator", carColorValidator);
        servletContext.setAttribute("carvolumeValidator", carVolumeValidator);

    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}
