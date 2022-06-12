package floread.backendapi.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import floread.backendapi.dao.AppUserDAO;
import floread.backendapi.dao.CompanyContractPersonDAO;
import floread.backendapi.dao.CompanyDAO;
import floread.backendapi.dao.UserRoleDAO;
import floread.backendapi.entities.AppUser;
import floread.backendapi.entities.Company;
import floread.backendapi.entities.CompanyContractPerson;
import floread.backendapi.entities.UserRole;

@RestController
@RequestMapping("/companyContractPerson")
class CompanyContractPersonController {

    @Autowired
    CompanyContractPersonDAO repository;
    @Autowired
    CompanyDAO companyDAO;
    @Autowired
    AppUserDAO appUserDAO;
    @Autowired
    UserRoleDAO userRoleDAO;

    @GetMapping
    public ResponseEntity<List<CompanyContractPerson>> getAll() {
        try {
            List<CompanyContractPerson> items = new ArrayList<CompanyContractPerson>();

            repository.findAll().forEach(items::add);

            if (items.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyContractPerson> getById(@PathVariable("id") String id) {
        Optional<CompanyContractPerson> existingItemOptional = repository.findById(id);

        if (existingItemOptional.isPresent()) {
            return new ResponseEntity<>(existingItemOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<CompanyContractPerson> create(@RequestBody CompanyContractPerson item, Principal principal) {
        Optional<AppUser> appUser = appUserDAO.findByUsername(principal.getName());
        if (appUser.isPresent()) {
            Optional<Company> company1 = companyDAO.findByCui(item.getCompanyId());
            if (company1.isPresent()){
                Optional<UserRole> uRole = userRoleDAO.findByCompanyIdAndAppUserId(company1.get().getCompanyId(), appUser.get().getAppUserId());
                if (uRole.isPresent() && uRole.get().getRoleType().getRoleCode() == "ADMIN" || uRole.get().getRoleType().getRoleCode() == "HR"){
                    try {
                        item.setCompanyId(company1.get().getCompanyId());
                        item.setAppUserId(appUser.get().getAppUserId());
                        CompanyContractPerson savedItem = repository.save(item);
                        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
                    } catch (Exception e) {
                        return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
                    }
                }
            }
            else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyContractPerson> update(@PathVariable("id") String id, @RequestBody CompanyContractPerson item) {
        Optional<CompanyContractPerson> existingItemOptional = repository.findById(id);
        if (existingItemOptional.isPresent()) {
            CompanyContractPerson existingItem = existingItemOptional.get();
            System.out.println("TODO for developer - update logic is unique to entity and must be implemented manually.");
            //existingItem.setSomeField(item.getSomeField());
            return new ResponseEntity<>(repository.save(existingItem), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") String id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}