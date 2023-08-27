package org.example.repositories;

import org.example.database.DatabaseConnector;
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
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(QuerySpecification spec) {
        String query = "delete from color where " + spec.toSQLClauses();

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Color> query(QuerySpecification spec) {
        String query = "select * from color where " + spec.toSQLClauses();

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            List<Color> resultList = new ArrayList<>();
            while (resultSet.next()){
                long id = resultSet.getLong("id");
                String color = resultSet.getString("color");

                resultList.add(new Color(id, color));
            }
            resultSet.close();
            return resultList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
