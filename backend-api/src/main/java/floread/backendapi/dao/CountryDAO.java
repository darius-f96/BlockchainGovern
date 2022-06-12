package floread.backendapi.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import floread.backendapi.entities.Country;
@Repository
public interface CountryDAO extends JpaRepository<Country, String>{}