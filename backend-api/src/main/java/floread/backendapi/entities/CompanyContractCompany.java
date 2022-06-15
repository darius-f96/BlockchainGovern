package floread.backendapi.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;


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
	@JsonProperty("id")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "UUID")
	@Column(name="\"CompanyContractCompanyId\"")
	private String companyContractCompanyId;

	@Column(name="\"CompanyId1\"")
	private String companyId1;

	@Column(name="\"CompanyId2\"")
	private String companyId2;

	@Column(name="\"ContractId\"")
	private String contractId;

	@Column(name="\"Terms\"")
	private String terms;

	@Column(name="\"ContractCode\"")
	private String contractCode;

	@Column(name="\"Accepted\"")
	private Boolean accepted;

	@Column(name="\"ContractDetailsId\"")
	private String contractDetailsId;

	//bi-directional many-to-one association to Company
	@ManyToOne
	private Company company1;

	//bi-directional many-to-one association to Company
	@ManyToOne
	private Company company2;

	@OneToMany(mappedBy = "contractDetailsId")
	private List<ContractDetails> contractDetails;

	public CompanyContractCompany() {
	}

	public String getCompanyContractCompanyId() {
		return this.companyContractCompanyId;
	}

	public void setCompanyContractCompanyId(String companyContractCompanyId) {
		this.companyContractCompanyId = companyContractCompanyId;
	}

	public String getCompanyId1() {
		return this.companyId1;
	}

	public void setCompanyId1(String companyId1) {
		this.companyId1 = companyId1;
	}

	public String getCompanyId2() {
		return this.companyId2;
	}

	public void setCompanyId2(String companyId2) {
		this.companyId2 = companyId2;
	}
	
	public String getContractCode() {
		return this.contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getContractId() {
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
	
	public void setTerms (String terms){
		this.terms = terms;
	}

	public String getTerms (){
		return this.terms;
	}

	public void setAccepted(Boolean accepted){
		this.accepted = accepted;
	}
	public Boolean getAccepted(){
		return this.accepted;
	}
	public List<ContractDetails> getContractDetails(){
		return this.contractDetails;
	}
	public void setContractDetails (List<ContractDetails> contractDetails){
		this.contractDetails = contractDetails;
	}
	public ContractDetails addContractDetails(ContractDetails cDetails) {
		getContractDetails().add(cDetails);
		cDetails.setCompanyContractCompany(this);

		return cDetails;
	}

	public ContractDetails removeContractDetails(ContractDetails cDetails) {
		getContractDetails().remove(cDetails);
		cDetails.setCompanyContractCompany(null);

		return cDetails;
	}

	public String getContractDetailsId(){
		return this.contractDetailsId;
	}
	public void setContractDetailsId(String contractDetailsId){
		this.contractDetailsId = contractDetailsId;
	}

}