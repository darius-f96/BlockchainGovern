package floread.backendapi.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import floread.backendapi.entities.City;
@Repository
public interface CityDAO extends JpaRepository<City, String>{}