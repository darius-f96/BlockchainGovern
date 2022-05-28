package floread.backendapi.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Array;
import java.util.List;


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

	//bi-directional many-to-one association to CompanyAddress
	@OneToMany(mappedBy="company")
	private List<CompanyAddress> companyAddresses;

	//bi-directional many-to-one association to CompanyContractCompany
	@OneToMany(mappedBy="company1")
	private List<CompanyContractCompany> companyContractCompanies1;

	//bi-directional many-to-one association to CompanyContractCompany
	@OneToMany(mappedBy="company2")
	private List<CompanyContractCompany> companyContractCompanies2;

	//bi-directional many-to-one association to CompanyContractPerson
	@OneToMany(mappedBy="company")
	private List<CompanyContractPerson> companyContractPersons;

	//bi-directional many-to-one association to CompanyWallet
	@OneToMany(mappedBy="company")
	private List<CompanyWallet> companyWallets;

	//bi-directional many-to-one association to Person
	@OneToMany(mappedBy="company")
	private List<Person> persons;

	//bi-directional many-to-one association to UserRole
	@OneToMany(mappedBy="company")
	private List<UserRole> userRoles;

	public Company() {
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Array getCui() {
		return this.cui;
	}

	public void setCui(Array cui) {
		this.cui = cui;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Array getName() {
		return this.name;
	}

	public void setName(Array name) {
		this.name = name;
	}

	public Array getRegIdentifier() {
		return this.regIdentifier;
	}

	public void setRegIdentifier(Array regIdentifier) {
		this.regIdentifier = regIdentifier;
	}

	public List<CompanyAddress> getCompanyAddresses() {
		return this.companyAddresses;
	}

	public void setCompanyAddresses(List<CompanyAddress> companyAddresses) {
		this.companyAddresses = companyAddresses;
	}

	public CompanyAddress addCompanyAddress(CompanyAddress companyAddress) {
		getCompanyAddresses().add(companyAddress);
		companyAddress.setCompany(this);

		return companyAddress;
	}

	public CompanyAddress removeCompanyAddress(CompanyAddress companyAddress) {
		getCompanyAddresses().remove(companyAddress);
		companyAddress.setCompany(null);

		return companyAddress;
	}

	public List<CompanyContractCompany> getCompanyContractCompanies1() {
		return this.companyContractCompanies1;
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