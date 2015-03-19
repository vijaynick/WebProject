package om.gov.moh.eab.refdata.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.Cookie;

import om.gov.moh.eab.constants.EABConstants;
import om.gov.moh.eab.master.vo.DepartmentVO;
import om.gov.moh.eab.master.vo.DesignationVO;
import om.gov.moh.eab.master.vo.DirectorateVO;
import om.gov.moh.eab.master.vo.GovernarateVO;
import om.gov.moh.eab.master.vo.SectionVO;
import om.gov.moh.eab.person.bo.PersonManager;
import om.gov.moh.eab.person.web.bean.PersonManagedBean;
import om.gov.moh.eab.security.vo.InstitutionVO;
import om.gov.moh.eab.security.vo.RoleVO;
import om.gov.moh.eab.security.vo.UserVO;
import om.gov.moh.eab.security.web.bean.LoginManagedBean;
import om.gov.moh.eab.utils.ArabicConversion;

import org.apache.log4j.Logger;

@ManagedBean
@SessionScoped
public class ReferenceDataBean implements Serializable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(ReferenceDataBean.class.getName());

	public List<SelectItem> governrateList = new ArrayList<SelectItem>();
	public List<SelectItem> directorateList = new ArrayList<SelectItem>();
	public List<SelectItem> sectionList = new ArrayList<SelectItem>();
	public List<SelectItem> instituteList = new ArrayList<SelectItem>();
	public List<SelectItem> departmentList = new ArrayList<SelectItem>();
	public List<SelectItem> designations = new ArrayList<SelectItem>();
	private List<SelectItem> isdSelectItems = new ArrayList<SelectItem>();
	private List<SelectItem> roleList = new ArrayList<SelectItem>();

	public List<SelectItem> governrateListNLS = new ArrayList<SelectItem>();
	public List<SelectItem> directorateListNLS = new ArrayList<SelectItem>();
	public List<SelectItem> sectionListNLS = new ArrayList<SelectItem>();
	public List<SelectItem> instituteListNLS = new ArrayList<SelectItem>();
	public List<SelectItem> departmentListNLS = new ArrayList<SelectItem>();
	public List<SelectItem> designationsNLS = new ArrayList<SelectItem>();
	private List<SelectItem> roleListNLS = new ArrayList<SelectItem>();
	// ***************************************************************//

	public List<SelectItem> governrateListSuper = new ArrayList<SelectItem>();
	public List<SelectItem> directorateListSuper = new ArrayList<SelectItem>();
	public List<SelectItem> sectionListSuper = new ArrayList<SelectItem>();
	public List<SelectItem> departmentListSuper = new ArrayList<SelectItem>();

	public List<SelectItem> governrateListSuperNLS = new ArrayList<SelectItem>();
	public List<SelectItem> directorateListSuperNLS = new ArrayList<SelectItem>();
	public List<SelectItem> sectionListSuperNLS = new ArrayList<SelectItem>();
	public List<SelectItem> departmentListSuperNLS = new ArrayList<SelectItem>();

	public List<SelectItem> governrateListNormal = new ArrayList<SelectItem>();
	public List<SelectItem> directorateListNormal = new ArrayList<SelectItem>();
	public List<SelectItem> governrateListNormalNLS = new ArrayList<SelectItem>();
	public List<SelectItem> directorateListNormalNLS = new ArrayList<SelectItem>();
	public List<SelectItem> categoryList = new ArrayList<SelectItem>();
	public List<SelectItem> usersList = new ArrayList<SelectItem>();
	public List<SelectItem> usersListNLS = new ArrayList<SelectItem>();
	
	private UserVO currentUser;

	@ManagedProperty(value = "#{loginManagedBean}")
	private LoginManagedBean loginManagedBean;
	@ManagedProperty(value = "#{referenceCacheBean}")
	private ReferenceCacheBean referenceCacheBean;

	public ReferenceDataBean() {

	}

	@PostConstruct
	public void init() {
		initializeSelectItems();
	}

	public void initializeSelectItems() {
		/**
		 * REFERENCE DATA WHICH REMAINS SAME FOR WHOLE APPLICATION AND FOR EVERY
		 * USER
		 **/

		List<DesignationVO> desigs = new ArrayList<DesignationVO>(referenceCacheBean.getDesigMap().values());

		designations = new ArrayList<SelectItem>();
		designationsNLS = new ArrayList<SelectItem>();
		for (DesignationVO designationVO : desigs) {
			designations.add(new SelectItem(designationVO.getId(), designationVO.getName()));
			if (designationVO.getNameNLS() != null && !designationVO.getNameNLS().isEmpty()) {
				designationsNLS.add(new SelectItem(designationVO.getId(), ArabicConversion.getArabicEncodedString(designationVO.getNameNLS())));
			} else {
				designationsNLS.add(new SelectItem(designationVO.getId(), designationVO.getName()));
			}
		}

		roleList = new ArrayList<SelectItem>();
		roleListNLS = new ArrayList<SelectItem>();
		List<RoleVO> roles = new ArrayList<RoleVO>(referenceCacheBean.getRoleMap().values());
		for (RoleVO roleVO : roles) {
			roleList.add(new SelectItem(roleVO.getRoleId(), roleVO.getRoleName()));
			if (roleVO.getRoleNameNLS() != null && !roleVO.getRoleNameNLS().isEmpty()) {
				roleListNLS.add(new SelectItem(roleVO.getRoleId(), ArabicConversion.getArabicEncodedString(roleVO.getRoleNameNLS())));
			} else {
				roleListNLS.add(new SelectItem(roleVO.getRoleId(), roleVO.getRoleName()));
			}
		}

		instituteList = new ArrayList<SelectItem>();
		instituteListNLS = new ArrayList<SelectItem>();
		if (instituteList == null || instituteList.isEmpty()) {
			List<InstitutionVO> instList = new ArrayList<InstitutionVO>(referenceCacheBean.getInstituteMap().values());
			for (InstitutionVO institutionVO : instList) {
				instituteList.add(new SelectItem(institutionVO.getInstCode(), institutionVO.getInstName()));
				if (institutionVO.getInstNameNLS() != null && !institutionVO.getInstNameNLS().isEmpty()) {
					instituteListNLS.add(new SelectItem(institutionVO.getInstCode(), ArabicConversion.getArabicEncodedString(institutionVO.getInstNameNLS())));
				} else {
					instituteListNLS.add(new SelectItem(institutionVO.getInstCode(), institutionVO.getInstName()));
				}
			}
		}
		
		
		
		categoryList = new ArrayList<SelectItem>();
		if(categoryList == null || categoryList.isEmpty()){
			List<String> categories = new ArrayList<String>(referenceCacheBean.getCategoryMap().values());
			for(String category : categories){
				categoryList.add(new SelectItem(category, category));
			}
		}
		
		

		/** REFERENCE DATA FOR ONLY SUPER USERS **/

		governrateListSuper = new ArrayList<SelectItem>();
		governrateListSuperNLS = new ArrayList<SelectItem>();
		List<GovernarateVO> govList = new ArrayList<GovernarateVO>(referenceCacheBean.getGovernrateMap().values());
		Collections.sort(govList, new Comparator<GovernarateVO>() {
			@Override
			public int compare(GovernarateVO a, GovernarateVO b) {
				return a.getId() < b.getId() ? -1 : a.getId() == b.getId() ? 0 : 1;
			}
		});
		for (GovernarateVO governarateVO : govList) {

			governrateListSuper.add(new SelectItem(governarateVO.getId(), governarateVO.getGovName()));
			if (governarateVO.getGovNameNLS() != null && !governarateVO.getGovNameNLS().isEmpty()) {
				governrateListSuperNLS.add(new SelectItem(governarateVO.getId(), ArabicConversion.getArabicEncodedString(governarateVO.getGovNameNLS())));
			} else {
				governrateListSuperNLS.add(new SelectItem(governarateVO.getId(), governarateVO.getGovName()));
			}
		}

		directorateListSuper = new ArrayList<SelectItem>();
		directorateListSuperNLS = new ArrayList<SelectItem>();
		List<DirectorateVO> directList = new ArrayList<DirectorateVO>(referenceCacheBean.getDirectorateMap().values());
		for (DirectorateVO directorateVO : directList) {
			directorateListSuper.add(new SelectItem(directorateVO.getId(), directorateVO.getDirecotrateName()));
			if (directorateVO.getDirecotrateNameNLS() != null && !directorateVO.getDirecotrateNameNLS().isEmpty()) {
				directorateListSuperNLS.add(new SelectItem(directorateVO.getId(), ArabicConversion.getArabicEncodedString(directorateVO.getDirecotrateNameNLS())));
			} else {
				directorateListSuperNLS.add(new SelectItem(directorateVO.getId(), directorateVO.getDirecotrateName()));
			}
		}

		sectionListSuper = new ArrayList<SelectItem>();
		sectionListSuperNLS = new ArrayList<SelectItem>();
		List<SectionVO> secList = new ArrayList<SectionVO>(referenceCacheBean.getSectionMap().values());
		for (SectionVO sectionVO : secList) {
			sectionListSuper.add(new SelectItem(sectionVO.getId(), sectionVO.getSectionName()));
			if (sectionVO.getSectionNameNLS() != null && !sectionVO.getSectionNameNLS().isEmpty()) {
				sectionListSuperNLS.add(new SelectItem(sectionVO.getId(), ArabicConversion.getArabicEncodedString(sectionVO.getSectionNameNLS())));
			} else {
				sectionListSuperNLS.add(new SelectItem(sectionVO.getId(), sectionVO.getSectionName()));
			}

		}

		departmentListSuper = new ArrayList<SelectItem>();
		departmentListSuperNLS = new ArrayList<SelectItem>();
		List<DepartmentVO> deptist = new ArrayList<DepartmentVO>(referenceCacheBean.getDepartmentMap().values());
		for (DepartmentVO departmentVO : deptist) {
			departmentListSuper.add(new SelectItem(departmentVO.getId(), departmentVO.getDepartmentName()));
			if (departmentVO.getDepartmentNameNLS() != null && !departmentVO.getDepartmentNameNLS().isEmpty()) {
				departmentListSuperNLS.add(new SelectItem(departmentVO.getId(), ArabicConversion.getArabicEncodedString(departmentVO.getDepartmentNameNLS())));
			} else {
				departmentListSuperNLS.add(new SelectItem(departmentVO.getId(), departmentVO.getDepartmentName()));
			}

		}
		
		usersList = new ArrayList<SelectItem>();
		usersListNLS = new ArrayList<SelectItem>();
		List<UserVO> userTempList = new ArrayList<UserVO>(referenceCacheBean.getUserMap().values());
		for(UserVO userVO : userTempList){
			usersList.add(new SelectItem(userVO.getUserId(),userVO.getUserName()));
		}

	}

	/** LOAD REFERENCE DATA SECTION **/

	public void loadReferenceData() {
		Long currentRole = null;

		/**
		 * IF USER IS LOGGED IN THEN LOAD DATA BASED ON HIS DIRECTORATE AND
		 * GOVERNORATE
		 **/
		if (loginManagedBean != null && loginManagedBean.isAuthorized()) {
			currentRole = loginManagedBean.getCurrentUser().getRoleVO().getRoleId();
			if (currentRole.equals(EABConstants.ROLE_SUPER_ADMIN)) {
				loadDataForSuperUser();
			}

			if (currentRole.equals(EABConstants.ROLE_ADMIN)) {
				Long dirId = loginManagedBean.getCurrentUser().getDirectorateVO().getId();
				Long govId = loginManagedBean.getCurrentUser().getGovernarateVO().getId();
				loadDataForNormalUser(govId, dirId);
			}

		}

		/** IF USER IS NOT LOGGED IN THEN LOAD DATA FROM SAVED COOKIES **/
		if (loginManagedBean == null || !loginManagedBean.isAuthorized()) {
			Long[] ids = getUserSelectedCookies();
			if (ids != null && ids[0] != null && ids[1] != null)
				loadDataForNormalUser(ids[1], ids[0]);
		}

	}

	public void loadDataForSuperUser() {

		/** This is the data which is different for super and normal users **/

		setGovernrateList(governrateListSuper);
		setGovernrateListNLS(governrateListSuperNLS);

		setDirectorateList(directorateListSuper);
		setDirectorateListNLS(directorateListSuperNLS);

		setSectionList(sectionListSuper);
		setSectionListNLS(sectionListSuperNLS);

		setDepartmentList(departmentListSuper);
		setDepartmentListNLS(departmentListSuperNLS);
	}

	public void loadDataForNormalUser(Long govId, Long dirId) {

		// populateCommonItems();

		// WE WILL ONLY SHOW SINGLE GOV AND DIR FOR USR OTHER THAN SUPER ADMIN

		GovernarateVO governarateVO = referenceCacheBean.getGovernrateMap().get(govId);
		governrateListNormal = new ArrayList<SelectItem>();
		governrateListNormalNLS = new ArrayList<SelectItem>();
		governrateListNormal.add(new SelectItem(governarateVO.getId(), governarateVO.getGovName()));
		governrateListNormalNLS.add(new SelectItem(governarateVO.getId(), ArabicConversion.getArabicEncodedString(governarateVO.getGovNameNLS())));
		setGovernrateList(governrateListNormal);
		setGovernrateListNLS(governrateListNormalNLS);

		DirectorateVO dirVO = referenceCacheBean.getDirectorateMap().get(dirId);
		directorateListNormal = new ArrayList<SelectItem>();
		directorateListNormalNLS = new ArrayList<SelectItem>();
		directorateListNormal.add(new SelectItem(dirVO.getId(), dirVO.getDirecotrateName()));
		directorateListNormalNLS.add(new SelectItem(dirVO.getId(), ArabicConversion.getArabicEncodedString(dirVO.getDirecotrateNameNLS())));
		setDirectorateList(directorateListNormal);
		setDirectorateListNLS(directorateListNormalNLS);

		// if (!(loginManagedBean.isAuthorized() && sectionList != null)) {
		List<SectionVO> secList = new ArrayList<SectionVO>();
		try {
			// secList =
			// referenceCacheBean.getReferenceDataManager().getSectionsOfDirAndGovAndDept(govId,
			// dirId, null, null);
			List<SectionVO> secListSuper = new ArrayList<SectionVO>(referenceCacheBean.getSectionMap().values());
			for (SectionVO secVO : secListSuper) {
				if (secVO.getActive().equals("Y") && secVO.getGovernarateVO() != null && secVO.getGovernarateVO().getId() != null && secVO.getGovernarateVO().getId().equals(govId)) {
					secList.add(secVO);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		sectionList = new ArrayList<SelectItem>();
		sectionListNLS = new ArrayList<SelectItem>();
		for (SectionVO sectionVO : secList) {
			sectionList.add(new SelectItem(sectionVO.getId(), sectionVO.getSectionName()));
			sectionListNLS.add(new SelectItem(sectionVO.getId(), ArabicConversion.getArabicEncodedString(sectionVO.getSectionNameNLS())));
		}

		// }

		// if (!(loginManagedBean.isAuthorized() && departmentList != null)) {
		List<DepartmentVO> deptist = new ArrayList<DepartmentVO>();
		try {

			// deptist =
			// referenceCacheBean.getReferenceDataManager().getDepartmentOfDirAndGovAndSec(govId,
			// dirId, null);
			List<DepartmentVO> deptListSuper = new ArrayList<DepartmentVO>(referenceCacheBean.getDepartmentMap().values());
			for (DepartmentVO deptVO : deptListSuper) {
				if (deptVO.getActive().equals("Y") && deptVO.getDirectorateVO() != null && deptVO.getDirectorateVO().getId() != null && deptVO.getDirectorateVO().getId().equals(dirId)) {
					
					deptist.add(deptVO);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		departmentList = new ArrayList<SelectItem>();
		departmentListNLS = new ArrayList<SelectItem>();
		for (DepartmentVO departmentVO : deptist) {
			departmentList.add(new SelectItem(departmentVO.getId(), departmentVO.getDepartmentName()));
			departmentListNLS.add(new SelectItem(departmentVO.getId(), ArabicConversion.getArabicEncodedString(departmentVO.getDepartmentNameNLS())));
		}
		// }

		// //////////////////////////////////////////////////////////

		// if (!(loginManagedBean.isAuthorized() && departmentList != null)) {

		List<InstitutionVO> instList = new ArrayList<InstitutionVO>();
		try {
			GovernarateVO govVO = referenceCacheBean.getGovernrateMap().get(govId);
			List<InstitutionVO> instListSuper = new ArrayList<InstitutionVO>(referenceCacheBean.getInstituteMap().values());
			for (InstitutionVO instVO : instListSuper) {
				if (govVO != null && govVO.getRegionCode() != null && instVO.getRegCode() != null && instVO.getRegCode().equals(govVO.getRegionCode())) {
					instList.add(instVO);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		instituteList = new ArrayList<SelectItem>();
		instituteListNLS = new ArrayList<SelectItem>();
		for (InstitutionVO instVO : instList) {

			instituteList.add(new SelectItem(instVO.getInstCode(), instVO.getInstName()));
			if (instVO.getInstNameNLS() == null || instVO.getInstNameNLS().isEmpty()) {
				instituteListNLS.add(new SelectItem(instVO.getInstCode(), instVO.getInstName()));
			} else {
				instituteListNLS.add(new SelectItem(instVO.getInstCode(), ArabicConversion.getArabicEncodedString(instVO.getInstNameNLS())));
			}

		}
		// }

	}

	public void loadDepartmentsForSuperUser(Long secId) {

	}

	public void loadDepartmentsForNormalUser(Long currentRole, Long secId) {

	}

	public Long[] getUserSelectedCookies() {
		Map<String, Object> requestCookieMap = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
		Long[] ids = new Long[2];
		Cookie directorateCookie = (Cookie) requestCookieMap.get(EABConstants.DIRECTORATE_COOKIE);
		Cookie governorateCookie = (Cookie) requestCookieMap.get(EABConstants.GOVERNORATE_COOKIE);
		if (directorateCookie != null && directorateCookie.getValue() != null) {
			Long directorateId = Long.valueOf(directorateCookie.getValue());
			ids[0] = directorateId;
		}
		if (governorateCookie != null && governorateCookie.getValue() != null) {
			Long govId = Long.valueOf(governorateCookie.getValue());
			ids[1] = govId;
		}
		return ids;
	}

	public void resetCache() {
		referenceCacheBean.loadCacheData();
		initializeSelectItems();
		loadReferenceData();
	}

	public List<SelectItem> getGovernrateList() {
		return governrateList;
	}

	public void setGovernrateList(List<SelectItem> governrateList) {
		this.governrateList = governrateList;
	}

	public List<SelectItem> getDirectorateList() {
		return directorateList;
	}

	public void setDirectorateList(List<SelectItem> directorateList) {
		this.directorateList = directorateList;
	}

	public List<SelectItem> getSectionList() {
		return sectionList;
	}

	public void setSectionList(List<SelectItem> sectionList) {
		this.sectionList = sectionList;
	}

	public List<SelectItem> getInstituteList() {
		return instituteList;
	}

	public void setInstituteList(List<SelectItem> instituteList) {
		this.instituteList = instituteList;
	}

	public List<SelectItem> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<SelectItem> departmentList) {
		this.departmentList = departmentList;
	}

	public List<SelectItem> getGovernrateListNLS() {
		return governrateListNLS;
	}

	public void setGovernrateListNLS(List<SelectItem> governrateListNLS) {
		this.governrateListNLS = governrateListNLS;
	}

	public List<SelectItem> getDirectorateListNLS() {
		return directorateListNLS;
	}

	public void setDirectorateListNLS(List<SelectItem> directorateListNLS) {
		this.directorateListNLS = directorateListNLS;
	}

	public List<SelectItem> getSectionListNLS() {
		return sectionListNLS;
	}

	public void setSectionListNLS(List<SelectItem> sectionListNLS) {
		this.sectionListNLS = sectionListNLS;
	}

	public List<SelectItem> getInstituteListNLS() {
		return instituteListNLS;
	}

	public void setInstituteListNLS(List<SelectItem> instituteListNLS) {
		this.instituteListNLS = instituteListNLS;
	}

	public List<SelectItem> getDepartmentListNLS() {
		return departmentListNLS;
	}

	public void setDepartmentListNLS(List<SelectItem> departmentListNLS) {
		this.departmentListNLS = departmentListNLS;
	}

	public List<SelectItem> getGovernrateListSuper() {
		return governrateListSuper;
	}

	public void setGovernrateListSuper(List<SelectItem> governrateListSuper) {
		this.governrateListSuper = governrateListSuper;
	}

	public List<SelectItem> getDirectorateListSuper() {
		return directorateListSuper;
	}

	public void setDirectorateListSuper(List<SelectItem> directorateListSuper) {
		this.directorateListSuper = directorateListSuper;
	}

	public List<SelectItem> getGovernrateListNormal() {
		return governrateListNormal;
	}

	public void setGovernrateListNormal(List<SelectItem> governrateListNormal) {
		this.governrateListNormal = governrateListNormal;
	}

	public List<SelectItem> getDirectorateListNormal() {
		return directorateListNormal;
	}

	public void setDirectorateListNormal(List<SelectItem> directorateListNormal) {
		this.directorateListNormal = directorateListNormal;
	}

	public List<SelectItem> getGovernrateListNormalNLS() {
		return governrateListNormalNLS;
	}

	public void setGovernrateListNormalNLS(List<SelectItem> governrateListNormalNLS) {
		this.governrateListNormalNLS = governrateListNormalNLS;
	}

	public List<SelectItem> getDirectorateListNormalNLS() {
		return directorateListNormalNLS;
	}

	public void setDirectorateListNormalNLS(List<SelectItem> directorateListNormalNLS) {
		this.directorateListNormalNLS = directorateListNormalNLS;
	}

	public List<SelectItem> getGovernrateListSuperNLS() {
		return governrateListSuperNLS;
	}

	public void setGovernrateListSuperNLS(List<SelectItem> governrateListSuperNLS) {
		this.governrateListSuperNLS = governrateListSuperNLS;
	}

	public List<SelectItem> getDirectorateListSuperNLS() {
		return directorateListSuperNLS;
	}

	public void setDirectorateListSuperNLS(List<SelectItem> directorateListSuperNLS) {
		this.directorateListSuperNLS = directorateListSuperNLS;
	}

	public UserVO getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(UserVO currentUser) {
		this.currentUser = currentUser;
	}

	public LoginManagedBean getLoginManagedBean() {
		return loginManagedBean;
	}

	public void setLoginManagedBean(LoginManagedBean loginManagedBean) {
		this.loginManagedBean = loginManagedBean;
	}

	public List<SelectItem> getDesignations() {
		return designations;
	}

	public void setDesignations(List<SelectItem> designations) {
		this.designations = designations;
	}

	public List<SelectItem> getIsdSelectItems() {
		return isdSelectItems;
	}

	public void setIsdSelectItems(List<SelectItem> isdSelectItems) {
		this.isdSelectItems = isdSelectItems;
	}

	public List<SelectItem> getDesignationsNLS() {
		return designationsNLS;
	}

	public void setDesignationsNLS(List<SelectItem> designationsNLS) {
		this.designationsNLS = designationsNLS;
	}

	public List<SelectItem> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<SelectItem> roleList) {
		this.roleList = roleList;
	}

	public List<SelectItem> getRoleListNLS() {
		return roleListNLS;
	}

	public void setRoleListNLS(List<SelectItem> roleListNLS) {
		this.roleListNLS = roleListNLS;
	}

	public ReferenceCacheBean getReferenceCacheBean() {
		return referenceCacheBean;
	}

	public void setReferenceCacheBean(ReferenceCacheBean referenceCacheBean) {
		this.referenceCacheBean = referenceCacheBean;
	}

	public List<SelectItem> getSectionListSuper() {
		return sectionListSuper;
	}

	public void setSectionListSuper(List<SelectItem> sectionListSuper) {
		this.sectionListSuper = sectionListSuper;
	}

	public List<SelectItem> getDepartmentListSuper() {
		return departmentListSuper;
	}

	public void setDepartmentListSuper(List<SelectItem> departmentListSuper) {
		this.departmentListSuper = departmentListSuper;
	}

	public List<SelectItem> getSectionListSuperNLS() {
		return sectionListSuperNLS;
	}

	public void setSectionListSuperNLS(List<SelectItem> sectionListSuperNLS) {
		this.sectionListSuperNLS = sectionListSuperNLS;
	}

	public List<SelectItem> getDepartmentListSuperNLS() {
		return departmentListSuperNLS;
	}

	public void setDepartmentListSuperNLS(List<SelectItem> departmentListSuperNLS) {
		this.departmentListSuperNLS = departmentListSuperNLS;
	}

	
	public List<SelectItem> getCategoryList() {
		return categoryList;
	}

	
	public void setCategoryList(List<SelectItem> categoryList) {
		this.categoryList = categoryList;
	}

	/**
	 * @return the usersList
	 */
	public List<SelectItem> getUsersList() {
		return usersList;
	}

	/**
	 * @param usersList the usersList to set
	 */
	public void setUsersList(List<SelectItem> usersList) {
		this.usersList = usersList;
	}

	/**
	 * @return the usersListNLS
	 */
	public List<SelectItem> getUsersListNLS() {
		return usersListNLS;
	}

	/**
	 * @param usersListNLS the usersListNLS to set
	 */
	public void setUsersListNLS(List<SelectItem> usersListNLS) {
		this.usersListNLS = usersListNLS;
	}
	

}
