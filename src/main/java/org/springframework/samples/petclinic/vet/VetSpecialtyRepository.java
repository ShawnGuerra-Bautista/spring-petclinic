package org.springframework.samples.petclinic.vet;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface VetSpecialtyRepository extends Repository<Vet, Integer> {
    @Transactional(readOnly = true)
    Collection<VetSpecialty> findAll() throws DataAccessException;
}
