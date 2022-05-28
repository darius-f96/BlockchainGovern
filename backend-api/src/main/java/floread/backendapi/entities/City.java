package floread.backendapi.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;

import java.util.List;


/**
 * The persistent class for the "City" database table.
 * 
 */
@Entity
@Table(name="\"City\"")
@NamedQuery(name="City.findAll", query="SELECT c FROM City c")
public class City implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"CityId\"")
	private String cityId;

	@Column(name="\"CountryId\"")
	private String countryId;

	@Column(name="\"CountyId\"")
	private String countyId;

	@Column(name="\"Name\"")
	private String name;

	//bi-directional many-to-one association to Address
	@OneToMany(mappedBy="city")
	private List<Address> addresses;

	//bi-directional many-to-one association to Country
	@ManyToOne
	private Country country;

	//bi-directional many-to-one association to County
	@ManyToOne
	private County county;

	public City() {
	}

	public String getCityId() {
		return this.cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public Object getCountryId() {
		return this.countryId;
	}

	public void setCountryId(Object countryId) {
		this.countryId = countryId;
	}

	public Object getCountyId() {
		return this.countyId;
	}

	public void setCountyId(Object countyId) {
		this.countyId = countyId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Address> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Address addAddress(Address address) {
		getAddresses().add(address);
		address.setCity(this);

		return address;
	}

	public Address removeAddress(Address address) {
		getAddresses().remove(address);
		address.setCity(null);

		return address;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public County getCounty() {
		return this.county;
	}

	public void setCounty(County county) {
		this.county = county;
	}

}