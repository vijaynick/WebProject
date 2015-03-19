package om.gov.moh.eab.security.web.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import om.gov.moh.eab.abstracts.bean.AbstractMB;
import om.gov.moh.eab.constants.EABConstants;
import om.gov.moh.eab.security.bo.UserManager;
import om.gov.moh.eab.security.vo.AuditVO;
import om.gov.moh.eab.security.vo.UserVO;
import om.gov.moh.eab.utils.ArabicConversion;
import om.gov.moh.eab.utils.ReportBean;

import org.primefaces.event.TabChangeEvent;

/**
 * 
 * @author Farid Ul Haq
 * 
 */
@ManagedBean(name = "userManagedBean")
@ViewScoped
public class UserManagedBean extends AbstractMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private UserVO userVO = new UserVO();
	private UserVO userAddVO = new UserVO();
	private AuditVO auditVO = new AuditVO();

	private Long tabIndex;
	private String oldPassword;
	private String newPassword;
	private String confirmNewPasswor;

	@ManagedProperty(value = "#{userManager}")
	private UserManager userManager;
	@ManagedProperty(value = "#{loginManagedBean}")
	private LoginManagedBean loginManagedBean;
	@ManagedProperty(value = "#{reportBean}")
	private ReportBean reportBean;
	private String exportOption;

	private List<UserVO> searchedList = new ArrayList<UserVO>();
	private List<AuditVO> searchedAuditList = new ArrayList<AuditVO>();

	@PostConstruct
	public void loadReferenceData() {
		try {
			userVO = new UserVO();
			List<UserVO> list = userManager.searchUsers(userVO);
			setSearchedList(list);
			this.exportOption = EABConstants.DOCX;
		} catch (Exception e) {
			displayErrorMessageToUser("common.error");
			e.printStackTrace();
		}

	}

	public void viewReport() {
		reportBean.setList(searchedList);
		reportBean.setReportName(EABConstants.USERS_REPORT_NAME);
		 Map<String, Object> params=new HashMap<>();
		try {
			switch (exportOption) {
			case EABConstants.PDF:
				reportBean.exportAsPDF(params);
				break;
			case EABConstants.DOCX:
				reportBean.exportAsDOCX(params);
				break;
			case EABConstants.HTML:
				reportBean.exportAsHTML(params);
				break;

			default:
				break;
			}

		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveUser() {
		try {

			if (!userManager.isLoginIdTaken(userAddVO)) {
				userAddVO.setLoginId(userAddVO.getStaffno());
				userAddVO.setPassword(userAddVO.getStaffno());
				userManager.saveUser(userAddVO);
				this.userManager.addAuditInfo(new AuditVO(userAddVO.getUserId(), loginManagedBean.getCurrentUser().getUserId(), EABConstants.EAB_CATOGORY_USER, "User " + userAddVO.getUserName() + " has been added", getUserIP()));

				displayInfoMessageToUser("record.saved.successfully");
				resetUser();
				loadReferenceData();
				tabIndex = 0L;
			} else {
				tabIndex = 1L;
				displayErrorMessageToUser("record.user.alreadyexistsloginid");
			}

		} catch (Exception e) {
			displayErrorMessageToUser("common.error");
			e.printStackTrace();
		}
	}

	public void editUser() {
		try {
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			String userId = params.get("userId");
			this.userAddVO = new UserVO();
			this.userAddVO = userManager.findUserById(userId);
		} catch (Exception e) {
			displayErrorMessageToUser("common.error");
			e.printStackTrace();
		}
	}

	public void resetPassword() {
		try {
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			String userId = params.get("userId");
			this.userAddVO = new UserVO();
			this.userAddVO = userManager.findUserById(userId);
			this.userAddVO.setPassword(this.getUserAddVO().getLoginId());
			userManager.saveUser(userAddVO);
			displayInfoMessageToUser("password.reset.successfully");
		} catch (Exception e) {
			displayErrorMessageToUser("common.error");
			e.printStackTrace();
		}
	}

	public void updateUser() {
		try {
			userManager.saveUser(userAddVO);

			this.userManager.addAuditInfo(new AuditVO(userAddVO.getUserId(), loginManagedBean.getCurrentUser().getUserId(), EABConstants.EAB_CATOGORY_USER, "User " + userAddVO.getUserName() + " has been updated", getUserIP()));

			displayInfoMessageToUser("record.saved.successfully");
			loadReferenceData();
			resetUser();
		} catch (Exception e) {
			displayErrorMessageToUser("common.error");
			e.printStackTrace();
		}
	}

	public void setDeleteId(Long Id) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		request.getSession().setAttribute("USER_DELETE_ID", Id);

	}

	public void deleteUser(Long userId) {
		try {
			userAddVO.setUserId(userId);
			userManager.deleteUser(userAddVO);
			this.userManager.addAuditInfo(new AuditVO(userId, loginManagedBean.getCurrentUser().getUserId(), EABConstants.EAB_CATOGORY_USER, "User " + userId + " has been deactivated", getUserIP()));
			displayInfoMessageToUser("record.deleted.successfully");
			resetUser();
			loadReferenceData();
			tabIndex = 0L;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void searchUsers() {
		try {
			setSearchedList(userManager.searchUsers(userVO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resetSearchForm() {
		userVO = new UserVO();
		try {
			setSearchedList(userManager.searchUsers(userVO));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void onTabChange(TabChangeEvent event) {
		try {
			loadReferenceData();
		} catch (Exception e) {
			displayErrorMessageToUser("common.error");
			e.printStackTrace();
		}
	}

	public void resetUser() {

		userAddVO = new UserVO();
		try {
			setSearchedList(userManager.searchUsers(userVO));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void changePassword() {

		if (!oldPassword.equals(loginManagedBean.getCurrentUser().getPassword())) {
			displayErrorMessageToUser("changepassword.oldpassdontmatch");
			return;
		} else if (!newPassword.equals(confirmNewPasswor)) {
			displayErrorMessageToUser("changepassword.confirmpassdontmatch");
			return;
		} else {
			userManager.changePassword(loginManagedBean.getCurrentUser().getLoginId(), newPassword);
			loginManagedBean.getCurrentUser().setPassword(newPassword);
			displayInfoMessageToUser("changepassword.passchangesuccessfully");

		}

	}

	public void redirectToAuditSearch() {
		try {
			// To load the data during the page load we are calling the method
			// loadAuditRefData() in auditSearch.xhtml
			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String contextPath = ctx.getContextPath();
			FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + "/pages/user/auditSearch.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadAuditRefData() {
		try {
			auditVO = new AuditVO();
			auditVO.setUserVO(new UserVO());
			setSearchedAuditList(userManager.searchAudit(auditVO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void searchAudit() {
		try {

			setSearchedAuditList(userManager.searchAudit(auditVO));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resetAuditSearchForm() {

		auditVO = new AuditVO();
		auditVO.setUserVO(new UserVO());
		try {

			setSearchedAuditList(userManager.searchAudit(auditVO));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public UserVO getUserAddVO() {
		return userAddVO;
	}

	public void setUserAddVO(UserVO userAddVO) {
		this.userAddVO = userAddVO;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public Long getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(Long tabIndex) {
		this.tabIndex = tabIndex;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPasswor() {
		return confirmNewPasswor;
	}

	public void setConfirmNewPasswor(String confirmNewPasswor) {
		this.confirmNewPasswor = confirmNewPasswor;
	}

	public LoginManagedBean getLoginManagedBean() {
		return loginManagedBean;
	}

	public void setLoginManagedBean(LoginManagedBean loginManagedBean) {
		this.loginManagedBean = loginManagedBean;
	}

	public List<UserVO> getSearchedList() {
		return searchedList;
	}

	public void setSearchedList(List<UserVO> searchedList) {
		this.searchedList = searchedList;
	}

	/**
	 * @return the auditVO
	 */
	public AuditVO getAuditVO() {
		return auditVO;
	}

	/**
	 * @param auditVO
	 *            the auditVO to set
	 */
	public void setAuditVO(AuditVO auditVO) {
		this.auditVO = auditVO;
	}

	/**
	 * @return the searchedAuditList
	 */
	public List<AuditVO> getSearchedAuditList() {
		return searchedAuditList;
	}

	/**
	 * @param searchedAuditList
	 *            the searchedAuditList to set
	 */
	public void setSearchedAuditList(List<AuditVO> searchedAuditList) {
		this.searchedAuditList = searchedAuditList;
	}

	public ReportBean getReportBean() {
		return reportBean;
	}

	public void setReportBean(ReportBean reportBean) {
		this.reportBean = reportBean;
	}

	public String getExportOption() {
		return exportOption;
	}

	public void setExportOption(String exportOption) {
		this.exportOption = exportOption;
	}

}