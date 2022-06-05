package floread.backendapi.dao;

import java.util.UUID;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import floread.backendapi.entities.Person;
@Repository
public interface PersonDAO extends JpaRepository<Person, UUID>{
    Page<Person> findAll(Pageable paging);
}