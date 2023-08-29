package org.example.repositories;

import org.example.database.DatabaseConnector;
import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.models.CarColor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarColorSQLRepository implements CrudRepository<CarColor> {
    private DatabaseConnector databaseConnector;

    public CarColorSQLRepository(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    @Override
    public void save(CarColor object) {
        String query = """
                       insert into car_color(id_car, id_color) values
                       (?, ?)
                       on conflict do nothing
                       """;

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, object.getIdCar());
            preparedStatement.setLong(2, object.getIdColor());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(CarColor object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(QuerySpecification spec) {
        String query = "delete from car_color where " + spec.toSQLClauses();

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CarColor> query(QuerySpecification spec) {
        String query = String.format("select * from car_color where %s", spec.toSQLClauses());

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            List<CarColor> resultList = new ArrayList<>();
            while (resultSet.next()){
                Long id_car = resultSet.getLong("id_car");
                Long id_color = resultSet.getLong("id_color");

                resultList.add(new CarColor(id_car, id_color));
            }
            resultSet.close();
            return resultList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
