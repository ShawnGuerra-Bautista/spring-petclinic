package org.springframework.samples.petclinic.utility;

import java.util.Comparator;

import org.springframework.samples.petclinic.visit.Visit;

public class VisitComparator implements Comparator<Visit> {
    @Override
    public int compare(Visit v1, Visit v2) {
        return v1.getDate().compareTo(v2.getDate());
    }
}