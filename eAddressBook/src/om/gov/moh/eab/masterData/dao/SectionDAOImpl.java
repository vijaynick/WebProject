/**
 * 
 */
package om.gov.moh.eab.masterData.dao;

import java.io.Serializable;
import java.util.List;

import om.gov.moh.eab.abstracts.dao.AbstractDaoImpl;
import om.gov.moh.eab.constants.EABConstants;
import om.gov.moh.eab.master.vo.DepartmentVO;
import om.gov.moh.eab.master.vo.SectionVO;
import om.gov.moh.eab.security.vo.UserVO;
import om.gov.moh.eab.utils.ArabicConversion;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author it-sajeer
 *
 */

@Repository
@Transactional
public class SectionDAOImpl extends AbstractDaoImpl<SectionVO, Long> implements SectionDAO, Serializable {

	
	private static final long serialVersionUID = 1L;

	protected SectionDAOImpl() {
		super(SectionVO.class);
	}

	protected SectionDAOImpl(Class<SectionVO> entityClass) {
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
	public void saveSection(SectionVO sectionVO) {
		sectionVO.setSectionName(sectionVO.getSectionName().toUpperCase());
		sectionVO.setActive("Y");
		this.getSessionFactory().getCurrentSession().saveOrUpdate(sectionVO);
		//System.out.println("Success");
	}
	
	
	
	
	@Override
	public List<SectionVO> searchSections(SectionVO sectionVO,
			UserVO loggedUser, int index, int count, String sortField,
			SortOrder sortOrder) throws Exception {
		List<SectionVO> result = null;
		
		Criteria criteria = createSectionCriteria(sectionVO, loggedUser, sortField, sortOrder);
		
		if (criteria != null) {
			criteria.setFirstResult(index).setMaxResults(count);
			result = criteria.list();
		}

		//System.out.println("Size of the list fetched is " + result.size());
		return result;
	}

	
	public Criteria createSectionCriteria(SectionVO sectionVO, UserVO loggedUser, String sortField, SortOrder sortOrder) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(SectionVO.class);
		
		
		
		if (sectionVO.getSectionName()!= null && !sectionVO.getSectionName().equals("")) {
			criteria.add(Restrictions.ilike("sectionName", sectionVO.getSectionName(), MatchMode.ANYWHERE));
		}

		if (sectionVO.getSectionNameNLS() != null && !sectionVO.getSectionNameNLS().equals("")) {
			criteria.add(Restrictions.ilike("sectionNameNLS", ArabicConversion.setArabicEncodedString(sectionVO.getSectionNameNLS()), MatchMode.ANYWHERE));
		}
		
		if (sectionVO.getGovernarateVO().getId() != null && sectionVO.getGovernarateVO().getId() != -1L) {
			criteria.add(Restrictions.eq("governarateVO.id", sectionVO.getGovernarateVO().getId()));
		} else if(loggedUser.getGovernarateVO() != null && loggedUser.getGovernarateVO().getId() != null && loggedUser.getRoleVO().getRoleId() != null && !loggedUser.getRoleVO().getRoleId().equals(EABConstants.ROLE_SUPER_ADMIN)) {
			criteria.add(Restrictions.eq("governarateVO.id", loggedUser.getGovernarateVO().getId()));
		}
		
		if (sectionVO.getDirectorateVO().getId() != null && sectionVO.getDirectorateVO().getId() != -1L) {
			criteria.add(Restrictions.eq("directorateVO", sectionVO.getDirectorateVO()));
		} else if(loggedUser.getDirectorateVO() != null && loggedUser.getDirectorateVO().getId() != null && loggedUser.getRoleVO().getRoleId() != null && !loggedUser.getRoleVO().getRoleId().equals(EABConstants.ROLE_SUPER_ADMIN)) {
			criteria.add(Restrictions.eq("directorateVO", loggedUser.getDirectorateVO()));
		}
		
		if (sectionVO.getDepartmentVO().getId() != null && sectionVO.getDepartmentVO().getId() != -1L) {
			criteria.add(Restrictions.eq("departmentVO", sectionVO.getDepartmentVO()));
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
		// criteria.addOrder(Order.desc("personCode"));
		return criteria;
	}
	
	

	@Override
	public int countTotalSections(SectionVO sectionVO, UserVO loggedUser)
			throws Exception {
		Criteria criteria = createSectionCriteria(sectionVO, loggedUser, null, null);
		
		Integer totalResult = ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();

		return totalResult;
	}

	
	@Override
	public SectionVO findSectionById(Long sectionId) throws Exception {
				List<SectionVO> list = getCurrentSession().createQuery("from SectionVO section where section.id=:sectionId").setParameter("sectionId", sectionId).list();
				return (list != null && list.size() > 0) ? list.get(0) : null;
	}

	
	@Override
	public void deleteSection(SectionVO sectionVO) throws Exception {
		getCurrentSession().createQuery("update SectionVO set active='N' where id=:sectionId").setParameter("sectionId", sectionVO.getId()).executeUpdate();

	}
	
	public boolean canDeleteSection(SectionVO sectionVO) throws Exception {
		List<SectionVO> list  = getCurrentSession().createQuery("select per from PersonVO per where per.entityVO.sectionVO.id=:secId and per.activeYn='Y'").setParameter("secId", sectionVO.getId()).list();				
		if(list!=null && list.size()>0){
			return false;
		}else{
			return true;
		}
		
	}

	@Override
	public boolean isSectionAlreadyExist(SectionVO sectionVO) throws Exception {
		sectionVO.setSectionName(sectionVO.getSectionName().toUpperCase());
		   //List<SectionVO> sects = getCurrentSession().createQuery("select sect from SectiontVO sect where sect.departmentVO.id =:depId and soundex(sect.sectionName)  =  soundex(:secName) or sect.sectionNameNLS like :sectNameNLS").setParameter("depId", sectionVO.getDepartmentVO().getId()).setParameter("secName", sectionVO.getSectionName()).setParameter("sectNameNLS", ArabicConversion.setArabicEncodedString(sectionVO.getSectionNameNLS())).list();
		   List<SectionVO> sects = getCurrentSession().createQuery("select sect from SectionVO sect where sect.departmentVO.id =:depId and sect.active='Y' and (sect.sectionName like :secName or sect.sectionNameNLS like :sectNameNLS) ").setParameter("depId", sectionVO.getDepartmentVO().getId()).setParameter("secName", sectionVO.getSectionName()).setParameter("sectNameNLS", ArabicConversion.setArabicEncodedString(sectionVO.getSectionNameNLS())).list();		
				if (sects != null && sects.size() > 0) {
					return true;
				} else {
					return false;
				}
	
	
	}

	@Override
	public List<SectionVO> findSectionsByDirId(Long dirId) throws Exception {
		
		List<SectionVO> sects = getCurrentSession().createQuery
				("select sect from SectionVO sect where sect.directorateVO.id =:dirId and sect.active='Y' ").setParameter("dirId", dirId).list();
		
		return sects;
	}

	@Override
	public void SaveSectionSortOrder(List<String> sortedList) throws Exception {
		for(String item:sortedList){
			
			Query q1 =	getCurrentSession().createQuery("update SectionVO set sortOrder=:sortOrder where id=:sectId");
					
			        q1.setParameter("sectId", Long.parseLong(item));
			        
					q1.setParameter("sortOrder", (long)(sortedList.indexOf(item)));
				q1.executeUpdate();			
			}
		
	}
	
}
