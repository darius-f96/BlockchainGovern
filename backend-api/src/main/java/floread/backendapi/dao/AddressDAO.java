package floread.backendapi.dao;



import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import floread.backendapi.entities.Address;
@Transactional
public interface AddressDAO extends CrudRepository<Address, String>{}