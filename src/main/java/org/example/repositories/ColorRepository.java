package org.example.repositories;

import org.example.database.DatabaseService;
import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.models.Color;
import org.example.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColorRepository implements CrudRepository<Color, Long> {

    private DatabaseService databaseService;

    public ColorRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public Color getById(Long id) {
        String query = "SELECT * FROM color WHERE id = ?";

        try(Connection connection = databaseService.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Color(resultSet.getLong("id"),
                        resultSet.getString("color"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void save(Color object) {
        String query = """
                       insert into color(color) values
                       (?)
                       """;

        try(Connection connection = databaseService.getConnection();
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
                update color set color = ?,
                where id = ?
                """;

        try(Connection connection = databaseService.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){

            statement.setString(1, object.getColor());
            statement.setLong(2, object.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        String query = """
                        delete from color
                        where id = ?
                       """;

        try(Connection connection = databaseService.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Color> query(QuerySpecification spec) {
        String query = "select * from users where " + spec.toSQLClauses();

        try(Connection connection = databaseService.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            List<Color> resultList = new ArrayList<>();
            while (resultSet.next()){
                long id = resultSet.getLong("id");
                String color = resultSet.getString("color");

                resultList.add(new Color(id, color));
            }

            return resultList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
