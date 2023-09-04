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
import org.example.models.CarColor;

import java.sql.*;
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
            e.printStackTrace();
            throw new DatabaseSaveException(e.getMessage());
        }
    }

    @Override
    public void update(CarColor object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Long id) {
        String query = "delete from car_color where id = ?";

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
    public List<CarColor> getById(Long id) {
        return query(List.of(new SearchCriteria("id_car", SearchOperator.EQUALS, id)));
    }

    @Override
    public List<CarColor> query(List<SearchCriteria> criteriaList) {

        String query = "select * from car_color where " + SpecificationBuilder.build(criteriaList);

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            int i = 1;
            for(SearchCriteria criteria : criteriaList){
                statement.setObject(i, criteria.getValue());
                i++;
            }

            try(ResultSet resultSet = statement.executeQuery()) {

                List<CarColor> resultList = new ArrayList<>();
                while (resultSet.next()) {
                    Long id_car = resultSet.getLong("id_car");
                    Long id_color = resultSet.getLong("id_color");

                    resultList.add(new CarColor(id_car, id_color));
                }
                return resultList;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseReadException(e.getMessage());
        }
    }
}
