package om.gov.moh.eab.entity.dao;

import java.io.Serializable;
import java.util.List;

import om.gov.moh.eab.abstracts.dao.AbstractDaoImpl;
import om.gov.moh.eab.constants.EABConstants;
import om.gov.moh.eab.entity.vo.EntityVO;
import om.gov.moh.eab.master.vo.DesignationVO;
import om.gov.moh.eab.person.vo.SortOrderId;
import om.gov.moh.eab.person.vo.SortOrderVO;
import om.gov.moh.eab.security.vo.UserVO;
import om.gov.moh.eab.utils.ArabicConversion;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.primefaces.model.SortOrder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author farid.haq
 * 
 */
@SuppressWarnings("all")
@Repository
@Transactional
public class EntityDAOImpl extends AbstractDaoImpl<EntityVO, Long> implements EntityDAO, Serializable {

	private static final long serialVersionUID = 1L;

	protected EntityDAOImpl() {
		super(EntityVO.class);
	}

	protected EntityDAOImpl(Class<EntityVO> entityClass) {
		super(entityClass);
	}

	@Override
	public List<EntityVO> searchEntities(EntityVO entityVO, UserVO loggedUser, int index, int count, String sortField, SortOrder sortOrder) {

		Criteria crit = creaetEntityCriteria(entityVO, loggedUser, sortField, sortOrder);
		crit.add(Restrictions.eq("active", "Y"));
		if (entityVO.isEmergencyBoolean()) {
			crit.add(Restrictions.eq("emergency", "Y"));
		}

		if (entityVO.isSearchAbleBoolean()) {
			crit.add(Restrictions.eq("searchAble", "Y"));
		}

		crit.setFirstResult(index).setMaxResults(count);
		return crit.list();
	}

	public Criteria creaetEntityCriteria(EntityVO entityVO, UserVO loggedUser, String sortField, SortOrder sortOrder) {
		Criteria criteria = this.getCurrentSession().createCriteria(EntityVO.class);

		if (entityVO.getEntityNameNLS() != null && !entityVO.getEntityNameNLS().equals("")) {
			criteria.add(Restrictions.ilike("entityNameNLS", ArabicConversion.setArabicEncodedString(entityVO.getEntityNameNLS()), MatchMode.START));
		}

		if (entityVO.getEntityName() != null && !entityVO.getEntityName().equals("")) {

			criteria.add(Restrictions.ilike("entityName", entityVO.getEntityName(), MatchMode.ANYWHERE));
		}

		if (entityVO.getEmailID() != null && !entityVO.getEmailID().equals("")) {

			criteria.add(Restrictions.ilike("emailID", entityVO.getEmailID(), MatchMode.ANYWHERE));
		}

		if (entityVO.getFaxNo() != null && !entityVO.getFaxNo().equals("")) {

			criteria.add(Restrictions.eq("faxNo", entityVO.getFaxNo()));
		}

		if (entityVO.getExtn() != null && !entityVO.getExtn().equals("")) {

			criteria.add(Restrictions.eq("extn", entityVO.getExtn()));
		}

		if (entityVO.getDirectTelNo() != null && !entityVO.getDirectTelNo().equals("")) {

			criteria.add(Restrictions.eq("directTelNo", entityVO.getDirectTelNo()));
		}

		if (entityVO.getGovernarateVO().getId() != null && entityVO.getGovernarateVO().getId() != -1L) {
			criteria.add(Restrictions.eq("governarateVO", entityVO.getGovernarateVO()));
		} else if (loggedUser.getGovernarateVO() != null && loggedUser.getGovernarateVO().getId() != null && loggedUser.getRoleVO().getRoleId() != null && !loggedUser.getRoleVO().getRoleId().equals(EABConstants.ROLE_SUPER_ADMIN)) {
			criteria.add(Restrictions.eq("governarateVO", loggedUser.getGovernarateVO()));
		}

		if (entityVO.getDirectorateVO().getId() != null && entityVO.getDirectorateVO().getId() != -1L) {

			criteria.add(Restrictions.eq("directorateVO", entityVO.getDirectorateVO()));
		} else if (loggedUser.getDirectorateVO() != null && loggedUser.getDirectorateVO().getId() != null && loggedUser.getRoleVO().getRoleId() != null && !loggedUser.getRoleVO().getRoleId().equals(EABConstants.ROLE_SUPER_ADMIN)) {
			criteria.add(Restrictions.eq("directorateVO", loggedUser.getDirectorateVO()));
		}

		if (entityVO.getSectionVO().getId() != null && entityVO.getSectionVO().getId() != -1L) {

			criteria.add(Restrictions.eq("sectionVO", entityVO.getSectionVO()));
		}

		if (entityVO.getDepartmentVO().getId() != null && entityVO.getDepartmentVO().getId() != -1L) {

			criteria.add(Restrictions.eq("departmentVO", entityVO.getDepartmentVO()));
		}

		if (entityVO.getInstitutionVO().getInstCode() != null && entityVO.getInstitutionVO().getInstCode() != -1L) {

			criteria.add(Restrictions.eq("institutionVO", entityVO.getInstitutionVO()));
		}

		if (sortField != null) {
			if (sortOrder == SortOrder.ASCENDING) {
				criteria.addOrder(Order.asc(sortField));
				// cq.orderBy(cb.asc(myObj.get(sortField)));
			} else if (sortOrder == SortOrder.DESCENDING) {
				criteria.addOrder(Order.desc(sortField));
				// cq.orderBy(cb.desc(myObj.get(sortField)));
			}
		}

		// criteria.addOrder(Order.desc("entityCode"));

		return criteria;

	}

	public Criteria creaetUniqueEntityCriteria(EntityVO entityVO) {
		Criteria criteria = this.getCurrentSession().createCriteria(EntityVO.class);

		if (entityVO.getDirectorateVO().getId() != null && entityVO.getDirectorateVO().getId() != -1L) {
			criteria.add(Restrictions.eq("directorateVO", entityVO.getDirectorateVO()));
		}

		if (entityVO.getSectionVO().getId() != null && entityVO.getSectionVO().getId() != -1L) {
			criteria.add(Restrictions.eq("sectionVO", entityVO.getSectionVO()));
		}

		if (entityVO.getDepartmentVO().getId() != null && entityVO.getDepartmentVO().getId() != -1L) {
			criteria.add(Restrictions.eq("departmentVO", entityVO.getDepartmentVO()));
		}

		if (entityVO.getInstitutionVO().getInstCode() != null && entityVO.getInstitutionVO().getInstCode() != -1L) {
			criteria.add(Restrictions.eq("institutionVO", entityVO.getInstitutionVO()));
		}

		// criteria.addOrder(Order.desc("entityCode"));

		return criteria;

	}

	@Override
	public void saveEntity(EntityVO entityVO) {
		
		if (entityVO.getDepartmentVO() == null || entityVO.getDepartmentVO().getId() == null || entityVO.getDepartmentVO().getId().equals(-1L)) {
			entityVO.setDepartmentVO(null);
		}

		if (entityVO.getInstitutionVO() == null || entityVO.getInstitutionVO().getInstCode() == null) {
			entityVO.setInstitutionVO(null);
		}

		if (entityVO.getSectionVO() == null || entityVO.getSectionVO().getId() == null || entityVO.getSectionVO().getId().equals(-1L)) {
			entityVO.setSectionVO(null);
		}

		saveOrUpdate(entityVO);
	}

	@Override
	public EntityVO findEntityById(Long Id) {
		List<EntityVO> list = getCurrentSession().createQuery("from EntityVO entityVO  join fetch entityVO.directorateVO join fetch entityVO.governarateVO where entityVO.entityCode=:entityCode").setParameter("entityCode", Id).list();
		return (list != null && list.size() > 0) ? list.get(0) : null;
	}

	@Override
	public void deleteEntity(EntityVO entityVO) {
		getCurrentSession().createQuery("update EntityVO set active=:active where entityCode=:entityCode").setParameter("active", "N").setParameter("entityCode", entityVO.getEntityCode()).executeUpdate();

	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isSameEntityCombinationExist(EntityVO entityVO) throws Exception {

		List<EntityVO> list = getCurrentSession().createQuery("from EntityVO where departmentVO.id=:departmentId and sectionVO.id=:sectionId and directorateVO.id=:directorateId and institutionVO.instCode=:instCode").setParameter("departmentId", entityVO.getDepartmentVO().getId()).setParameter("sectionId", entityVO.getSectionVO().getId()).setParameter("directorateId", entityVO.getDirectorateVO().getId()).setParameter("instCode", entityVO.getInstitutionVO().getInstCode()).list();

		return (list != null && list.size() > 0) ? true : false;
	}

	public int countTotalEntities(EntityVO entityVO, UserVO loggedUser) throws Exception {
		Criteria crit = creaetEntityCriteria(entityVO, loggedUser, null, null);
		crit.add(Restrictions.eq("active", "Y"));
		if (entityVO.isEmergencyBoolean()) {
			crit.add(Restrictions.eq("emergency", "Y"));
		}

		if (entityVO.isSearchAbleBoolean()) {
			crit.add(Restrictions.eq("searchAble", "Y"));
		}
		Integer totalResult = ((Number)crit.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		return totalResult;
	}

	@Override
	public EntityVO findEntityByCombination(Long govId, Long dirId, Long depId, Long secId) throws Exception {
		
		EntityVO result = new EntityVO();
		
        Criteria crt = this.getCurrentSession().createCriteria(EntityVO.class);
        if(govId == null)
        	crt.add(Restrictions.isNull("governarateVO.id"));
        else
        	crt.add(Restrictions.eq("governarateVO.id", govId));
        
        if(dirId == null)
        	crt.add(Restrictions.isNull("directorateVO.id"));
        else
        	crt.add(Restrictions.eq("directorateVO.id", dirId));
        
        if(depId == null)
        	crt.add(Restrictions.isNull("departmentVO.id"));
        else
        	crt.add(Restrictions.eq("departmentVO.id", depId));
        
        if(secId == null)
        	crt.add(Restrictions.isNull("sectionVO.id"));
        else
        	crt.add(Restrictions.eq("sectionVO.id", secId));

        result = (EntityVO)crt.uniqueResult();
		
		return result;
	}

	@Override
	public List<DesignationVO> findDesignationsByEntityCode(Long entityCode) throws Exception {
		
		Query query = this.getCurrentSession().createSQLQuery("SELECT DISTINCT B.ID as id, B.NAME as name, B.NAME_NLS as nameNLS,C.SORT_ORDER FROM EAB_PERSON A, EAB_TB_DESIG_MAST B, EAB_PERSON_SORT_ORDER C " +
				" WHERE A.ENTITY_CODE = :entityCode AND A.DESIGNATION = B.ID " +
				"AND  A.DESIGNATION = C.DESIGNATION_ID(+) " +
				"AND A.ENTITY_CODE = C.ENTITY_CODE(+) ORDER BY C.SORT_ORDER ASC")
				.addScalar("id", StandardBasicTypes.LONG)
				.addScalar("name", StandardBasicTypes.STRING)
				.addScalar("nameNLS", StandardBasicTypes.STRING).setResultTransformer(Transformers.aliasToBean(DesignationVO.class)).setParameter("entityCode", entityCode);
		List<DesignationVO> result = query.list();
		
		return result;
	}

	@Override
	public void saveDesignationSortOrder(Long entityCode, List<String> designationList) throws Exception {
      
		for(String designation :designationList){
			SortOrderId sid = new SortOrderId(entityCode, Long.parseLong(designation));
		   // SortOrderVO s1 = new SortOrderVO(entityCode, Long.parseLong(designation), designationList.indexOf(designation));
		    SortOrderVO s1 = new SortOrderVO(sid, designationList.indexOf(designation));
			//System.out.println("entityCode:"+entityCode+"Long.parseLong(designation):" +Long.parseLong(designation) + "designationList.indexOf(designation):" +designationList.indexOf(designation));
		   this.getCurrentSession().saveOrUpdate(s1);
		}
		
		
		
	}

}
