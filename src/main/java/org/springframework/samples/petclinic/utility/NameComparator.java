package org.springframework.samples.petclinic.utility;

import org.springframework.samples.petclinic.model.NamedEntity;

import java.util.Comparator;

public class NameComparator implements Comparator<NamedEntity> {
    @Override
    public int compare(NamedEntity o1, NamedEntity o2) {
        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}
