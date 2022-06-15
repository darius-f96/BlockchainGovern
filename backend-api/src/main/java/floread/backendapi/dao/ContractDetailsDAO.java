package floread.backendapi.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import floread.backendapi.entities.ContractDetails;

@Repository
public interface ContractDetailsDAO extends JpaRepository<ContractDetails, String>{
    
}