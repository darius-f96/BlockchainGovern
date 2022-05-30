package floread.backendapi.entities;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the "RoleType" database table.
 * 
 */
@Entity
@Table(name="\"RoleType\"")
@NamedQuery(name="RoleType.findAll", query="SELECT r FROM RoleType r")
public class RoleType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"RoleTypeId\"")
	private String roleTypeId;

	@Column(name="\"Description\"")
	private String description;

	@Column(name="\"RoleCode\"")
	private String roleCode;

	//bi-directional many-to-one association to UserRole
	@OneToMany(mappedBy="roleType")
	private List<UserRole> userRoles;

	public RoleType() {
	}

	public String getRoleTypeId() {
		return this.roleTypeId;
	}

	public void setRoleTypeId(String roleTypeId) {
		this.roleTypeId = roleTypeId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public List<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public UserRole addUserRole(UserRole userRole) {
		getUserRoles().add(userRole);
		userRole.setRoleType(this);

		return userRole;
	}

	public UserRole removeUserRole(UserRole userRole) {
		getUserRoles().remove(userRole);
		userRole.setRoleType(null);

		return userRole;
	}

}