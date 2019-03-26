package org.springframework.samples.petclinic.db;

import java.util.Collection;

import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.samples.petclinic.vet.VetSqliteRepository;
import org.springframework.stereotype.Service;

@Service
public class VetConsistencyChecker implements ConsistencyChecker<Vet> {

	private final VetRepository oldVetRepository;
	private final VetSqliteRepository newVetRepository;

	public VetConsistencyChecker(VetRepository oldVetRepository, VetSqliteRepository newVetRepository) {
	    this.newVetRepository = newVetRepository;
		this.oldVetRepository = oldVetRepository;
	}

    @Override
    public int checkConsistency() {
        int inconsistency = 0;
        Collection<Vet> oldVets = getOldEntities();
        Collection<Vet> newVets = getNewEntities();

        for(Vet expectedVet: oldVets){
            Vet actualVet = newVets.iterator().next();
            System.out.println(actualVet + " " + expectedVet);
            if(!expectedVet.equals(actualVet)) {
                inconsistency+=1;
                violation(expectedVet, actualVet);
                fixViolation(actualVet);
            }
        }
        return inconsistency;
    }

    @Override
    public void violation(Vet expectedVet, Vet actualVet) {
        System.out.println("Violation: " +
            "\n Expected: " + expectedVet +
            "\n Actual: " + actualVet);
    }

    @Override
    public void fixViolation(Vet actualVet) {
        String query = "UPDATE vets SET first_name= " + "'" + actualVet.getFirstName() + "'" +
            " AND last_name = " + "'" + actualVet.getLastName() + "'" +
            " WHERE id = " + actualVet.getId();
        newVetRepository.executeUpdate(query, "Update");
    }

    @Override
    public Collection<Vet> getOldEntities() {
        return this.oldVetRepository.findAll();
    }

    @Override
    public Collection<Vet> getNewEntities(){
	    return newVetRepository.findAll();
    }
}

