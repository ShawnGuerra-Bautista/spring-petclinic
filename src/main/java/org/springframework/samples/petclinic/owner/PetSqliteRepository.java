package org.springframework.samples.petclinic.owner;

import org.springframework.samples.petclinic.db.SqliteRepository;
import org.springframework.samples.petclinic.db.helper.ColumnProvider;
import org.springframework.samples.petclinic.db.helper.SqliteRepositoryHelper;
import org.springframework.samples.petclinic.db.helper.SqliteValuesBuilder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Collection;

@Repository
public class PetSqliteRepository extends SqliteRepository<Pet> {

    public final String TABLE_NAME = "pets";
    public final String TYPE_TABLE_NAME = "types";

    enum Column implements ColumnProvider {
        id,
        name,
        birth_date,
        type_id,
        owner_id
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
    public String toValues(Pet pet) {
        return new SqliteValuesBuilder()
            .withInt(pet.getId())
            .withVarchar(pet.getName())
            .withDate(pet.getBirthDate())
            .withInt(pet.getType().getId())
            .withInt(pet.getOwner().getId())
            .build();
    }

    @Override
    public Collection<Pet> parseResultSet(ResultSet resultSet) {
        return null;
    }

    @Override
    public Collection<Pet> findAll() {
        return null;
    }

    @Override
    public Pet findById(int id) {
        return null;
    }
}
