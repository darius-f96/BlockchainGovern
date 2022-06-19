package floread.backendapi.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;


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
	@JsonProperty("id")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "UUID")
	@Column(name="\"CompanyContractPersonId\"")
	private String companyContractPersonId;

	@Column(name="\"AppUserId\"")
	private String appUserId;

	@Column(name="\"CompanyId\"")
	private String companyId;

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

	//bi-directional many-to-one association to AppUser
	@ManyToOne
	private AppUser appUser;

	//bi-directional many-to-one association to Company
	@ManyToOne
	private Company company;

	@OneToOne
    @JoinColumn(name = "contract_details_id", insertable = false, updatable = false)
	private ContractDetails contractDetails;

	public CompanyContractPerson() {
	}

	public String getCompanyContractPersonId() {
		return this.companyContractPersonId;
	}

	public void setCompanyContractPersonId(String companyContractPersonId) {
		this.companyContractPersonId = companyContractPersonId;
	}

	public String getAppUserId() {
		return this.appUserId;
	}

	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getContractId() {
		return this.contractId;
	}

	public void setContractId(String contractId) {
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

	public String getContractCode() {
		return this.contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public ContractDetails getContractDetails(){
		return this.contractDetails;
	}
	public void setContractDetails (ContractDetails contractDetails){
		this.contractDetails = contractDetails;
	}
	public String getContractDetailsId(){
		return this.contractDetailsId;
	}
	public void setContractDetailsId(String contractDetailsId){
		this.contractDetailsId = contractDetailsId;
	}
}