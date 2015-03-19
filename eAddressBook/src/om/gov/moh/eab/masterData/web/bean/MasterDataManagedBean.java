/**
 * 
 */
package om.gov.moh.eab.masterData.web.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;

import om.gov.moh.eab.abstracts.bean.AbstractMB;
import om.gov.moh.eab.constants.EABConstants;
import om.gov.moh.eab.master.vo.DepartmentVO;
import om.gov.moh.eab.master.vo.DirectorateVO;
import om.gov.moh.eab.master.vo.GovernarateVO;
import om.gov.moh.eab.master.vo.SectionVO;
import om.gov.moh.eab.masterData.bo.MasterDataManager;
import om.gov.moh.eab.refdata.bean.ReferenceCacheBean;
import om.gov.moh.eab.refdata.bean.ReferenceDataBean;
import om.gov.moh.eab.security.bo.UserManager;
import om.gov.moh.eab.security.vo.AuditVO;
import om.gov.moh.eab.security.vo.RoleVO;
import om.gov.moh.eab.security.vo.UserVO;
import om.gov.moh.eab.security.web.bean.LoginManagedBean;
import om.gov.moh.eab.utils.ArabicConversion;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

/**
 * @author Sajeer Koroth
 * 
 */

@ManagedBean
@ViewScoped
public class MasterDataManagedBean extends AbstractMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LazyDataModel<DepartmentVO> configDataModel;

	private LazyDataModel<SectionVO> configSectionDataModel;

	private DepartmentVO departmentVO = new DepartmentVO();

	private DepartmentVO departmentAddVO = new DepartmentVO();

	private SectionVO sectionVO = new SectionVO();

	private SectionVO sectionAddVO = new SectionVO();

	private List<DepartmentVO> departments = new ArrayList();

	private UserVO loggedUser;

	@ManagedProperty(value = "#{loginManagedBean}")
	private LoginManagedBean loginManagedBean;

	@ManagedProperty(value = "#{referenceDataBean}")
	private ReferenceDataBean referenceDataBean;

	@ManagedProperty(value = "#{masterDataManager}")
	private MasterDataManager masterDataManager;
    
	@ManagedProperty(value = "#{userManager}")
	private UserManager userManager;
	
	@ManagedProperty(value = "#{referenceCacheBean}")
	private ReferenceCacheBean referenceCacheBean;

	@SuppressWarnings("serial")
	public LazyDataModel<DepartmentVO> getConfigDataModel() {
		if (configDataModel == null) {
			configDataModel = new LazyDataModel<DepartmentVO>() {
				
				@Override
				public List<DepartmentVO> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
					// TODO Auto-generated method stub
					List<DepartmentVO> list = new ArrayList<DepartmentVO>();					
					try {

						list = masterDataManager.searchDepartments(departmentVO, loggedUser, first, pageSize, null, null);
						list = getArabicConvertedList(list);
						setRowCount(masterDataManager.countTotalDepartments(departmentVO, loggedUser));
						setPageSize(pageSize);
					} catch (Exception e) {
						e.printStackTrace();
					}										
					return list;
				}

				

			};

		}

		return configDataModel;
	}

	public void setConfigDataModel(LazyDataModel<DepartmentVO> configDataModel) {
		this.configDataModel = configDataModel;
	}

	@SuppressWarnings("serial")
	public LazyDataModel<SectionVO> getConfigSectionDataModel() {
		if (configSectionDataModel == null) {
			configSectionDataModel = new LazyDataModel<SectionVO>() {

				@Override
				public List<SectionVO> load(int first, int pagesize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {

					List<SectionVO> list = new ArrayList<SectionVO>();
					try {

						list = masterDataManager.searchSections(sectionVO, loggedUser, first, pagesize, null, null);
						list = getArabicConvertedSectionList(list);
						setRowCount(masterDataManager.countTotalSections(sectionVO, loggedUser));
						setPageSize(pagesize);
					} catch (Exception e) {
						e.printStackTrace();
					}

					return list;
				}

			};

		}

		return configSectionDataModel;
	}

	/**
	 * @param configSectionDataModel
	 *            the configSectionDataModel to set
	 */
	public void setConfigSectionDataModel(LazyDataModel<SectionVO> configSectionDataModel) {
		this.configSectionDataModel = configSectionDataModel;
	}

	@PostConstruct
	public void loadReferenceData() {
		try {
			if (loggedUser == null || loggedUser.getRoleVO() == null || loggedUser.getRoleVO().getRoleId() == null) {
				setLoggedUser();
			}
			referenceDataBean.loadReferenceData();
			departmentVO = new DepartmentVO();
			sectionVO = new SectionVO();

		} catch (Exception e) {
			// TODO: handle exception
			displayErrorMessageToUser("common.error");
			e.printStackTrace();
			System.err.println("Inside loadReferenceData....................... " + e.getMessage());
		}

	}

	public void setLoggedUser() {

		try {
			/**
			 * This is the case when user is logged in...we will fetch data as
			 * per user
			 */
			if (loginManagedBean.isAuthorized()) {
				loggedUser = loginManagedBean.getCurrentUser();
			}

			/**
			 * This the case when user is not logged in...We will fetch data as
			 * per his selection of Governorate and Directorate
			 */
			if (!loginManagedBean.isAuthorized()) {
				loggedUser = new UserVO();
				RoleVO roleVO = new RoleVO();
				roleVO.setRoleId(EABConstants.ROLE_GUEST);
				loggedUser.setRoleVO(roleVO);
				getUserSelectedCookies(loggedUser);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Inside setLoggedUser....................... " + e.getMessage());
		}

	}

	public void getUserSelectedCookies(UserVO loggedUser) {
		Map<String, Object> requestCookieMap = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
		Cookie directorateCookie = (Cookie) requestCookieMap.get(EABConstants.DIRECTORATE_COOKIE);
		Cookie governorateCookie = (Cookie) requestCookieMap.get(EABConstants.GOVERNORATE_COOKIE);
		if (directorateCookie != null && directorateCookie.getValue() != null) {
			Long directorateId = Long.valueOf(directorateCookie.getValue());
			loggedUser.getDirectorateVO().setId(directorateId);
		}
		if (governorateCookie != null && governorateCookie.getValue() != null) {
			Long govId = Long.valueOf(governorateCookie.getValue());
			loggedUser.getGovernarateVO().setId(govId);
		}
	}

	public void redirectToDeptSearch() {
		try {

			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

			String contextPath = ctx.getContextPath();
			FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + "/pages/master/deptHome.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void redirectToSectSearch() {
		try {

			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

			String contextPath = ctx.getContextPath();
			FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + "/pages/master/sectionHome.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<DepartmentVO> getArabicConvertedList(List<DepartmentVO> list) {
		List<DepartmentVO> departments = new ArrayList<DepartmentVO>();
		DepartmentVO department = null;
		for (DepartmentVO departmentVO : list) {
			department = new DepartmentVO();

			department.setDepartmentName(departmentVO.getDepartmentName());
			if (departmentVO.getDepartmentNameNLS() != null) {
				department.setDepartmentNameNLS(ArabicConversion.getArabicEncodedString(departmentVO.getDepartmentNameNLS()));
			} else {
				department.setDepartmentNameNLS(departmentVO.getDepartmentName());
			}

			department.setDepartmentShortName(departmentVO.getDepartmentShortName());
			if (departmentVO.getDepartmentShortSNameNLS() != null) {
				department.setDepartmentShortSNameNLS(ArabicConversion.getArabicEncodedString(departmentVO.getDepartmentShortSNameNLS()));
			} else {
				department.setDepartmentShortSNameNLS(departmentVO.getDepartmentShortName());
			}

			if (departmentVO != null && departmentVO.getGovernarateVO() != null && departmentVO.getGovernarateVO().getGovNameNLS() != null) {
				department.getGovernarateVO().setGovNameNLS(ArabicConversion.getArabicEncodedString(departmentVO.getGovernarateVO().getGovNameNLS()));
			}
			if (departmentVO != null && departmentVO.getGovernarateVO() != null && departmentVO.getGovernarateVO().getGovName() != null) {
				department.getGovernarateVO().setGovName(departmentVO.getGovernarateVO().getGovName());
			}
			if (departmentVO != null && departmentVO.getDirectorateVO() != null && departmentVO.getDirectorateVO().getDirecotrateName() != null) {
				department.getDirectorateVO().setDirecotrateName(departmentVO.getDirectorateVO().getDirecotrateName());
			}
			department.setIsCommonDept(departmentVO.getIsCommonDept());
			department.setId(departmentVO.getId());
			

			departments.add(department);

		}
		return departments;
	}

	private List<SectionVO> getArabicConvertedSectionList(List<SectionVO> list) {
		List<SectionVO> sections = new ArrayList<SectionVO>();
		SectionVO section = null;
		for (SectionVO sectionVO : list) {
			section = new SectionVO();

			section.setSectionName(sectionVO.getSectionName());
			if (sectionVO.getSectionNameNLS() != null) {
				section.setSectionNameNLS(ArabicConversion.getArabicEncodedString(sectionVO.getSectionNameNLS()));
			} else {
				section.setSectionNameNLS(sectionVO.getSectionName());
			}

			section.setSectionShortName(sectionVO.getSectionShortName());
			if (sectionVO.getSectionShortNameNLS() != null) {
				section.setSectionShortNameNLS(ArabicConversion.getArabicEncodedString(sectionVO.getSectionShortNameNLS()));
			} else {
				section.setSectionShortNameNLS(sectionVO.getSectionShortName());
			}

			if (sectionVO != null && sectionVO.getGovernarateVO() != null && sectionVO.getGovernarateVO().getGovNameNLS() != null) {
				section.getGovernarateVO().setGovNameNLS(ArabicConversion.getArabicEncodedString(sectionVO.getGovernarateVO().getGovNameNLS()));
			}
			if (sectionVO != null && sectionVO.getGovernarateVO() != null && sectionVO.getGovernarateVO().getGovName() != null) {
				section.getGovernarateVO().setGovName(sectionVO.getGovernarateVO().getGovName());
			}
			
			if (sectionVO != null && sectionVO.getDirectorateVO() != null && sectionVO.getDirectorateVO().getDirecotrateNameNLS() != null) {
				section.getDirectorateVO().setDirecotrateNameNLS(ArabicConversion.getArabicEncodedString(sectionVO.getDirectorateVO().getDirecotrateNameNLS()));
			}
			if (sectionVO != null && sectionVO.getDirectorateVO() != null && sectionVO.getDirectorateVO().getDirecotrateName() != null) {
				section.getDirectorateVO().setDirecotrateName(sectionVO.getDirectorateVO().getDirecotrateName());
			}
			
			if (sectionVO != null && sectionVO.getDepartmentVO() != null && sectionVO.getDepartmentVO().getDepartmentNameNLS() != null) {
				section.getDepartmentVO().setDepartmentNameNLS(ArabicConversion.getArabicEncodedString(sectionVO.getDepartmentVO().getDepartmentNameNLS()));
			}
			if (sectionVO != null && sectionVO.getDepartmentVO() != null && sectionVO.getDepartmentVO().getDepartmentName() != null) {
				section.getDepartmentVO().setDepartmentName(sectionVO.getDepartmentVO().getDepartmentName());
			}
			
			
			
			section.setIsCommonSection(sectionVO.getIsCommonSection());
			section.setId(sectionVO.getId());

			sections.add(section);

		}
		return sections;
	}

	public void reset() {
		this.departmentVO = new DepartmentVO();
		getConfigDataModel();
	}

	public void resetDepartment() {
		// Long regCode = departmentAddVO.getGovernarateVO().getId();
		departmentAddVO = new DepartmentVO();
		// departmentAddVO.getGovernarateVO().setId(regCode);
	}

	public void resetSection() {
		this.sectionVO = new SectionVO();
		getConfigSectionDataModel();
	}

	public void resetAddSection() {
		// Long regCode = departmentAddVO.getGovernarateVO().getId();
		sectionAddVO = new SectionVO();
		// departmentAddVO.getGovernarateVO().setId(regCode);
	}

	public void saveDepartment() throws Exception {
		try {

			boolean validate = false;
			// boolean validateDeptName = false;
			List<DepartmentVO> similarDeptList = new ArrayList<DepartmentVO>();
			if (loginManagedBean.getCurrentUser().getRoleVO().getRoleId().equals(EABConstants.ROLE_ADMIN)) {
				departmentAddVO.getGovernarateVO().setId(loginManagedBean.getCurrentUser().getGovernarateVO().getId());
				departmentAddVO.getDirectorateVO().setId(loginManagedBean.getCurrentUser().getDirectorateVO().getId());
				validate = true;
			}
			
			if (!validate && departmentAddVO.getGovernarateVO() == null || departmentAddVO.getGovernarateVO().getId()==null || departmentAddVO.getGovernarateVO().getId().equals(-1L) || departmentAddVO.getGovernarateVO().getId().equals(-100L)) {
				displayErrorMessageToUser("personadd.selectgov");
				validate = false;
			
			}else{
				validate=true;
			}
			if (!validate && departmentAddVO.getDirectorateVO() == null || departmentAddVO.getDirectorateVO().getId().equals(-1L) || departmentAddVO.getDirectorateVO().getId().equals(-100L)) {
				displayErrorMessageToUser("personadd.selectdir");
				validate = false;
			}else{
				validate=true;
			}
			if(departmentAddVO.getId()== null){
				
				similarDeptList = this.masterDataManager.isDeptAlreadyExist(departmentAddVO);
				if(similarDeptList.size()>0){
					displayErrorMessageToUser("dept.already.exist");
					validate = false;
			}
			
			}
			

			if (validate) {
				boolean updatedFlag = false;
				departmentAddVO.setDepartmentNameNLS(ArabicConversion.setArabicEncodedString(departmentAddVO.getDepartmentNameNLS()));

				if (departmentAddVO.getId() == null) {
					departmentAddVO.setCreatedTime(new Date());
					departmentAddVO.setCreatedBy(loginManagedBean.getCurrentUser().getUserId());
				} else {
					updatedFlag = true;
					departmentAddVO.setModifiedTime(new Date());
					departmentAddVO.setModifiedBy(loginManagedBean.getCurrentUser().getUserId());

				}

				this.masterDataManager.saveDepartment(departmentAddVO);
				
				if (!updatedFlag){
					 this.userManager.addAuditInfo(new AuditVO(departmentAddVO.getId(), loginManagedBean.getCurrentUser().getUserId(), EABConstants.EAB_CATOGORY_DEPT,
	    		               "Department "+departmentAddVO.getDepartmentName()+" has been Added", getUserIP()));	
				}
				else{
					this.userManager.addAuditInfo(new AuditVO(departmentAddVO.getId(), loginManagedBean.getCurrentUser().getUserId(), EABConstants.EAB_CATOGORY_DEPT,
	    		               "Department "+departmentAddVO.getDepartmentName()+" has been updated", getUserIP()));
				}
				
				displayInfoMessageToUser("record.saved.successfully");
				resetDepartment();
				this.referenceDataBean.resetCache();
			}

		} catch (Exception e) {
			// TODO: handle exception
			displayErrorMessageToUser("common.error");
			e.printStackTrace();
		}

	}

	public void editDepartment(Long deptId) {
		try {

			departmentAddVO = new DepartmentVO();
			this.departmentAddVO = masterDataManager.findDepartmentById(deptId);
			this.departmentAddVO.setDepartmentNameNLS(ArabicConversion.getArabicEncodedString(this.departmentAddVO.getDepartmentNameNLS()));
			this.departmentAddVO.setDepartmentShortSNameNLS(ArabicConversion.getArabicEncodedString(this.departmentAddVO.getDepartmentShortSNameNLS()));
			if (departmentAddVO.getGovernarateVO() == null) {
			
				departmentAddVO.setGovernarateVO(new GovernarateVO());
			}
		} catch (Exception e) {
			// TODO: handle exception
			displayErrorMessageToUser("common.error");
			e.printStackTrace();

		}
	}

	public void deleteDepartment(Long deptId) throws Exception {
		try {

			departmentAddVO.setId(deptId);
			
			if (!masterDataManager.canDeleteDepartment(departmentAddVO)) {
				displayErrorMessageToUser("dept.cant.delete");
				departmentAddVO.setId(null);
				return;
			}
			
			if (!masterDataManager.isDepartmentHavingPersons(departmentAddVO)) {
				displayErrorMessageToUser("dept.cant.delete.person");
				departmentAddVO.setId(null);
				return;
			}
			
			
			masterDataManager.deleteDepartment(departmentAddVO);
			this.userManager.addAuditInfo(new AuditVO(deptId, loginManagedBean.getCurrentUser().getUserId(), EABConstants.EAB_CATOGORY_DEPT,
		               "Department with id "+deptId+" has been deactivated", getUserIP()));
			displayInfoMessageToUser("record.deleted.successfully");
			departmentAddVO = new DepartmentVO();
			departmentAddVO.setId(null);
			this.referenceDataBean.resetCache();
		} catch (Exception e) {
			// TODO: handle exception
			displayErrorMessageToUser("common.error");
			e.printStackTrace();
		}
	}

	/**
	 * SECTIONS/
	 */

	public void saveSection() throws Exception {
		try {
			boolean validate = false;
			boolean validateSectName = false;
			
			if (loginManagedBean.getCurrentUser().getRoleVO().getRoleId().equals(EABConstants.ROLE_ADMIN)) {
				sectionAddVO.getGovernarateVO().setId(loginManagedBean.getCurrentUser().getGovernarateVO().getId());
				sectionAddVO.getDirectorateVO().setId(loginManagedBean.getCurrentUser().getDirectorateVO().getId());
				validate = true;
			}
			if (!validate && sectionAddVO.getGovernarateVO()==null||sectionAddVO.getGovernarateVO().getId() == null|| sectionAddVO.getGovernarateVO().getId() == null|| sectionAddVO.getGovernarateVO().getId().equals(-1L) || sectionAddVO.getGovernarateVO().getId().equals(-100L)) {
				displayErrorMessageToUser("personadd.selectgov");
				validate = false;
			}else{
				validate=true;
			}
			if (!validate && sectionAddVO.getDirectorateVO() == null || sectionAddVO.getDirectorateVO().getId().equals(-1L) || sectionAddVO.getDirectorateVO().getId().equals(-100L)) {
				displayErrorMessageToUser("personadd.selectdir");
				validate = false;
			}else{
				validate=true;
			}
			
			if(sectionAddVO.getId()== null){
				validateSectName = this.masterDataManager.isSectionAlreadyExist(sectionAddVO);
				if(validateSectName){
					displayErrorMessageToUser("sect.already.exist");
					validate = false;
			     }
			}

			if (sectionAddVO.getDepartmentVO() == null || sectionAddVO.getDepartmentVO().getId() == null || sectionAddVO.getDepartmentVO().getId().equals(-100L)) {
				sectionAddVO.setDepartmentVO(null);
			}
			if (validate) {
				boolean updatedFlag = false;
				sectionAddVO.setSectionNameNLS(ArabicConversion.setArabicEncodedString(sectionAddVO.getSectionNameNLS()));

				if (sectionAddVO.getId() == null) {
					sectionAddVO.setCreatedTime(new Date());
					sectionAddVO.setCreatedBy(loginManagedBean.getCurrentUser().getUserId());
				} else {
					updatedFlag = true;
					sectionAddVO.setModifiedTime(new Date());
					sectionAddVO.setModifiedBy(loginManagedBean.getCurrentUser().getUserId());
				}

				this.masterDataManager.saveSection(sectionAddVO);
				
				
				if (!updatedFlag){
					 this.userManager.addAuditInfo(new AuditVO(sectionAddVO.getId(), loginManagedBean.getCurrentUser().getUserId(), EABConstants.EAB_CATOGORY_SECTION,
	    		               "Section "+sectionAddVO.getSectionName()+" has been Added", getUserIP()));	
				}
				else{
					this.userManager.addAuditInfo(new AuditVO(sectionAddVO.getId(), loginManagedBean.getCurrentUser().getUserId(), EABConstants.EAB_CATOGORY_SECTION,
	    		               "Section "+sectionAddVO.getSectionName()+" has been Updated", getUserIP()));
				}
				
				
				
				displayInfoMessageToUser("record.saved.successfully");
				resetAddSection();
				this.referenceDataBean.resetCache();
			}

		} catch (Exception e) {
			// TODO: handle exception
			displayErrorMessageToUser("common.error");
			e.printStackTrace();
		}

	}

	public void editSection(Long sectionId) {
		try {

			sectionAddVO = new SectionVO();
			this.sectionAddVO = masterDataManager.findSectionById(sectionId);
			this.sectionAddVO.setSectionNameNLS(ArabicConversion.getArabicEncodedString(this.sectionAddVO.getSectionNameNLS()));
			this.sectionAddVO.setSectionShortNameNLS(ArabicConversion.getArabicEncodedString(this.sectionAddVO.getSectionShortNameNLS()));
			if (sectionAddVO.getGovernarateVO() == null) {
				sectionAddVO.setGovernarateVO(new GovernarateVO());
			}
			if (sectionAddVO.getDirectorateVO() == null) {
				sectionAddVO.setDirectorateVO(new DirectorateVO());
			}
			if (sectionAddVO.getDepartmentVO() == null) {
				sectionAddVO.setDepartmentVO(new DepartmentVO());
			}

			// List<DirectorateVO> directorateSuperList = new
			// ArrayList<DirectorateVO>(referenceCacheBean.getDirectorateMap().values());
			// List<DirectorateVO> dirList = new ArrayList<DirectorateVO>();
			//
			// for (DirectorateVO dirVO : directorateSuperList) {
			// if (this.sectionAddVO.getGovernarateVO() != null
			// && dirVO.getRegionCode().equals(
			// this.sectionAddVO.getGovernarateVO()
			// .getRegionCode())) {
			// dirList.add(dirVO);
			// }
			// }
			// referenceDataBean.directorateList = new ArrayList<SelectItem>();
			// referenceDataBean.directorateListNLS = new
			// ArrayList<SelectItem>();
			// for (DirectorateVO dirVO : dirList) {
			// referenceDataBean.directorateList.add(new
			// SelectItem(dirVO.getId(), dirVO.getDirecotrateName()));
			// referenceDataBean.directorateListNLS.add(new
			// SelectItem(dirVO.getId(),
			// ArabicConversion.getArabicEncodedString(dirVO.getDirecotrateNameNLS())));
			// }
			//

		} catch (Exception e) {
			// TODO: handle exception
			displayErrorMessageToUser("common.error");
			e.printStackTrace();

		}
	}

	public void deleteSection(Long sectionId) throws Exception {
		try {

			sectionAddVO.setId(sectionId);
			if (!masterDataManager.canDeleteSection(sectionAddVO)) {
				displayErrorMessageToUser("sec.cant.delete");
				sectionAddVO.setId(null);
				return;
			}

			masterDataManager.deleteSection(sectionAddVO);
			this.userManager.addAuditInfo(new AuditVO(sectionId, loginManagedBean.getCurrentUser().getUserId(), EABConstants.EAB_CATOGORY_SECTION,
		               "Section with id"+sectionId+" has been deactivated", getUserIP()));
			displayInfoMessageToUser("record.deleted.successfully");
			sectionAddVO = new SectionVO();
			sectionAddVO.setId(null);
			this.referenceDataBean.resetCache();
		} catch (Exception e) {
			// TODO: handle exception
			displayErrorMessageToUser("common.error");
			e.printStackTrace();
		}
	}

	/**
	 * @return the departmentVO
	 */
	public DepartmentVO getDepartmentVO() {
		return departmentVO;
	}

	/**
	 * @param departmentVO
	 *            the departmentVO to set
	 */
	public void setDepartmentVO(DepartmentVO departmentVO) {
		this.departmentVO = departmentVO;
	}

	/**
	 * @return the masterDataManager
	 */
	public MasterDataManager getMasterDataManager() {
		return masterDataManager;
	}

	/**
	 * @param masterDataManager
	 *            the masterDataManager to set
	 */
	public void setMasterDataManager(MasterDataManager masterDataManager) {
		this.masterDataManager = masterDataManager;
	}

	/**
	 * @return the loggedUser
	 */
	public UserVO getLoggedUser() {
		return loggedUser;
	}

	/**
	 * @param loggedUser
	 *            the loggedUser to set
	 */
	public void setLoggedUser(UserVO loggedUser) {
		this.loggedUser = loggedUser;
	}

	/**
	 * @return the referenceDataBean
	 */
	public ReferenceDataBean getReferenceDataBean() {
		return referenceDataBean;
	}

	/**
	 * @param referenceDataBean
	 *            the referenceDataBean to set
	 */
	public void setReferenceDataBean(ReferenceDataBean referenceDataBean) {
		this.referenceDataBean = referenceDataBean;
	}

	/**
	 * @return the loginManagedBean
	 */
	public LoginManagedBean getLoginManagedBean() {
		return loginManagedBean;
	}

	/**
	 * @param loginManagedBean
	 *            the loginManagedBean to set
	 */
	public void setLoginManagedBean(LoginManagedBean loginManagedBean) {
		this.loginManagedBean = loginManagedBean;
	}

	/**
	 * @return the departments
	 */
	public List<DepartmentVO> getDepartments() {
		return departments;
	}

	/**
	 * @param departments
	 *            the departments to set
	 */
	public void setDepartments(List<DepartmentVO> departments) {
		this.departments = departments;
	}

	public DepartmentVO getDepartmentAddVO() {
		return departmentAddVO;
	}

	public void setDepartmentAddVO(DepartmentVO departmentAddVO) {
		this.departmentAddVO = departmentAddVO;
	}

	/**
	 * @return the sectionVO
	 */
	public SectionVO getSectionVO() {
		return sectionVO;
	}

	/**
	 * @param sectionVO
	 *            the sectionVO to set
	 */
	public void setSectionVO(SectionVO sectionVO) {
		this.sectionVO = sectionVO;
	}

	/**
	 * @return the sectionAddVO
	 */
	public SectionVO getSectionAddVO() {
		return sectionAddVO;
	}

	/**
	 * @param sectionAddVO
	 *            the sectionAddVO to set
	 */
	public void setSectionAddVO(SectionVO sectionAddVO) {
		this.sectionAddVO = sectionAddVO;
	}

	/**
	 * @return the referenceCacheBean
	 */
	public ReferenceCacheBean getReferenceCacheBean() {
		return referenceCacheBean;
	}

	/**
	 * @param referenceCacheBean
	 *            the referenceCacheBean to set
	 */
	public void setReferenceCacheBean(ReferenceCacheBean referenceCacheBean) {
		this.referenceCacheBean = referenceCacheBean;
	}

	/**
	 * @return the userManager
	 */
	public UserManager getUserManager() {
		return userManager;
	}

	/**
	 * @param userManager the userManager to set
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

}
