package floread.backendapi.entities;

import java.io.Serializable;
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

	//bi-directional many-to-one association to AppUser
	@ManyToOne
	private AppUser appUser;

	//bi-directional many-to-one association to Company
	@ManyToOne
	private Company company;

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

	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}

	public Object getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Object getContractId() {
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

}