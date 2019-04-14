/*
 * Copyright 2012-2018 the original author or authors.
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
package org.springframework.samples.petclinic.owner;



import org.springframework.samples.petclinic.system.PetClinicToggles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.samples.petclinic.system.PetClinicToggles;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
class OwnerController {

    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
    private final OwnerRepository owners;

    private static Logger logger = LogManager.getLogger("listOfOwner");

    public OwnerController(OwnerRepository clinicService) {
        this.owners = clinicService;

        if (PetClinicToggles.toggleFindOwnerByLastName) {
            logger.info("Find Owner by Last Name Enabled");
        }
        
        if (PetClinicToggles.toggleFindOwnerByFirstName) {
            logger.info("Find Owner by First Name Enabled");
        }

        if (PetClinicToggles.toggleListOfOwners) {
            logger.info("List of Owners Enabled");
        }
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/owners/new")
    public String initCreationForm(Map<String, Object> model) {
        Owner owner = new Owner();
        model.put("owner", owner);
        Collection<Boolean> toggles = PetClinicToggles.toggles;
        model.put("toggles", toggles);
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/owners/new")
    public String processCreationForm(@Valid Owner owner, BindingResult result, Map<String, Object> model) {
        if (result.hasErrors()) {
            Collection<Boolean> toggles = PetClinicToggles.toggles;
            model.put("toggles", toggles);
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            this.owners.save(owner);
            return "redirect:/owners/" + owner.getId();
        }
    }

    @GetMapping("/owners/find")
    public String initFindForm(Map<String, Object> model) {
        model.put("owner", new Owner());

        Collection<Boolean> toggles = PetClinicToggles.toggles;
        model.put("toggles", toggles);

        System.out.println("Logger level is " + logger.getLevel());
        logger.trace("TRACE");
        logger.info("INFO");

        logger.debug("DEBUG");
        logger.error("ERROR");
        logger.fatal("FATAL");
        

        //logger.debug("DEBUG");
        //logger.error("ERROR");
        //logger.fatal("FATAL");

        return "owners/findOwners";
    }

    @GetMapping("/owners.html")
    public String showOwnerList(Map<String, Object> model) {
        Collection<Owner> results = this.owners.findAll();
        boolean displayingListOfAll = true;
        model.put("selections", results);
        model.put("isOptionListOfAll", displayingListOfAll);
        Collection<Boolean> toggles = PetClinicToggles.toggles;
        model.put("toggles", toggles);
        return "owners/ownersList";
    }

    @GetMapping("/owners")
    public String processFindForm(Owner owner, BindingResult result, Map<String, Object> model) {

        // allow parameterless GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        Collection<Owner> results = this.owners.findByLastName(owner.getLastName());
        if (results.isEmpty()) {
            // no owners found
            result.rejectValue("lastName", "notFound", "not found");
            Collection<Boolean> toggles = PetClinicToggles.toggles;
            model.put("toggles", toggles);
            return "owners/findOwners";
        } else if (results.size() == 1) {
            // 1 owner found
            owner = results.iterator().next();
            return "redirect:/owners/" + owner.getId();
        } else {
            // multiple owners found
            boolean displayingListOfAll = false;
            model.put("isOptionListOfAll", displayingListOfAll);
            model.put("selections", results);
            Collection<Boolean> toggles = PetClinicToggles.toggles;
            model.put("toggles", toggles);
            return "owners/ownersList";
        }
        
        
    }
    
    @GetMapping("/owners_first_name")
    public String findOwnersByFirstName(Owner owner, BindingResult result, Map<String, Object> model) {

        // allow parameterless GET request for /owners to return all records
        if (owner.getFirstName() == null) {
            owner.setFirstName(""); // empty string signifies broadest possible search
        }

        // find owners by first name
        Collection<Owner> results = this.owners.findByFirstName(owner.getFirstName());
        if (results.isEmpty()) {
            // no owners found
            result.rejectValue("firstName", "notFound", "not found");
            Collection<Boolean> toggles = PetClinicToggles.toggles;
            model.put("toggles", toggles);
            return "owners/findOwners";
        } else if (results.size() == 1) {
            // 1 owner found
            owner = results.iterator().next();
            return "redirect:/owners/" + owner.getId();
        } else {
            // multiple owners found
            boolean displayingListOfAll = false;
            model.put("isOptionListOfAll", displayingListOfAll);
            model.put("selections", results);
            Collection<Boolean> toggles = PetClinicToggles.toggles;
            model.put("toggles", toggles);
            return "owners/ownersList";
        }
    }

    @GetMapping("/owners/{ownerId}/edit")
    public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId, Map<String, Object> model) {
        Owner owner = this.owners.findById(ownerId);
        model.put("owner", owner);
        Collection<Boolean> toggles = PetClinicToggles.toggles;
        model.put("toggles", toggles);
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/owners/{ownerId}/edit")
    public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result, @PathVariable("ownerId") int ownerId,  Map<String, Object> model) {
        if (result.hasErrors()) {
            Collection<Boolean> toggles = PetClinicToggles.toggles;
            model.put("toggles", toggles);
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            owner.setId(ownerId);
            this.owners.save(owner);
            return "redirect:/owners/{ownerId}";
        }
    }

    /**
     * Custom handler for displaying an owner.
     *
     * @param ownerId the ID of the owner to display
     * @return a ModelMap with the model attributes for the view
     */
    @GetMapping("/owners/{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") int ownerId, Map<String, Object> model) {
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        mav.addObject(this.owners.findById(ownerId));
        Collection<Boolean> toggles = PetClinicToggles.toggles;
        model.put("toggles", toggles);
        return mav;
    }

}
