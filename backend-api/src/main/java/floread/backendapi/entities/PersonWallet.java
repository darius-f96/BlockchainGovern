package floread.backendapi.entities;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;


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
	@JsonProperty("id")
	@Column(name="\"PersonWalletId\"")
	private String personWalletId;

	@Column(name="\"AppUserId\"")
	private String appUserId;

	@Column(name="\"WalletId\"")
	private String walletId;
	
	@Column(name="\"WalletDescription\"")
	private String walletDescription;

	@Column(name="\"WalletCode\"")
	private String walletCode;

	//bi-directional many-to-one association to AppUser
	@ManyToOne
	private AppUser appUser;

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

	public AppUser getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

}