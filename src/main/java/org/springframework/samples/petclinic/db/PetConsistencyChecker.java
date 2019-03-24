package org.springframework.samples.petclinic.db;

import java.util.Collection;

import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetRepository;


public class PetConsistencyChecker {

	private final PetRepository petRepo;
	
	public PetConsistencyChecker() {
		petRepo=null;
	}
	
	public PetConsistencyChecker(PetRepository petRepo) {
		this.petRepo=petRepo;
	}
	
	public Collection<Pet> getVetList(){
		return this.petRepo.findAll();
	}
}
