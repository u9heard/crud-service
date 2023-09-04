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
import org.example.models.User;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            preparedStatement.setDate(4, Date.valueOf(object.getDob()));
            preparedStatement.setString(5, object.getSex());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseSaveException(e.getMessage());
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
            statement.setDate(4, Date.valueOf(object.getDob()));
            statement.setString(5, object.getSex());
            statement.setLong(6, object.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseUpdateException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) {
        String query = "delete from users where id = ?";

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
    public List<User> getById(Long id) {
        return query(List.of(new SearchCriteria("id", SearchOperator.EQUALS, id)));
    }

    @Override
    public List<User> query(List<SearchCriteria> criteriaList) {
        String query = "select * from users where " + SpecificationBuilder.build(criteriaList);

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            int i = 1;
            for(SearchCriteria criteria : criteriaList){
                statement.setObject(i, criteria.getValue());
                i++;
            }

            System.out.println(statement.toString());

            try(ResultSet resultSet = statement.executeQuery()) {
                List<User> resultList = new ArrayList<>();
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    String father_name = resultSet.getString("father_name");
                    LocalDate dob = resultSet.getDate("dob").toLocalDate();
                    String sex = resultSet.getString("sex");

                    resultList.add(new User(id, name, surname, father_name, dob, sex));
                }
                resultSet.close();
                return resultList;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(query);
            throw new DatabaseReadException(e.getMessage());
        }
    }
}
