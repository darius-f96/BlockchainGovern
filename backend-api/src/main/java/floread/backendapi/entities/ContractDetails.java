package floread.backendapi.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="\"ContractDetails\"")
@NamedQuery(name="ContractDetails.findAll", query="SELECT c FROM ContractDetails c")
public class ContractDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@JsonProperty("id")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@GeneratedValue(generator = "UUID")
	@Column(name="\"ContractDetailsId\"")
	private String contractDetailsId;

	@Column(name="\"WireFrequency\"")
	private Integer wireFrequency;

	@Column(name="\"LastWire\"")
	private Date lastWire;

	@Column(name="\"Active\"")
	private Boolean active;

	@Column(name="\"DaysBeforeCancel\"")
	private Integer daysBeforeCancel;

	@Column(name="\"StartDate\"")
	private Date startDate;

	@Column(name="\"Amount\"")
	private Integer amount;

	
	@ManyToOne
	private CompanyContractCompany companyContractCompany;

    @ManyToOne
	private CompanyContractPerson companyContractPerson;


	public ContractDetails() {
	}

	public Integer getWireFrequency(){
		return this.wireFrequency;
	}
	public Date getLastWire(){
		return this.lastWire;
	}
	public Boolean getActive () {
		return this.active;
	}
	public Integer getDaysBeforeCancel (){
		return this.daysBeforeCancel;
	}

	public Date getStartDate (){
		return this.startDate;
	}
	public Integer getAmount(){
		return this.amount;
	}

	public void setWireFrequency (Integer wireFrequency){
		this.wireFrequency = wireFrequency;
	}
	public void setLastWire (Date lastWire){
		this.lastWire = lastWire;
	}
	public void setActive (Boolean active){
		this.active = active;
	}
	public void setDaysBeforeCancel (Integer daysBeforeCancel){
		this.daysBeforeCancel = daysBeforeCancel;
	}
	public void setStartDate (Date startDate){
		this.startDate = startDate;
	}
	public void setAmount (Integer amount){
		this.amount = amount;
	}

	public String getContractDetailsId() {
		return this.contractDetailsId;
	}

	public CompanyContractCompany getCompanyContractCompany() {
		return this.companyContractCompany;
	}

	public void setCompanyContractCompany(CompanyContractCompany companyContractCompany) {
		this.companyContractCompany = companyContractCompany;
	}

	public CompanyContractPerson getCompanyContractPerson() {
		return this.companyContractPerson;
	}

	public void setCompanyContractPerson(CompanyContractPerson companyContractPerson) {
		this.companyContractPerson = companyContractPerson;
	}

}