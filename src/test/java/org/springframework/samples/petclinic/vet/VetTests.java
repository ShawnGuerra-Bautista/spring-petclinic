/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.vet;

import org.junit.Test;

import org.springframework.util.SerializationUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/**
 * @author Dave Syer
 *
 */
public class VetTests {

    @Test
    public void testCharacterizeOrderOfGetSpecialties(){
        Set<Specialty> specialties = new HashSet<>();
        specialties.add(new Specialty(null, "Nurse"));
        specialties.add(new Specialty(null, "Vice Doctor"));
        specialties.add(new Specialty(null, "Vice Nurse"));
        specialties.add(new Specialty(null, "Doctor"));
        specialties.add(new Specialty(null, "Intern"));

        //PropertyComparator propertyComparator = mock(PropertyComparator.class);
        //Vet vet = new Vet(specialties, propertyComparator);
        Vet vet = new Vet(specialties);

        List<Specialty> sortedSpecialties = vet.getSpecialties();

        assertEquals("Doctor", sortedSpecialties.get(0).getName());
        assertEquals("Intern", sortedSpecialties.get(1).getName());
        assertEquals("Nurse", sortedSpecialties.get(2).getName());
        assertEquals("Vice Doctor", sortedSpecialties.get(3).getName());
        assertEquals("Vice Nurse", sortedSpecialties.get(4).getName());
    }


    @Test
    public void testSerialization() {
        Vet vet = new Vet();
        vet.setFirstName("Zaphod");
        vet.setLastName("Beeblebrox");
        vet.setId(123);
        Vet other = (Vet) SerializationUtils
            .deserialize(SerializationUtils.serialize(vet));
        assertThat(other.getFirstName()).isEqualTo(vet.getFirstName());
        assertThat(other.getLastName()).isEqualTo(vet.getLastName());
        assertThat(other.getId()).isEqualTo(vet.getId());
    }

}
