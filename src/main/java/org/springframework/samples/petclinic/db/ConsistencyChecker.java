package org.springframework.samples.petclinic.db;

import org.springframework.samples.petclinic.model.BaseEntity;

import java.util.Collection;

public interface ConsistencyChecker{
    int checkConsistency();
    void violation();
    void fixViolation();
    Collection<BaseEntity> getListOfEntity();
}
