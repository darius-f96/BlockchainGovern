package floread.backendapi.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the "Address" database table.
 * 
 */
@Entity
@Table(name="\"Address\"")
@NamedQuery(name="Address.findAll", query="SELECT a FROM Address a")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"AddressId\"")
	private String addressId;

	@Column(name="\"AptNumber\"")
	private Integer aptNumber;

	@Column(name="\"Building\"")
	private String building;

	@Column(name="\"CityId\"")
	private String cityId;

	@Column(name="\"CountyId\"")
	private String countyId;

	@Column(name="\"Floor\"")
	private Integer floor;

	@Column(name="\"Number\"")
	private String number;

	@Column(name="\"PostCode\"")
	private String postCode;

	@Column(name="\"Street\"")
	private String street;

	//bi-directional many-to-one association to City
	@ManyToOne
	private City city;

	//bi-directional many-to-one association to CompanyAddress
	@OneToMany(mappedBy="address")
	private List<CompanyAddress> companyAddresses;

	//bi-directional many-to-one association to PersonAddress
	@OneToMany(mappedBy="address")
	private List<PersonAddress> personAddresses;

	public Address() {
	}

	public String getAddressId() {
		return this.addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public Integer getAptNumber() {
		return this.aptNumber;
	}

	public void setAptNumber(Integer aptNumber) {
		this.aptNumber = aptNumber;
	}

	public String getBuilding() {
		return this.building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public Object getCityId() {
		return this.cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public Object getCountyId() {
		return this.countyId;
	}

	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

	public Integer getFloor() {
		return this.floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public City getCity() {
		return this.city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public List<CompanyAddress> getCompanyAddresses() {
		return this.companyAddresses;
	}

	public void setCompanyAddresses(List<CompanyAddress> companyAddresses) {
		this.companyAddresses = companyAddresses;
	}

	public CompanyAddress addCompanyAddress(CompanyAddress companyAddress) {
		getCompanyAddresses().add(companyAddress);
		companyAddress.setAddress(this);

		return companyAddress;
	}

	public CompanyAddress removeCompanyAddress(CompanyAddress companyAddress) {
		getCompanyAddresses().remove(companyAddress);
		companyAddress.setAddress(null);

		return companyAddress;
	}

	public List<PersonAddress> getPersonAddresses() {
		return this.personAddresses;
	}

	public void setPersonAddresses(List<PersonAddress> personAddresses) {
		this.personAddresses = personAddresses;
	}

	public PersonAddress addPersonAddress(PersonAddress personAddress) {
		getPersonAddresses().add(personAddress);
		personAddress.setAddress(this);

		return personAddress;
	}

	public PersonAddress removePersonAddress(PersonAddress personAddress) {
		getPersonAddresses().remove(personAddress);
		personAddress.setAddress(null);

		return personAddress;
	}

}