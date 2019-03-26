package org.springframework.samples.petclinic.owner;

import org.springframework.samples.petclinic.db.SqliteRepository;
import org.springframework.samples.petclinic.db.helper.ColumnProvider;
import org.springframework.samples.petclinic.db.helper.SqliteRepositoryHelper;
import org.springframework.samples.petclinic.db.helper.SqliteValuesBuilder;
import org.springframework.stereotype.Repository;


@Repository
public class OwnerSqliteRepository extends SqliteRepository<Owner> {

    public final String TABLE_NAME = "owners";

    enum Column implements ColumnProvider {
        id,
        first_name,
        last_name,
        address,
        city,
        telephone
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getAllColumns() {
        return SqliteRepositoryHelper.getAllColumns(Column.values());
    }

    @Override
    public String toValues(Owner owner) {
        return new SqliteValuesBuilder()
            .withInt(owner.getId())
            .withVarchar(owner.getFirstName())
            .withVarchar(owner.getLastName())
            .withVarchar(owner.getAddress())
            .withVarchar(owner.getCity())
            .withVarchar(owner.getTelephone())
            .build();
    }
}
