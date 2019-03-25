package org.springframework.samples.petclinic.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.OwnerSqliteRepository;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.samples.petclinic.vet.VetRepository;
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

    @Autowired
    public DatabaseForklift(OwnerRepository ownerRepository, PetRepository petRepository, VetRepository vetRepository, VisitRepository visitRepository,
                            OwnerSqliteRepository ownerSqliteRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.vetRepository = vetRepository;
        this.visitRepository = visitRepository;
        this.ownerSqliteRepository = ownerSqliteRepository;
    }


    // TODO end up with only one forklift that calls other fork lifts
    public void forkliftOwners() {
        List<Owner> allExistingOwners = new ArrayList<>(ownerRepository.findAll());
        ownerSqliteRepository.saveAll(allExistingOwners);
    }
}
