package floread.backendapi.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the "PersonWallet" database table.
 * 
 */
@Entity
@Table(name="\"PersonWallet\"")
@NamedQuery(name="PersonWallet.findAll", query="SELECT p FROM PersonWallet p")
public class PersonWallet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"PersonWalletId\"")
	private String personWalletId;

	@Column(name="\"AppUserId\"")
	private String appUserId;

	@Column(name="\"WalletId\"")
	private String walletId;

	//bi-directional many-to-one association to AppUser
	@ManyToOne
	private AppUser appUser;

	//bi-directional many-to-one association to Wallet
	@ManyToOne
	private Wallet wallet;

	public PersonWallet() {
	}

	public String getPersonWalletId() {
		return this.personWalletId;
	}

	public void setPersonWalletId(String personWalletId) {
		this.personWalletId = personWalletId;
	}

	public Object getAppUserId() {
		return this.appUserId;
	}

	public void setAppUserId(String appUserId) {
		this.appUserId = appUserId;
	}

	public Object getWalletId() {
		return this.walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public AppUser getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public Wallet getWallet() {
		return this.wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

}