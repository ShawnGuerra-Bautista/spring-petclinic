package org.springframework.samples.petclinic.vet;

import org.springframework.samples.petclinic.db.SqliteRepository;
import org.springframework.samples.petclinic.db.helper.ColumnProvider;
import org.springframework.samples.petclinic.db.helper.SqliteRepositoryHelper;
import org.springframework.samples.petclinic.db.helper.SqliteValuesBuilder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class VetSqliteRepository extends SqliteRepository<Vet> {

    public final String TABLE_NAME = "vets";
    public final String SPECIALTIES_TABLE_NAME = "specialties";
    public final String MAPPING_TABLE_NAME = "vet_specialties";

    enum Column implements ColumnProvider {
        id,
        first_name,
        last_name
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
    public String toValues(Vet vet) {
        return new SqliteValuesBuilder()
            .withInt(vet.getId())
            .withVarchar(vet.getFirstName())
            .withVarchar(vet.getLastName())
            .build();
    }

    @Override
    public void save(Vet vet) {
        super.save(vet);
        //save mappings too
        saveMappingWithSpecialties(vet);
    }

    @Override
    public void saveAll(List<Vet> vets) {
        super.saveAll(vets);
        //save mappings too
        for (Vet vet : vets) {
            saveMappingWithSpecialties(vet);
        }
    }

    private void saveMappingWithSpecialties(Vet vet) {
        for (Specialty specialty : vet.getSpecialtiesInternal()) {
            String vals = new SqliteValuesBuilder()
                .withInt(vet.getId())
                .withInt(specialty.getId())
                .build();
            String mapWithSpecialty = "INSERT OR REPLACE INTO " + MAPPING_TABLE_NAME + " VALUES " + vals;
            executeUpdate(mapWithSpecialty, "Vet Mapping with Specialty");
        }
    }

    @Override
    public Collection<Vet> parseResultSet(ResultSet resultSet) {
        List<Vet> newVets  = new ArrayList<>();
        try{
            while (resultSet.next()) {
                Vet newVet = new Vet(resultSet.getInt("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"));
                newVets.add(newVet);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return newVets;
    }

    public Collection<Vet> findAll(){
        String query = "SELECT * FROM vets";
        return parseResultSet(executeQuery(query));
    }

    public Vet findById(int id){
        String query = "SELECT * FROM vets WHERE id=" + "'" + id + "'";
        return parseResultSet(executeQuery(query)).iterator().next();
    }

}
