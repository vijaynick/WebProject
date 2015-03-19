package om.gov.moh.eab.refdata.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import om.gov.moh.eab.master.vo.DepartmentVO;
import om.gov.moh.eab.master.vo.DesignationVO;
import om.gov.moh.eab.master.vo.DirectorateVO;
import om.gov.moh.eab.master.vo.GovernarateVO;
import om.gov.moh.eab.master.vo.SectionVO;
import om.gov.moh.eab.refdata.bo.ReferenceDataManager;
import om.gov.moh.eab.security.vo.InstitutionVO;
import om.gov.moh.eab.security.vo.RoleVO;
import om.gov.moh.eab.security.vo.UserVO;

@Component
@ApplicationScoped
public class ReferenceCacheBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(ReferenceCacheBean.class.getName());

	private Map<Long, DirectorateVO> directorateMap = new LinkedHashMap<Long, DirectorateVO>();
	private Map<Long, SectionVO> sectionMap = new HashMap<Long, SectionVO>();
	private Map<Long, InstitutionVO> instituteMap = new HashMap<Long, InstitutionVO>();
	private Map<Long, DepartmentVO> departmentMap = new HashMap<Long, DepartmentVO>();
	private Map<Long, GovernarateVO> governrateMap = new HashMap<Long, GovernarateVO>();
	private Map<Long, DesignationVO> desigMap = new HashMap<Long, DesignationVO>();
	private Map<String, String> isd = new HashMap<String, String>();
	private Map<String, String> categoryMap = new HashMap<String, String>();
	private Map<Long, RoleVO> roleMap = new HashMap<Long, RoleVO>();
	private Map<Long, UserVO> userMap = new HashMap<Long, UserVO>();
    
	@Autowired
	ReferenceDataManager referenceDataManager;

	@PostConstruct
	public void init() {
		loadCacheData();
	}

	public void loadCacheData() {
		try {
			// initialize all maps
			log.info("GOING TO FETCH REFERENCE DATA");
			referenceDataManager.loadEntityRefData(this);
			referenceDataManager.loadPersonRefData(this);
			referenceDataManager.loadUserRefData(this);
			log.info("FINISHED FETCHING REFERENCE DATA");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<Long, SectionVO> getSectionMap() {
		return sectionMap;
	}

	public void setSectionMap(Map<Long, SectionVO> sectionMap) {
		this.sectionMap = sectionMap;
	}

	public Map<Long, InstitutionVO> getInstituteMap() {
		return instituteMap;
	}

	public void setInstituteMap(Map<Long, InstitutionVO> instituteMap) {
		this.instituteMap = instituteMap;
	}

	public Map<Long, DepartmentVO> getDepartmentMap() {
		return departmentMap;
	}

	public void setDepartmentMap(Map<Long, DepartmentVO> departmentMap) {
		this.departmentMap = departmentMap;
	}

	public Map<Long, GovernarateVO> getGovernrateMap() {
		return governrateMap;
	}

	public void setGovernrateMap(Map<Long, GovernarateVO> governrateMap) {
		this.governrateMap = governrateMap;
	}

	public Map<Long, DesignationVO> getDesigMap() {
		return desigMap;
	}

	public void setDesigMap(Map<Long, DesignationVO> desigMap) {
		this.desigMap = desigMap;
	}

	public Map<String, String> getIsd() {
		return isd;
	}

	public void setIsd(Map<String, String> isd) {
		this.isd = isd;
	}

	public Map<Long, RoleVO> getRoleMap() {
		return roleMap;
	}

	public void setRoleMap(Map<Long, RoleVO> roleMap) {
		this.roleMap = roleMap;
	}

	public ReferenceDataManager getReferenceDataManager() {
		return referenceDataManager;
	}

	public void setReferenceDataManager(ReferenceDataManager referenceDataManager) {
		this.referenceDataManager = referenceDataManager;
	}

	public Map<Long, DirectorateVO> getDirectorateMap() {
		return directorateMap;
	}

	public void setDirectorateMap(Map<Long, DirectorateVO> directorateMap) {
		this.directorateMap = directorateMap;
	}

	/**
	 * @return the categoryMap
	 */
	public Map<String, String> getCategoryMap() {
		return categoryMap;
	}

	/**
	 * @param categoryMap the categoryMap to set
	 */
	public void setCategoryMap(Map<String, String> categoryMap) {
		this.categoryMap = categoryMap;
	}

	/**
	 * @return the userMap
	 */
	public Map<Long, UserVO> getUserMap() {
		return userMap;
	}

	/**
	 * @param userMap the userMap to set
	 */
	public void setUserMap(Map<Long, UserVO> userMap) {
		this.userMap = userMap;
	}

}
