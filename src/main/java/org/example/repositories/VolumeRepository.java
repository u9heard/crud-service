package org.example.repositories;

import org.example.database.DatabaseService;
import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.models.User;
import org.example.models.Volume;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VolumeRepository implements CrudRepository<Volume, Long> {

    private DatabaseService databaseService;

    public VolumeRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public Volume getById(Long id) {
        String query = "SELECT * FROM volume WHERE id = ?";

        try(Connection connection = databaseService.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                return new Volume(result.getLong("id"),
                        result.getDouble("vol"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void save(Volume object) {
        String query = """
                       insert into volume(vol) values
                       (?)
                       """;

        try(Connection connection = databaseService.getConnection();
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
                update volume set vol = ?,
                where id = ?
                """;

        try(Connection connection = databaseService.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){

            statement.setDouble(1, object.getVolume());
            statement.setLong(2, object.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        String query = """
                        delete from volume
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
    public List<Volume> query(QuerySpecification spec) {
        String query = "select * from volume where " + spec.toSQLClauses();

        try(Connection connection = databaseService.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            List<Volume> resultList = new ArrayList<>();
            while (resultSet.next()){
                long id = resultSet.getLong("id");
                double vol = resultSet.getDouble("vol");

                resultList.add(new Volume(id, vol));
            }

            return resultList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
