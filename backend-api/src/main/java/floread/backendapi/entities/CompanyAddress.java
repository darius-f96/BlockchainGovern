package floread.backendapi.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the "CompanyAddress" database table.
 * 
 */
@Entity
@Table(name="\"CompanyAddress\"")
@NamedQuery(name="CompanyAddress.findAll", query="SELECT c FROM CompanyAddress c")
public class CompanyAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"CompanyAddressId\"")
	private String companyAddressId;

	@Column(name="\"AddressId\"")
	private String addressId;

	@Column(name="\"CompanyId\"")
	private String companyId;

	//bi-directional many-to-one association to Address
	@ManyToOne
	private Address address;

	//bi-directional many-to-one association to Company
	@ManyToOne
	private Company company;

	public CompanyAddress() {
	}

	public String getCompanyAddressId() {
		return this.companyAddressId;
	}

	public void setCompanyAddressId(String companyAddressId) {
		this.companyAddressId = companyAddressId;
	}

	public Object getAddressId() {
		return this.addressId;
	}

	public void setAddressId(Object addressId) {
		this.addressId = addressId;
	}

	public Object getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Object companyId) {
		this.companyId = companyId;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}