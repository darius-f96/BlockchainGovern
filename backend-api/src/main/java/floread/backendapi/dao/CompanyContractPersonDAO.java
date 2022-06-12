package floread.backendapi.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import floread.backendapi.entities.CompanyContractPerson;
@Repository
public interface CompanyContractPersonDAO extends JpaRepository<CompanyContractPerson, String>{}