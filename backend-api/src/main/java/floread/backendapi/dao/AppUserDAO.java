package floread.backendapi.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import floread.backendapi.entities.AppUser;

@Repository
public interface AppUserDAO extends JpaRepository<AppUser, String>{
    Page<AppUser> findByEmail(String email, Pageable paging);
    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findById(String id);
    Optional<AppUser> findByUsername(String username);
}