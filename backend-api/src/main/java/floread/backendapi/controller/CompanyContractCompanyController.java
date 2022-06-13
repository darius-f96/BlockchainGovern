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
import floread.backendapi.dao.CompanyContractCompanyDAO;
import floread.backendapi.dao.CompanyDAO;
import floread.backendapi.dao.RoleTypeDAO;
import floread.backendapi.dao.UserRoleDAO;
import floread.backendapi.entities.AppUser;
import floread.backendapi.entities.Company;
import floread.backendapi.entities.CompanyContractCompany;
import floread.backendapi.entities.UserRole;

@RestController
@RequestMapping("/companyContractCompany")
class CompanyContractCompanyController {

    @Autowired
    CompanyContractCompanyDAO repository;
    @Autowired
    CompanyDAO companyDAO;
    @Autowired
    AppUserDAO appUserDAO;
    @Autowired
    UserRoleDAO userRoleDAO;
    @Autowired
    RoleTypeDAO roleTypeDAO;

    @GetMapping
    public ResponseEntity<List<CompanyContractCompany>> getAll(Principal principal) {
        try {
            List<CompanyContractCompany> items = new ArrayList<CompanyContractCompany>();

            repository.findAll().forEach(items::add);

            if (items.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyContractCompany> getById(@PathVariable("id") String id) {
        Optional<CompanyContractCompany> existingItemOptional = repository.findById(id);

        if (existingItemOptional.isPresent()) {
            return new ResponseEntity<>(existingItemOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<CompanyContractCompany> create(@RequestBody CompanyContractCompany item, Principal principal) {
        
        //this breaks if the role types are not added to db     
        final String ROLETYPEID_ADMIN = roleTypeDAO.findByRoleCode("ADMIN").get().getRoleTypeId();
        final String ROLETYPEID_HR = roleTypeDAO.findByRoleCode("HR").get().getRoleTypeId();

        Optional<AppUser> appUser = appUserDAO.findByUsername(principal.getName());
        if (appUser.isPresent()) {
            Optional<Company> company1 = companyDAO.findByCui(item.getCompanyId1());
            Optional<Company> company2 = companyDAO.findByCui(item.getCompanyId2());
            if (company1.isPresent() && company2.isPresent()){
                Optional<UserRole> uRole = userRoleDAO.findByCompanyIdAndAppUserId(company1.get().getCompanyId(), appUser.get().getAppUserId());
                if (!uRole.isPresent()) {return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); }
                if (uRole.get().getRoleTypeId().equals(ROLETYPEID_ADMIN) || uRole.get().getRoleTypeId().equals(ROLETYPEID_HR)){
                    try {
                        item.setCompanyId1(company1.get().getCompanyId());
                        item.setCompanyId2(company2.get().getCompanyId());
                        CompanyContractCompany savedItem = repository.save(item);
                        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
                    } catch (Exception e) {
                        return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
                    }
                }
                else {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);}
            }
            else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }


    //only someone that represents company2 can accept 
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyContractCompany> update(@PathVariable("id") String id, @RequestBody CompanyContractCompany item, Principal principal) {
       //this breaks if the role types are not added to db     
        final String ROLETYPEID_ADMIN = roleTypeDAO.findByRoleCode("ADMIN").get().getRoleTypeId();
        final String ROLETYPEID_HR = roleTypeDAO.findByRoleCode("HR").get().getRoleTypeId();

        Optional<AppUser> appUser = appUserDAO.findByUsername(principal.getName());
        Optional<CompanyContractCompany> existingItemOptional = repository.findById(id);
        if (existingItemOptional.isPresent()) {
            if (appUser.isPresent()) {
                Optional<Company> company2 = companyDAO.findByCui(item.getCompanyId2());
                if (company2.isPresent()){
                    Optional<UserRole> uRole = userRoleDAO.findByCompanyIdAndAppUserId(company2.get().getCompanyId(), appUser.get().getAppUserId());
                    if (!uRole.isPresent()) {return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); }
                    if (uRole.get().getRoleTypeId().equals(ROLETYPEID_ADMIN) || uRole.get().getRoleTypeId().equals(ROLETYPEID_HR)){
                        CompanyContractCompany existingItem = existingItemOptional.get();
                        existingItem.setAccepted(item.getAccepted());
                        return new ResponseEntity<>(repository.save(existingItem), HttpStatus.OK);
                    } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                } return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") String id, Principal principal) {
         //this breaks if the role types are not added to db     
         final String ROLETYPEID_ADMIN = roleTypeDAO.findByRoleCode("ADMIN").get().getRoleTypeId();
         final String ROLETYPEID_HR = roleTypeDAO.findByRoleCode("HR").get().getRoleTypeId();
 
        Optional<AppUser> appUser = appUserDAO.findByUsername(principal.getName());
        CompanyContractCompany item = repository.findById(id).get();
        if (appUser.isPresent()) {
            Optional<Company> company1 = companyDAO.findByCui(item.getCompanyId1());
            if (company1.isPresent()){
                Optional<UserRole> uRole = userRoleDAO.findByCompanyIdAndAppUserId(company1.get().getCompanyId(), appUser.get().getAppUserId());
                if (!uRole.isPresent()) {return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); }
                if (uRole.get().getRoleTypeId().equals(ROLETYPEID_ADMIN) || uRole.get().getRoleTypeId().equals(ROLETYPEID_HR)){
                    try {
                        repository.deleteById(id);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                    } catch (Exception e) {
                        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
                    }
                }
                else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}