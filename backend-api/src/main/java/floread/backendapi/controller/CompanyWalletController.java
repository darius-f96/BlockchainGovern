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
import floread.backendapi.dao.CompanyDAO;
import floread.backendapi.dao.CompanyWalletDAO;
import floread.backendapi.dao.PersonWalletDAO;
import floread.backendapi.dao.RoleTypeDAO;
import floread.backendapi.dao.UserRoleDAO;
import floread.backendapi.entities.AppUser;
import floread.backendapi.entities.Company;
import floread.backendapi.entities.CompanyWallet;
import floread.backendapi.entities.PersonWallet;
import floread.backendapi.entities.UserRole;

@RestController
@RequestMapping("/companyWallet")
class CompanyWalletController {

    @Autowired
    CompanyWalletDAO repository;
    @Autowired
    AppUserDAO appUserDAO;
    @Autowired
    CompanyDAO companyDAO;
    @Autowired
    UserRoleDAO userRoleDAO;
    @Autowired
    RoleTypeDAO roleTypeDAO;
    @Autowired
    PersonWalletDAO personWalletDAO;

    @GetMapping
    public ResponseEntity<List<CompanyWallet>> getAll() {
        try {
            List<CompanyWallet> items = new ArrayList<CompanyWallet>();

            repository.findAll().forEach(items::add);

            if (items.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyWallet> getById(@PathVariable("id") String id) {
        Optional<CompanyWallet> existingItemOptional = repository.findById(id);

        if (existingItemOptional.isPresent()) {
            return new ResponseEntity<>(existingItemOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<CompanyWallet> create(@RequestBody CompanyWallet item, Principal principal) {

        if (isUserAllowed(principal, item))
        {
            Optional<PersonWallet> checkExistingPersonWallet = personWalletDAO.findByWalletId(item.getWalletId());
            if (checkExistingPersonWallet.isPresent()){
                return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
            }
            Optional<CompanyWallet> checkExistingCompany = repository.findByWalletId(item.getWalletId());
            if (checkExistingCompany.isPresent()){
                return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
            }
            try {
                CompanyWallet savedItem = repository.save(item);
                return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
            }   
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompanyWallet> update(@PathVariable("id") String id, @RequestBody CompanyWallet item, Principal principal) {
        Optional<CompanyWallet> existingItemOptional = repository.findById(id);
        if (existingItemOptional.isPresent()) {
            if (isUserAllowed(principal, item)){
                CompanyWallet existingItem = existingItemOptional.get();
                existingItem.setWalletDescription(item.getWalletDescription());
                existingItem.setWalletCode(item.getWalletCode());
                return new ResponseEntity<>(repository.save(existingItem), HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") String id, Principal principal) {
        Optional<CompanyWallet> item = repository.findById(id);
        if (item.isPresent()){
            if (isUserAllowed(principal, item.get())){
                try {
                    repository.deleteById(id);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
                }
            }
            else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private Boolean isUserAllowed(Principal principal, CompanyWallet item){
        //this breaks if the role types are not added to db     
        final String ROLETYPEID_ADMIN = roleTypeDAO.findByRoleCode("ADMIN").get().getRoleTypeId();
        final String ROLETYPEID_HR = roleTypeDAO.findByRoleCode("HR").get().getRoleTypeId();
        Optional<AppUser> appUser = appUserDAO.findByUsername(principal.getName());
        if (appUser.isPresent()) {
            Optional<Company> company = companyDAO.findById(item.getCompanyId());
            if (company.isPresent()){
                Optional<UserRole> uRole = userRoleDAO.findByCompanyIdAndAppUserId(company.get().getCompanyId(), appUser.get().getAppUserId());
                if (uRole.isPresent() && (uRole.get().getRoleTypeId().equals(ROLETYPEID_ADMIN) || uRole.get().getRoleTypeId().equals(ROLETYPEID_HR))){
                    return true;
                }
            }
        }
        return false;
    }
}