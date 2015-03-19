/*
 * AuditVO.java
 *
 * Copyright 2004 Ministry Of Health, Oman. All Rights Reserved.
 *
 * This software is the proprietary information of Ministry Of Health, Oman.
 * Use is subject to license terms.
 *
 */
package om.gov.moh.eab.security.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;


/**
 * @author farid.haq
 *
 */
@Entity
@Table(name = "EAB_AUDIT_LOG")
public class AuditVO {
		

	public AuditVO(Long referenceNo,Long userId, String category, String action,String clientAddress) {
		super();
		this.referenceNo = referenceNo;
		this.userVO = new UserVO();
		this.userVO.setUserId(userId);
		this.category = category;
		this.action = action;
		this.clientAddress = clientAddress;
	}
	
	public AuditVO(){
		super();
	}
	
	private Long id;
	private Long referenceNo;
	private String category;
	private String action;
	private String clientAddress;
	private Date logTime;
	private UserVO userVO=new UserVO();
	
	
	
	/**
	 * @return the id
	 */
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "AUD_ID")
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	

	/**
	 * @return the category
	 */
	
	@Column(name = "AUDIT_CAT")
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the action
	 */
	
	@Column(name = "REMARK")
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the clientAddress
	 */
	
	@Column(name = "IP_ADDRESS")
	public String getClientAddress() {
		return clientAddress;
	}

	/**
	 * @param clientAddress the clientAddress to set
	 */
	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	/**
	 * @return the logTime
	 */
	
	@Column(name = "LOG_TIME")
	public Date getLogTime() {
		return logTime;
	}

	/**
	 * @param logTime the logTime to set
	 */
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	/**
	 * @return the referenceNo
	 */
	@Column(name="REF_NO")
	public Long getReferenceNo() {
		return referenceNo;
	}

	/**
	 * @param referenceNo the referenceNo to set
	 */
	public void setReferenceNo(Long referenceNo) {
		this.referenceNo = referenceNo;
	}

	/**
	 * @return the userVO
	 */
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_ID")
	@NotFound
	public UserVO getUserVO() {
		return userVO;
	}

	/**
	 * @param userVO the userVO to set
	 */
	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}
	
	
}
