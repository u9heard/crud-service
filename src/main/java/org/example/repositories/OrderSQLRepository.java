package org.example.repositories;

import org.example.database.DatabaseConnector;
import org.example.exceptions.database.access.DatabaseDeleteException;
import org.example.exceptions.database.access.DatabaseReadException;
import org.example.exceptions.database.access.DatabaseSaveException;
import org.example.exceptions.database.access.DatabaseUpdateException;
import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.models.Order;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderSQLRepository implements CrudRepository<Order> {
    private final DatabaseConnector databaseConnector;

    public OrderSQLRepository(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    @Override
    public void save(Order object) {
        String query = """
                       insert into orders(id_user, id_car, id_color, id_vol, date_buy) values
                       (?, ?, ?, ?, ?)
                       """;

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setLong(1, object.getIdUser());
            preparedStatement.setLong(2, object.getIdCar());
            preparedStatement.setLong(3, object.getIdColor());
            preparedStatement.setLong(4, object.getIdVolume());
            preparedStatement.setDate(5, Date.valueOf(object.getDateBuy()));

            preparedStatement.executeUpdate();

            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()){
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
    public void update(Order object) {
        String query = """
                update orders set id_user = ?,
                id_car = ?,
                id_color = ?,
                id_vol = ?,
                date_buy = ?
                where id = ?
                """;

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){

            statement.setLong(1, object.getIdUser());
            statement.setLong(2, object.getIdCar());
            statement.setLong(3, object.getIdColor());
            statement.setLong(4, object.getIdVolume());
            statement.setDate(5, Date.valueOf(object.getDateBuy()));
            statement.setLong(6, object.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseUpdateException(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String query = "delete from orders where id = ?";

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
    public Order read(Long id) {
        Order searchOrder = new Order();
        searchOrder.setId(id);
        return query(searchOrder).stream().findFirst().orElse(null);
    }

    @Override
    public List<Order> query(Order criteriaModel) {
        StringBuilder query = new StringBuilder("select * from orders ");
        List<Object> parameterList = new ArrayList<>();

        if(criteriaModel != null) {
            query.append("where ");
            if (criteriaModel.getId() != null) {
                query.append("id = ? ");
                parameterList.add(criteriaModel.getId());
            }
            if (criteriaModel.getIdCar() != null) {
                if (!parameterList.isEmpty()) {
                    query.append(" and ");
                }
                query.append("id_car = ? ");
                parameterList.add(criteriaModel.getIdCar());
            }
            if (criteriaModel.getIdUser() != null) {
                if (!parameterList.isEmpty()) {
                    query.append(" and ");
                }
                query.append("id_user = ? ");
                parameterList.add(criteriaModel.getIdUser());
            }
            if (criteriaModel.getIdColor() != null) {
                if (!parameterList.isEmpty()) {
                    query.append(" and ");
                }
                query.append("id_color = ? ");
                parameterList.add(criteriaModel.getIdColor());
            }
            if (criteriaModel.getIdVolume() != null) {
                if (!parameterList.isEmpty()) {
                    query.append(" and ");
                }
                query.append("id_volume = ? ");
                parameterList.add(criteriaModel.getIdVolume());
            }
        }

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query.toString())) {

            for(int i=0; i<parameterList.size(); i++){
                statement.setObject(i+1, parameterList.get(i));
            }

            try(ResultSet resultSet = statement.executeQuery()) {
                List<Order> resultList = new ArrayList<>();
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    Long id_user = resultSet.getLong("id_user");
                    Long id_car = resultSet.getLong("id_car");
                    Long id_color = resultSet.getLong("id_color");
                    Long id_vol = resultSet.getLong("id_vol");
                    LocalDate date_buy = resultSet.getDate("date_buy").toLocalDate();
                    resultList.add(new Order(id, id_user, id_car, id_vol, id_color, date_buy));
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
