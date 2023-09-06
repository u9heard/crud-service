package org.example.repositories;

import org.example.database.DatabaseConnector;
import org.example.exceptions.database.access.DatabaseDeleteException;
import org.example.exceptions.database.access.DatabaseReadException;
import org.example.exceptions.database.access.DatabaseSaveException;
import org.example.exceptions.database.access.DatabaseUpdateException;
import org.example.interfaces.CrudRepository;
import org.example.interfaces.QuerySpecification;
import org.example.models.Catalog;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public void delete(Long id){
        String query = "delete from catalog where id = ?";

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
    public Catalog read(Long id) {
        Catalog searchCatalog = new Catalog();
        searchCatalog.setId(id);
        return query(searchCatalog).stream().findFirst().orElse(null);
    }

    @Override
    public List<Catalog> query(Catalog criteriaModel) {
        StringBuilder query = new StringBuilder("select * from catalog where ");
        List<Object> parameterList = new ArrayList<>();

        if(criteriaModel.getId() != null){
            query.append("id = ? ");
            parameterList.add(criteriaModel.getId());
        }
        if(criteriaModel.getBrand() != null){
            if(!parameterList.isEmpty()){
                query.append(" and ");
            }
            query.append("brand = ? ");
            parameterList.add(criteriaModel.getBrand());
        }
        if(criteriaModel.getModel() != null){
            if(!parameterList.isEmpty()){
                query.append(" and ");
            }
            query.append("model = ? ");
            parameterList.add(criteriaModel.getModel());
        }
        if(criteriaModel.getRelease_date() != null){
            if(!parameterList.isEmpty()){
                query.append(" and ");
            }
            query.append("release_date = ? ");
            parameterList.add(criteriaModel.getRelease_date());
        }
        if(criteriaModel.getPrice() != null ){
            if(!parameterList.isEmpty()){
                query.append(" and ");
            }
            query.append("price = ?");
            parameterList.add(criteriaModel.getPrice());
        }

        try(Connection connection = databaseConnector.getConnection();
            PreparedStatement statement = connection.prepareStatement(query.toString())) {

            for(int i=0; i<parameterList.size(); i++){
                statement.setObject(i+1, parameterList.get(i));
            }

            System.out.println(statement.toString());

            try(ResultSet resultSet = statement.executeQuery()) {
                List<Catalog> resultList = new ArrayList<>();
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String brand = resultSet.getString("brand");
                    String model = resultSet.getString("model");
                    LocalDate release_date = resultSet.getDate("release_date").toLocalDate();
                    BigDecimal price = resultSet.getBigDecimal("price");

                    resultList.add(new Catalog(id, brand, model, release_date, price));
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
