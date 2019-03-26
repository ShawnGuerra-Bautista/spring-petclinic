package org.springframework.samples.petclinic.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.NamedEntity;
import org.springframework.samples.petclinic.model.NamedEntitySqliteRepository;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.samples.petclinic.vet.VetSqliteRepository;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseForklift {

    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
    private final VetRepository vetRepository;
    private final VisitRepository visitRepository;

    private final OwnerSqliteRepository ownerSqliteRepository;
    private final PetSqliteRepository petSqliteRepository;
    private final VetSqliteRepository vetSqliteRepository;
    private final NamedEntitySqliteRepository namedEntitySqliteRepository;


    @Autowired
    public DatabaseForklift(OwnerRepository ownerRepository, PetRepository petRepository, VetRepository vetRepository, VisitRepository visitRepository,
                            OwnerSqliteRepository ownerSqliteRepository, PetSqliteRepository petSqliteRepository, VetSqliteRepository vetSqliteRepository, NamedEntitySqliteRepository namedEntitySqliteRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.vetRepository = vetRepository;
        this.visitRepository = visitRepository;

        this.ownerSqliteRepository = ownerSqliteRepository;
        this.petSqliteRepository = petSqliteRepository;
        this.vetSqliteRepository = vetSqliteRepository;
        this.namedEntitySqliteRepository = namedEntitySqliteRepository;
    }

    public void forklift() {
        forkliftOwners();
        forkliftPets();
        forkliftVets();
    }

    // TODO end up with only one forklift that calls other fork lifts
    private void forkliftOwners() {
        List<Owner> existingOwners = new ArrayList<>(ownerRepository.findAll());
        ownerSqliteRepository.saveAll(existingOwners);
    }

    private void forkliftPets() {
        List<Pet> existingPets = new ArrayList<>(petRepository.findAll());
        petSqliteRepository.saveAll(existingPets);

        // also add the PetTypes too
        List<NamedEntity> existingPetTypes = (List<NamedEntity>) (List<?>) petRepository.findPetTypes();
        namedEntitySqliteRepository.setTableName(petSqliteRepository.TYPE_TABLE_NAME);
        namedEntitySqliteRepository.saveAll(existingPetTypes);
    }

    private void forkliftVets() {
        List<Vet> existingVets = new ArrayList<>(vetRepository.findAll());
        vetSqliteRepository.saveAll(existingVets);

        // also add the Specialties too
        List<NamedEntity> existingSpecialties = (List<NamedEntity>) (List<?>) vetRepository.findSpecialties();
        namedEntitySqliteRepository.setTableName(vetSqliteRepository.SPECIALTIES_TABLE_NAME);
        namedEntitySqliteRepository.saveAll(existingSpecialties);
    }
}
