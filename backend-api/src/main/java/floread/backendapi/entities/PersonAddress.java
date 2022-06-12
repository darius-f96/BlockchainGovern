package floread.backendapi.entities;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * The persistent class for the "PersonAddress" database table.
 * 
 */
@Entity
@Table(name="\"PersonAddress\"")
@NamedQuery(name="PersonAddress.findAll", query="SELECT p FROM PersonAddress p")
public class PersonAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@JsonProperty("id")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "UUID")
	@Column(name="\"PersonAddressId\"")
	private String personAddressId;

	@Column(name="\"AddressId\"")
	private String addressId;

	@Column(name="\"PersonId\"")
	private String personId;

	//bi-directional many-to-one association to Address
	@ManyToOne
	private Address address;

	//bi-directional many-to-one association to Person
	@ManyToOne
	private Person person;

	public PersonAddress() {
	}

	public String getPersonAddressId() {
		return this.personAddressId;
	}

	public void setPersonAddressId(String personAddressId) {
		this.personAddressId = personAddressId;
	}

	public Object getAddressId() {
		return this.addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public Object getPersonId() {
		return this.personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}