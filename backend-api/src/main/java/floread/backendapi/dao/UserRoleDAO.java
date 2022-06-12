package floread.backendapi.dao;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import floread.backendapi.entities.UserRole;
@Repository
public interface UserRoleDAO extends JpaRepository<UserRole, String>{
    public Optional<UserRole> findByCompanyIdAndAppUserId(String companyId, String appUserId);
}