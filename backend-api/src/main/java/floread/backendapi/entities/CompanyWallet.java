package floread.backendapi.entities;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * The persistent class for the "CompanyWallet" database table.
 * 
 */
@Entity
@Table(name="\"CompanyWallet\"")
@NamedQuery(name="CompanyWallet.findAll", query="SELECT c FROM CompanyWallet c")
public class CompanyWallet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@JsonProperty("id")
	@Column(name="\"CompanyWalletId\"")
	private String companyWalletId;

	@Column(name="\"CompanyId\"")
	private String companyId;

	@Column(name="\"WalletId\"")
	private String walletId;

	@Column(name="\"WalletDescription\"")
	private String walletDescription;

	@Column(name="\"WalletCode\"")
	private String walletCode;

	//bi-directional many-to-one association to Company
	@ManyToOne
	private Company company;

	public CompanyWallet() {
	}

	public String getCompanyWalletId() {
		return this.companyWalletId;
	}

	public void setCompanyWalletId(String companyWalletId) {
		this.companyWalletId = companyWalletId;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getWalletId() {
		return this.walletId;
	}
	public String getWalletDescription() {
		return this.walletDescription;
	}
	public String getWalletCode() {
		return this.walletCode;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}
	public void setWalletDescription(String walletDescription) {
		this.walletDescription = walletDescription;
	}
	public void setWalletCode(String walletCode) {
		this.walletCode = walletCode;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}