package org.springframework.samples.petclinic.owner;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

public class Owners {

    private List<Owner> owners;

    @XmlElement
    public List<Owner> getOwnerList() {
        if (owners == null) {
            owners = new ArrayList<>();
        }
        return owners;
    }
    
}
