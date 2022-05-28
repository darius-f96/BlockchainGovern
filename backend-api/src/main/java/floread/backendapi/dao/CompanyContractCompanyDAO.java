package floread.backendapi.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import floread.backendapi.entities.CompanyContractCompany;
@Repository
public interface CompanyContractCompanyDAO extends JpaRepository<CompanyContractCompany, UUID>{}