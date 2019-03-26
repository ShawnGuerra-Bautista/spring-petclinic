package org.springframework.samples.petclinic.visit;

import org.springframework.samples.petclinic.db.SqliteRepository;
import org.springframework.samples.petclinic.db.helper.ColumnProvider;
import org.springframework.samples.petclinic.db.helper.SqliteRepositoryHelper;
import org.springframework.samples.petclinic.db.helper.SqliteValuesBuilder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Collection;

@Repository
public class VisitSqliteRepository extends SqliteRepository<Visit> {

    public final String TABLE_NAME = "visits";

    enum Column implements ColumnProvider {
        id,
        pet_id,
        visit_date,
        description
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
    public String toValues(Visit visit) {
        return new SqliteValuesBuilder()
            .withInt(visit.getId())
            .withInt(visit.getPetId())
            .withDate(visit.getDate())
            .withVarchar(visit.getDescription())
            .build();
    }

    @Override
    public Collection<Visit> parseResultSet(ResultSet resultSet) {
        return null;
    }
}
