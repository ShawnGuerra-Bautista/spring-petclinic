package org.springframework.samples.petclinic.db;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public abstract class SqliteRepository<T extends BaseEntity> {

    public abstract String getTableName();
    public abstract String getAllColumns();
    public abstract String toValues(T entity);

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
