/**
 * 
 */
package om.gov.moh.eab.masterData.bo;

import java.util.List;

import org.primefaces.model.SortOrder;

import om.gov.moh.eab.entity.vo.EntityVO;
import om.gov.moh.eab.master.vo.DepartmentVO;
import om.gov.moh.eab.master.vo.DesignationVO;
import om.gov.moh.eab.master.vo.SectionVO;
import om.gov.moh.eab.person.vo.PersonVO;
import om.gov.moh.eab.security.vo.UserVO;

/**
 * @author Sajeer Koroth
 * 
 */
public interface MasterDataManager {

	public void saveDepartment(DepartmentVO departmentVO) throws Exception;

	public List<DepartmentVO> searchDepartments(DepartmentVO departmentVO, UserVO loggedUser, int start, int count, String sortField, SortOrder sortOrder) throws Exception;

	public int countTotalDepartments(DepartmentVO departmentVO, UserVO loggedUser) throws Exception;

	public DepartmentVO findDepartmentById(Long deptId) throws Exception;

	public void deleteDepartment(DepartmentVO departmentVO) throws Exception;

	public void saveSection(SectionVO sectionVO) throws Exception;

	public List<SectionVO> searchSections(SectionVO sectionVO, UserVO loggedUser, int start, int count, String sortField, SortOrder sortOrder) throws Exception;

	public int countTotalSections(SectionVO sectionVO, UserVO loggedUser) throws Exception;

	public SectionVO findSectionById(Long SectionId) throws Exception;

	public void deleteSection(SectionVO sectionVO) throws Exception;

	public boolean canDeleteSection(SectionVO sectionVO) throws Exception;

	public boolean canDeleteDepartment(DepartmentVO departmentVO) throws Exception;

	public boolean isDepartmentHavingPersons(DepartmentVO departmentVO) throws Exception;
	
	public List<DepartmentVO> isDeptAlreadyExist(DepartmentVO departmentVO) throws Exception ;
	
	public boolean isSectionAlreadyExist(SectionVO sectionVO) throws Exception ;
	
	public List<SectionVO> findSectionsByDirId(Long dirId) throws Exception;
	
	public void SaveDepartmentSortOrder(List<String> sortedList) throws Exception;
	
	public void SaveSectionSortOrder(List<String> sortedList) throws Exception;
	
    /**
     * This method is used to find the EntityVO based on the combination of parameter values 
     * Eg:- If any of the following parameter say secId is null, it will fetch that entity code which has values for govId,dirId and depId and secId is null
     * @param govId
     * @param dirId
     * @param depId
     * @param secId
     * @return
     * @throws Exception
     */
	public EntityVO findEntityByCombination(Long govId,Long dirId,Long depId, Long secId) throws Exception;
	
	/**
	 * This method will list the designations of the persons who belongs to this entityCode
	 * @param entityCode
	 * @return
	 * @throws Exception
	 */
	public List<DesignationVO> findDesignationsByEntityCode(Long entityCode) throws Exception;
	
	/**
	 * This method is used to save the sorter order of designations with respect to an entity code
	 * Here @param designationList is a List<String> since the primefaces control p:orderList internally convert List<DesignationVO> to  List<String>
	 * The sort order will be the position of that designation in designationList.
	 * @param entityCode
	 * @param designationList
	 * @throws Exception
	 */
	public void saveDesignationSortOrder(Long entityCode,List<String> designationList) throws Exception;
	
	
	
	
}
