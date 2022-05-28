package floread.backendapi.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Array;
import java.util.List;


/**
 * The persistent class for the "County" database table.
 * 
 */
@Entity
@Table(name="\"County\"")
@NamedQuery(name="County.findAll", query="SELECT c FROM County c")
public class County implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"CountyId\"")
	private String countyId;

	@Column(name="\"Code\"")
	private String code;

	@Column(name="\"CountryId\"")
	private String countryId;

	@Column(name="\"Name\"")
	private String name;

	//bi-directional many-to-one association to City
	@OneToMany(mappedBy="county")
	private List<City> cities;

	//bi-directional many-to-one association to Country
	@ManyToOne
	private Country country;

	public County() {
	}

	public String getCountyId() {
		return this.countyId;
	}

	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

	public Array getCode() {
		return this.code;
	}

	public void setCode(Array code) {
		this.code = code;
	}

	public Object getCountryId() {
		return this.countryId;
	}

	public void setCountryId(Object countryId) {
		this.countryId = countryId;
	}

	public Array getName() {
		return this.name;
	}

	public void setName(Array name) {
		this.name = name;
	}

	public List<City> getCities() {
		return this.cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public City addCity(City city) {
		getCities().add(city);
		city.setCounty(this);

		return city;
	}

	public City removeCity(City city) {
		getCities().remove(city);
		city.setCounty(null);

		return city;
	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

}