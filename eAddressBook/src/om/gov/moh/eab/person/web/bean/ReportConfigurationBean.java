package om.gov.moh.eab.person.web.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.event.SelectEvent;

import om.gov.moh.eab.abstracts.bean.AbstractMB;
import om.gov.moh.eab.entity.vo.EntityVO;
import om.gov.moh.eab.master.vo.DepartmentVO;
import om.gov.moh.eab.master.vo.DesignationVO;
import om.gov.moh.eab.master.vo.DirectorateVO;
import om.gov.moh.eab.master.vo.GovernarateVO;
import om.gov.moh.eab.master.vo.SectionVO;
import om.gov.moh.eab.masterData.bo.MasterDataManager;
import om.gov.moh.eab.refdata.bean.ReferenceCacheBean;
import om.gov.moh.eab.refdata.bean.ReferenceDataBean;
import om.gov.moh.eab.security.vo.InstitutionVO;
import om.gov.moh.eab.security.vo.UserVO;
import om.gov.moh.eab.security.web.bean.LanguageBean;
import om.gov.moh.eab.security.web.bean.LoginManagedBean;
import om.gov.moh.eab.utils.ArabicConversion;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

@ManagedBean
@ViewScoped
public class ReportConfigurationBean extends AbstractMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UserVO loggedUser;
	private GovernarateVO governarateVO;
	private DirectorateVO directorateVO;
	private DepartmentVO departmentVO;
	private SectionVO sectionVO;
	private Long dirId;
	private EntityVO entityObj = new EntityVO();
    	
	
	private List<DepartmentVO> deptList = new ArrayList<>();
	
	private List<SectionVO> sectionList = new ArrayList<>();
	
	private List<DesignationVO> designationList = new ArrayList<DesignationVO>();
	
	@ManagedProperty(value = "#{referenceDataBean}")
	private ReferenceDataBean referenceDataBean;
	
	@ManagedProperty(value = "#{referenceCacheBean}")
	private ReferenceCacheBean referenceCacheBean;

	
	@ManagedProperty(value = "#{languageBean}")
	private LanguageBean languageBean;
	
	@ManagedProperty(value = "#{loginManagedBean}")
	private LoginManagedBean loginManagedBean;
	
	@ManagedProperty(value = "#{masterDataManager}")
	private MasterDataManager masterDataManager;
	
	@PostConstruct
	public void loadReferenceData() {
		try {
			if (loggedUser == null || loggedUser.getRoleVO() == null || loggedUser.getRoleVO().getRoleId() == null) {
				setLoggedUser();
			}
			referenceDataBean.loadReferenceData();
			directorateVO = new DirectorateVO();
			governarateVO = new GovernarateVO();
			sectionVO = new SectionVO();
			departmentVO = new DepartmentVO();
		} catch (Exception e) {
			
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

			

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Inside setLoggedUser....................... " + e.getMessage());
		}

	}
	
	
	public void redirectToConfigureReport() {
		try {

			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

			String contextPath = ctx.getContextPath();
			FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + "/pages/master/configureReport.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void redirectToSortSection() {
		try {

			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

			String contextPath = ctx.getContextPath();
			FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + "/pages/master/sectionSort.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void redirectToSortDesignation() {
		try {

			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

			String contextPath = ctx.getContextPath();
			FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + "/pages/master/designationSort.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public void searchDepartments(){
		/**
		 * Here  We are making directorate mandatory   
		 */
		boolean validate = false;
		if(this.directorateVO != null && this.directorateVO.getId() != null && this.directorateVO.getId() >0){
			validate = true;
		}
		if(validate){
			DepartmentVO departmentVO = new DepartmentVO();
			/*if(governarateVO != null && governarateVO.getId()!= null && governarateVO.getId()>0){
				departmentVO.setGovernarateVO(governarateVO);
			}*/
			
				departmentVO.setDirectorateVO(directorateVO);
			 try {
				   this.deptList = masterDataManager.searchDepartments(departmentVO, loggedUser, 0, 0, null	, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		else {
			displayErrorMessageToUser("personadd.selectdir");
		}
		
			
		
		
	}
	
	public void searchSections(){
		
		boolean validate = false;
		
		if(this.directorateVO != null && this.directorateVO.getId() != null && this.directorateVO.getId() >0){
			validate = true;
		}
		
		if(validate){
			
			//SectionVO sectionVO = new SectionVO();
			//DepartmentVO depVO = new DepartmentVO();
			//depVO.setId(departmentId);
			//sectionVO.setDepartmentVO(depVO);
			
			
			try {
				this.sectionList = masterDataManager.searchSections(this.sectionVO, loggedUser, 0, 0, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		else {
			displayErrorMessageToUser("personadd.selectdir");
		}
		
	}
	
	
	public void searchDesignations(){
		
		/**
		 * To search the designations, First we will find the entityCode base on the combination of Governorate,Directorate,Department and section selected by the user.
		 * Then we will load the designations of those persons  having that Entity code.
		 */
		
		Long dirId = null; Long govId = null; Long depId = null; Long secId = null;
		
		
		
		if(this.directorateVO != null && this.directorateVO.getId() != null && this.directorateVO.getId() >0){
			dirId = this.directorateVO.getId();
			//System.out.println("this.directorateVO.getId():"+dirId);
			
		}
		
		if(this.governarateVO != null && this.governarateVO.getId() != null && this.governarateVO.getId() >0){
			govId = this.governarateVO.getId();
			//System.out.println("govId:"+govId);
		}
		
		if(this.departmentVO != null && this.departmentVO.getId() != null && this.departmentVO.getId() >0 ){
			depId = this.departmentVO.getId();
			//System.out.println("depId"+depId);
			
		}
		
		if(this.sectionVO != null && this.sectionVO.getId() != null && this.sectionVO.getId() >0 ){
			secId = this.sectionVO.getId();
			//System.out.println("secId"+secId);
			
		}
		
		// To get designations based on an entity, first we will find the entity code based on the criteria he applies.
		
		try {
			 this.entityObj = masterDataManager.findEntityByCombination(govId, dirId, depId, secId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(entityObj != null){
			
			//System.out.println("Entity Code is:"+entityObj.getEntityCode());

			this.designationList = new ArrayList<DesignationVO>();
			
			try {
				/**
				 * Fetching the Persons Designations in that Entity Code
				 */
				this.designationList = masterDataManager.findDesignationsByEntityCode(entityObj.getEntityCode());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else{
			this.designationList = new ArrayList<DesignationVO>();
		}
					
		
	}
	
	
	public void onChangeOfDirCombo(Long dirId) {
		
		
		this.dirId = dirId;
		
		List<DepartmentVO> depList = new  ArrayList<DepartmentVO>();
		List<DepartmentVO> deptListSuper = new ArrayList<DepartmentVO>(referenceCacheBean.getDepartmentMap().values());
		for (DepartmentVO deptVO : deptListSuper) {
			if (deptVO.getDirectorateVO() != null && (deptVO.getDirectorateVO().getId() != null && deptVO.getDirectorateVO().getId().equals(dirId)) || (deptVO.getIsCommonDept() != null && deptVO.getIsCommonDept().equals("Y"))) {
				depList.add(deptVO);
			}
		}

		referenceDataBean.departmentList = new ArrayList<SelectItem>();
		referenceDataBean.departmentListNLS = new ArrayList<SelectItem>();
		for (DepartmentVO deptVO : depList) {
			referenceDataBean.departmentList.add(new SelectItem(deptVO.getId(), deptVO.getDepartmentName()));
			referenceDataBean.departmentListNLS.add(new SelectItem(deptVO.getId(), ArabicConversion.getArabicEncodedString(deptVO.getDepartmentNameNLS())));
		}

		List<SectionVO> secList = new ArrayList<SectionVO>();
		List<SectionVO> sectionSuperList = new ArrayList<SectionVO>(referenceCacheBean.getSectionMap().values());
		for (SectionVO secVO : sectionSuperList) {
			if (secVO.getDirectorateVO() != null && secVO.getDirectorateVO().getId().equals(dirId)) {
				secList.add(secVO);
			}
		}
		
	}
	
	private Long govId;
	public void onChangeOfGovCombo(Long govId) {
		this.govId = govId;

		List<DirectorateVO> dirList = new ArrayList<DirectorateVO>();
		List<DirectorateVO> dirListSuper = new ArrayList<DirectorateVO>(referenceCacheBean.getDirectorateMap().values());
		for(DirectorateVO dirVO:dirListSuper){
			if(dirVO.getRegionCode()!=null && dirVO.getRegionCode().equals(govId)){
				dirList.add(dirVO);
			}
		}
		
		referenceDataBean.directorateList = new ArrayList<SelectItem>();
		 referenceDataBean.directorateListNLS = new ArrayList<SelectItem>();
		 for (DirectorateVO dirVO : dirList) {
		 referenceDataBean.directorateList.add(new SelectItem(dirVO.getId(), dirVO.getDirecotrateName()));
		 referenceDataBean.directorateListNLS.add(new SelectItem(dirVO.getId(),ArabicConversion.getArabicEncodedString(dirVO.getDirecotrateNameNLS())));
		 }
		
	}
	
	
	public void saveSortedDepartments(){
		
		/**
		 * In the view configureReport.xhtml , we use p:orderList which automatically converts its binding list to List Of strings.
		 * So here this.deptList which was a List<DepartmentVo> will become a List<String> 
		 * 
		 */
		
		List<String> sortingDepts = new ArrayList<String>();
		for(int i=0;i<this.deptList.size();i++){
			//System.out.println("this.deptList.indexOf(i):"+this.deptList.get(i)+"At index:"+i);
			sortingDepts.add(this.deptList.get(i)+"");
		}
		if(this.departmentVO== null)
			this.departmentVO = new DepartmentVO();
		 if(this.directorateVO != null && this.directorateVO.getId()!= null && this.directorateVO.getId()>0)
			 this.departmentVO.setDirectorateVO(this.directorateVO);
		 if(this.governarateVO != null && this.governarateVO.getId()!= null && this.governarateVO.getId()>0)
			 this.departmentVO.setGovernarateVO(this.governarateVO);
		
		try {
		     /**
		      * Sort order will be in the order of the OrderList . ie First element in the p:orderList will be having sorter_order 0 , that of 2nd will b 1 and so on
		      * Later in while querying , we will fetch it by sortOrder ascending.
		      * 
		      */
			 masterDataManager.SaveDepartmentSortOrder(sortingDepts);
			 /**
			  * Since  this.deptList is now a List<String>, we need to query it again to show it in the page as List<DepartmentVO>
			  */
			 this.deptList = masterDataManager.searchDepartments(this.departmentVO, loggedUser, 0, 0, null	, null);
		     displayInfoMessageToUser("dept.order.sort.success");
		 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
public void saveSortedSections(){
		
		List<String> sortingSections = new ArrayList<String>();
		for(int i=0;i<this.sectionList.size();i++){
			System.out.println("this.sectionList.indexOf(i):"+this.sectionList.get(i)+"At index:"+i);
			sortingSections.add(this.sectionList.get(i)+"");
		}
		
		try {
		
			 masterDataManager.SaveSectionSortOrder(sortingSections);
			   displayInfoMessageToUser("sect.order.sort.success");
			//this.deptList = masterDataManager.searchDepartments(new DepartmentVO(), loggedUser, 0, 0, null	, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


  public void saveSortedDesignations(){
	  List<String> sortingDesignations = new ArrayList<String>();
	  
	  for(int i=0;i<this.designationList.size();i++){
		  System.out.println("this.designationList.indexOf(i):"+this.designationList.get(i)+"At index:"+i);
		  sortingDesignations.add(this.designationList.get(i)+"");
	  }
	  System.out.println("Entity Code:"+this.entityObj.getEntityCode());
	  
	 // save sortOrder in the table EAB_PERSON_SORT_ORDER . 
	 try {
		masterDataManager.saveDesignationSortOrder(this.entityObj.getEntityCode(), sortingDesignations);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	 try {
			designationList = masterDataManager.findDesignationsByEntityCode(entityObj.getEntityCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	  
  }
	
	
	
	/**
	 * @return the loggedUser
	 */
	public UserVO getLoggedUser() {
		return loggedUser;
	}

	/**
	 * @param loggedUser the loggedUser to set
	 */
	public void setLoggedUser(UserVO loggedUser) {
		this.loggedUser = loggedUser;
	}

	/**
	 * @return the governarateVO
	 */
	public GovernarateVO getGovernarateVO() {
		return governarateVO;
	}

	/**
	 * @param governarateVO the governarateVO to set
	 */
	public void setGovernarateVO(GovernarateVO governarateVO) {
		this.governarateVO = governarateVO;
	}

	/**
	 * @return the directorateVO
	 */
	public DirectorateVO getDirectorateVO() {
		return directorateVO;
	}

	/**
	 * @param directorateVO the directorateVO to set
	 */
	public void setDirectorateVO(DirectorateVO directorateVO) {
		this.directorateVO = directorateVO;
	}

	/**
	 * @return the referenceDataBean
	 */
	public ReferenceDataBean getReferenceDataBean() {
		return referenceDataBean;
	}

	/**
	 * @param referenceDataBean the referenceDataBean to set
	 */
	public void setReferenceDataBean(ReferenceDataBean referenceDataBean) {
		this.referenceDataBean = referenceDataBean;
	}

	/**
	 * @return the languageBean
	 */
	public LanguageBean getLanguageBean() {
		return languageBean;
	}

	/**
	 * @param languageBean the languageBean to set
	 */
	public void setLanguageBean(LanguageBean languageBean) {
		this.languageBean = languageBean;
	}

	/**
	 * @return the loginManagedBean
	 */
	public LoginManagedBean getLoginManagedBean() {
		return loginManagedBean;
	}

	/**
	 * @param loginManagedBean the loginManagedBean to set
	 */
	public void setLoginManagedBean(LoginManagedBean loginManagedBean) {
		this.loginManagedBean = loginManagedBean;
	}

	/**
	 * @return the masterDataManager
	 */
	public MasterDataManager getMasterDataManager() {
		return masterDataManager;
	}

	/**
	 * @param masterDataManager the masterDataManager to set
	 */
	public void setMasterDataManager(MasterDataManager masterDataManager) {
		this.masterDataManager = masterDataManager;
	}

	/**
	 * @return the deptList
	 */
	public List<DepartmentVO> getDeptList() {
		return deptList;
	}

	/**
	 * @param deptList the deptList to set
	 */
	public void setDeptList(List<DepartmentVO> deptList) {
		this.deptList = deptList;
	}

	/**
	 * @return the sectionList
	 */
	public List<SectionVO> getSectionList() {
		return sectionList;
	}

	/**
	 * @param sectionList the sectionList to set
	 */
	public void setSectionList(List<SectionVO> sectionList) {
		this.sectionList = sectionList;
	}

	/**
	 * @return the departmentVO
	 */
	public DepartmentVO getDepartmentVO() {
		return departmentVO;
	}

	/**
	 * @param departmentVO the departmentVO to set
	 */
	public void setDepartmentVO(DepartmentVO departmentVO) {
		this.departmentVO = departmentVO;
	}

	/**
	 * @return the sectionVO
	 */
	public SectionVO getSectionVO() {
		return sectionVO;
	}

	/**
	 * @param sectionVO the sectionVO to set
	 */
	public void setSectionVO(SectionVO sectionVO) {
		this.sectionVO = sectionVO;
	}

	/**
	 * @return the referenceCacheBean
	 */
	public ReferenceCacheBean getReferenceCacheBean() {
		return referenceCacheBean;
	}

	/**
	 * @param referenceCacheBean the referenceCacheBean to set
	 */
	public void setReferenceCacheBean(ReferenceCacheBean referenceCacheBean) {
		this.referenceCacheBean = referenceCacheBean;
	}

	/**
	 * @return the dirId
	 */
	public Long getDirId() {
		return dirId;
	}

	/**
	 * @param dirId the dirId to set
	 */
	public void setDirId(Long dirId) {
		this.dirId = dirId;
	}

	/**
	 * @return the govId
	 */
	public Long getGovId() {
		return govId;
	}

	/**
	 * @param govId the govId to set
	 */
	public void setGovId(Long govId) {
		this.govId = govId;
	}

	/**
	 * @return the designationList
	 */
	public List<DesignationVO> getDesignationList() {
		return designationList;
	}

	/**
	 * @param designationList the designationList to set
	 */
	public void setDesignationList(List<DesignationVO> designationList) {
		this.designationList = designationList;
	}
	
	

}
