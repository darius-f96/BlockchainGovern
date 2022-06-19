package floread.backendapi.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
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
import floread.backendapi.dao.CompanyWalletDAO;
import floread.backendapi.dao.PersonDAO;
import floread.backendapi.dao.PersonWalletDAO;
import floread.backendapi.entities.AppUser;
import floread.backendapi.entities.CompanyWallet;
import floread.backendapi.entities.Person;
import floread.backendapi.entities.PersonWallet;

@RestController
@RequestMapping("/personWallet")
class PersonWalletController {

    @Autowired
    PersonWalletDAO repository;
    @Autowired
    AppUserDAO appUserDAO;
    @Autowired
    CompanyWalletDAO companyWalletDAO;

    @GetMapping
    public ResponseEntity<List<PersonWallet>> getAll() {
        try {
            List<PersonWallet> items = new ArrayList<PersonWallet>();

            repository.findAll().forEach(items::add);

            if (items.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonWallet> getById(@PathVariable("id") String id) {
        Optional<PersonWallet> existingItemOptional = repository.findById(id);

        if (existingItemOptional.isPresent()) {
            return new ResponseEntity<>(existingItemOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/userWallets", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonWallet>> getByAppUserId(Principal principal){
        
        AppUser appUser = appUserDAO.findByUsername(principal.getName()).get(); 
        List<PersonWallet> personWallets = repository.findByAppUserId(appUser.getAppUserId());
        if (personWallets.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(personWallets, HttpStatus.OK);
     
    }

    @PostMapping
    public ResponseEntity<PersonWallet> create(@RequestBody PersonWallet item, Principal principal) {
        if (isUserAllowed(principal, item)){
            Optional<PersonWallet> checkExisting = repository.findByWalletId(item.getWalletId());
            if (checkExisting.isPresent()){
                return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
            }
            Optional<CompanyWallet> checkExistingCompany = companyWalletDAO.findByWalletId(item.getWalletId());
            if (checkExistingCompany.isPresent()){
                return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
            }
            try {
                PersonWallet savedItem = repository.save(item);
                return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
            }
        }
        else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonWallet> update(@PathVariable("id") String id, @RequestBody PersonWallet item, Principal principal) {
        Optional<PersonWallet> existingItemOptional = repository.findById(id);
        if (existingItemOptional.isPresent()){
            if (isUserAllowed(principal, item)){
                PersonWallet existingItem = existingItemOptional.get();
                existingItem.setWalletCode(item.getWalletCode());
                existingItem.setWalletDescription(item.getWalletDescription());
                return new ResponseEntity<>(repository.save(existingItem), HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") String id, Principal principal) {
        Optional<PersonWallet> item = repository.findById(id);
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
    
    private Boolean isUserAllowed(Principal principal, PersonWallet item){
        Optional<AppUser> appUser = appUserDAO.findByUsername(principal.getName());
        if (appUser.isPresent() && appUser.get().getAppUserId().equals(item.getAppUserId())) {    
            return true;
        }
        return false;
    }
}