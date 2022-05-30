package floread.backendapi.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the "Contract" database table.
 * 
 */
@Entity
@Table(name="\"Contract\"")
@NamedQuery(name="Contract.findAll", query="SELECT c FROM Contract c")
public class Contract implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"ContractId\"")
	private String contractId;

	@Column(name="\"ContractCode\"")
	private String contractCode;

	@Column(name="\"Description\"")
	private String description;

	//bi-directional many-to-one association to CompanyContractCompany
	@OneToMany(mappedBy="contract")
	private List<CompanyContractCompany> companyContractCompanies;

	//bi-directional many-to-one association to CompanyContractPerson
	@OneToMany(mappedBy="contract")
	private List<CompanyContractPerson> companyContractPersons;

	public Contract() {
	}

	public String getContractId() {
		return this.contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getContractCode() {
		return this.contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<CompanyContractCompany> getCompanyContractCompanies() {
		return this.companyContractCompanies;
	}

	public void setCompanyContractCompanies(List<CompanyContractCompany> companyContractCompanies) {
		this.companyContractCompanies = companyContractCompanies;
	}

	public CompanyContractCompany addCompanyContractCompany(CompanyContractCompany companyContractCompany) {
		getCompanyContractCompanies().add(companyContractCompany);
		companyContractCompany.setContract(this);

		return companyContractCompany;
	}

	public CompanyContractCompany removeCompanyContractCompany(CompanyContractCompany companyContractCompany) {
		getCompanyContractCompanies().remove(companyContractCompany);
		companyContractCompany.setContract(null);

		return companyContractCompany;
	}

	public List<CompanyContractPerson> getCompanyContractPersons() {
		return this.companyContractPersons;
	}

	public void setCompanyContractPersons(List<CompanyContractPerson> companyContractPersons) {
		this.companyContractPersons = companyContractPersons;
	}

	public CompanyContractPerson addCompanyContractPerson(CompanyContractPerson companyContractPerson) {
		getCompanyContractPersons().add(companyContractPerson);
		companyContractPerson.setContract(this);

		return companyContractPerson;
	}

	public CompanyContractPerson removeCompanyContractPerson(CompanyContractPerson companyContractPerson) {
		getCompanyContractPersons().remove(companyContractPerson);
		companyContractPerson.setContract(null);

		return companyContractPerson;
	}

}