package om.gov.moh.eab.security.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author farid.haq
 * 
 */
@Entity
@Table(name = "EAB_ROLES")
public class RoleVO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long roleId;
	private String roleName;
	private String roleNameNLS;
	private List<RoleAccessVO> roleAccessList;
	private List<UserVO> users=new ArrayList<UserVO>();

	public RoleVO() {
		super();
	}

	public RoleVO(Long roleId) {
		super();
		this.roleId = roleId;
	}

	/**
	 * @return the roleId
	 */
	@Id
	@Column(name = "ROLE_ID")
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the roleName
	 */
	@Column(name = "ROLE_NAME")
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName
	 *            the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	/**
	 * @return the roleAccessList
	 */
	@Transient
	public List<RoleAccessVO> getRoleAccessList() {
		return roleAccessList;
	}

	/**
	 * @param roleAccessList
	 *            the roleAccessList to set
	 */
	public void setRoleAccessList(List<RoleAccessVO> roleAccessList) {
		this.roleAccessList = roleAccessList;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof RoleVO))
			return false;
		RoleVO obj1 = (RoleVO) obj;
		return ((obj1.roleId).equals(this.roleId));
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "roleVO")
	public List<UserVO> getUsers() {
		return users;
	}

	public void setUsers(List<UserVO> users) {
		this.users = users;
	}

	@Column(name="ROLE_NAME_NLS")
	public String getRoleNameNLS() {
		return roleNameNLS;
	}

	public void setRoleNameNLS(String roleNameNLS) {
		this.roleNameNLS = roleNameNLS;
	}

}
