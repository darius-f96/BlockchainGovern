package floread.backendapi.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the "CompanyContractCompany" database table.
 * 
 */
@Entity
@Table(name="\"CompanyContractCompany\"")
@NamedQuery(name="CompanyContractCompany.findAll", query="SELECT c FROM CompanyContractCompany c")
public class CompanyContractCompany implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"CompanyContractCompanyId\"")
	private String companyContractCompanyId;

	@Column(name="\"CompanyId1\"")
	private String companyId1;

	@Column(name="\"CompanyId2\"")
	private String companyId2;

	@Column(name="\"ContractId\"")
	private String contractId;

	//bi-directional many-to-one association to Company
	@ManyToOne
	private Company company1;

	//bi-directional many-to-one association to Company
	@ManyToOne
	private Company company2;

	//bi-directional many-to-one association to Contract
	@ManyToOne
	private Contract contract;

	public CompanyContractCompany() {
	}

	public String getCompanyContractCompanyId() {
		return this.companyContractCompanyId;
	}

	public void setCompanyContractCompanyId(String companyContractCompanyId) {
		this.companyContractCompanyId = companyContractCompanyId;
	}

	public Object getCompanyId1() {
		return this.companyId1;
	}

	public void setCompanyId1(String companyId1) {
		this.companyId1 = companyId1;
	}

	public Object getCompanyId2() {
		return this.companyId2;
	}

	public void setCompanyId2(String companyId2) {
		this.companyId2 = companyId2;
	}

	public Object getContractId() {
		return this.contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public Company getCompany1() {
		return this.company1;
	}

	public void setCompany1(Company company1) {
		this.company1 = company1;
	}

	public Company getCompany2() {
		return this.company2;
	}

	public void setCompany2(Company company2) {
		this.company2 = company2;
	}

	public Contract getContract() {
		return this.contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

}