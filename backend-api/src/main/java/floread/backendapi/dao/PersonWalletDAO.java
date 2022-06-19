package floread.backendapi.dao;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import floread.backendapi.entities.PersonWallet;
@Repository
public interface PersonWalletDAO extends JpaRepository<PersonWallet, String>{
    public Optional<PersonWallet> findByWalletId(String walletId);
    public List<PersonWallet> findByAppUserId(String appUserId);
}