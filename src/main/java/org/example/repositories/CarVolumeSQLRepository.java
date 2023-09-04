package org.example.repositories;

import org.example.criteria.SearchCriteria;
import org.example.criteria.SearchOperator;
import org.example.criteria.SpecificationBuilder;
import org.example.database.DatabaseConnector;
import org.example.exceptions.database.access.DatabaseDeleteException;
import org.example.exceptions.database.access.DatabaseReadException;
import org.example.exceptions.database.access.DatabaseSaveException;
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
            e.printStackTrace();
            throw new DatabaseSaveException(e.getMessage());
        }
    }

    @Override
    public void update(CarVolume object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Long id) {
        String query = "delete from car_volume where id = ?";

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseDeleteException(e.getMessage());
        }
    }

    @Override
    public List<CarVolume> getById(Long id) {
        return query(List.of(new SearchCriteria("id_volume", SearchOperator.EQUALS, id)));
    }

    @Override
    public List<CarVolume> query(List<SearchCriteria> criteriaList) {
        String query = "select * from car_volume where " + SpecificationBuilder.build(criteriaList);

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            int i = 1;
            for(SearchCriteria criteria : criteriaList){
                statement.setObject(i, criteria.getValue());
                i++;
            }

            try(ResultSet resultSet = statement.executeQuery()) {
                List<CarVolume> resultList = new ArrayList<>();
                while (resultSet.next()) {
                    Long id_car = resultSet.getLong("id_car");
                    Long id_volume = resultSet.getLong("id_volume");

                    resultList.add(new CarVolume(id_car, id_volume));
                }
                resultSet.close();
                return resultList;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseReadException(e.getMessage());
        }
    }
}
