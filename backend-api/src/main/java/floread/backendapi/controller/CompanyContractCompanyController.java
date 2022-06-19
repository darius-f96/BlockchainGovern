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
import floread.backendapi.dao.ContractDetailsDAO;
import floread.backendapi.dao.RoleTypeDAO;
import floread.backendapi.dao.UserRoleDAO;
import floread.backendapi.entities.AppUser;
import floread.backendapi.entities.Company;
import floread.backendapi.entities.CompanyContractCompany;
import floread.backendapi.entities.ContractDetails;
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
    @Autowired
    ContractDetailsDAO contractDetailsDAO;

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
                        ContractDetails contractDetails = contractDetailsDAO.save(item.getContractDetails());
                        item.setContractDetailsId(contractDetails.getContractDetailsId());
                        item.setContractDetails(contractDetails);
                        CompanyContractCompany savedItem = repository.save(item);
                        
                        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
                    } catch (Exception e) {
                        System.out.println(e);
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

        Optional<CompanyContractCompany> existingItemOptional = repository.findById(id);
        if (existingItemOptional.isPresent()) {
                Optional<Company> company2 = companyDAO.findByCui(item.getCompanyId2());
                Optional<Company> company1 = companyDAO.findByCui(item.getCompanyId1());

                if (!company2.isPresent() || !company1.isPresent())
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);

                Boolean isUserCompany1Allowed = isUserAllowed(principal, company1.get());
                Boolean isUserCompany2Allowed = isUserAllowed(principal, company2.get());
                if (!isUserCompany1Allowed && !isUserCompany2Allowed)
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

                CompanyContractCompany existingItem = existingItemOptional.get();
                if (isUserCompany2Allowed){
                    existingItem.setAccepted(item.getAccepted());
                    existingItem.getContractDetails().setWireToAddress(item.getContractDetails().getWireToAddress());
                }
                if (isUserCompany1Allowed){
                    existingItem.getContractDetails().setAmount(item.getContractDetails().getAmount());
                    existingItem.setContractId(item.getContractId());
                }
                existingItem.getContractDetails().setEndDate(item.getContractDetails().getEndDate());
                existingItem.getContractDetails().setLastWire(item.getContractDetails().getLastWire());
                existingItem.getContractDetails().setActive(item.getContractDetails().getActive());
                return new ResponseEntity<>(repository.save(existingItem), HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
 
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") String id, Principal principal) {
         //this breaks if the role types are not added to db     
         final String ROLETYPEID_ADMIN = roleTypeDAO.findByRoleCode("ADMIN").get().getRoleTypeId();
         final String ROLETYPEID_HR = roleTypeDAO.findByRoleCode("HR").get().getRoleTypeId();

        Optional<AppUser> appUser = appUserDAO.findByUsername(principal.getName());
        CompanyContractCompany item = repository.findById(id).get();
        if (item.getAccepted()) {return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);}
        if (appUser.isPresent()) {
            Optional<Company> company1 = companyDAO.findById(item.getCompanyId1());
            Optional<Company> company2 = companyDAO.findById(item.getCompanyId2());
            if (company1.isPresent()){
                Optional<UserRole> uRole = userRoleDAO.findByCompanyIdAndAppUserId(company1.get().getCompanyId(), appUser.get().getAppUserId());
                if (uRole.isPresent() && (uRole.get().getRoleTypeId().equals(ROLETYPEID_ADMIN) || uRole.get().getRoleTypeId().equals(ROLETYPEID_HR))){
                    try {
                        repository.deleteById(id);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                    } catch (Exception e) {
                        System.out.println(e);
                        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
                    }
                }
                else if (company2.isPresent()){
                    uRole = userRoleDAO.findByCompanyIdAndAppUserId(company2.get().getCompanyId(), appUser.get().getAppUserId());
                    if (uRole.isPresent() && (uRole.get().getRoleTypeId().equals(ROLETYPEID_ADMIN) || uRole.get().getRoleTypeId().equals(ROLETYPEID_HR))){
                        try {
                            repository.deleteById(id);
                            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                        } catch (Exception e) {
                            System.out.println(e);
                            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
                        }
                    }
                    else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
            }
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    private Boolean isUserAllowed(Principal principal, Company item){
        //this breaks if the role types are not added to db     
        final String ROLETYPEID_ADMIN = roleTypeDAO.findByRoleCode("ADMIN").get().getRoleTypeId();
        final String ROLETYPEID_HR = roleTypeDAO.findByRoleCode("HR").get().getRoleTypeId();
       Optional<AppUser> appUser = appUserDAO.findByUsername(principal.getName());
       if (appUser.isPresent()) {
           Optional<UserRole> uRole = userRoleDAO.findByCompanyIdAndAppUserId(item.getCompanyId(), appUser.get().getAppUserId());
           if (uRole.isPresent() && (uRole.get().getRoleTypeId().equals(ROLETYPEID_ADMIN) || uRole.get().getRoleTypeId().equals(ROLETYPEID_HR))){
               return true;
           }
       }
       return false;
   }
}