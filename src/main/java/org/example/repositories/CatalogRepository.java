package org.example.repositories;

import liquibase.CatalogAndSchema;
import org.example.database.DatabaseService;
import org.example.models.Catalog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CatalogRepository {
    private DatabaseService databaseService;

    public CatalogRepository(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public Catalog getById(Long id){
        String query = "SELECT * FROM catalog WHERE id = ?";

        try(Connection connection = databaseService.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if(result.next())
                return new Catalog(
                        result.getLong("id"),
                        result.getString("brand"),
                        result.getString("model"),
                        result.getDate("release_date"),
                        result.getBigDecimal("price"));
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Catalog object){
        String query = """
                       insert into catalog(brand, model, release_date, price) values
                       (?, ?, ?, ?)
                       """;

        try(Connection connection = databaseService.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, object.getBrand());
            statement.setString(2, object.getModel());
            statement.setDate(3, object.getRelease_date());
            statement.setBigDecimal(4, object.getPrice());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
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

        try(Connection connection = databaseService.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, object.getBrand());
            statement.setString(2, object.getModel());
            statement.setDate(3, object.getRelease_date());
            statement.setBigDecimal(4, object.getPrice());
            statement.setLong(5, object.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long id){
        String query = """
                        delete from catalog
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
}
