package floread.backendapi.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import floread.backendapi.dao.AppUserDAO;
import floread.backendapi.dao.PersonDAO;
import floread.backendapi.entities.AppUser;
import floread.backendapi.entities.Person;

@RestController
@RequestMapping("/person")
class PersonController {

    @Autowired
    PersonDAO repository;
    @Autowired
    AppUserDAO appuserRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "2") int pageSize
    ) {
        try { 
            page--;
            List<Person> items = new ArrayList<Person>();
            Pageable paging = PageRequest.of(page, pageSize);
            Page<Person> personPage;
            personPage = repository.findAll(paging);
            items = personPage.getContent();
            Map<String, Object> response = new HashMap<>();
            
            response.put("persons", items);
            response.put("page", page);
            response.put("totalElements", personPage.getTotalElements());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> getById(@PathVariable("id") String id) {
        Optional<Person> existingItemOptional = repository.findById(id);

        if (existingItemOptional.isPresent()) {
            return new ResponseEntity<>(existingItemOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Person> create(@RequestBody Person item, Principal principal) {
        Optional<AppUser> appUser = appuserRepository.findByUsername(principal.getName());
        if (appUser.isPresent()) {
            Optional<Person> existingItem = repository.findByAppUserId(appUser.get().getAppUserId());
            if (existingItem.isPresent()){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            item.setAppUserId(appUser.get().getAppUserId());
            try {
                Person savedItem = repository.save(item);
                return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
            }
        }
        if (appUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> update(@PathVariable("id") String id, @RequestBody Person item) {
        Optional<Person> existingItemOptional = repository.findById(id);
        if (existingItemOptional.isPresent()) {
            Person existingItem = existingItemOptional.get();
            
            existingItem.setFirstname(item.getFirstname());
            existingItem.setLastname(item.getLastname());
            existingItem.setBirthDate(item.getBirthDate());
            existingItem.setGender(item.getGender());
            existingItem.setSalutation(item.getSalutation());
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