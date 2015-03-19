package om.gov.moh.eab.person.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import om.gov.moh.eab.abstracts.dao.AbstractDaoImpl;
import om.gov.moh.eab.constants.EABConstants;
import om.gov.moh.eab.entity.vo.EntityVO;
import om.gov.moh.eab.master.vo.DesignationVO;
import om.gov.moh.eab.person.vo.PersonVO;
import om.gov.moh.eab.security.vo.InstitutionVO;
import om.gov.moh.eab.security.vo.UserVO;
import om.gov.moh.eab.utils.ArabicConversion;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author farid.haq
 * 
 */
@Repository
@Transactional
public class PersonDAOImpl extends AbstractDaoImpl<PersonVO, Integer> implements PersonDAO, Serializable {

	private static final long serialVersionUID = 1L;

	protected PersonDAOImpl() {
		super(PersonVO.class);
	}

	protected PersonDAOImpl(Class<PersonVO> entityClass) {
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
	public List<PersonVO> searchPersons(PersonVO personVO, UserVO loggedUser, int index, int count, String sortField, SortOrder sortOrder, boolean report) throws Exception {
		List<PersonVO> result = null;
		Criteria criteria = createPersonCriteria(personVO, loggedUser, sortField, sortOrder);
		if (criteria != null) {

			criteria.add(Restrictions.eq("activeYn", "Y"));

			if (personVO.isImportantBoolean()) {
				criteria.add(Restrictions.eq("important", "Y"));
			}

			if (!report)
				criteria.setFirstResult(index).setMaxResults(count);

			result = criteria.list();
		}

		// System.out.println("Size of the list fetched is " + result.size());
		
		
		// on some request
		
		
		/*List<PersonVO> returnList = new ArrayList<>(result);
		
		for(int i=0;i<result.size();i++){
			PersonVO person = result.get(i);
			if(person.getExtn()==1401){
				//returnList.remove(i);
				//returnList.add(0, person);
				returnList.set(0, person);
			}
			
			if(person.getExtn()==1125){
				returnList.remove(i);
				returnList.set(1, person);
			}
			
			
		}*/
		
		/*for (PersonVO personVO2 : result) {
			System.out.println(personVO2.getPersonCode().toString().equals("4539"));
		}*/
		//System.out.println(returnList);
		return result;
	}

	public Criteria createPersonCriteria(PersonVO personVO, UserVO loggedUser, String sortField, SortOrder sortOrder) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(PersonVO.class);

		createMainCriteria(personVO, loggedUser, criteria);

		Criteria crit = createSubCriteria(personVO, loggedUser, criteria);

		if (personVO.getEntityVO().getSectionVO().getId() != null && personVO.getEntityVO().getSectionVO().getId() != -1L) {
			crit.add(Restrictions.eq("entityVO.sectionVO", personVO.getEntityVO().getSectionVO()));
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
		 criteria.addOrder(Order.asc("personCode"));
		return criteria;
	}

	private void createMainCriteria(PersonVO personVO, UserVO loggedUser, Criteria criteria) {
		if (personVO.getPersonName() != null && !personVO.getPersonName().equals("")) {
			criteria.add(Restrictions.ilike("personName", personVO.getPersonName(), MatchMode.ANYWHERE));
		}

		if (personVO.getPersonNameNLS() != null && !personVO.getPersonNameNLS().equals("")) {
			criteria.add(Restrictions.ilike("personNameNLS", ArabicConversion.setArabicEncodedString(personVO.getPersonNameNLS()), MatchMode.ANYWHERE));
		}

		if (personVO.getStaffNo() != null && !personVO.getStaffNo().equals("")) {
			criteria.add(Restrictions.eq("staffNo", personVO.getStaffNo()));
		}

		if (personVO.getFax() != null && !personVO.getFax().equals("")) {
			criteria.add(Restrictions.eq("fax", personVO.getFax()));
		}

		if (personVO.getCivilId() != null && personVO.getCivilId() != 0L) {
			criteria.add(Restrictions.eq("civilId", personVO.getCivilId()));
		}
		if (personVO.getDesignationVO().getId() != null && personVO.getDesignationVO().getId() != -1L) {
			criteria.add(Restrictions.eq("designationVO", personVO.getDesignationVO()));
		}
		if (personVO.getStaffType() != null && personVO.getStaffType() != 0L) {
			criteria.add(Restrictions.eq("staffType", personVO.getStaffType()));
		}

		// Restricting the Person Search for the Guest User. He can see only
		// those peson who is having the designation with guestViewYN='Y'
		
		if (loggedUser.getRoleVO().getRoleId() != null && loggedUser.getRoleVO().getRoleId().equals(EABConstants.ROLE_GUEST)) {
			Criteria desigCriteria = criteria.createAlias("designationVO", "designationVO");
			desigCriteria.add(Restrictions.eq("designationVO.guestViewYN", "Y"));
		}

		/*
		 * if(personVO.getGsmPrimary() != null && personVO.getGsmPrimary() !=
		 * 0L){ criteria.add(Restrictions.eq("gsmPrimary",
		 * personVO.getGsmPrimary())); }
		 */
		if (personVO.getGsm() != null && personVO.getGsm() != 0L) {
			Criterion cr1 = Restrictions.eq("gsmPrimary", personVO.getGsm());
			Criterion cr2 = Restrictions.eq("gsmAddnl", personVO.getGsm());
			Criterion cr3 = Restrictions.eq("resiTelNo", personVO.getGsm());
			Criterion cr4 = Restrictions.eq("directTelNo", personVO.getGsm());

			criteria.add(Restrictions.or(cr1, cr2, cr3, cr4));
		}
		if (personVO.getEmail() != null && !personVO.getEmail().equals("")) {
			criteria.add(Restrictions.ilike("email", personVO.getEmail(), MatchMode.ANYWHERE));
		}

		if (personVO.getExtn() != null && personVO.getExtn() != 0L) {
			Criterion cr1 = Restrictions.eq("extn", personVO.getExtn());
			Criterion cr2 = Restrictions.eq("extn2", personVO.getExtn());
			criteria.add(Restrictions.or(cr1, cr2));
		}
	}

	private Criteria createSubCriteria(PersonVO personVO, UserVO loggedUser, Criteria criteria) {
		Criteria crit = criteria.createAlias("entityVO", "entityVO");

		if (personVO.getEntityVO().getGovernarateVO().getId() != null && personVO.getEntityVO().getGovernarateVO().getId() != -1L) {
			crit.add(Restrictions.eq("entityVO.governarateVO", personVO.getEntityVO().getGovernarateVO()));
		} else if (loggedUser.getGovernarateVO() != null && loggedUser.getGovernarateVO().getId() != null && loggedUser.getRoleVO().getRoleId() != null && !loggedUser.getRoleVO().getRoleId().equals(EABConstants.ROLE_SUPER_ADMIN)) {
			crit.add(Restrictions.eq("entityVO.governarateVO", loggedUser.getGovernarateVO()));
		}

		if (personVO.getEntityVO().getDirectorateVO().getId() != null && personVO.getEntityVO().getDirectorateVO().getId() != -1L) {
			crit.add(Restrictions.eq("entityVO.directorateVO", personVO.getEntityVO().getDirectorateVO()));
		} else if (loggedUser.getDirectorateVO() != null && loggedUser.getDirectorateVO().getId() != null && loggedUser.getRoleVO().getRoleId() != null && !loggedUser.getRoleVO().getRoleId().equals(EABConstants.ROLE_SUPER_ADMIN)) {
			criteria.add(Restrictions.eq("entityVO.directorateVO", loggedUser.getDirectorateVO()));
		}

		if (personVO.getEntityVO().getDepartmentVO().getId() != null && personVO.getEntityVO().getDepartmentVO().getId() != -1L) {
			crit.add(Restrictions.eq("entityVO.departmentVO", personVO.getEntityVO().getDepartmentVO()));
		}

		if (personVO.getEntityVO().getInstitutionVO().getInstCode() != null && personVO.getEntityVO().getInstitutionVO().getInstCode() != -1L) {
			crit.add(Restrictions.eq("entityVO.institutionVO", personVO.getEntityVO().getInstitutionVO()));
		}
		return crit;
	}

	public Long getEntityCodeOfPerson(EntityVO entityVO) {

		Criteria crit = this.getSessionFactory().getCurrentSession().createCriteria(EntityVO.class);

		crit.add(entityVO.getGovernarateVO().getId() == null ? Restrictions.isNull("governarateVO") : Restrictions.eq("governarateVO", entityVO.getGovernarateVO()));

		crit.add(entityVO.getDirectorateVO().getId() == null ? Restrictions.isNull("directorateVO") : Restrictions.eq("directorateVO", entityVO.getDirectorateVO()));

		crit.add((entityVO.getSectionVO().getId() == null || entityVO.getSectionVO().getId().equals(-1L)) ? Restrictions.isNull("sectionVO") : Restrictions.eq("sectionVO", entityVO.getSectionVO()));

		crit.add((entityVO.getDepartmentVO().getId() == null || entityVO.getDepartmentVO().getId().equals(-1L)) ? Restrictions.isNull("departmentVO") : Restrictions.eq("departmentVO", entityVO.getDepartmentVO()));

		crit.add((entityVO.getInstitutionVO().getInstCode() == null || entityVO.getInstitutionVO().getInstCode().equals(-1L)) ? Restrictions.isNull("institutionVO") : Restrictions.eq("institutionVO", entityVO.getInstitutionVO()));

		List<EntityVO> list = crit.list();

		if (list != null && list.size() > 0) {
			entityVO.setEntityCode(list.get(0).getEntityCode());
		} else {
			saveEnttiy(entityVO);
		}

		return entityVO.getEntityCode();
	}

	@Override
	public void savePerson(PersonVO personVO) {

		personVO.getEntityVO().setEntityCode(getEntityCodeOfPerson(personVO.getEntityVO()));
		if (personVO.getDesignationVO() == null || personVO.getDesignationVO().getId() == null || personVO.getDesignationVO().getId().equals(-1L)) {
			personVO.setDesignationVO(null);
		}
		personVO.setActiveYn("Y");
		// personVO.setCreatedBy(2);
		// personVO.setCreatedTime(new Date());
		personVO.setPersonName(personVO.getPersonName().toUpperCase());
		this.getSessionFactory().getCurrentSession().saveOrUpdate(personVO);
	}

	private void saveEnttiy(EntityVO entityVO) {
		if (entityVO.getDepartmentVO() == null || entityVO.getDepartmentVO().getId() == null || entityVO.getDepartmentVO().getId().equals(-1L)) {
			entityVO.setDepartmentVO(null);
		}

		if (entityVO.getInstitutionVO() == null || entityVO.getInstitutionVO().getInstCode() == null) {
			entityVO.setInstitutionVO(null);
		}

		if (entityVO.getSectionVO() == null || entityVO.getSectionVO().getId() == null || entityVO.getSectionVO().getId().equals(-1L)) {
			entityVO.setSectionVO(null);
		}

		getCurrentSession().save(entityVO);
	}

	@Override
	public PersonVO findPersonById(Integer personCode) throws Exception {
		List<PersonVO> list = getCurrentSession().createQuery("from PersonVO person join fetch person.entityVO   where person.personCode=:personCode").setParameter("personCode", personCode).list();
		return (list != null && list.size() > 0) ? list.get(0) : null;

	}

	@Override
	public void deletePerson(PersonVO personVO) throws Exception {

		getCurrentSession().createQuery("update PersonVO set activeYn = :activeYN where personCode=:personCode").setParameter("activeYN", "N").setParameter("personCode", personVO.getPersonCode()).executeUpdate();
	}

	@Override
	@Cacheable(value = "loadPersonReferenceData")
	public List<DesignationVO> loadReferenceData() throws Exception {
		// System.out.println(" ----------   PersonDAO .... load Reference Data.....");
		return (List<DesignationVO>) findAll(om.gov.moh.eab.master.vo.DesignationVO.class);

	}

	private List<InstitutionVO> getAllinstitutes() {
		List<InstitutionVO> list = getCurrentSession().createQuery("from InstitutionVO inst order by inst.instName").list();
		return (list != null && list.size() > 0) ? list : null;
	}

	@Override
	public boolean isStaffNoExist(PersonVO personVO) throws Exception {
		List<PersonVO> list = getCurrentSession().createQuery("from PersonVO person where person.activeYn='Y' and  person.staffNo=:staffNo").setParameter("staffNo", personVO.getStaffNo()).list();
		return (list != null && list.size() > 0) ? true : false;
	}

	@Override
	public boolean isGsmPrimaryExist(PersonVO personVO) throws Exception {
		List<PersonVO> list = getCurrentSession().createQuery("from PersonVO person where person.activeYn='Y' and  person.gsmPrimary=:gsmPrimary").setParameter("gsmPrimary", personVO.getGsmPrimary()).list();
		return (list != null && list.size() > 0) ? true : false;
	}

	@Override
	public boolean isGsmAddnlExist(PersonVO personVO) throws Exception {
		List<PersonVO> list = getCurrentSession().createQuery("from PersonVO person where person.gsmAddnl=:gsmAddnl").setParameter("gsmAddnl", personVO.getGsmAddnl()).list();
		return (list != null && list.size() > 0) ? true : false;
	}

	@Override
	public boolean isResiTelExist(PersonVO personVO) throws Exception {
		List<PersonVO> list = getCurrentSession().createQuery("from PersonVO person where person.resiTelNo=:resiTelNo").setParameter("resiTelNo", personVO.getResiTelNo()).list();
		return (list != null && list.size() > 0) ? true : false;
	}

	@Override
	public boolean isDirectTelExist(PersonVO personVO) throws Exception {
		List<PersonVO> list = getCurrentSession().createQuery("from PersonVO person where person.directTelNo=:directTelNo").setParameter("directTelNo", personVO.getDirectTelNo()).list();
		return (list != null && list.size() > 0) ? true : false;
	}

	public List<EntityVO> getEntitiesByInstituteId(EntityVO entityVO) {
		StringBuffer queryString = new StringBuffer();

		queryString.append("from EntityVO entityVO where departmentVO.id=:deptId and directorateVO.id=:dirId and sectionVO.id=:secId and governarateVO.id=:govId");

		if (entityVO.getInstitutionVO() != null && entityVO.getInstitutionVO().getInstCode() != null && !entityVO.getInstitutionVO().getInstCode().equals(-1L)) {
			queryString.append(" and entityVO.institutionVO.instCode=:instId");
		}

		org.hibernate.Query query = getCurrentSession().createQuery(queryString.toString());

		query.setParameter("deptId", entityVO.getDepartmentVO().getId()).setParameter("dirId", entityVO.getDirectorateVO().getId()).setParameter("secId", entityVO.getSectionVO().getId()).setParameter("govId", entityVO.getGovernarateVO().getId());

		if (entityVO.getInstitutionVO() != null && entityVO.getInstitutionVO().getInstCode() != null && !entityVO.getInstitutionVO().getInstCode().equals(-1L)) {
			query.setParameter("instId", entityVO.getInstitutionVO().getInstCode());
		}

		List<EntityVO> list = query.list();
		return list;

	}

	public int countTotalPersons(PersonVO personVO, UserVO loggedUser) throws Exception {

		Criteria criteria = createPersonCriteria(personVO, loggedUser, null, null);
		criteria.add(Restrictions.eq("activeYn", "Y"));
		if (personVO.isImportantBoolean()) {
			criteria.add(Restrictions.eq("important", "Y"));
		}
		if (personVO.isSearchAbleBoolean()) {
			criteria.add(Restrictions.eq("searchAble", "Y"));
		}

		Integer totalResult = ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();

		return totalResult;
	}

	public void updatePersonRow(PersonVO personVO) throws Exception {
		getCurrentSession().createQuery("update PersonVO set gsmPrimary=:gsmPrimary,directTelNo=:directTelNo,extn=:extn,modifiedBy=:modifiedBy,modifiedTime=:modifiedTime where personCode=:personCode").setParameter("gsmPrimary", personVO.getGsmPrimary()).setParameter("directTelNo", personVO.getDirectTelNo()).setParameter("extn", personVO.getExtn()).setParameter("modifiedBy", personVO.getModifiedBy()).setParameter("modifiedTime", personVO.getModifiedTime()).setParameter("personCode", personVO.getPersonCode()).executeUpdate();
	}

	@Override
	public List<PersonVO> searchPersonsWithNoSection(PersonVO personVO, UserVO loggedUser) throws Exception {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(PersonVO.class);
		createMainCriteria(personVO, loggedUser, criteria);
		Criteria sub = createSubCriteria(personVO, loggedUser, criteria);
		sub.add(Restrictions.isNull("entityVO.sectionVO"));
		return criteria.list();
	}

}
