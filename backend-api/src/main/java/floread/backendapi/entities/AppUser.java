package floread.backendapi.entities;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collection;
import java.util.Collections;
import java.util.List;



/**
 * The persistent class for the "AppUser" database table.
 * 
 */
@Entity
@Table(name="\"AppUser\"")
@NamedQuery(name="AppUser.findAll", query="SELECT a FROM AppUser a")
public class AppUser implements UserDetails {
	private static final long serialVersionUID = 1L;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		SimpleGrantedAuthority authority;
	
		authority = new SimpleGrantedAuthority("ADMIN");
	
		return Collections.singletonList(authority);
	}

	@Id
	@Column(name="\"AppUserId\"")
	@JsonProperty("id")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "UUID")
	private String appUserId;

	@Column(name="\"Email\"")
	private String email;

	@Column(name="\"Password\"")
	private String password;

	@Column(name="\"Username\"")
	private String username;

	private boolean locked;
	private boolean enabled;

	//bi-directional many-to-one association to CompanyContractPerson
	@OneToMany(mappedBy="appUser")
	private List<CompanyContractPerson> companyContractPersons;

	//bi-directional many-to-one association to Person
	@OneToMany(mappedBy="appUserId")
	private List<Person> persons;

	//bi-directional many-to-one association to PersonWallet
	@OneToMany(mappedBy="appUserId")
	private List<PersonWallet> personWallets;

	//bi-directional many-to-one association to UserRole
	@OneToMany(mappedBy="appUserId")
	private List<UserRole> userRoles;

	@Transient
	private Collection<? extends GrantedAuthority> authorities;

	public AppUser(String username,
					String email,
					String password) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.locked = false;
		this.enabled = true;
	}
	public AppUser(){
		
	}

	public String getAppUserId() {
		return this.appUserId;
	}

	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
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
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return !locked;

	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled(){
		return enabled;
	}
	public void setEnabled (Boolean enabled){
		this.enabled = enabled;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities){
		this.authorities = authorities;
	}

}