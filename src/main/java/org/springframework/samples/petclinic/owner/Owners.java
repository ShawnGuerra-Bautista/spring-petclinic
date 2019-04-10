package org.springframework.samples.petclinic.owner;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
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
