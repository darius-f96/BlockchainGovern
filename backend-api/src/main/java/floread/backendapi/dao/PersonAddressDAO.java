package floread.backendapi.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import floread.backendapi.entities.PersonAddress;
@Repository
public interface PersonAddressDAO extends JpaRepository<PersonAddress, String>{}