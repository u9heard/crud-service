package org.example.repositories;

import org.example.criteria.SearchCriteria;
import org.example.criteria.SearchOperator;
import org.example.criteria.SpecificationBuilder;
import org.example.database.DatabaseConnector;
import org.example.exceptions.database.access.DatabaseDeleteException;
import org.example.exceptions.database.access.DatabaseReadException;
import org.example.exceptions.database.access.DatabaseSaveException;
import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.models.CarColor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarColorSQLRepository implements CrudRepository<CarColor> {
    private DatabaseConnector databaseConnector;

    public CarColorSQLRepository(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    @Override
    public void save(CarColor object) {
        String query = """
                       insert into car_color(id_car, id_color) values
                       (?, ?)
                       on conflict do nothing
                       """;

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, object.getIdCar());
            preparedStatement.setLong(2, object.getIdColor());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseSaveException(e.getMessage());
        }
    }

    @Override
    public void update(CarColor object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) {
        String query = "delete from car_color where id = ?";

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
    public CarColor read(Long id) {
        CarColor searchCarColor = new CarColor();
        searchCarColor.setId(id);
        return query(searchCarColor).stream().findFirst().orElse(null);
    }

    @Override
    public List<CarColor> query(CarColor criteriaModel) {
        StringBuilder query = new StringBuilder("select * from car_color where ");
        List<Object> parameterList = new ArrayList<>();

        if(criteriaModel.getId() != null){
            query.append("id = ? ");
            parameterList.add(criteriaModel.getId());
        }
        if(criteriaModel.getIdCar() != null){
            if(!parameterList.isEmpty()){
                query.append(" and ");
            }
            query.append("id_car = ? ");
            parameterList.add(criteriaModel.getIdCar());
        }
        if(criteriaModel.getIdColor() != null){
            if(!parameterList.isEmpty()){
                query.append(" and ");
            }
            query.append("id_color = ?");
            parameterList.add(criteriaModel.getIdColor());
        }

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query.toString())) {

            for(int i = 0; i<parameterList.size(); i++){
                statement.setObject(i+1, parameterList.get(i));
            }

            try(ResultSet resultSet = statement.executeQuery()) {

                List<CarColor> resultList = new ArrayList<>();
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    Long id_car = resultSet.getLong("id_car");
                    Long id_color = resultSet.getLong("id_color");

                    resultList.add(new CarColor(id, id_car, id_color));
                }
                return resultList;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseReadException(e.getMessage());
        }
    }
}
