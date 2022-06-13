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
import floread.backendapi.dao.CompanyDAO;
import floread.backendapi.dao.RoleTypeDAO;
import floread.backendapi.dao.UserRoleDAO;
import floread.backendapi.entities.AppUser;
import floread.backendapi.entities.Company;
import floread.backendapi.entities.CompanyContractCompany;
import floread.backendapi.entities.UserRole;

@RestController
@RequestMapping("/company")
class CompanyController {

    @Autowired
    CompanyDAO repository;
    @Autowired
    AppUserDAO appuserRepository;
    @Autowired
    UserRoleDAO userRoleDAO;
    @Autowired
    RoleTypeDAO roleTypeDAO;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "2") int pageSize
    ) {
        try { 
            page--;
            List<Company> items = new ArrayList<Company>();
            Pageable paging = PageRequest.of(page, pageSize);
            Page<Company> companyPage;
            companyPage = repository.findAll(paging);
            items = companyPage.getContent();

            //remapping companyid to cui
            for (Company el : items){
                for (CompanyContractCompany remap : el.getCompanyContractCompanies1()){
                    if (el.getCompanyId().equals(remap.getCompanyId1())){
                        remap.setCompanyId1(el.getCui());
                    }
                    Optional<Company> c2 = repository.findById(remap.getCompanyId2());
                    if (c2.isPresent()){
                        remap.setCompanyId2(c2.get().getCui());
                    }
                    
                }
            }

            Map<String, Object> response = new HashMap<>();
            
            response.put("companies", items);
            response.put("page", page);
            response.put("totalElements", companyPage.getTotalElements());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Company> getById(@PathVariable("id") String id) {
        Optional<Company> existingItemOptional = repository.findById(id);

        if (existingItemOptional.isPresent()) {
            return new ResponseEntity<>(existingItemOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Company> create(@RequestBody Company item, Principal principal) {
        Optional<AppUser> appUser = appuserRepository.findByUsername(principal.getName());
        if (appUser.isPresent()) {
            try {
                Company savedItem = repository.save(item);
                UserRole addAdminRole = new UserRole();
                addAdminRole.setAppUserId(appUser.get().getAppUserId());
                addAdminRole.setRoleTypeId(roleTypeDAO.findByRoleCode("ADMIN").get().getRoleTypeId());
                addAdminRole.setCompanyId(savedItem.getCompanyId());
                userRoleDAO.save(addAdminRole);
                return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
            }
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Company> update(@PathVariable("id") String id, @RequestBody Company item, Principal principal) {
        Optional<Company> existingItemOptional = repository.findById(id);
        if (existingItemOptional.isPresent()) {
            Company existingItem = existingItemOptional.get();
            if (isUserAllowed(principal, existingItem)){
                existingItem.setCompanyAddresses(item.getCompanyAddresses());
                existingItem.setCompanyContractCompanies1(item.getCompanyContractCompanies1());
                existingItem.setCompanyContractCompanies2(item.getCompanyContractCompanies2());
                existingItem.setName(item.getName());
                existingItem.setDescription(item.getDescription());
                System.out.println("still have to adjust company update");
                return new ResponseEntity<>(repository.save(existingItem), HttpStatus.OK);
            }
            else return new ResponseEntity<Company>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") String id, Principal principal) {
        Optional<Company> item = repository.findById(id);
        if (item.isPresent()){
            if (isUserAllowed(principal, item.get())){
                try {
                    repository.deleteById(id);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
                }
            }
            else return new ResponseEntity<HttpStatus>(HttpStatus.UNAUTHORIZED);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       
    }

    private Boolean isUserAllowed(Principal principal, Company item){
         //this breaks if the role types are not added to db     
         final String ROLETYPEID_ADMIN = roleTypeDAO.findByRoleCode("ADMIN").get().getRoleTypeId();
         final String ROLETYPEID_HR = roleTypeDAO.findByRoleCode("HR").get().getRoleTypeId();
        Optional<AppUser> appUser = appuserRepository.findByUsername(principal.getName());
        if (appUser.isPresent()) {
            Optional<UserRole> uRole = userRoleDAO.findByCompanyIdAndAppUserId(item.getCompanyId(), appUser.get().getAppUserId());
            if (uRole.isPresent() && (uRole.get().getRoleTypeId().equals(ROLETYPEID_ADMIN) || uRole.get().getRoleTypeId().equals(ROLETYPEID_HR))){
                return true;
            }
        }
        return false;
    }
}