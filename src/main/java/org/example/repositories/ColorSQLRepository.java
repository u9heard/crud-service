package org.example.repositories;

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
import java.util.Objects;

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
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, object.getColor());
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
    public void delete(Long id) {
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
    public Color read(Long id) {
        Color searchColor = new Color();
        searchColor.setId(id);
        return query(searchColor).stream().findFirst().orElse(null);
    }

    @Override
    public List<Color> query(Color criteriaModel) {
        StringBuilder query = new StringBuilder("select * from color ");
        List<Object> parameterList = new ArrayList<>();

        if(criteriaModel != null) {
            query.append("where ");
            if (criteriaModel.getId() != null) {
                query.append("id = ? ");
                parameterList.add(criteriaModel.getId());
            }
            if (criteriaModel.getColor() != null) {
                if (!parameterList.isEmpty()) {
                    query.append(" and ");
                }
                query.append("color = ?");
                parameterList.add(criteriaModel.getColor());
            }
        }

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query.toString())) {

            for(int i=0; i<parameterList.size(); i++){
                statement.setObject(i+1, parameterList.get(i));
            }

            try(ResultSet resultSet = statement.executeQuery()) {
                List<Color> resultList = new ArrayList<>();
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String color = resultSet.getString("color");

                    resultList.add(new Color(id, color));
                }
                return resultList;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseReadException(e.getMessage());
        }
    }
}
