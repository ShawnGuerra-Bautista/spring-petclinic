package org.springframework.samples.petclinic.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.stereotype.Service;

@Service
public class DatabaseForklift {

    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
    private final VetRepository vetRepository;
    private final VisitRepository visitRepository;

    @Autowired
    public DatabaseForklift(OwnerRepository ownerRepository, PetRepository petRepository, VetRepository vetRepository, VisitRepository visitRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.vetRepository = vetRepository;
        this.visitRepository = visitRepository;
    }

    
}
