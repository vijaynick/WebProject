/*
 * UserVO.java
 *
 * Copyright 2004 Ministry Of Health, Oman. All Rights Reserved.
 *
 * This software is the proprietary information of Ministry Of Health, Oman.
 * Use is subject to license terms.
 *
 */

package om.gov.moh.eab.security.vo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import om.gov.moh.eab.entity.vo.EntityVO;
import om.gov.moh.eab.master.vo.DirectorateVO;
import om.gov.moh.eab.master.vo.GovernarateVO;
import om.gov.moh.eab.utils.Utils;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author farid.haq
 * 
 */
@Entity
@Table(name = "EAB_USERS")
public class UserVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long userId;

	private String loginId;

	private String password;

	private String userName;

	private String email;

	private String mobile;

	private String oldPassword;

	private String newPassword;

	private String confirmNewPassword;

	private RoleVO roleVO=new RoleVO();

	private List<InstitutionVO> institutionList;

	private String remarks;

	private String activeYN;

	private String staffno;

	private String canEditYn;
	private DirectorateVO directorateVO=new DirectorateVO();
	
	private GovernarateVO governarateVO=new GovernarateVO();
	
	/**
	 * @return the userId
	 */
	@Column(name = "USER_ID")
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the loginId
	 */
	@Column(name = "LOGIN_ID")
	public String getLoginId() {
		return loginId;
	}

	/**
	 * @param loginId
	 *            the loginId to set
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * @return the password
	 */
	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the userName
	 */
	@Column(name = "USER_NAME")
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the email
	 */
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the mobile
	 */
	@Column(name = "MOBILE")
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the oldPassword
	 */
	@Transient
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @param oldPassword
	 *            the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * @return the newPassword
	 */
	@Transient
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword
	 *            the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the confirmNewPassword
	 */
	@Transient
	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	/**
	 * @param confirmNewPassword
	 *            the confirmNewPassword to set
	 */
	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	
	@Transient
	public List<InstitutionVO> getInstitutionList() {
		return institutionList;
	}

	public void setInstitutionList(List<InstitutionVO> institutionList) {
		this.institutionList = institutionList;
	}

	/**
	 * @return the remarks
	 */
	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the activeYN
	 */
	@Column(name = "ACTIVE_YN")
	public String getActiveYN() {
		return activeYN;
	}

	/**
	 * @param activeYN
	 *            the activeYN to set
	 */
	public void setActiveYN(String activeYN) {
		this.activeYN = activeYN;
	}

	/**
	 * @return the staffno
	 */
	@Column(name = "STAFF_NO")
	public String getStaffno() {
		return staffno;
	}

	/**
	 * @param staffno
	 *            the staffno to set
	 */
	public void setStaffno(String staffno) {
		this.staffno = staffno;
	}

	/**
	 * toString methode: creates a String representation of the object
	 * 
	 * @return the String representation
	 */
	public String toString() {
		return Utils.generalizedToString(this);
	}

	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ROLE_ID")
	public RoleVO getRoleVO() {
		return roleVO;
	}

	public void setRoleVO(RoleVO roleVO) {
		this.roleVO = roleVO;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="DIRECTORATE_CODE")
	public DirectorateVO getDirectorateVO() {
		return directorateVO;
	}

	public void setDirectorateVO(DirectorateVO directorateVO) {
		this.directorateVO = directorateVO;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GOVERNORATE_ID")
	public GovernarateVO getGovernarateVO() {
		return governarateVO;
	}

	public void setGovernarateVO(GovernarateVO governarateVO) {
		this.governarateVO = governarateVO;
	}

	/**
	 * @return the canEditYn
	 */
	@Column(name="CAN_EDIT_YN")
	public String getCanEditYn() {
		return canEditYn;
	}

	/**
	 * @param canEditYn the canEditYn to set
	 */
	public void setCanEditYn(String canEditYn) {
		this.canEditYn = canEditYn;
	}

}
