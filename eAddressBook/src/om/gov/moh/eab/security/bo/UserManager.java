/*
 * UserManager.java
 * Jun 14, 2011
 * Copyright 2011 Ministry Of Health, Oman. All Rights Reserved.
 *
 * This software is the proprietary information of Ministry Of Health, Oman.
 * Use is subject to license terms.
 *
 */
package om.gov.moh.eab.security.bo;

import java.util.List;

import om.gov.moh.eab.master.vo.DirectorateVO;
import om.gov.moh.eab.master.vo.GovernarateVO;
import om.gov.moh.eab.security.dao.UserDAO;
import om.gov.moh.eab.security.vo.AuditVO;
import om.gov.moh.eab.security.vo.UserVO;

/**
 * @author farid.haq
 * 
 */
public interface UserManager {

	public List<UserVO> searchUsers(UserVO userVO) throws Exception;

	public void saveUser(UserVO userVO) throws Exception;

	public UserVO findUserById(String Id) throws Exception;

	public void deleteUser(UserVO userVO) throws Exception;

	public UserVO findUserByLoginId(String loginId) throws Exception;

	public boolean isLoginIdTaken(UserVO userVO) throws Exception;

	public void changePassword(String loginId, String newPassword);

	public UserDAO getUserDAO();

	public DirectorateVO findDirById(Long id) throws Exception;

	public GovernarateVO findGovById(Long id) throws Exception;
	
    public void addAuditInfo(AuditVO auditVO) throws Exception;
    
    public List<AuditVO> searchAudit(AuditVO auditVO) throws Exception;
}
