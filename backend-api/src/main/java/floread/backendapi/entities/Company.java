package floread.backendapi.entities;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * The persistent class for the "Company" database table.
 * 
 */
@Entity
@Table(name="\"Company\"")
@NamedQuery(name="Company.findAll", query="SELECT c FROM Company c")
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@JsonProperty("id")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "UUID")
	@Column(name="\"CompanyId\"")
	private String companyId;

	@Column(name="\"CUI\"")
	private String cui;

	@Column(name="\"Description\"")
	private String description;

	@Column(name="\"Name\"")
	private String name;

	@Column(name="\"RegIdentifier\"")
	private String regIdentifier;

	//bi-directional many-to-one association to CompanyContractCompany
	@OneToMany(mappedBy="companyId1")
	private List<CompanyContractCompany> companyContractCompanies1;

	//bi-directional many-to-one association to CompanyContractCompany
	@OneToMany(mappedBy="companyId2")
	private List<CompanyContractCompany> companyContractCompanies2;

	//bi-directional many-to-one association to CompanyContractPerson
	@OneToMany(mappedBy="companyId")
	private List<CompanyContractPerson> companyContractPersons;

	//bi-directional many-to-one association to CompanyWallet
	@OneToMany(mappedBy="companyId")
	private List<CompanyWallet> companyWallets;

	//bi-directional many-to-one association to Person
	@OneToMany(mappedBy="companyId")
	private List<Person> persons;

	//bi-directional many-to-one association to UserRole
	@OneToMany(mappedBy="companyId")
	private List<UserRole> userRoles;

	public Company() {
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCui() {
		return this.cui;
	}

	public void setCui(String cui) {
		this.cui = cui;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegIdentifier() {
		return this.regIdentifier;
	}

	public void setRegIdentifier(String regIdentifier) {
		this.regIdentifier = regIdentifier;
	}

	public List<CompanyContractCompany> getCompanyContractCompanies1() {
		List<CompanyContractCompany> joinedList = new ArrayList<>();
		Stream.of(this.companyContractCompanies1, this.companyContractCompanies2).forEach(joinedList::addAll);
		return joinedList;
	}

	public void setCompanyContractCompanies1(List<CompanyContractCompany> companyContractCompanies1) {
		this.companyContractCompanies1 = companyContractCompanies1;
	}

	public CompanyContractCompany addCompanyContractCompanies1(CompanyContractCompany companyContractCompanies1) {
		getCompanyContractCompanies1().add(companyContractCompanies1);
		companyContractCompanies1.setCompany1(this);

		return companyContractCompanies1;
	}

	public CompanyContractCompany removeCompanyContractCompanies1(CompanyContractCompany companyContractCompanies1) {
		getCompanyContractCompanies1().remove(companyContractCompanies1);
		companyContractCompanies1.setCompany1(null);

		return companyContractCompanies1;
	}

	public List<CompanyContractCompany> getCompanyContractCompanies2() {
		return this.companyContractCompanies2;
	}

	public void setCompanyContractCompanies2(List<CompanyContractCompany> companyContractCompanies2) {
		this.companyContractCompanies2 = companyContractCompanies2;
	}

	public CompanyContractCompany addCompanyContractCompanies2(CompanyContractCompany companyContractCompanies2) {
		getCompanyContractCompanies2().add(companyContractCompanies2);
		companyContractCompanies2.setCompany2(this);

		return companyContractCompanies2;
	}

	public CompanyContractCompany removeCompanyContractCompanies2(CompanyContractCompany companyContractCompanies2) {
		getCompanyContractCompanies2().remove(companyContractCompanies2);
		companyContractCompanies2.setCompany2(null);

		return companyContractCompanies2;
	}

	public List<CompanyContractPerson> getCompanyContractPersons() {
		return this.companyContractPersons;
	}

	public void setCompanyContractPersons(List<CompanyContractPerson> companyContractPersons) {
		this.companyContractPersons = companyContractPersons;
	}

	public CompanyContractPerson addCompanyContractPerson(CompanyContractPerson companyContractPerson) {
		getCompanyContractPersons().add(companyContractPerson);
		companyContractPerson.setCompany(this);

		return companyContractPerson;
	}

	public CompanyContractPerson removeCompanyContractPerson(CompanyContractPerson companyContractPerson) {
		getCompanyContractPersons().remove(companyContractPerson);
		companyContractPerson.setCompany(null);

		return companyContractPerson;
	}

	public List<CompanyWallet> getCompanyWallets() {
		return this.companyWallets;
	}

	public void setCompanyWallets(List<CompanyWallet> companyWallets) {
		this.companyWallets = companyWallets;
	}

	public CompanyWallet addCompanyWallet(CompanyWallet companyWallet) {
		getCompanyWallets().add(companyWallet);
		companyWallet.setCompany(this);

		return companyWallet;
	}

	public CompanyWallet removeCompanyWallet(CompanyWallet companyWallet) {
		getCompanyWallets().remove(companyWallet);
		companyWallet.setCompany(null);

		return companyWallet;
	}

	public List<Person> getPersons() {
		return this.persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public Person addPerson(Person person) {
		getPersons().add(person);
		person.setCompany(this);

		return person;
	}

	public Person removePerson(Person person) {
		getPersons().remove(person);
		person.setCompany(null);

		return person;
	}

	public List<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public UserRole addUserRole(UserRole userRole) {
		getUserRoles().add(userRole);
		userRole.setCompany(this);

		return userRole;
	}

	public UserRole removeUserRole(UserRole userRole) {
		getUserRoles().remove(userRole);
		userRole.setCompany(null);

		return userRole;
	}

}