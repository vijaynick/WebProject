package om.gov.moh.eab.search.web.action;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;

import om.gov.moh.eab.constants.EABConstants;
import om.gov.moh.eab.entity.vo.EntityVO;
import om.gov.moh.eab.entity.web.bean.EntityManagedBean;
import om.gov.moh.eab.master.vo.DepartmentVO;
import om.gov.moh.eab.master.vo.DirectorateVO;
import om.gov.moh.eab.master.vo.GovernarateVO;
import om.gov.moh.eab.master.vo.SectionVO;
import om.gov.moh.eab.person.vo.PersonVO;
import om.gov.moh.eab.person.web.bean.PersonManagedBean;
import om.gov.moh.eab.refdata.bean.ReferenceCacheBean;
import om.gov.moh.eab.security.bo.UserManager;
import om.gov.moh.eab.security.vo.InstitutionVO;
import om.gov.moh.eab.security.vo.UserVO;
import om.gov.moh.eab.security.web.bean.LoginManagedBean;
import om.gov.moh.eab.utils.ArabicConversion;

import org.primefaces.component.datagrid.DataGrid;

@ViewScoped
@ManagedBean
public class SearchManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String currentSearch;
	private String searchType;
	private boolean searchMode;
	private boolean visible;

	private DirectorateVO directorateVO = new DirectorateVO();
	private GovernarateVO governarateVO = new GovernarateVO();
	private InstitutionVO institutionVO = new InstitutionVO();
	private SectionVO sectionVO = new SectionVO();
	private DepartmentVO departmentVO = new DepartmentVO();
	private DataGrid dataGridIMP;
	private DataGrid dataGridEMR;

	@ManagedProperty(value = "#{personManagedBean}")
	private PersonManagedBean personManagedBean;
	@ManagedProperty(value = "#{loginManagedBean}")
	private LoginManagedBean loginManagedBean;
	@ManagedProperty(value = "#{entityManagedBean}")
	private EntityManagedBean entityManagedBean;
	@ManagedProperty(value = "#{userManager}")
	private UserManager userManager;
	@ManagedProperty(value = "#{referenceCacheBean}")
	private ReferenceCacheBean referenceCacheBean;

	@PostConstruct
	public void init() {
		getUserSelectedCookies();
	}

	public void saveUserSelectedCookies() {
		Map<String, Object> props = new HashMap<String, Object>();
		props.put("maxAge", 10 * 365 * 24 * 60 * 60);

		Map<String, Object> requestCookieMap = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
		Cookie directorateCookie = (Cookie) requestCookieMap.get(EABConstants.DIRECTORATE_COOKIE);
		Cookie governorateCookie = (Cookie) requestCookieMap.get(EABConstants.GOVERNORATE_COOKIE);

		if (directorateCookie != null) {
			directorateCookie.setValue(this.getDirectorateVO().getId().toString());
		}

		List<DirectorateVO> directorates =  new ArrayList<DirectorateVO>(referenceCacheBean.getDirectorateMap().values());
		for (DirectorateVO dirVO : directorates) {
			if (dirVO.getId().equals(this.getDirectorateVO().getId())) {
				if (governorateCookie != null) {
					governorateCookie.setValue(dirVO.getRegionCode().toString());					
				}
				this.getGovernarateVO().setId(dirVO.getRegionCode());
			}
		}

		FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(EABConstants.GOVERNORATE_COOKIE, this.getGovernarateVO().getId().toString(), props);
		FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(EABConstants.DIRECTORATE_COOKIE, this.getDirectorateVO().getId().toString(), props);

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/index.jsf");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showModelPanel() {
		visible = true;
	}

	public void getUserSelectedCookies() {
		try {
			if (!loginManagedBean.isAuthorized()) {

				Map<String, Object> requestCookieMap = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
				Cookie directorateCookie = (Cookie) requestCookieMap.get(EABConstants.DIRECTORATE_COOKIE);
				Cookie governorateCookie = (Cookie) requestCookieMap.get(EABConstants.GOVERNORATE_COOKIE);

				if ((directorateCookie != null && directorateCookie.getValue() != null) && (governorateCookie != null && governorateCookie.getValue() != null)) {
					Long directorateId = Long.valueOf(directorateCookie.getValue());
					Long govId = Long.valueOf(governorateCookie.getValue());

					DirectorateVO dirVO = userManager.findDirById(directorateId);
					dirVO.setDirecotrateNameNLS(ArabicConversion.getArabicEncodedString(dirVO.getDirecotrateNameNLS()));
					setDirectorateVO(dirVO);

					GovernarateVO govVO = userManager.findGovById(govId);
					govVO.setGovNameNLS(ArabicConversion.getArabicEncodedString(govVO.getGovNameNLS()));
					setGovernarateVO(govVO);

				} 
			} else {

				UserVO usrVo = userManager.findUserByLoginId(loginManagedBean.getCurrentUser().getLoginId());
				usrVo.getDirectorateVO().setDirecotrateNameNLS(ArabicConversion.getArabicEncodedString(usrVo.getDirectorateVO().getDirecotrateNameNLS()));
				usrVo.getGovernarateVO().setGovNameNLS(ArabicConversion.getArabicEncodedString(usrVo.getGovernarateVO().getGovNameNLS()));
				setDirectorateVO(usrVo.getDirectorateVO());
				setGovernarateVO(usrVo.getGovernarateVO());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void reset() {
		try {
			directorateVO = new DirectorateVO();
			institutionVO = new InstitutionVO();
			sectionVO = new SectionVO();
			departmentVO = new DepartmentVO();
			personManagedBean.setPersonVO(new PersonVO());
			entityManagedBean.setEntityVO(new EntityVO());

			searchMode = false;
			searchType = null;
			dataGridIMP.setFirst(0);
			dataGridIMP.setRows(3);
			dataGridEMR.setFirst(0);
			dataGridEMR.setRows(3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getCurrentSearch() {
		return currentSearch;
	}

	public void setCurrentSearch(String currentSearch) {
		this.currentSearch = currentSearch;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public PersonManagedBean getPersonManagedBean() {
		return personManagedBean;
	}

	public void setPersonManagedBean(PersonManagedBean personManagedBean) {
		this.personManagedBean = personManagedBean;
	}

	public EntityManagedBean getEntityManagedBean() {
		return entityManagedBean;
	}

	public void setEntityManagedBean(EntityManagedBean entityManagedBean) {
		this.entityManagedBean = entityManagedBean;
	}

	public DirectorateVO getDirectorateVO() {
		return directorateVO;
	}

	public void setDirectorateVO(DirectorateVO directorateVO) {
		this.directorateVO = directorateVO;
	}

	public InstitutionVO getInstitutionVO() {
		return institutionVO;
	}

	public void setInstitutionVO(InstitutionVO institutionVO) {
		this.institutionVO = institutionVO;
	}

	public SectionVO getSectionVO() {
		return sectionVO;
	}

	public void setSectionVO(SectionVO sectionVO) {
		this.sectionVO = sectionVO;
	}

	public DepartmentVO getDepartmentVO() {
		return departmentVO;
	}

	public void setDepartmentVO(DepartmentVO departmentVO) {
		this.departmentVO = departmentVO;
	}

	public boolean isSearchMode() {
		return searchMode;
	}

	public void setSearchMode(boolean searchMode) {
		this.searchMode = searchMode;
	}

	public DataGrid getDataGridIMP() {
		return dataGridIMP;
	}

	public void setDataGridIMP(DataGrid dataGridIMP) {
		this.dataGridIMP = dataGridIMP;
	}

	public DataGrid getDataGridEMR() {
		return dataGridEMR;
	}

	public void setDataGridEMR(DataGrid dataGridEMR) {
		this.dataGridEMR = dataGridEMR;
	}

	public GovernarateVO getGovernarateVO() {
		return governarateVO;
	}

	public void setGovernarateVO(GovernarateVO governarateVO) {
		this.governarateVO = governarateVO;
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

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public ReferenceCacheBean getReferenceCacheBean() {
		return referenceCacheBean;
	}

	public void setReferenceCacheBean(ReferenceCacheBean referenceCacheBean) {
		this.referenceCacheBean = referenceCacheBean;
	}

}
