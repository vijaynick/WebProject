package om.gov.moh.eab.person.bo;

import java.io.Serializable;
import java.util.List;

import om.gov.moh.eab.entity.vo.EntityVO;
import om.gov.moh.eab.person.dao.PersonDAO;
import om.gov.moh.eab.person.vo.PersonVO;
import om.gov.moh.eab.security.vo.UserVO;

import org.hibernate.Hibernate;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author farid.haq
 * 
 */
@Service("personManager")
@Transactional
public class PersonManagerImpl implements PersonManager, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private PersonDAO personDAO;

	public PersonDAO getPersonDAO() {
		return personDAO;
	}

	public void setPersonDAO(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Override
	@Transactional
	public List<PersonVO> searchPersons(PersonVO personVO, UserVO loggedUser, int start, int count,String sortField,SortOrder sortOrder,boolean isReport) throws Exception {
		List<PersonVO> list = this.personDAO.searchPersons(personVO, loggedUser, start, count,sortField,sortOrder,isReport);
		if (list != null && list.size() > 0) {
			for (PersonVO personVO2 : list) {
				Hibernate.initialize(personVO2.getDesignationVO());
				Hibernate.initialize(personVO2.getEntityVO().getDirectorateVO());
				Hibernate.initialize(personVO2.getEntityVO().getSectionVO());
				Hibernate.initialize(personVO2.getEntityVO().getDepartmentVO());
			}
		}

		return list;

	}

	
	@Override
	@Transactional
	public void savePerson(PersonVO personAddVO) throws Exception {
		personAddVO.setActiveYn("Y");
		this.personDAO.savePerson(personAddVO);
	}

	@Override
	public PersonVO findPersonById(Integer personCode) throws Exception {
		PersonVO person = this.personDAO.findPersonById(personCode);
		
		Hibernate.initialize(person.getEntityVO().getInstitutionVO());
		Hibernate.initialize(person.getEntityVO().getGovernarateVO());
		Hibernate.initialize(person.getEntityVO().getDirectorateVO());
		Hibernate.initialize(person.getEntityVO().getDepartmentVO());
		Hibernate.initialize(person.getEntityVO().getSectionVO());
		Hibernate.initialize(person.getEntityVO().getInstitutionVO());
		
		return person;
	}

	
	public void updatePersonRow(PersonVO personVO) throws Exception{
		personDAO.updatePersonRow(personVO);
	}
	
	@Override
	public void deletePerson(PersonVO personVO) throws Exception {
		this.personDAO.deletePerson(personVO);
	}

	@Override
	public boolean isStaffNoExist(PersonVO personVO) throws Exception {
		return personDAO.isStaffNoExist(personVO);
	}

	@Override
	public boolean isGsmPrimaryExist(PersonVO personVO) throws Exception {
		return personDAO.isGsmPrimaryExist(personVO);
	}

	@Override
	public boolean isGsmAddnlExist(PersonVO personVO) throws Exception {
		return personDAO.isGsmAddnlExist(personVO);
	}

	@Override
	public boolean isResiTelExist(PersonVO personVO) throws Exception {
		return personDAO.isResiTelExist(personVO);
	}

	@Override
	public boolean isDirectTelExist(PersonVO personVO) throws Exception {
		return personDAO.isDirectTelExist(personVO);
	}

	@Override
	public List<EntityVO> getEntitiesByInstituteId(EntityVO entityVO) throws Exception {
		return personDAO.getEntitiesByInstituteId(entityVO);
	}

	public int countTotalPersons(PersonVO personVO, UserVO loggedUser) throws Exception {
		return personDAO.countTotalPersons(personVO, loggedUser);
	}

	@Override
	public List<PersonVO> searchPersonsWithNoSection(PersonVO personVO, UserVO loggedUser) throws Exception {
		
		List<PersonVO> list = this.personDAO.searchPersonsWithNoSection(personVO, loggedUser);
		if (list != null && list.size() > 0) {
			for (PersonVO personVO2 : list) {
				Hibernate.initialize(personVO2.getDesignationVO());
				Hibernate.initialize(personVO2.getEntityVO().getDirectorateVO());
				Hibernate.initialize(personVO2.getEntityVO().getSectionVO());
				Hibernate.initialize(personVO2.getEntityVO().getDepartmentVO());
			}
		}

		return list;
	}

	

}
