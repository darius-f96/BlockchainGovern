package floread.backendapi.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import floread.backendapi.entities.RoleType;
@Repository
public interface RoleTypeDAO extends JpaRepository<RoleType, UUID>{}