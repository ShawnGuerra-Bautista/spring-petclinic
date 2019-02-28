package org.springframework.samples.petclinic.utility;

import org.junit.Before;
import org.junit.Test;
import org.springframework.samples.petclinic.model.NamedEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class NameComparatorTest {

    private NameComparator nameComparator;

    @Before
    public void setup() {
        this.nameComparator = new NameComparator();
    }

    @Test
    public void testNamesAreInAscendingOrder() {
        List<NamedEntity> namedEntities = new ArrayList<>();
        namedEntities.add(new NamedEntity(null, "ca"));
        namedEntities.add(new NamedEntity(null, "cc"));
        namedEntities.add(new NamedEntity(null, "bb"));
        namedEntities.add(new NamedEntity(null, "aa"));

        namedEntities.sort(nameComparator);

        assertEquals("aa", namedEntities.get(0).getName());
        assertEquals("bb", namedEntities.get(1).getName());
        assertEquals("ca", namedEntities.get(2).getName());
        assertEquals("cc", namedEntities.get(3).getName());
    }

    public void testCaseDoeNotEffectOrdering() {
        NamedEntity lowerCaseName = new NamedEntity(null, "name");
        NamedEntity upperCaseName = new NamedEntity(null, "NAME");

        int result = nameComparator.compare(lowerCaseName, upperCaseName);

        assertEquals(0, result);
    }

}
