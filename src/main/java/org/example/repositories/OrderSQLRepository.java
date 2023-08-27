package org.example.repositories;

import org.example.database.DatabaseConnector;
import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.models.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderSQLRepository implements CrudRepository<Order> {
    private DatabaseConnector databaseConnector;

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
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, object.getIdUser());
            preparedStatement.setLong(2, object.getIdCar());
            preparedStatement.setLong(3, object.getIdColor());
            preparedStatement.setLong(4, object.getIdVolume());
            preparedStatement.setDate(5, object.getDateBuy());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            statement.setDate(5, object.getDateBuy());
            statement.setLong(6, object.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(QuerySpecification spec) {
        String query = "delete from orders where " + spec.toSQLClauses();

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> query(QuerySpecification spec) {
        String query = "select * from orders where " + spec.toSQLClauses();

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            List<Order> resultList = new ArrayList<>();
            while (resultSet.next()){
                Long id = resultSet.getLong("id");
                Long id_user = resultSet.getLong("id_user");
                Long id_car = resultSet.getLong("id_car");
                Long id_color = resultSet.getLong("id_color");
                Long id_vol = resultSet.getLong("id_vol");
                Date date_buy = resultSet.getDate("date_buy");

                resultList.add(new Order(id, id_user, id_car, id_vol, id_color, date_buy));
            }
            resultSet.close();
            return resultList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
