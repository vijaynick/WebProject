/**
 * 
 */
package om.gov.moh.eab.masterData.bo;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import om.gov.moh.eab.entity.dao.EntityDAO;
import om.gov.moh.eab.entity.vo.EntityVO;
import om.gov.moh.eab.master.vo.DepartmentVO;
import om.gov.moh.eab.master.vo.DesignationVO;
import om.gov.moh.eab.master.vo.SectionVO;
import om.gov.moh.eab.masterData.dao.DepartmentDAO;
import om.gov.moh.eab.masterData.dao.SectionDAO;
import om.gov.moh.eab.person.vo.PersonVO;
import om.gov.moh.eab.security.vo.UserVO;

/**
 * @author Sajeer Koroth
 *
 */

@Service("masterDataManager")
@Transactional
public class MasterDataManagerImpl implements MasterDataManager, Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private DepartmentDAO departmentDAO;
	
	@Autowired
	private SectionDAO sectionDAO;
	
	@Autowired
	private EntityDAO entityDAO;
	
	

	@Override
	@Transactional
	public void saveDepartment(DepartmentVO departmentVO) throws Exception {
		departmentDAO.saveDepartment(departmentVO);
	}
	
	@Override
	@Transactional
	public List<DepartmentVO> searchDepartments(DepartmentVO departmentVO, UserVO loggedUser, int start, int count,String sortField,SortOrder sortOrder) throws Exception {
		List<DepartmentVO> list = this.departmentDAO.searchDepartments(departmentVO, loggedUser, start, count,sortField,sortOrder);
		if (list != null && list.size() > 0) {
			for (DepartmentVO deptmentVO : list) {
				Hibernate.initialize(deptmentVO.getGovernarateVO());
				Hibernate.initialize(deptmentVO.getDirectorateVO());
			}
		}
		return list;

	}
	
	public int countTotalDepartments(DepartmentVO departmentVO, UserVO loggedUser) throws Exception {
		return departmentDAO.countTotalDepartments(departmentVO, loggedUser);
	}
	
	public DepartmentVO findDepartmentById(Long deptId) throws Exception {
		DepartmentVO department = this.departmentDAO.findDepartmentById(deptId);
		if(department!= null && department.getGovernarateVO() != null) {
			Hibernate.initialize(department.getGovernarateVO());
			Hibernate.initialize(department.getDirectorateVO());
		}
		return department;
	}
	
	public void deleteDepartment(DepartmentVO departmentVO) throws Exception {
		this.departmentDAO.deleteDepartment(departmentVO);
	}
	
	
	@Override
	public void saveSection(SectionVO sectionVO) throws Exception {
		sectionDAO.saveSection(sectionVO);
	}

	@Override
	public List<SectionVO> searchSections(SectionVO sectionVO,
			UserVO loggedUser, int start, int count, String sortField,
			SortOrder sortOrder) throws Exception {
		List<SectionVO> list = this.sectionDAO.searchSections(sectionVO, loggedUser, start, count,sortField,sortOrder);
		if (list != null && list.size() > 0) {
			for (SectionVO sections : list) {
				Hibernate.initialize(sections.getGovernarateVO());
				Hibernate.initialize(sections.getDirectorateVO());
				Hibernate.initialize(sections.getDepartmentVO());
			}
		}
		return list;
	}

	@Override
	public int countTotalSections(SectionVO sectionVO, UserVO loggedUser)
			throws Exception {
		return sectionDAO.countTotalSections(sectionVO, loggedUser);
	}

	@Override
	public SectionVO findSectionById(Long SectionId) throws Exception {
		SectionVO section = this.sectionDAO.findSectionById(SectionId);
		
		if(section!= null && section.getGovernarateVO() != null) {
			Hibernate.initialize(section.getGovernarateVO());
		}
		
		if(section!= null && section.getDirectorateVO() != null) {
			Hibernate.initialize(section.getDirectorateVO());
		}
		if(section!= null && section.getDepartmentVO() != null) {
			Hibernate.initialize(section.getDepartmentVO());
		}
		
		
		return section;
	}

	@Override
	public void deleteSection(SectionVO sectionVO) throws Exception {
		this.sectionDAO.deleteSection(sectionVO);
		
	}
	
	
	@Override
	public EntityVO findEntityByCombination(Long govId, Long dirId, Long depId, Long secId) throws Exception {
		return this.entityDAO.findEntityByCombination(govId,dirId,depId,secId);
	}
	
	
	@Override
	public List<DesignationVO> findDesignationsByEntityCode(Long entityCode) throws Exception {
		return this.entityDAO.findDesignationsByEntityCode(entityCode);
	}
	
	
	
	
	/**
	 * @return the departmentDAO
	 */
	public DepartmentDAO getDepartmentDAO() {
		return departmentDAO;
	}

	/**
	 * @param departmentDAO the departmentDAO to set
	 */
	public void setDepartmentDAO(DepartmentDAO departmentDAO) {
		this.departmentDAO = departmentDAO;
	}

	/**
	 * @return the sectionDAO
	 */
	public SectionDAO getSectionDAO() {
		return sectionDAO;
	}

	/**
	 * @param sectionDAO the sectionDAO to set
	 */
	public void setSectionDAO(SectionDAO sectionDAO) {
		this.sectionDAO = sectionDAO;
	}

	@Override
	public boolean canDeleteSection(SectionVO sectionVO) throws Exception {
		return sectionDAO.canDeleteSection(sectionVO);
	}

	@Override
	public boolean canDeleteDepartment(DepartmentVO departmentVO) throws Exception {
		return departmentDAO.canDeleteDepartment(departmentVO);
	}

	public boolean isDepartmentHavingPersons(DepartmentVO departmentVO) throws Exception	{
		return departmentDAO.isDepartmentHavingPersons(departmentVO);
	}

	@Override
	public List<DepartmentVO> isDeptAlreadyExist(DepartmentVO departmentVO) throws Exception {
		return departmentDAO.isDeptAlreadyExist(departmentVO);
	}

	@Override
	public boolean isSectionAlreadyExist(SectionVO sectionVO) throws Exception {
		return sectionDAO.isSectionAlreadyExist(sectionVO);
	}

	@Override
	public List<SectionVO> findSectionsByDirId(Long dirId) throws Exception {
		return sectionDAO.findSectionsByDirId(dirId);
	}

	@Override
	public void SaveDepartmentSortOrder(List<String> sortedList) throws Exception {
		 departmentDAO.SaveDepartmentSortOrder(sortedList);
		
	}

	@Override
	public void SaveSectionSortOrder(List<String> sortedList) throws Exception {
		sectionDAO.SaveSectionSortOrder(sortedList);
		
	}
	
	@Override
	public void saveDesignationSortOrder(Long entityCode, List<String> designationList) throws Exception {
		entityDAO.saveDesignationSortOrder(entityCode, designationList);
		
	}



	/**
	 * @return the entityDAO
	 */
	public EntityDAO getEntityDAO() {
		return entityDAO;
	}

	/**
	 * @param entityDAO the entityDAO to set
	 */
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	
	
   
}
