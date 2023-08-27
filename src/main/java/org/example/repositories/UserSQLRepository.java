package org.example.repositories;

import org.example.database.DatabaseConnector;
import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserSQLRepository implements CrudRepository<User> {

    private final DatabaseConnector databaseConnector;

    public UserSQLRepository(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;

    }

    @Override
    public void save(User object) {
        String query = """
                       insert into users(name, surname, father_name, dob, sex) values
                       (?, ?, ?, ?, ?)
                       """;

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, object.getName());
            preparedStatement.setString(2, object.getSurname());
            preparedStatement.setString(3, object.getFather_name());
            preparedStatement.setDate(4, object.getDob());
            preparedStatement.setString(5, object.getSex());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User object) {
        String query = """
                update users set name = ?,
                surname = ?,
                father_name = ?,
                dob = ?,
                sex = ?
                where id = ?
                """;

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){

            statement.setString(1, object.getName());
            statement.setString(2, object.getSurname());
            statement.setString(3, object.getFather_name());
            statement.setDate(4, object.getDob());
            statement.setString(5, object.getSex());
            statement.setLong(6, object.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(QuerySpecification spec) {
        String query = "delete from users where " + spec.toSQLClauses();

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> query(QuerySpecification spec) {
        String query = "select * from users where " + spec.toSQLClauses();

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            List<User> resultList = new ArrayList<>();
            while (resultSet.next()){
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String father_name = resultSet.getString("father_name");
                Date dob = resultSet.getDate("dob");
                String sex = resultSet.getString("sex");

                resultList.add(new User(id, name, surname, father_name, dob, sex));
            }
            resultSet.close();
            return resultList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
