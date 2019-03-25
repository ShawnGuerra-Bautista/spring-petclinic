package org.springframework.samples.petclinic.owner;

import org.springframework.samples.petclinic.db.SQLiteDatabase;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.sql.Statement;


@Repository
public class OwnerSqliteRepository {

    public static final String TABLE_NAME = "owners";

    enum Column {
        id,
        first_name,
        last_name,
        address,
        city,
        telephone
    }

    public static String getAllColumns() {

        StringBuilder columns = new StringBuilder("(");

        for(Column col : Column.values()) {
            columns.append(col.toString()).append(", ");
        }

        columns.replace(columns.length() - 2, columns.length(), ")");
        return columns.toString();
    }

    private static String asSqlString(String string) {
        return "\"" + string + "\"";
    }

    public static String toValues(Owner owner) {
        return "(" +
            owner.getId() + ", " +
            asSqlString(owner.getFirstName()) + ", " +
            asSqlString(owner.getLastName()) + ", " +
            asSqlString(owner.getAddress()) + ", " +
            asSqlString(owner.getCity()) + ", " +
            asSqlString(owner.getTelephone()) +
            ")";
    }

    public static String allToValues(List<Owner> owners) {
        StringBuilder values = new StringBuilder();

        for(Owner owner : owners) {
            values.append(toValues(owner)).append(", ");
        }

        values.replace(values.length() - 2, values.length(), "");
        return values.toString();
    }

    public void save(Owner owner) {
        Connection con = SQLiteDatabase.getConnection();
        Statement statement;
        try {
            statement = con.createStatement();
            String insertQuery = "INSERT OR REPLACE INTO " + TABLE_NAME + " " + getAllColumns() + " VALUES " + toValues(owner);
            System.out.println("Running Save All Query... \n" + insertQuery);
            statement.executeUpdate(insertQuery);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveAll(List<Owner> owners) {
        Connection con = SQLiteDatabase.getConnection();
        Statement statement;
        try {
            statement = con.createStatement();
            String insertAllQuery = "INSERT OR REPLACE INTO " + TABLE_NAME + " " + getAllColumns() + " VALUES " + allToValues(owners);
            System.out.println("Running Save All Query... \n" + insertAllQuery);
            statement.executeUpdate(insertAllQuery);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
