package org.example.repositories;

import org.example.database.DatabaseConnector;
import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.models.CarVolume;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarVolumeSQLRepository implements CrudRepository<CarVolume> {

    private DatabaseConnector databaseConnector;

    public CarVolumeSQLRepository(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    @Override
    public void save(CarVolume object) {
        String query = """
                       insert into car_volume(id_car, id_volume) values
                       (?, ?)
                       on conflict do nothing
                       """;

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, object.getIdCar());
            preparedStatement.setLong(2, object.getIdVolume());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(CarVolume object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(QuerySpecification spec) {
        String query = "delete from car_volume where " + spec.toSQLClauses();

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CarVolume> query(QuerySpecification spec) {
        String query = String.format("select * from car_volume where %s ", spec.toSQLClauses());

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            List<CarVolume> resultList = new ArrayList<>();
            while (resultSet.next()){
                Long id_car = resultSet.getLong("id_car");
                Long id_volume = resultSet.getLong("id_volume");

                resultList.add(new CarVolume(id_car, id_volume));
            }
            resultSet.close();
            return resultList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
