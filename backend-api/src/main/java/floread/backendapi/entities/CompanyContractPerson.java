package floread.backendapi.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the "CompanyContractPerson" database table.
 * 
 */
@Entity
@Table(name="\"CompanyContractPerson\"")
@NamedQuery(name="CompanyContractPerson.findAll", query="SELECT c FROM CompanyContractPerson c")
public class CompanyContractPerson implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"CompanyContractPersonId\"")
	private String companyContractPersonId;

	@Column(name="\"AppUserId\"")
	private String appUserId;

	@Column(name="\"CompanyId\"")
	private String companyId;

	@Column(name="\"ContractId\"")
	private String contractId;

	//bi-directional many-to-one association to AppUser
	@ManyToOne
	private AppUser appUser;

	//bi-directional many-to-one association to Company
	@ManyToOne
	private Company company;

	//bi-directional many-to-one association to Contract
	@ManyToOne
	private Contract contract;

	public CompanyContractPerson() {
	}

	public String getCompanyContractPersonId() {
		return this.companyContractPersonId;
	}

	public void setCompanyContractPersonId(String companyContractPersonId) {
		this.companyContractPersonId = companyContractPersonId;
	}

	public Object getAppUserId() {
		return this.appUserId;
	}

	public void setAppUserId(Object appUserId) {
		this.appUserId = appUserId;
	}

	public Object getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Object companyId) {
		this.companyId = companyId;
	}

	public Object getContractId() {
		return this.contractId;
	}

	public void setContractId(Object contractId) {
		this.contractId = contractId;
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

	public Contract getContract() {
		return this.contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

}