package floread.backendapi.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import floread.backendapi.entities.CompanyWallet;
@Repository
public interface CompanyWalletDAO extends JpaRepository<CompanyWallet, String>{}