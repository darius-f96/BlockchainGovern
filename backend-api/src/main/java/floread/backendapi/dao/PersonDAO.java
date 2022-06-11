package floread.backendapi.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import floread.backendapi.entities.Person;
@Repository
public interface PersonDAO extends JpaRepository<Person, String>{
    Page<Person> findAll(Pageable paging);
}