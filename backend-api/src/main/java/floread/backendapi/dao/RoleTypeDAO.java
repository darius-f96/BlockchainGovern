package floread.backendapi.dao;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import floread.backendapi.entities.RoleType;
@Repository
public interface RoleTypeDAO extends JpaRepository<RoleType, String>{
    public  Optional<RoleType> findByRoleCode(String roleCode);
}