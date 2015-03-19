package om.gov.moh.eab.refdata.bo;

import java.util.List;

import om.gov.moh.eab.master.vo.DepartmentVO;
import om.gov.moh.eab.master.vo.DirectorateVO;
import om.gov.moh.eab.master.vo.SectionVO;
import om.gov.moh.eab.refdata.bean.ReferenceCacheBean;
import om.gov.moh.eab.security.vo.InstitutionVO;

public interface ReferenceDataManager {

	public void loadEntityRefData(ReferenceCacheBean refDataBean) throws Exception;

	public void loadPersonRefData(ReferenceCacheBean refDataBean) throws Exception;

	public void loadUserRefData(ReferenceCacheBean refDataBean) throws Exception;

	public List<SectionVO> getSectionsOfDirAndGovAndDept(Long govId, Long dirId, Long deptId, Long sectId) throws Exception;

	public List<DepartmentVO> getDepartmentOfDirAndGovAndSec(Long govId, Long dirId, Long secId) throws Exception;

	public List<DirectorateVO> getDirectoratesOfGov(Long govId);

	public List<DepartmentVO> getDepartmentsOfDir(Long dirId,Long govId);
	
	public List<InstitutionVO> getInstitutionsOfGov(Long govId);
}
