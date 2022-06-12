package floread.backendapi.entities;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


/**
 * The persistent class for the "Person" database table.
 * 
 */
@Entity
@Table(name="\"Person\"")
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@JsonProperty("id")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "UUID")
	@Column(name="\"PersonId\"")
	private String personId;

	@Column(name="\"AppUserId\"")
	private String appUserId;

	@Column(name="\"BirthDate\"")
	private String birthDate;

	@Column(name="\"CompanyId\"")
	private String companyId;

	@Column(name="\"Firstname\"")
	private String firstname;

	@Column(name="\"Gender\"")
	private String gender;

	@Column(name="\"Lastname\"")
	private String lastname;

	@Column(name="\"Salutation\"")
	private String salutation;

	@Column(name="\"PersonCode\"")
	private String personCode;

	//bi-directional many-to-one association to AppUser
	@ManyToOne
	private AppUser appUser;

	//bi-directional many-to-one association to Company
	@ManyToOne
	private Company company;

	//bi-directional many-to-one association to PersonAddress
	@OneToMany(mappedBy="person")
	private List<PersonAddress> personAddresses;

	public Person() {
	}

	public String getPersonId() {
		return this.personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getPersonCode(){
		return this.personCode;
	}
	public void setPersonCode(String personCode){
		this.personCode = personCode;
	}

	public Object getAppUserId() {
		return this.appUserId;
	}

	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}

	public String getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public Object getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getSalutation() {
		return this.salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public AppUser getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<PersonAddress> getPersonAddresses() {
		return this.personAddresses;
	}

	public void setPersonAddresses(List<PersonAddress> personAddresses) {
		this.personAddresses = personAddresses;
	}

	public PersonAddress addPersonAddress(PersonAddress personAddress) {
		getPersonAddresses().add(personAddress);
		personAddress.setPerson(this);

		return personAddress;
	}

	public PersonAddress removePersonAddress(PersonAddress personAddress) {
		getPersonAddresses().remove(personAddress);
		personAddress.setPerson(null);

		return personAddress;
	}

}