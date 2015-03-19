package om.gov.moh.eab.refdata.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import om.gov.moh.eab.constants.EABConstants;
import om.gov.moh.eab.master.vo.DepartmentVO;
import om.gov.moh.eab.master.vo.DesignationVO;
import om.gov.moh.eab.master.vo.DirectorateVO;
import om.gov.moh.eab.master.vo.GovernarateVO;
import om.gov.moh.eab.master.vo.SectionVO;
import om.gov.moh.eab.refdata.bean.ReferenceCacheBean;
import om.gov.moh.eab.security.vo.InstitutionVO;
import om.gov.moh.eab.security.vo.RoleVO;
import om.gov.moh.eab.security.vo.UserVO;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ReferenceDataDAOImpl implements ReferenceDataDAO, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReferenceDataDAOImpl() {

	}

	@Autowired
	private SessionFactory sessionFactory;

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void loadEntityRefData(ReferenceCacheBean refDataBean) throws Exception {
		List<Long> authTypes = new ArrayList<Long>();
		authTypes.add(13L); // MOH Establishments
		authTypes.add(14L);// Non MOH Establishments
		List<Long> estTypes = new ArrayList<Long>();
		estTypes.add(106L);
		estTypes.add(107L);
		estTypes.add(108L);
		estTypes.add(109L);
		estTypes.add(110L);
		estTypes.add(111L);
		Query q = getCurrentSession().createQuery("from InstitutionVO where active=:act and authorotyType IN (:authType) and estTypeCode IN(:estTypes) order by instName asc");
		q.setParameter("act", "Y");
		q.setParameterList("authType", authTypes);
		q.setParameterList("estTypes", estTypes);
		List<InstitutionVO> insts = q.list();
		for (InstitutionVO institutionVO : insts) {
			refDataBean.getInstituteMap().put(institutionVO.getInstCode(), institutionVO);
		}
		List<DirectorateVO> dirs = this.getCurrentSession().createCriteria(DirectorateVO.class).addOrder(Order.asc("direcotrateName")).list();
		refDataBean.getDirectorateMap().clear();
		for (DirectorateVO directorateVO : dirs) {
			refDataBean.getDirectorateMap().put(directorateVO.getId(), directorateVO);
		}
		
		List<GovernarateVO> govs = this.getCurrentSession().createCriteria(GovernarateVO.class).addOrder(Order.asc("id")).list();
		refDataBean.getGovernrateMap().clear();
		for (GovernarateVO governarateVO : govs) {
			refDataBean.getGovernrateMap().put(governarateVO.getId(), governarateVO);
		}
		
		List<DepartmentVO> depts = this.getCurrentSession().createQuery("from DepartmentVO where active='Y' order by departmentName").list();
		refDataBean.getDepartmentMap().clear();
		for (DepartmentVO departmentVO : depts) {
			refDataBean.getDepartmentMap().put(departmentVO.getId(), departmentVO);
		}
		List<SectionVO> secs = this.getCurrentSession().createQuery("from SectionVO where active='Y' order by sectionName").list();
		refDataBean.getSectionMap().clear();
		for (SectionVO sectionVO : secs) {
			refDataBean.getSectionMap().put(sectionVO.getId(), sectionVO);
		}

	}

	@Override
	public void loadPersonRefData(ReferenceCacheBean refDataBean) throws Exception {
		// findAll(om.gov.moh.eab.master.vo.DesignationVO.class);

		List<DesignationVO> desigs = (List<DesignationVO>) getCurrentSession().createQuery("from DesignationVO").list();
		for (DesignationVO designationVO : desigs) {
			refDataBean.getDesigMap().put(designationVO.getId(), designationVO);
		}
		List<UserVO> usersList = (List<UserVO>) getCurrentSession().createQuery("from UserVO").list();
		for(UserVO userVO:usersList){
			refDataBean.getUserMap().put(userVO.getUserId(), userVO);
		}
		
		refDataBean.setIsd(EABConstants.isdMap);
		refDataBean.setCategoryMap(EABConstants.categoryMap);
		

	}

	@Override
	public void loadUserRefData(ReferenceCacheBean refDataBean) throws Exception {
		// Query q = getCurrentSession().createQuery("from RoleVO");
		List<RoleVO> roleList = (List<RoleVO>) getCurrentSession().createCriteria(RoleVO.class).addOrder(Order.asc("roleName")).list();

		for (RoleVO roleVO : roleList) {
			refDataBean.getRoleMap().put(roleVO.getRoleId(), roleVO);
		}
		
		

	}

	@Override
	public List<DirectorateVO> getDirectoratesOfGov(Long govId) {
		
		StringBuffer query = new StringBuffer("select eVO.directorateVO from DirectorateVO eVO where 1=1");

		if (govId != null && !govId.equals(-1L)) {
			query.append("and eVO.regionCode=:govId ");
		}
		Query q = getCurrentSession().createQuery(query.toString());

		if (govId != null && !govId.equals(-1L)) {
			q.setParameter("govId", govId);
		}
		return (List<DirectorateVO>) q.list();
	}

	
	@Override
	public List<DepartmentVO> getDepartmentsOfGov(Long govId) {
		StringBuffer query = new StringBuffer("select eVO.departmentVO from DepartmentVO eVO where 1=1 ");

		if (govId != null && !govId.equals(-1L)) {
			query.append("and eVO.governarateVO.regionCode=:govId ");
		}


		Query q = getCurrentSession().createQuery(query.toString());

		if (govId != null && !govId.equals(-1L)) {
			q.setParameter("govId", govId);
		}

		return (List<DepartmentVO>) q.list();
	}
	
	
	
	
	@Override
	public List<DepartmentVO> getDepartmentsOfDir(Long dirId, Long govId) {
		StringBuffer query = new StringBuffer("select distinct eVO.departmentVO from EntityVO eVO where 1=1 ");

		if (govId != null && !govId.equals(-1L)) {
			query.append("and eVO.governarateVO.id=:govId ");
		}

		if (dirId != null && !dirId.equals(-1L)) {
			query.append("and eVO.directorateVO.id=:dirId ");
		}

		Query q = getCurrentSession().createQuery(query.toString());

		if (govId != null && !govId.equals(-1L)) {
			q.setParameter("govId", govId);
		}

		if (dirId != null && !dirId.equals(-1L)) {
			q.setParameter("dirId", dirId);
		}

		return (List<DepartmentVO>) q.list();
	}

	// commented by Mujeeb(as per new requirement now we are reading data from master tables)
	/*public List<SectionVO> getSectionsOfDirAndGovAndDept(Long govId, Long dirId, Long deptId) throws Exception {
		StringBuffer query = new StringBuffer("select distinct eVO.sectionVO from EntityVO eVO where 1=1 ");

		if (deptId != null && !deptId.equals(-1L)) {
			query.append("and eVO.departmentVO.id=:deptId ");
		}

		if (dirId != null && !dirId.equals(-1L)) {
			query.append(" and eVO.directorateVO.id=:dirId ");
		}

		if (govId != null && !govId.equals(-1L)) {
			query.append(" and eVO.governarateVO.id=:govId");
		}

		Query q = getCurrentSession().createQuery(query.toString());

		if (deptId != null && !deptId.equals(-1L)) {
			q.setParameter("deptId", deptId);
		}
		if (dirId != null && !dirId.equals(-1L)) {
			q.setParameter("dirId", dirId);
		}

		if (govId != null && !govId.equals(-1L)) {
			q.setParameter("govId", govId);
		}

		List<SectionVO> list = (List<SectionVO>) q.list();

		return list;
	}*/
	
	
	public List<SectionVO> getSectionsOfDirAndGovAndDept(Long govId, Long dirId, Long deptId, Long sectId) throws Exception {
		StringBuffer query = new StringBuffer("from SectionVO sectionVO where 1=1   ");

		if (deptId != null && !deptId.equals(-1L)) {
			query.append("and sectionVO.departmentVO.id=:deptId ");
		}

		if (dirId != null && !dirId.equals(-1L)) {
			query.append(" and sectionVO.directorateVO.id=:dirId ");
		}

		if (govId != null && !govId.equals(-1L)) {
			query.append(" and sectionVO.governarateVO.id=:govId");
		}
		
		if (sectId != null && sectId.equals(-100L)) {
			query.append(" OR sectionVO.isCommonSection = 'Y'");
		}

		Query q = getCurrentSession().createQuery(query.toString());

		if (deptId != null && !deptId.equals(-1L)) {
			q.setParameter("deptId", deptId);
		}
		if (dirId != null && !dirId.equals(-1L)) {
			q.setParameter("dirId", dirId);
		}

		if (govId != null && !govId.equals(-1L)) {
			q.setParameter("govId", govId);
		}

		List<SectionVO> list = (List<SectionVO>) q.list();

		return list;
	}

	// commented by Mujeeb(as per new requirement now we are reading data from master tables)
	/*public List<DepartmentVO> getDepartmentOfDirAndGovAndSec(Long govId, Long dirId, Long secId) throws Exception {
		
		StringBuffer query = new StringBuffer("select distinct eVO.departmentVO from EntityVO eVO where 1=1 ");

		
		
		if (secId != null && !secId.equals((-1L))  && (!secId.equals(-200L))) {
			query.append(" and eVO.sectionVO.id=:secId");
		}
		
		// Appending the query for loadAall departments for the selected governerate
		
	   if (govId != null && !govId.equals(-1L)  && secId != null && (secId.equals(-200L)) ) {
			// This will fetch all the departments for the selected governerate and commonDepartments for all
			query.append(" and (eVO.departmentVO.governarateVO.id=:govId OR eVO.departmentVO.isCommonDept = 'Y' )");
		}
	   else if (govId != null && !govId.equals(-1L)) {
			//query.append(" and eVO.governarateVO.id=:govId");
			// Instead of linking in EAB_ENTITY now we have GOVERNORATE AND DEPT_MAST link, so linking directly into EAB_DEPARTMENT_MAST
			query.append(" and eVO.departmentVO.governarateVO.id=:govId");
		}
		
		
		if (dirId != null && !dirId.equals(-1L)) {
			query.append(" and eVO.directorateVO.id=:dirId ");
		}

		

		Query q = getCurrentSession().createQuery(query.toString());

		if (secId != null && !secId.equals(-1L) && (!secId.equals(-200L))) {
			q.setParameter("secId", secId);
		}
		if (dirId != null && !dirId.equals(-1L)) {
			q.setParameter("dirId", dirId);
		}

		if (govId != null && !govId.equals(-1L)) {
			q.setParameter("govId", govId);
		}

		List<DepartmentVO> list = (List<DepartmentVO>) q.list();

		return list;
	}*/
	
	public List<DepartmentVO> getDepartmentOfDirAndGovAndSec(Long govId, Long dirId, Long secId) throws Exception {
		
		StringBuffer query = new StringBuffer("from DepartmentVO departmentVO where 1=1 ");
		
		 if (govId != null && !govId.equals(-1L)  && secId != null && (secId.equals(-200L)) ) {
			// This will fetch all the departments for the selected governerate and commonDepartments for all
			query.append(" and (departmentVO.governarateVO.id=:govId OR departmentVO.isCommonDept = 'Y' )");
		 } else if (govId != null && !govId.equals(-1L)) {
			// query.append(" and eVO.governarateVO.id=:govId");
			// Instead of linking in EAB_ENTITY now we have GOVERNORATE AND DEPT_MAST link, so linking directly into EAB_DEPARTMENT_MAST
			query.append(" and departmentVO.governarateVO.id=:govId");
		 }
	
		Query q = getCurrentSession().createQuery(query.toString());

		if (govId != null && !govId.equals(-1L)) {
			q.setParameter("govId", govId);
		}

		List<DepartmentVO> list = (List<DepartmentVO>) q.list();

		return list;
	}
	

	@Override
	public List<InstitutionVO> getInstitutionsOfGov(Long govId) {
		List<Long> authTypes = new ArrayList<Long>();
		authTypes.add(13L); // MOH Establishments
		authTypes.add(14L);// Non MOH Establishments
		Query q1 = getCurrentSession().createQuery("select g.regionCode from  GovernarateVO g where g.id=:govId" );
		q1.setParameter("govId", govId);
		Query q = getCurrentSession().createQuery("from InstitutionVO instVO where instVO.active=:act and instVO.regCode=:regCode  and authorotyType IN (:authType)  order by instName asc");
		q.setParameter("act", "Y");
		q.setParameter("regCode", q1.uniqueResult());
		q.setParameterList("authType", authTypes);
		List<InstitutionVO> insts = q.list();
		return insts;
	}

}
