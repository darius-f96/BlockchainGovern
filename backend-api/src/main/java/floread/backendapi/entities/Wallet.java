package floread.backendapi.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the "Wallet" database table.
 * 
 */
@Entity
@Table(name="\"Wallet\"")
@NamedQuery(name="Wallet.findAll", query="SELECT w FROM Wallet w")
public class Wallet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"WalletId\"")
	private String walletId;

	@Column(name="\"WalletCode\"")
	private String walletCode;

	//bi-directional many-to-one association to CompanyWallet
	@OneToMany(mappedBy="wallet")
	private List<CompanyWallet> companyWallets;

	//bi-directional many-to-one association to PersonWallet
	@OneToMany(mappedBy="wallet")
	private List<PersonWallet> personWallets;

	public Wallet() {
	}

	public String getWalletId() {
		return this.walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public String getWalletCode() {
		return this.walletCode;
	}

	public void setWalletCode(String walletCode) {
		this.walletCode = walletCode;
	}

	public List<CompanyWallet> getCompanyWallets() {
		return this.companyWallets;
	}

	public void setCompanyWallets(List<CompanyWallet> companyWallets) {
		this.companyWallets = companyWallets;
	}

	public CompanyWallet addCompanyWallet(CompanyWallet companyWallet) {
		getCompanyWallets().add(companyWallet);
		companyWallet.setWallet(this);

		return companyWallet;
	}

	public CompanyWallet removeCompanyWallet(CompanyWallet companyWallet) {
		getCompanyWallets().remove(companyWallet);
		companyWallet.setWallet(null);

		return companyWallet;
	}

	public List<PersonWallet> getPersonWallets() {
		return this.personWallets;
	}

	public void setPersonWallets(List<PersonWallet> personWallets) {
		this.personWallets = personWallets;
	}

	public PersonWallet addPersonWallet(PersonWallet personWallet) {
		getPersonWallets().add(personWallet);
		personWallet.setWallet(this);

		return personWallet;
	}

	public PersonWallet removePersonWallet(PersonWallet personWallet) {
		getPersonWallets().remove(personWallet);
		personWallet.setWallet(null);

		return personWallet;
	}

}