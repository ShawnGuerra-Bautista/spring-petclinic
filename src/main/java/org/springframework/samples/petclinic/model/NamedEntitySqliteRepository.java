package org.springframework.samples.petclinic.model;

import org.springframework.samples.petclinic.db.SqliteRepository;
import org.springframework.samples.petclinic.db.helper.ColumnProvider;
import org.springframework.samples.petclinic.db.helper.SqliteRepositoryHelper;
import org.springframework.samples.petclinic.db.helper.SqliteValuesBuilder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Collection;

@Repository
public class NamedEntitySqliteRepository extends SqliteRepository<NamedEntity> {

    public String tableName;

    enum Column implements ColumnProvider {
        id,
        name
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public String getAllColumns() {
        return SqliteRepositoryHelper.getAllColumns(Column.values());
    }

    @Override
    public String toValues(NamedEntity entity) {
        return new SqliteValuesBuilder()
            .withInt(entity.getId())
            .withVarchar(entity.getName())
            .build();
    }

    @Override
    public Collection<NamedEntity> parseResultSet(ResultSet resultSet) {
        return null;
    }

    @Override
    public Collection<NamedEntity> findAll() {
        return null;
    }

    @Override
    public NamedEntity findById(int id) {
        return null;
    }
}
