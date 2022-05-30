package floread.backendapi.entities;

import java.io.Serializable;
import javax.persistence.*;


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
	@Column(name="\"CompanyWalletId\"")
	private String companyWalletId;

	@Column(name="\"CompanyId\"")
	private String companyId;

	@Column(name="\"WalletId\"")
	private String walletId;

	//bi-directional many-to-one association to Company
	@ManyToOne
	private Company company;

	//bi-directional many-to-one association to Wallet
	@ManyToOne
	private Wallet wallet;

	public CompanyWallet() {
	}

	public String getCompanyWalletId() {
		return this.companyWalletId;
	}

	public void setCompanyWalletId(String companyWalletId) {
		this.companyWalletId = companyWalletId;
	}

	public Object getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Object getWalletId() {
		return this.walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Wallet getWallet() {
		return this.wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

}