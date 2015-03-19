/**
 * 
 */
package om.gov.moh.eab.masterData.dao;

import java.util.List;

import om.gov.moh.eab.master.vo.DepartmentVO;
import om.gov.moh.eab.person.vo.PersonVO;
import om.gov.moh.eab.security.vo.UserVO;

import org.primefaces.model.SortOrder;

/**
 * @author Sajeer Koroth
 *
 */
public interface DepartmentDAO {
	
	public List<DepartmentVO> searchDepartments(DepartmentVO departmentVO, UserVO loggedUser, int index, int count,String sortField,SortOrder sortOrder) throws Exception;
	
	public void saveDepartment(DepartmentVO departmentVO);
	
	public int countTotalDepartments(DepartmentVO departmentVO, UserVO loggedUser) throws Exception;
	
	public DepartmentVO findDepartmentById(Long deptId) throws Exception;
	
	public void deleteDepartment(DepartmentVO departmentVO) throws Exception;
	
	public boolean canDeleteDepartment(DepartmentVO departmentVO) throws Exception;
	
	public boolean isDepartmentHavingPersons(DepartmentVO departmentVO) throws Exception;
	
	public List<DepartmentVO> isDeptAlreadyExist(DepartmentVO departmentVO) throws Exception ;
	
	public void SaveDepartmentSortOrder(List<String> sortedList) throws Exception;

}
