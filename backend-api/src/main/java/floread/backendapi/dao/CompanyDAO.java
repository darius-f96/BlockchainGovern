package floread.backendapi.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import floread.backendapi.entities.Company;
@Repository
public interface CompanyDAO extends JpaRepository<Company, String>{
    public Optional<Company> findByCui(String cui);
}