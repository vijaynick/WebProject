/*
 * UserDAOImpl.java
 * Jun 14, 2011
 * Copyright 2011 Ministry Of Health, Oman. All Rights Reserved.
 *
 * This software is the proprietary information of Ministry Of Health, Oman.
 * Use is subject to license terms.
 *
 */
package om.gov.moh.eab.security.dao;

import java.io.Serializable;
import java.util.List;

import om.gov.moh.eab.abstracts.dao.AbstractDaoImpl;
import om.gov.moh.eab.master.vo.DirectorateVO;
import om.gov.moh.eab.master.vo.GovernarateVO;
import om.gov.moh.eab.security.vo.AuditVO;
import om.gov.moh.eab.security.vo.UserVO;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author farid.haq
 * 
 */
@Repository
@Transactional
public class UserDAOImpl extends AbstractDaoImpl<UserVO, Long> implements UserDAO, Serializable {

	private static final long serialVersionUID = 1L;

	protected UserDAOImpl() {
		super(UserVO.class);
	}

	protected UserDAOImpl(Class<UserVO> userClass) {
		super(userClass);
	}

	@Override
	public List<UserVO> searchUsers(UserVO userVO) throws Exception {
		return creaetUserCriteria(userVO).list();
	}

	@Override
	public void saveUser(UserVO userVO) throws Exception {
		saveOrUpdate(userVO);

	}

	@Override
	public UserVO findUserById(String Id) throws Exception {

		Query q = getCurrentSession().createQuery("from UserVO as userVO inner join fetch userVO.roleVO inner join fetch userVO.governarateVO  where userVO.userId=:userId");
		q.setString("userId", Id);

		return (q.list() != null && q.list().size() > 0) ? (UserVO) q.list().get(0) : null;
	}

	@Override
	public void deleteUser(UserVO userVO) throws Exception {
		getCurrentSession().createQuery("update UserVO set activeYN=:activeYN where userId=:userId").setParameter("activeYN", "N").setParameter("userId", userVO.getUserId()).executeUpdate();

	}

	@Override
	public boolean isLoginIdTaken(UserVO userVO) throws Exception {

		List<UserVO> result = getCurrentSession().createQuery("from UserVO usr where usr.loginId=:loginId and usr.activeYN='Y'").setParameter("loginId", userVO.getStaffno()).list();

		return (result != null && result.size() > 0) ? true : false;
	}

	public Criteria creaetUserCriteria(UserVO userVO) {
		Criteria criteria = this.getCurrentSession().createCriteria(UserVO.class);

		if (userVO.getLoginId() != null && !userVO.getLoginId().equals("")) {
			criteria.add(Restrictions.ilike("loginId", userVO.getLoginId(), MatchMode.ANYWHERE));
		}

		if (userVO.getUserName() != null && !userVO.getUserName().equals("")) {
			criteria.add(Restrictions.ilike("userName", userVO.getUserName(), MatchMode.ANYWHERE));
		}

		if (userVO.getEmail() != null && !userVO.getEmail().equals("")) {
			criteria.add(Restrictions.ilike("email", userVO.getEmail(), MatchMode.ANYWHERE));
		}

		if (userVO.getMobile() != null && !userVO.getMobile().equals("")) {
			criteria.add(Restrictions.eq("mobile", userVO.getMobile()));
		}

		if (userVO.getStaffno() != null && !userVO.getStaffno().equals("") && !userVO.getStaffno().equals(0L)) {
			criteria.add(Restrictions.eq("staffno", userVO.getStaffno()));
		}

		if (userVO.getDirectorateVO().getId() != null && userVO.getDirectorateVO().getId() != -1L) {
			criteria.add(Restrictions.eq("directorateVO", userVO.getDirectorateVO()));
		}

		if (userVO.getGovernarateVO().getId() != null && userVO.getGovernarateVO().getId() != -1L) {
			criteria.add(Restrictions.eq("governarateVO", userVO.getGovernarateVO()));
		}

		criteria.addOrder(Order.desc("userId"));

		criteria.add(Restrictions.eq("activeYN", "Y"));
		return criteria;

	}

	@Override
	public UserVO findUserByLoginId(String loginId) throws Exception {

		Query q = getCurrentSession().createQuery("from UserVO as userVO inner join fetch userVO.roleVO where userVO.loginId=:loginId and userVO.activeYN='Y'");
		q.setString("loginId", loginId);

		return (q.list() != null && q.list().size() > 0) ? (UserVO) q.list().get(0) : null;
	}

	public void changePassword(String loginId, String newPassword) {
		Query q = getCurrentSession().createQuery("update UserVO set password=:newPassword where loginId=:loginId");
		q.setString("newPassword", newPassword);
		q.setString("loginId", loginId);
		q.executeUpdate();
	}

	public DirectorateVO findDirById(Long id) throws Exception {
		Query q = getCurrentSession().createQuery("from DirectorateVO where id=:id");
		q.setLong("id", id);
		return (q.list() != null && q.list().size() > 0) ? (DirectorateVO) q.list().get(0) : null;

	}

	public GovernarateVO findGovById(Long id) throws Exception {
		Query q = getCurrentSession().createQuery("from GovernarateVO where id=:id");
		q.setLong("id", id);
		return (q.list() != null && q.list().size() > 0) ? (GovernarateVO) q.list().get(0) : null;

	}

	@Override
	public void addAuditInfo(AuditVO auditVO) throws Exception {
	
		getCurrentSession().save(auditVO);
		
	}

	@Override
	public List<AuditVO> searchAudit(AuditVO auditVO) throws Exception {
		
		 return createAuditCriteria(auditVO).list();
		 
	}
	
	
	public Criteria createAuditCriteria(AuditVO auditVO) {
		Criteria criteria = this.getCurrentSession().createCriteria(AuditVO.class);

		if (auditVO.getCategory() != null && !auditVO.getCategory().equals("")) {
			criteria.add(Restrictions.ilike("category", auditVO.getCategory(), MatchMode.START));
		}
		if(auditVO.getUserVO() != null && auditVO.getUserVO().getUserId() != null && !auditVO.getUserVO().equals("") && !auditVO.getUserVO().equals(0L)){
			criteria.add(Restrictions.eq("userVO.userId", auditVO.getUserVO().getUserId()));
		}
		
		if (auditVO.getAction() != null && !auditVO.getAction().equals("")) {
			criteria.add(Restrictions.ilike("action", auditVO.getAction(), MatchMode.ANYWHERE));
		}

		criteria.addOrder(Order.desc("logTime"));
		return criteria;

	}
	
	
	

}
