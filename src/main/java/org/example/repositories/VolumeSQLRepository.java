package org.example.repositories;

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
import java.util.Objects;

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
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setDouble(1, object.getVolume());

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                if(resultSet.next()){
                    object.setId(resultSet.getLong(1));
                }
                else{
                    throw new DatabaseSaveException(String.format("The request was completed but no data was returned: %s", preparedStatement.toString()));
                }
            }
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
    public void delete(Long id) {
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
    public Volume read(Long id) {
        Volume searchVolume = new Volume();
        searchVolume.setId(id);
        return query(searchVolume).stream().findFirst().orElse(null);
    }

    @Override
    public List<Volume> query(Volume criteriaModel) {
        StringBuilder query = new StringBuilder("select * from volume where ");
        List<Object> parameterList = new ArrayList<>();

        if(criteriaModel.getId() != null){
            query.append("id = ? ");
            parameterList.add(criteriaModel.getId());
        }
        if(criteriaModel.getVolume() != null){
            if(!parameterList.isEmpty()){
                query.append(" and ");
            }
            query.append("vol = ? ");
            parameterList.add(criteriaModel.getVolume());
        }


        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query.toString())) {

            for(int i=0; i<parameterList.size(); i++){
                statement.setObject(i+1, parameterList.get(i));
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
