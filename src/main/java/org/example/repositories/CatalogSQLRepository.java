package org.example.repositories;

import org.example.database.DatabaseConnector;
import org.example.exceptions.database.access.DatabaseDeleteException;
import org.example.exceptions.database.access.DatabaseReadException;
import org.example.exceptions.database.access.DatabaseSaveException;
import org.example.exceptions.database.access.DatabaseUpdateException;
import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.models.Catalog;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CatalogSQLRepository implements CrudRepository<Catalog> {
    private final DatabaseConnector databaseConnector;

    public CatalogSQLRepository(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }


    public void save(Catalog object){
        String query = """
                       insert into catalog(brand, model, release_date, price) values
                       (?, ?, ?, ?)
                       """;

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, object.getBrand());
            statement.setString(2, object.getModel());
            statement.setDate(3, new Date(object.getRelease_date().toEpochDay()));
            statement.setBigDecimal(4, object.getPrice());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseSaveException(e.getMessage());
        }
    }

    public void update(Catalog object){
        String query = """
                update catalog set brand = ?,
                model = ?,
                release_date = ?,
                price = ?
                where id = ?
                """;

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, object.getBrand());
            statement.setString(2, object.getModel());
            statement.setDate(3, new Date(object.getRelease_date().toEpochDay()));
            statement.setBigDecimal(4, object.getPrice());
            statement.setLong(5, object.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseUpdateException(e.getMessage());
        }
    }

    public void delete(QuerySpecification spec){
        String query = "delete from catalog where " + spec.toSQLClauses();

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseDeleteException(e.getMessage());
        }
    }

    @Override
    public List<Catalog> query(QuerySpecification spec) {
        String query = "select * from catalog where " + spec.toSQLClauses();

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();
            List<Catalog> resultList = new ArrayList<>();
            while (resultSet.next()){
                long id = resultSet.getLong("id");
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                LocalDate release_date = resultSet.getDate("release_date").toLocalDate();
                BigDecimal price = resultSet.getBigDecimal("price");

                resultList.add(new Catalog(id, brand, model, release_date, price));
            }
            resultSet.close();
            return resultList;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseReadException(e.getMessage());
        }
    }
}
