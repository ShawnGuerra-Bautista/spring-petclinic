package org.springframework.samples.petclinic.system;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Controller
class WelcomeController {

    @GetMapping("/")
    public String welcome(Map<String, Object> model) {
        Collection<Boolean> toggles = PetClinicToggles.toggles;
        model.put("toggles", toggles);
        return "welcome";
    }

}
