/**
 * 
 */
package om.gov.moh.eab.masterData.dao;

import java.util.List;

import om.gov.moh.eab.master.vo.SectionVO;
import om.gov.moh.eab.security.vo.UserVO;

import org.primefaces.model.SortOrder;

/**
 * @author Sajeer Koroth
 *
 */
public interface SectionDAO {
	

	public List<SectionVO> searchSections(SectionVO sectionVO, UserVO loggedUser, int index, int count,String sortField,SortOrder sortOrder) throws Exception;
	
	public void saveSection(SectionVO sectionVO);
	
	public int countTotalSections(SectionVO sectionVO, UserVO loggedUser) throws Exception;
	
	public SectionVO findSectionById(Long sectionId) throws Exception;
	
	public void deleteSection(SectionVO sectionVO) throws Exception;
	
	public boolean canDeleteSection(SectionVO sectionVO) throws Exception;
	
	public boolean isSectionAlreadyExist(SectionVO sectionVO) throws Exception ;
	
	public List<SectionVO> findSectionsByDirId(Long dirId) throws Exception ;
	
	public void SaveSectionSortOrder(List<String> sortedList) throws Exception; 
	

}
