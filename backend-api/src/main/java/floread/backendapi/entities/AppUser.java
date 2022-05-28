package floread.backendapi.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Array;
import java.util.List;


/**
 * The persistent class for the "AppUser" database table.
 * 
 */
@Entity
@Table(name="\"AppUser\"")
@NamedQuery(name="AppUser.findAll", query="SELECT a FROM AppUser a")
public class AppUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"AppUserId\"")
	private String appUserId;

	@Column(name="\"Email\"")
	private String email;

	@Column(name="\"Password\"")
	private String password;

	@Column(name="\"Username\"")
	private String username;

	//bi-directional many-to-one association to CompanyContractPerson
	@OneToMany(mappedBy="appUser")
	private List<CompanyContractPerson> companyContractPersons;

	//bi-directional many-to-one association to Person
	@OneToMany(mappedBy="appUser")
	private List<Person> persons;

	//bi-directional many-to-one association to PersonWallet
	@OneToMany(mappedBy="appUser")
	private List<PersonWallet> personWallets;

	//bi-directional many-to-one association to UserRole
	@OneToMany(mappedBy="appUser")
	private List<UserRole> userRoles;

	public AppUser() {
	}

	public String getAppUserId() {
		return this.appUserId;
	}

	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}

	public Array getEmail() {
		return this.email;
	}

	public void setEmail(Array email) {
		this.email = email;
	}

	public Array getPassword() {
		return this.password;
	}

	public void setPassword(Array password) {
		this.password = password;
	}

	public Array getUsername() {
		return this.username;
	}

	public void setUsername(Array username) {
		this.username = username;
	}

	public List<CompanyContractPerson> getCompanyContractPersons() {
		return this.companyContractPersons;
	}

	public void setCompanyContractPersons(List<CompanyContractPerson> companyContractPersons) {
		this.companyContractPersons = companyContractPersons;
	}

	public CompanyContractPerson addCompanyContractPerson(CompanyContractPerson companyContractPerson) {
		getCompanyContractPersons().add(companyContractPerson);
		companyContractPerson.setAppUser(this);

		return companyContractPerson;
	}

	public CompanyContractPerson removeCompanyContractPerson(CompanyContractPerson companyContractPerson) {
		getCompanyContractPersons().remove(companyContractPerson);
		companyContractPerson.setAppUser(null);

		return companyContractPerson;
	}

	public List<Person> getPersons() {
		return this.persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public Person addPerson(Person person) {
		getPersons().add(person);
		person.setAppUser(this);

		return person;
	}

	public Person removePerson(Person person) {
		getPersons().remove(person);
		person.setAppUser(null);

		return person;
	}

	public List<PersonWallet> getPersonWallets() {
		return this.personWallets;
	}

	public void setPersonWallets(List<PersonWallet> personWallets) {
		this.personWallets = personWallets;
	}

	public PersonWallet addPersonWallet(PersonWallet personWallet) {
		getPersonWallets().add(personWallet);
		personWallet.setAppUser(this);

		return personWallet;
	}

	public PersonWallet removePersonWallet(PersonWallet personWallet) {
		getPersonWallets().remove(personWallet);
		personWallet.setAppUser(null);

		return personWallet;
	}

	public List<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public UserRole addUserRole(UserRole userRole) {
		getUserRoles().add(userRole);
		userRole.setAppUser(this);

		return userRole;
	}

	public UserRole removeUserRole(UserRole userRole) {
		getUserRoles().remove(userRole);
		userRole.setAppUser(null);

		return userRole;
	}

}