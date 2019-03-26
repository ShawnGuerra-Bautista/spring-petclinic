package org.springframework.samples.petclinic.db;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

@Repository
public abstract class SqliteRepository<T extends BaseEntity> {

    /**
     * Enforces the name of the db table the Repo refers to
     * @return The name of the db table
     */
    public abstract String getTableName();

    /**
     * Allows getting all columns of the db table the Repo refers to
     * @return All column names of the table
     */
    public abstract String getAllColumns();

    /**
     * Takes an entity and breaks it down into SQLite VALUES section
     * @return all the values
     */
    public abstract String toValues(T entity);

    /**
     * Takes a result set and creates a collection of the superset BaseEntity
     * @return A collection of type BaseEntity
     */
    public abstract Collection<T> parseResultSet(ResultSet resultSet);

    /**
     * Returns a collection of items from the database
     * @return A collection of items from the database
     */
    public abstract Collection<T> findAll();

    /**
     * Returns an item from the database
     * @param id id of the item
     * @return An item from the database
     */
    public abstract T findById(int id);

    /**
     * Same as above, except to refer to multiple entities at once
     * @return comma separated values
     */
    public String allToValues(List<T> entities) {
        StringBuilder values = new StringBuilder();

        for(T entity : entities) {
            values.append(toValues(entity)).append(", ");
        }

        values.replace(values.length() - 2, values.length(), "");
        return values.toString();
    }

    /**
     * Pass any Sqlite Query to make a change in the DB
     * @param query The query to execute
     * @param type Name the query for logging
     */
    public void executeUpdate(String query, String type) {
        Connection con = SQLiteDatabase.getConnection();
        Statement statement;
        try {
            statement = con.createStatement();
            System.out.println("Running " + type +" Query... \n" + query);
            statement.executeUpdate(query);
            System.out.println(type + " Successful");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pass and SQLite query to return a resultSet
     * @param query The query to execute
     * @return A resultset of the data
     */
    public ResultSet executeQuery(String query){
        Connection con = SQLiteDatabase.getConnection();
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = con.createStatement();
            System.out.println(" Query... \n" + query);
            resultSet = statement.executeQuery(query);
            System.out.println(query + " Successful");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * Saves one DB entity row. If new, it's added. If existing, it's replaced
     * @param entity is saved
     */
    public void save(T entity) {
        String saveQuery = "INSERT OR REPLACE INTO " + getTableName() + " " + getAllColumns()
                         + " VALUES " + toValues(entity);
        executeUpdate(saveQuery, "Save to " + getTableName());
    }

    /**
     * Like save, but for a collection of DB entities
     * @param entities are saved
     */
    public void saveAll(List<T> entities) {
        String saveAllQuery = "INSERT OR REPLACE INTO " + getTableName() + " " + getAllColumns()
            + " VALUES " + allToValues(entities);
        executeUpdate(saveAllQuery, "Save All");
    }

}
