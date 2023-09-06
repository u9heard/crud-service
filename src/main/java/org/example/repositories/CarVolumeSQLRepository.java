package org.example.repositories;

import org.example.database.DatabaseConnector;
import org.example.exceptions.database.access.DatabaseDeleteException;
import org.example.exceptions.database.access.DatabaseReadException;
import org.example.exceptions.database.access.DatabaseSaveException;
import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.models.CarColor;
import org.example.models.CarVolume;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CarVolumeSQLRepository implements CrudRepository<CarVolume> {

    private DatabaseConnector databaseConnector;

    public CarVolumeSQLRepository(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    @Override
    public void save(CarVolume object) {
        String query = """
                       insert into car_volume(id_car, id_volume) values
                       (?, ?)
                       on conflict do nothing
                       """;

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, object.getIdCar());
            preparedStatement.setLong(2, object.getIdVolume());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseSaveException(e.getMessage());
        }
    }

    @Override
    public void update(CarVolume object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) {
        String query = "delete from car_volume where id = ?";

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
    public CarVolume read(Long id) {
        CarVolume searchModel = new CarVolume();
        searchModel.setId(id);
        return query(searchModel).stream().findFirst().orElse(null);
    }

    @Override
    public List<CarVolume> query(CarVolume criteriaModel) {
        StringBuilder query = new StringBuilder("select * from car_volume where ");
        List<Object> parametersList = new ArrayList<>();

        if(criteriaModel.getId() != null){
            query.append("id = ? ");
            parametersList.add(criteriaModel.getId());
        }
        if(criteriaModel.getIdCar() != null){
            if(!parametersList.isEmpty()){
                query.append(" and ");
            }
            query.append("id_car = ? ");
            parametersList.add(criteriaModel.getIdCar());
        }
        if(criteriaModel.getIdVolume() != null){
            if(!parametersList.isEmpty()){
                query.append(" and ");
            }
            query.append("id_volume = ?");
            parametersList.add(criteriaModel.getIdVolume());
        }

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query.toString())) {

            for(int i=0; i<parametersList.size(); i++){
                statement.setObject(i+1, parametersList.get(i));
            }

            try(ResultSet resultSet = statement.executeQuery()) {
                List<CarVolume> resultList = new ArrayList<>();
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    Long id_car = resultSet.getLong("id_car");
                    Long id_volume = resultSet.getLong("id_volume");

                    resultList.add(new CarVolume(id, id_car, id_volume));
                }
                return resultList;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseReadException(e.getMessage());
        }
    }
}
