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
            e.printStackTrace();
            throw new DatabaseSaveException(e.getMessage());
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
            e.printStackTrace();
            throw new DatabaseUpdateException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) {
        String query = "delete from volume where id = ?";

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
    public List<Volume> getById(Long id) {
        return query(List.of(new SearchCriteria("id", SearchOperator.EQUALS, id)));
    }

    @Override
    public List<Volume> query(List<SearchCriteria> criteriaList) {
        String query = "select * from volume where " + SpecificationBuilder.build(criteriaList);

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            int i = 1;
            for(SearchCriteria criteria : criteriaList){
                statement.setObject(i, criteria.getValue());
                i++;
            }

            try(ResultSet resultSet = statement.executeQuery()) {
                List<Volume> resultList = new ArrayList<>();
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    double vol = resultSet.getDouble("vol");

                    resultList.add(new Volume(id, vol));
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
