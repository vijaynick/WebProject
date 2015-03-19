/**
 * 
 */
package om.gov.moh.eab.masterData.dao;

import java.io.Serializable;
import java.util.List;


import org.apache.taglibs.standard.lang.jstl.test.beans.PublicBean1;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import om.gov.moh.eab.abstracts.dao.AbstractDaoImpl;
import om.gov.moh.eab.constants.EABConstants;
import om.gov.moh.eab.master.vo.DepartmentVO;
import om.gov.moh.eab.master.vo.SectionVO;
import om.gov.moh.eab.person.vo.PersonVO;
import om.gov.moh.eab.security.vo.UserVO;
import om.gov.moh.eab.utils.ArabicConversion;

/**
 * @author Sajeer Koroth
 *
 */

@Repository
@Transactional
public class DepartmentDAOImpl extends AbstractDaoImpl<DepartmentVO, Long> implements DepartmentDAO, Serializable {
	
	private static final long serialVersionUID = 1L;

	protected DepartmentDAOImpl() {
		super(DepartmentVO.class);
	}

	protected DepartmentDAOImpl(Class<DepartmentVO> entityClass) {
		super(entityClass);
	}

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	

	@Override
	public void saveDepartment(DepartmentVO departmentVO) {
		departmentVO.setDepartmentName(departmentVO.getDepartmentName().toUpperCase());	
		departmentVO.setActive("Y");
		this.getSessionFactory().getCurrentSession().saveOrUpdate(departmentVO);
		//System.out.println("Success");
		
	}
	
	@Override
	public List<DepartmentVO> searchDepartments(DepartmentVO departmentVO, UserVO loggedUser, int index, int count, String sortField, SortOrder sortOrder) throws Exception {
		
		List<DepartmentVO> result = null;
		
		Criteria criteria = createDepartmentCriteria(departmentVO, loggedUser, sortField, sortOrder);
		
		if (criteria != null) {
			criteria.setFirstResult(index).setMaxResults(count);
			result = criteria.list();
		}

		//System.out.println("Size of the list fetched is " + result.size());
		return result;
	}
	
	public Criteria createDepartmentCriteria(DepartmentVO departmentVO, UserVO loggedUser, String sortField, SortOrder sortOrder) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(DepartmentVO.class);
		
		
		
		if (departmentVO != null) {
			if (departmentVO.getDepartmentName() != null && !departmentVO.getDepartmentName().equals("")) {
				criteria.add(Restrictions.ilike("departmentName", departmentVO.getDepartmentName(), MatchMode.ANYWHERE));
			}
			if (departmentVO.getDepartmentNameNLS() != null && !departmentVO.getDepartmentNameNLS().equals("")) {
				criteria.add(Restrictions.ilike("departmentNameNLS", ArabicConversion.setArabicEncodedString(departmentVO.getDepartmentNameNLS()), MatchMode.ANYWHERE));
			}
			if (departmentVO.getGovernarateVO().getId() != null && departmentVO.getGovernarateVO().getId() != -1L) {
				criteria.add(Restrictions.eq("governarateVO", departmentVO.getGovernarateVO()));
			} else if (loggedUser.getGovernarateVO() != null && loggedUser.getGovernarateVO().getId() != null && loggedUser.getRoleVO().getRoleId() != null && !loggedUser.getRoleVO().getRoleId().equals(EABConstants.ROLE_SUPER_ADMIN)) {
				criteria.add(Restrictions.eq("governarateVO", loggedUser.getGovernarateVO()));
			}
			if (departmentVO.getDirectorateVO().getId() != null && departmentVO.getDirectorateVO().getId() != -1L) {
				criteria.add(Restrictions.eq("directorateVO", departmentVO.getDirectorateVO()));
			} else if (loggedUser.getDirectorateVO() != null && loggedUser.getDirectorateVO().getId() != null && loggedUser.getRoleVO().getRoleId() != null && !loggedUser.getRoleVO().getRoleId().equals(EABConstants.ROLE_SUPER_ADMIN)) {
				criteria.add(Restrictions.eq("directorateVO", loggedUser.getDirectorateVO()));
			}
		}
		criteria.add(Restrictions.eq("active","Y"));
		/*if (sortField != null) {
			if (sortOrder == SortOrder.ASCENDING) {
				criteria.addOrder(Order.asc(sortField));
				// cq.orderBy(cb.asc(myObj.get(sortField)));
			} else if (sortOrder == SortOrder.DESCENDING) {
				criteria.addOrder(Order.desc(sortField));
				// cq.orderBy(cb.desc(myObj.get(sortField)));
			}
		}*/
		 criteria.addOrder(Order.asc("sortOrder"));
		return criteria;
	}
	
	public int countTotalDepartments(DepartmentVO departmentVO, UserVO loggedUser) throws Exception {

		Criteria criteria = createDepartmentCriteria(departmentVO, loggedUser, null, null);
		
		Integer totalResult = ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();

		return totalResult;
	}
	
	public DepartmentVO findDepartmentById(Long deptId) throws Exception {
		//List<DepartmentVO> list = getCurrentSession().createQuery("from DepartmentVO department join fetch department.governarateVO   where department.id=:deptId").setParameter("deptId", deptId).list();
		List<DepartmentVO> list = getCurrentSession().createQuery("from DepartmentVO department where department.id=:deptId").setParameter("deptId", deptId).list();
		return (list != null && list.size() > 0) ? list.get(0) : null;

	}
	
	public boolean canDeleteDepartment(DepartmentVO departmentVO) throws Exception {
		List<SectionVO> list  = getCurrentSession().createQuery("select sec from SectionVO sec where sec.departmentVO.id=:deptId and sec.active='Y'").setParameter("deptId", departmentVO.getId()).list();				
		if(list!=null && list.size()>0){
			return false;
		}else{
			return true;
		}
		
	}
	
	
	
	public boolean isDepartmentHavingPersons(DepartmentVO departmentVO) throws Exception {
		List<SectionVO> list  = getCurrentSession().createQuery("select per from PersonVO per where per.entityVO.departmentVO.id=:deptId and per.activeYn='Y'").setParameter("deptId", departmentVO.getId()).list();				
		if(list!=null && list.size()>0){
			return false;
		}else{
			return true;
		}
		
	}
	
	
	
	
	
	public void deleteDepartment(DepartmentVO departmentVO) throws Exception {
		getCurrentSession().createQuery("update DepartmentVO set active='N' where id=:deptId").setParameter("deptId", departmentVO.getId()).executeUpdate();				
	}

	@Override
	public List<DepartmentVO> isDeptAlreadyExist(DepartmentVO departmentVO) throws Exception {
		departmentVO.setDepartmentName(departmentVO.getDepartmentName().toUpperCase());	
		//List<DepartmentVO> depts = getCurrentSession().createQuery("select dept from DepartmentVO dept where dept.directorateVO.id =:dirId and soundex(dept.departmentName)  =  soundex(:deptName) or dept.departmentNameNLS like :dptNameNLS").setParameter("dirId", departmentVO.getDirectorateVO().getId()).setParameter("deptName", departmentVO.getDepartmentName()).setParameter("dptNameNLS", ArabicConversion.setArabicEncodedString(departmentVO.getDepartmentNameNLS())).list();
		List<DepartmentVO> depts = getCurrentSession().createQuery("select dept from DepartmentVO dept where dept.directorateVO.id =:dirId and dept.departmentName =:deptName or dept.departmentNameNLS like :dptNameNLS").setParameter("dirId", departmentVO.getDirectorateVO().getId()).setParameter("deptName", departmentVO.getDepartmentName()).setParameter("dptNameNLS", ArabicConversion.setArabicEncodedString(departmentVO.getDepartmentNameNLS())).list();
        return depts;
		//		if (depts != null && depts.size() > 0) {
//			return false;
//		} else {
//			return true;
//		}

	}

	@Override
	public void SaveDepartmentSortOrder(List<String> sortedList) throws Exception {
		for(String item:sortedList){
			
		Query q1 =	getCurrentSession().createQuery("update DepartmentVO set sortOrder=:sortOrder where id=:deptId");
				
		        q1.setParameter("deptId", Long.parseLong(item));
		        
				q1.setParameter("sortOrder", (long)(sortedList.indexOf(item)));
			q1.executeUpdate();			
		}
		
  		
	}
}
