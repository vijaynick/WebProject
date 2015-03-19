package om.gov.moh.eab.entity.web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import om.gov.moh.eab.abstracts.bean.AbstractMB;
import om.gov.moh.eab.constants.EABConstants;
import om.gov.moh.eab.entity.bo.EntityManager;
import om.gov.moh.eab.entity.vo.EntityVO;
import om.gov.moh.eab.master.vo.DepartmentVO;
import om.gov.moh.eab.master.vo.DirectorateVO;
import om.gov.moh.eab.master.vo.SectionVO;
import om.gov.moh.eab.refdata.bean.ReferenceDataBean;
import om.gov.moh.eab.security.bo.UserManager;
import om.gov.moh.eab.security.vo.InstitutionVO;
import om.gov.moh.eab.security.vo.RoleVO;
import om.gov.moh.eab.security.vo.UserVO;
import om.gov.moh.eab.security.web.bean.LoginManagedBean;
import om.gov.moh.eab.utils.ArabicConversion;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

@ManagedBean
@ViewScoped
public class EntityManagedBean extends AbstractMB implements Serializable {

	private static final long serialVersionUID = 1L;
	private EntityVO entityVO = new EntityVO();
	private EntityVO entityAddVO = new EntityVO();

	private UserVO loggedUser;

	private Long tabIndex;
	private Long activeTabMenuIndex;

	@ManagedProperty(value = "#{entityManager}")
	private EntityManager entityManager;
	@ManagedProperty(value = "#{loginManagedBean}")
	private LoginManagedBean loginManagedBean;
	@ManagedProperty(value = "#{userManager}")
	private UserManager userManager;
	@ManagedProperty(value = "#{referenceDataBean}")
	private ReferenceDataBean referenceDataBean;
	private LazyDataModel<EntityVO> configDataModel;

	@PostConstruct
	public void loadReferenceData() {
		try {
			entityVO = new EntityVO();

			if (loggedUser == null || loggedUser.getRoleVO() == null || loggedUser.getRoleVO().getRoleId() == null) {
				setLoggedUser();
			}
			// this.entityBean = entityManager.loadReferenceData(loggedUser);
			//referenceDataBean.loadReferenceData();

			HttpSession httpSession = getHttpSession();
			if (httpSession.getAttribute("ENTITY_EDIT_BEAN") != null) {
				this.entityAddVO = (EntityVO) httpSession.getAttribute("ENTITY_EDIT_BEAN");
			}

		} catch (Exception e) {
			displayErrorMessageToUser("common.error");
			e.printStackTrace();
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
			if (loggedUser == null) {
				loggedUser = new UserVO();
				RoleVO roleVO = new RoleVO();
				roleVO.setRoleId(EABConstants.ROLE_GUEST);
				loggedUser.setRoleVO(roleVO);
				getUserSelectedCookies(loggedUser);
			}

		} catch (Exception e) {
			e.printStackTrace();
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

	public void saveEntity() {
		try {

			if (!entityManager.isSameEntityCombinationExist(entityAddVO)) {

				entityAddVO.setSearchAbleBoolean(entityAddVO.isSearchAbleBooleanCustom());
				entityAddVO.setEmergency(entityAddVO.isEmergencyBoolean() ? "Y" : "N");
				entityAddVO.setSearchAble(entityAddVO.isSearchAbleBoolean() ? "Y" : "N");
				entityAddVO.setActive("Y");
				entityManager.saveEntity(entityAddVO);
				displayInfoMessageToUser("record.saved.successfully");
				resetEntity();
				// loadReferenceData();
				tabIndex = 0L;
			} else {
				tabIndex = 1L;
				displayErrorMessageToUser("record.entity.alreadyexists");
			}

		} catch (Exception e) {
			displayErrorMessageToUser("common.error");
			e.printStackTrace();
		}
	}

	public void editEntity() {
		try {
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			Long entityCode = Long.valueOf(params.get("entityCode"));
			this.entityAddVO = new EntityVO();
			this.entityAddVO = entityManager.findEntityById(entityCode);
			this.entityAddVO.setEntityNameNLS(ArabicConversion.getArabicEncodedString(this.entityAddVO.getEntityNameNLS()));
			this.entityAddVO.setSearchAbleBoolean((this.entityAddVO.getSearchAble() != null && this.entityAddVO.getSearchAble().equals("Y")) ? true : false);
			this.entityAddVO.setSearchAbleBooleanCustom((this.entityAddVO.getSearchAble() != null && this.entityAddVO.getSearchAble().equals("Y")) ? true : false);
			if (entityAddVO.getInstitutionVO() == null) {
				entityAddVO.setInstitutionVO(new InstitutionVO());
			}

			if (entityAddVO.getSectionVO() == null) {
				entityAddVO.setSectionVO(new SectionVO());
			}

			if (entityAddVO.getDepartmentVO() == null) {
				entityAddVO.setDepartmentVO(new DepartmentVO());
			}

			FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/pages/entity/entityAdd.xhtml");

			HttpSession httpSession = getHttpSession();
			httpSession.setAttribute("ENTITY_EDIT_BEAN", entityAddVO);
		} catch (Exception e) {
			displayErrorMessageToUser("common.error");
			e.printStackTrace();
		}
	}

	public void updateEntity() {
		try {

			entityAddVO.setSearchAbleBoolean(entityAddVO.isSearchAbleBooleanCustom());
			entityAddVO.setEmergency(entityAddVO.isEmergencyBoolean() ? "Y" : "N");
			entityAddVO.setSearchAble(entityAddVO.isSearchAbleBoolean() ? "Y" : "N");

			entityManager.saveEntity(entityAddVO);
			displayInfoMessageToUser("record.saved.successfully");
			loadReferenceData();
			resetEntity();
			tabIndex = 0L;
			HttpSession httpSession = getHttpSession();
			httpSession.removeAttribute("ENTITY_EDIT_BEAN");

		} catch (Exception e) {
			displayErrorMessageToUser("common.error");
			e.printStackTrace();
		}
	}

	public void setDeleteId(Long Id) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = request.getSession(false);
		httpSession.setAttribute("ENTITY_DELETE_ID", Id);
	}

	public void deleteEntity(Long entityCode) {
		try {
			entityAddVO.setEntityCode(entityCode);
			entityManager.deleteEntity(entityAddVO);
			displayInfoMessageToUser("record.deleted.successfully");
			resetEntity();
			loadReferenceData();
			tabIndex = 0L;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resetSearchForm() {
		try {
			entityVO = new EntityVO();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void resetEntity() {
		tabIndex = 1L;
		entityAddVO = new EntityVO();
		getHttpSession().removeAttribute("ENTITY_DELETE_ID");
	}

	private List<EntityVO> getArabicConvertedList(List<EntityVO> list) {
		List<EntityVO> entities = new ArrayList<EntityVO>();
		EntityVO vo = null;

		for (EntityVO entityVO : list) {
			vo = new EntityVO();

			vo.setEntityNameNLS(ArabicConversion.getArabicEncodedString(entityVO.getEntityNameNLS()));

			vo.getDirectorateVO().setDirecotrateNameNLS(ArabicConversion.getArabicEncodedString(entityVO.getDirectorateVO().getDirecotrateNameNLS()));

			vo.getGovernarateVO().setGovNameNLS(ArabicConversion.getArabicEncodedString(entityVO.getGovernarateVO().getGovName()));

			vo.getGovernarateVO().setGovName(entityVO.getGovernarateVO().getGovName());
			vo.getDirectorateVO().setDirecotrateName(entityVO.getDirectorateVO().getDirecotrateName());

			if (entityVO.getSectionVO() != null) {
				vo.getSectionVO().setSectionName(entityVO.getSectionVO().getSectionName());
				vo.getSectionVO().setSectionNameNLS(ArabicConversion.getArabicEncodedString(entityVO.getSectionVO().getSectionNameNLS()));
			}

			if (entityVO.getDepartmentVO() != null) {
				vo.getDepartmentVO().setDepartmentNameNLS(ArabicConversion.getArabicEncodedString(entityVO.getDepartmentVO().getDepartmentNameNLS()));
				vo.getDepartmentVO().setDepartmentName(entityVO.getDepartmentVO().getDepartmentName());
			}

			if (entityVO.getInstitutionVO() != null) {
				vo.getInstitutionVO().setInstName(entityVO.getInstitutionVO().getInstName());
				vo.getInstitutionVO().setInstNameNLS(ArabicConversion.getArabicEncodedString(entityVO.getInstitutionVO().getInstNameNLS()));
			}
			vo.setDirectTelNo(entityVO.getDirectTelNo());
			vo.setFaxNo(entityVO.getFaxNo());
			vo.setExtn(entityVO.getExtn());
			vo.setEntityCode(entityVO.getEntityCode());
			vo.setEntityName(entityVO.getEntityName());
			vo.setEmailID(entityVO.getEmailID());
			vo.setSearchAble(entityVO.getSearchAble());
			vo.setEmergency(entityVO.getEmergency());

			entities.add(vo);
		}
		return entities;
	}

	private HttpSession getHttpSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession httpSession = request.getSession(false);
		return httpSession;

	}

	public void OnChangeDropDown() {

		StringBuffer entityName = new StringBuffer("");

		String govName = "";

		govName = referenceDataBean.getReferenceCacheBean().getGovernrateMap().get(entityAddVO.getGovernarateVO().getId()).getGovShortName();

		DirectorateVO directorateVO = referenceDataBean.getReferenceCacheBean().getDirectorateMap().get(entityAddVO.getDirectorateVO().getId());
		if (directorateVO.getDirecotrateShortName() != null && !directorateVO.getDirecotrateShortName().isEmpty()) {
			entityName.append(directorateVO.getDirecotrateShortName());
		}

		if (govName != null && !govName.equals("")) {
			entityName.append("(").append(govName).append(")");
		}

		DepartmentVO departmentVO = referenceDataBean.getReferenceCacheBean().getDepartmentMap().get(entityAddVO.getDepartmentVO().getId());
		if (departmentVO.getDepartmentShortName() != null && !departmentVO.getDepartmentShortName().isEmpty()) {
			entityName.append(" / ");
			entityName.append(departmentVO.getDepartmentShortName());
		}

		SectionVO sectionVO = referenceDataBean.getReferenceCacheBean().getSectionMap().get(this.entityAddVO.getSectionVO().getId());
		if (sectionVO.getSectionShortName() != null && !sectionVO.getSectionShortName().isEmpty()) {
			entityName.append(" / ");
			entityName.append(sectionVO.getSectionShortName());
		}

		this.entityAddVO.setEntityName(entityName.toString());

	}

	@SuppressWarnings("serial")
	public LazyDataModel<EntityVO> getConfigDataModel() {
		if (configDataModel == null) {
			configDataModel = new LazyDataModel<EntityVO>() {

				@Override
				public List<EntityVO> load(int first, int pagesize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {

					List<EntityVO> list = new ArrayList<EntityVO>();
					try {

						if (loggedUser.getRoleVO() == null || loggedUser.getRoleVO().getRoleId() == null) {
							setLoggedUser();
						}

						list = entityManager.searchEntities(entityVO, loggedUser, first, pagesize, null,null);
						list = getArabicConvertedList(list);
						setRowCount(entityManager.countTotalEntities(entityVO, loggedUser));

						setPageSize(pagesize);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return list;
				}

			};

		}

		return configDataModel;
	}

	public EntityVO getEntityVO() {
		return entityVO;
	}

	public void setEntityVO(EntityVO entityVO) {
		this.entityVO = entityVO;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityVO getEntityAddVO() {
		return entityAddVO;
	}

	public void setEntityAddVO(EntityVO entityAddVO) {
		this.entityAddVO = entityAddVO;
	}

	public Long getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(Long tabIndex) {
		this.tabIndex = tabIndex;
	}

	public Long getActiveTabMenuIndex() {
		return activeTabMenuIndex;
	}

	public void setActiveTabMenuIndex(Long activeTabMenuIndex) {
		this.activeTabMenuIndex = activeTabMenuIndex;
	}

	public LoginManagedBean getLoginManagedBean() {
		return loginManagedBean;
	}

	public void setLoginManagedBean(LoginManagedBean loginManagedBean) {
		this.loginManagedBean = loginManagedBean;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public UserVO getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(UserVO loggedUser) {
		this.loggedUser = loggedUser;
	}
	public void setConfigDataModel(LazyDataModel<EntityVO> configDataModel) {
		this.configDataModel = configDataModel;
	}

	public ReferenceDataBean getReferenceDataBean() {
		return referenceDataBean;
	}

	public void setReferenceDataBean(ReferenceDataBean referenceDataBean) {
		this.referenceDataBean = referenceDataBean;
	}

}