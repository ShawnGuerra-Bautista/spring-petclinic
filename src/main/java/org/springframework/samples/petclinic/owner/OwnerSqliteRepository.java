package org.springframework.samples.petclinic.owner;

import org.springframework.samples.petclinic.db.SqliteRepository;
import org.springframework.stereotype.Repository;


@Repository
public class OwnerSqliteRepository extends SqliteRepository<Owner> {

    public final String TABLE_NAME = "owners";

    enum Column {
        id,
        first_name,
        last_name,
        address,
        city,
        telephone
    }

    public String getTableName() {
        return TABLE_NAME;
    }

    public String getAllColumns() {

        StringBuilder columns = new StringBuilder("(");

        for(Column col : Column.values()) {
            columns.append(col.toString()).append(", ");
        }

        columns.replace(columns.length() - 2, columns.length(), ")");
        return columns.toString();
    }

    public String toValues(Owner owner) {
        return "(" +
            owner.getId() + ", " +
            asSqlString(owner.getFirstName()) + ", " +
            asSqlString(owner.getLastName()) + ", " +
            asSqlString(owner.getAddress()) + ", " +
            asSqlString(owner.getCity()) + ", " +
            asSqlString(owner.getTelephone()) +
            ")";
    }
}
