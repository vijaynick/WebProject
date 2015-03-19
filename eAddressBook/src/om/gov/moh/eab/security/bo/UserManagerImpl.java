/*
 * UserManagerImpl.java
 * Jun 14, 2011
 * Copyright 2011 Ministry Of Health, Oman. All Rights Reserved.
 *
 * This software is the proprietary information of Ministry Of Health, Oman.
 * Use is subject to license terms.
 *
 */
package om.gov.moh.eab.security.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import om.gov.moh.eab.master.vo.DirectorateVO;
import om.gov.moh.eab.master.vo.GovernarateVO;
import om.gov.moh.eab.security.dao.UserDAO;
import om.gov.moh.eab.security.vo.AuditVO;
import om.gov.moh.eab.security.vo.UserVO;
import om.gov.moh.eab.utils.ArabicConversion;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author farid.haq
 * 
 */
@Service("userManager")
@Transactional
public class UserManagerImpl implements UserManager, Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private UserDAO userDAO;

	@Override
	public List<UserVO> searchUsers(UserVO userVO) throws Exception {

		List<UserVO> users = userDAO.searchUsers(userVO);
		for (UserVO userVO2 : users) {
			Hibernate.initialize(userVO2.getDirectorateVO());
			Hibernate.initialize(userVO2.getGovernarateVO());
		}
		return users;
	}

	@Override
	public void saveUser(UserVO userVO) throws Exception {
		userVO.setActiveYN("Y");
		userDAO.saveUser(userVO);

	}

	@Override
	public UserVO findUserById(String Id) throws Exception {
		return userDAO.findUserById(Id);
	}

	@Override
	public void deleteUser(UserVO userVO) throws Exception {
		userDAO.deleteUser(userVO);

	}

	@Override
	public boolean isLoginIdTaken(UserVO userVO) throws Exception {
		return userDAO.isLoginIdTaken(userVO);
	}

	@Override
	public UserDAO getUserDAO() {
		return userDAO;
	}

	@Override
	public UserVO findUserByLoginId(String loginId) throws Exception {

		UserVO usr = userDAO.findUserByLoginId(loginId);
		Hibernate.initialize(usr.getDirectorateVO());
		Hibernate.initialize(usr.getGovernarateVO());

		return usr;
	}

	@Override
	public void changePassword(String loginId, String newPassword) {
		userDAO.changePassword(loginId, newPassword);

	}

	@Override
	public DirectorateVO findDirById(Long id) throws Exception {
		return userDAO.findDirById(id);
	}

	@Override
	public GovernarateVO findGovById(Long id) throws Exception {
		return userDAO.findGovById(id);
	}

	@Override
	public void addAuditInfo(AuditVO auditVO) throws Exception {
		auditVO.setLogTime(new Date());
		userDAO.addAuditInfo(auditVO);
		
	}

	@Override
	public List<AuditVO> searchAudit(AuditVO auditVO) throws Exception {
		
		return userDAO.searchAudit(auditVO);
	
	}
}
