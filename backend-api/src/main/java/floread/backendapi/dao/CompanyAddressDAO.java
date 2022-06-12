package floread.backendapi.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import floread.backendapi.entities.CompanyAddress;
@Repository
public interface CompanyAddressDAO extends JpaRepository<CompanyAddress, String>{}