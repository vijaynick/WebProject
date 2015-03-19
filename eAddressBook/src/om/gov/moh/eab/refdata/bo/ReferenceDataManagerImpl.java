package om.gov.moh.eab.refdata.bo;

import java.io.Serializable;
import java.util.List;

import om.gov.moh.eab.master.vo.DepartmentVO;
import om.gov.moh.eab.master.vo.DirectorateVO;
import om.gov.moh.eab.master.vo.SectionVO;
import om.gov.moh.eab.refdata.bean.ReferenceCacheBean;
import om.gov.moh.eab.refdata.dao.ReferenceDataDAO;
import om.gov.moh.eab.security.vo.InstitutionVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("referenceDataManager")
@Transactional
public class ReferenceDataManagerImpl implements ReferenceDataManager, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReferenceDataManagerImpl() {

	}

	@Autowired
	private ReferenceDataDAO referenceDataDAO;

	@Override
	public void loadEntityRefData(ReferenceCacheBean refDataBean) throws Exception {
		referenceDataDAO.loadEntityRefData(refDataBean);

	}

	@Override
	public void loadPersonRefData(ReferenceCacheBean refDataBean) throws Exception {

		referenceDataDAO.loadPersonRefData(refDataBean);

	}

	@Override
	public void loadUserRefData(ReferenceCacheBean refDataBean) throws Exception {
		referenceDataDAO.loadUserRefData(refDataBean);
	}

	public List<SectionVO> getSectionsOfDirAndGovAndDept(Long govId, Long dirId, Long deptId, Long sectId) throws Exception {
		return referenceDataDAO.getSectionsOfDirAndGovAndDept(govId, dirId, deptId, sectId);
	}

	public List<DepartmentVO> getDepartmentOfDirAndGovAndSec(Long govId, Long dirId, Long secId) throws Exception {
		return referenceDataDAO.getDepartmentOfDirAndGovAndSec(govId, dirId, secId);
	}

	public List<DirectorateVO> getDirectoratesOfGov(Long govId) {
		return referenceDataDAO.getDirectoratesOfGov(govId);
	}

	public List<DepartmentVO> getDepartmentsOfDir(Long dirId, Long govId) {
		return referenceDataDAO.getDepartmentsOfDir(dirId, govId);
	}
	
	public List<InstitutionVO> getInstitutionsOfGov(Long govId){
		return referenceDataDAO.getInstitutionsOfGov(govId);
	}
}
