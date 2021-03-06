package om.gov.moh.eab.person.dao;

import java.util.List;

import om.gov.moh.eab.entity.vo.EntityVO;
import om.gov.moh.eab.master.vo.DesignationVO;
import om.gov.moh.eab.person.vo.PersonVO;
import om.gov.moh.eab.security.vo.UserVO;

import org.primefaces.model.SortOrder;

/**
 * @author farid.haq
 * 
 */
public interface PersonDAO {

	public List<PersonVO> searchPersons(PersonVO personVO, UserVO loggedUser, int index, int count,String sortField,SortOrder sortOrder,boolean isReport) throws Exception;
	public List<PersonVO> searchPersonsWithNoSection(PersonVO personVO, UserVO loggedUser) throws Exception;
	
	
	public void savePerson(PersonVO personAddVO) throws Exception;

	public PersonVO findPersonById(Integer personCode) throws Exception;

	public void deletePerson(PersonVO personVO) throws Exception;

	public List<DesignationVO> loadReferenceData() throws Exception;

	public boolean isStaffNoExist(PersonVO personVO) throws Exception;

	public boolean isGsmPrimaryExist(PersonVO personVO) throws Exception;

	public boolean isGsmAddnlExist(PersonVO personVO) throws Exception;

	public boolean isResiTelExist(PersonVO personVO) throws Exception;

	public boolean isDirectTelExist(PersonVO personVO) throws Exception;

	public List<EntityVO> getEntitiesByInstituteId(EntityVO entityVO) throws Exception;

	public int countTotalPersons(PersonVO personVO, UserVO loggedUser) throws Exception;
	
	public void updatePersonRow(PersonVO personVO) throws Exception;
	
}
