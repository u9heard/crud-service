package org.example.repositories;

import org.example.criteria.SearchCriteria;
import org.example.criteria.SearchOperator;
import org.example.criteria.SpecificationBuilder;
import org.example.database.DatabaseConnector;
import org.example.exceptions.database.access.DatabaseDeleteException;
import org.example.exceptions.database.access.DatabaseReadException;
import org.example.exceptions.database.access.DatabaseSaveException;
import org.example.exceptions.database.access.DatabaseUpdateException;
import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.models.Color;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColorSQLRepository implements CrudRepository<Color> {

    private DatabaseConnector databaseConnector;

    public ColorSQLRepository(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    @Override
    public void save(Color object) {
        String query = """
                       insert into color(color) values
                       (?)
                       """;

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, object.getColor());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseSaveException(e.getMessage());
        }
    }

    @Override
    public void update(Color object) {
        String query = """
                update color set color = ?
                where id = ?
                """;

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){

            statement.setString(1, object.getColor());
            statement.setLong(2, object.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseUpdateException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) {
        String query = "delete from color where id = ?";

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
    public List<Color> getById(Long id) {
        return query(List.of(new SearchCriteria("id", SearchOperator.EQUALS, id)));
    }

    @Override
    public List<Color> query(List<SearchCriteria> criteriaList) {
        String query = "select * from color where " + SpecificationBuilder.build(criteriaList);

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            int i = 1;
            for(SearchCriteria criteria : criteriaList){
                statement.setObject(i, criteria.getValue());
                i++;
            }

            try(ResultSet resultSet = statement.executeQuery()) {
                List<Color> resultList = new ArrayList<>();
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String color = resultSet.getString("color");

                    resultList.add(new Color(id, color));
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
