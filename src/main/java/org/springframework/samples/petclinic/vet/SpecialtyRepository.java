package org.springframework.samples.petclinic.vet;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface SpecialtyRepository extends Repository<Vet, Integer> {
    @Transactional(readOnly = true)
    Collection<Specialty> findAll() throws DataAccessException;
}
