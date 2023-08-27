package org.example.repositories;

import org.example.database.DatabaseConnector;
import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.models.Volume;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VolumeSQLRepository implements CrudRepository<Volume> {

    private DatabaseConnector databaseConnector;

    public VolumeSQLRepository(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    @Override
    public void save(Volume object) {
        String query = """
                       insert into volume(vol) values
                       (?)
                       """;

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDouble(1, object.getVolume());

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Volume object) {
        String query = """
                update volume set vol = ?
                where id = ?
                """;

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){

            statement.setDouble(1, object.getVolume());
            statement.setLong(2, object.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(QuerySpecification spec) {
        String query = "delete from volume where " + spec.toSQLClauses();

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Volume> query(QuerySpecification spec) {
        String query = "select * from volume where " + spec.toSQLClauses();

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            List<Volume> resultList = new ArrayList<>();
            while (resultSet.next()){
                long id = resultSet.getLong("id");
                double vol = resultSet.getDouble("vol");

                resultList.add(new Volume(id, vol));
            }
            resultSet.close();
            return resultList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
