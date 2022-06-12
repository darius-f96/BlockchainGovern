package floread.backendapi.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import floread.backendapi.entities.County;
@Repository
public interface CountyDAO extends JpaRepository<County, String>{}