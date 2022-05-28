package floread.backendapi.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the "UserRole" database table.
 * 
 */
@Entity
@Table(name="\"UserRole\"")
@NamedQuery(name="UserRole.findAll", query="SELECT u FROM UserRole u")
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"UserRoleId\"")
	private String userRoleId;

	@Column(name="\"AppUserId\"")
	private String appUserId;

	@Column(name="\"CompanyId\"")
	private String companyId;

	@Column(name="\"RoleTypeId\"")
	private String roleTypeId;

	//bi-directional many-to-one association to AppUser
	@ManyToOne
	private AppUser appUser;

	//bi-directional many-to-one association to Company
	@ManyToOne
	private Company company;

	//bi-directional many-to-one association to RoleType
	@ManyToOne
	private RoleType roleType;

	public UserRole() {
	}

	public String getUserRoleId() {
		return this.userRoleId;
	}

	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}

	public Object getAppUserId() {
		return this.appUserId;
	}

	public void setAppUserId(Object appUserId) {
		this.appUserId = appUserId;
	}

	public Object getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(Object companyId) {
		this.companyId = companyId;
	}

	public Object getRoleTypeId() {
		return this.roleTypeId;
	}

	public void setRoleTypeId(Object roleTypeId) {
		this.roleTypeId = roleTypeId;
	}

	public AppUser getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public RoleType getRoleType() {
		return this.roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

}