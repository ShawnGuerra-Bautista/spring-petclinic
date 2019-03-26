package org.springframework.samples.petclinic.db;

import org.springframework.samples.petclinic.model.BaseEntity;

import java.util.Collection;

public interface ConsistencyChecker <T extends BaseEntity>{
    int checkConsistency();
    void violation(T expected, T actual);
    void fixViolation(T actual);
    Collection<T> getOldEntities();
    Collection<T> getNewEntities();
}
