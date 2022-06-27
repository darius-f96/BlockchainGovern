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
import floread.backendapi.dao.ContractDetailsDAO;
import floread.backendapi.dao.RoleTypeDAO;
import floread.backendapi.dao.UserRoleDAO;
import floread.backendapi.entities.AppUser;
import floread.backendapi.entities.Company;
import floread.backendapi.entities.CompanyContractPerson;
import floread.backendapi.entities.ContractDetails;
import floread.backendapi.entities.RoleType;
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
    @Autowired
    RoleTypeDAO roleTypeDAO;
    @Autowired
    ContractDetailsDAO contractDetailsDAO;

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
        //this breaks if the role types are not added to db     
        final String ROLETYPEID_ADMIN = roleTypeDAO.findByRoleCode("ADMIN").get().getRoleTypeId();
        final String ROLETYPEID_HR = roleTypeDAO.findByRoleCode("HR").get().getRoleTypeId();
        Optional<AppUser> appUser = appUserDAO.findByUsername(principal.getName());
        if (appUser.isPresent()) {
            Optional<Company> company1 = companyDAO.findByCui(item.getCompanyId());
            if (company1.isPresent()){
                Optional<UserRole> uRole = userRoleDAO.findByCompanyIdAndAppUserId(company1.get().getCompanyId(), appUser.get().getAppUserId());
                if (!uRole.isPresent()) {return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); }
                if (uRole.get().getRoleTypeId().equals(ROLETYPEID_ADMIN) || uRole.get().getRoleTypeId().equals(ROLETYPEID_HR)){
                    Optional<AppUser> contractor = appUserDAO.findByUsername(item.getAppUserId());
                    if (contractor.isPresent()){
                        try {
                            ContractDetails contractDetails = contractDetailsDAO.save(item.getContractDetails());
                            item.setContractDetailsId(contractDetails.getContractDetailsId());
                            item.setContractDetails(contractDetails);
                            item.setCompanyId(company1.get().getCompanyId());
                            item.setAppUserId(contractor.get().getAppUserId());
                            CompanyContractPerson savedItem = repository.save(item);
                            return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
                        } catch (Exception e) {
                            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
                        }
                    }
                    else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    
                }
            }
            else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    //only person can accept contract
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyContractPerson> update(@PathVariable("id") String id, @RequestBody CompanyContractPerson item, Principal principal) {
        Optional<CompanyContractPerson> existingItemOptional = repository.findById(id);
        if (existingItemOptional.isPresent()) {

            Optional<Company> company = companyDAO.findById(existingItemOptional.get().getCompanyId());
            if (!company.isPresent()) 
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            Boolean isPrivatePersonAllowed = principal.getName().equals(item.getAppUserId());
            Boolean isUserCompanyAllowed = isUserAllowed(principal, company.get());

            if (!isUserCompanyAllowed && !isPrivatePersonAllowed)
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            
            CompanyContractPerson existingItem = existingItemOptional.get();
            if (isPrivatePersonAllowed){
                
                if (!existingItem.getAccepted().equals(item.getAccepted()))
                    addEmployeeRoleToUser(existingItem.getAppUserId(), existingItem.getCompanyId());
                existingItem.setAccepted(item.getAccepted());
                existingItem.getContractDetails().setWireToAddress(item.getContractDetails().getWireToAddress());
            }
            if (isUserCompanyAllowed){
                existingItem.getContractDetails().setAmount(item.getContractDetails().getAmount());
                existingItem.setContractId(item.getContractId());
            }
            existingItem.getContractDetails().setEndDate(item.getContractDetails().getEndDate());
            existingItem.getContractDetails().setLastWire(item.getContractDetails().getLastWire());
            existingItem.getContractDetails().setActive(item.getContractDetails().getActive());
            return new ResponseEntity<>(repository.save(existingItem), HttpStatus.OK);
            
        } else {
            System.out.println("Contract not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") String id, Principal principal) {
        //this breaks if the role types are not added to db     
        final String ROLETYPEID_ADMIN = roleTypeDAO.findByRoleCode("ADMIN").get().getRoleTypeId();
        final String ROLETYPEID_HR = roleTypeDAO.findByRoleCode("HR").get().getRoleTypeId();
        Optional<AppUser> appUser = appUserDAO.findByUsername(principal.getName());
        CompanyContractPerson item = repository.findById(id).get();
        if (item.getAccepted()) {return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);}
        if (appUser.isPresent()) {
            Optional<Company> company1 = companyDAO.findById(item.getCompanyId());
            if (company1.isPresent()){
                Optional<UserRole> uRole = userRoleDAO.findByCompanyIdAndAppUserId(company1.get().getCompanyId(), appUser.get().getAppUserId());
                if (uRole.isPresent() && (uRole.get().getRoleTypeId().equals(ROLETYPEID_ADMIN) || uRole.get().getRoleTypeId().equals(ROLETYPEID_HR) || appUser.get().getAppUserId() == item.getAppUserId())){

                    try {
                        repository.deleteById(id);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                    } catch (Exception e) {
                        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
                    }
                }
                else if (appUser.get().getAppUserId().equals(item.getAppUserId())){
                    try {
                        repository.deleteById(id);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                    } catch (Exception e) {
                        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
                    }
                }
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            else return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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

    private void addEmployeeRoleToUser(String appUserId, String companyId){
        UserRole userRole = new UserRole();
        Optional<RoleType> roleType = roleTypeDAO.findByRoleCode("EMPLOYEE");
        if (roleType.isPresent()){
            userRole.setAppUserId(appUserId);
            userRole.setRoleTypeId(roleType.get().getRoleTypeId());
            userRole.setCompanyId(companyId);
        }
        userRoleDAO.save(userRole);
    }
}