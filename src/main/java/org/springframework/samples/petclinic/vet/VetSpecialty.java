package org.springframework.samples.petclinic.vet;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "vet_specialties")
public class VetSpecialty implements Serializable {

    private Integer vetId;
    private Integer specialtyId;

    public VetSpecialty(){
        this(null, null);
    }

    public VetSpecialty(Integer vetId,  Integer specialtyId){
        this.vetId = vetId;
        this.specialtyId = specialtyId;
    }
}
