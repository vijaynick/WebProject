package om.gov.moh.eab.person.web.bean;

/**
 * @author farid.haq
 *
 */
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;

import net.sf.jasperreports.engine.JRException;
import om.gov.moh.eab.abstracts.bean.AbstractMB;
import om.gov.moh.eab.constants.EABConstants;
import om.gov.moh.eab.entity.vo.EntityVO;
import om.gov.moh.eab.master.vo.DepartmentVO;
import om.gov.moh.eab.master.vo.DesignationVO;
import om.gov.moh.eab.master.vo.DirectorateVO;
import om.gov.moh.eab.master.vo.GovernarateVO;
import om.gov.moh.eab.master.vo.SectionVO;
import om.gov.moh.eab.masterData.bo.MasterDataManager;
import om.gov.moh.eab.person.bo.PersonManager;
import om.gov.moh.eab.person.vo.PersonVO;
import om.gov.moh.eab.refdata.bean.ReferenceCacheBean;
import om.gov.moh.eab.refdata.bean.ReferenceDataBean;
import om.gov.moh.eab.security.bo.UserManager;
import om.gov.moh.eab.security.vo.AuditVO;
import om.gov.moh.eab.security.vo.InstitutionVO;
import om.gov.moh.eab.security.vo.RoleVO;
import om.gov.moh.eab.security.vo.UserVO;
import om.gov.moh.eab.security.web.bean.LanguageBean;
import om.gov.moh.eab.security.web.bean.LoginManagedBean;
import om.gov.moh.eab.utils.ArabicConversion;
import om.gov.moh.eab.utils.ReportBean;

import org.apache.commons.lang.StringUtils;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

@ManagedBean
@ViewScoped
public class PersonManagedBean extends AbstractMB implements Serializable {
	private static final long serialVersionUID = 1L;
	private PersonVO personVO = new PersonVO();
	private PersonVO personAddVO = new PersonVO();
	private List<PersonVO> persons = new ArrayList();
	private String exportOption;
	private boolean isReport;
	List<PersonVO> list = new ArrayList<PersonVO>();

	@ManagedProperty(value = "#{personManager}")
	private PersonManager personManager;
	private UserVO loggedUser;
	@ManagedProperty(value = "#{loginManagedBean}")
	private LoginManagedBean loginManagedBean;
	@ManagedProperty(value = "#{userManager}")
	private UserManager userManager;
	private LazyDataModel<PersonVO> configDataModel;
	@ManagedProperty(value = "#{referenceDataBean}")
	private ReferenceDataBean referenceDataBean;

	@ManagedProperty(value = "#{referenceCacheBean}")
	private ReferenceCacheBean referenceCacheBean;

	@ManagedProperty(value = "#{reportBean}")
	private ReportBean reportBean;

	@ManagedProperty(value = "#{masterDataManager}")
	private MasterDataManager masterDataManager;

	@ManagedProperty(value = "#{languageBean}")
	private LanguageBean languageBean;

	@SuppressWarnings("serial")
	public LazyDataModel<PersonVO> getConfigDataModel() {
		if (configDataModel == null) {
			configDataModel = new LazyDataModel<PersonVO>() {

				@Override
				public List<PersonVO> load(int first, int pagesize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {

					try {
						isReport = false;
						list = personManager.searchPersons(personVO, loggedUser, first, pagesize, null, null, isReport);
						list = getArabicConvertedList(list);
						setRowCount(personManager.countTotalPersons(personVO, loggedUser));
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

	public void setConfigDataModel(LazyDataModel<PersonVO> configDataModel) {
		this.configDataModel = configDataModel;
	}

	@PostConstruct
	public void loadReferenceData() {
		try {
			if (loggedUser == null || loggedUser.getRoleVO() == null || loggedUser.getRoleVO().getRoleId() == null) {
				setLoggedUser();
			}
			referenceDataBean.loadReferenceData();
			personVO = new PersonVO();
			this.exportOption = EABConstants.DOCX;
		} catch (Exception e) {
			// TODO: handle exception
			displayErrorMessageToUser("common.error");
			e.printStackTrace();
			System.err.println("Inside loadReferenceData....................... " + e.getMessage());
		}

	}

	public void viewReport() {

		// Person Report is needed to be shown in a format where on the top of
		// the list, the name of the section is also provided.
		// So we have done some THARIKIDA here by setting the section name as a
		// person name on the top of Persons List.
		// For Person Report , the selection of Governarate and Directorate is
		// mandatory.

		boolean validate = false;
		if (personVO.getEntityVO().getGovernarateVO() == null || personVO.getEntityVO().getGovernarateVO().getId().equals(-1L) || personVO.getEntityVO().getGovernarateVO().getId().equals(-100L)) {
			displayErrorMessageToUser("personadd.selectgov");
			validate = true;

		}

		if (personVO.getEntityVO().getDirectorateVO() == null || personVO.getEntityVO().getDirectorateVO().getId().equals(-1L) || personVO.getEntityVO().getDirectorateVO().getId().equals(-100L)) {
			displayErrorMessageToUser("personadd.selectdir");
			validate = true;
		}

		if (!validate) {

			List<SectionVO> sections = new ArrayList<>();
			try {
				// Fetching all the sections under the selected Directorate.
				sections = masterDataManager.findSectionsByDirId(personVO.getEntityVO().getDirectorateVO().getId());
			} catch (Exception e2) {
				e2.printStackTrace();
			}

			List<PersonVO> mainList = new ArrayList<PersonVO>();
			List<PersonVO> personsList = null;

			// Iterating the Sections
			for (SectionVO sec : sections) {

				personsList = new ArrayList<>();
				personVO.getEntityVO().setSectionVO(sec);
				try {
					// Fetching all the persons under the curent section
					personsList = personManager.searchPersons(personVO, loggedUser, 0, 0, null, null, true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (personsList.size() == 0)
					continue;
				Set<Long> ids = new HashSet<>();
				for (PersonVO person : personsList) {

					if (!StringUtils.isEmpty(person.getPersonNameNLS())) {
						person.setPersonNameNLS(ArabicConversion.getArabicEncodedString(person.getPersonNameNLS()));
					}

					if (person.getDesignationVO() != null && !StringUtils.isEmpty(person.getDesignationVO().getNameNLS())) {
						if (ids.add(person.getDesignationVO().getId()))
							person.getDesignationVO().setNameNLS(ArabicConversion.getArabicEncodedString(person.getDesignationVO().getNameNLS()));
					} else {
						person.setDesignationVO(new DesignationVO());
						person.getDesignationVO().setNameNLS("-");
					}

					if (StringUtils.isEmpty(person.getDesignationVO().getName())) {
						person.getDesignationVO().setName("-");
					}

					if (person.getGsmPrimary() == null) {
						person.setGsmPrimary(1L);
					}

					if (person.getDirectTelNo() == null) {
						person.setDirectTelNo(1L);
					}

					if (person.getFax() == null) {
						person.setFax(1L);
					}

					if (person.getExtn() == null) {
						person.setExtn(1);
					}

				}

				PersonVO topSection = new PersonVO();
				if (languageBean.getLanguage().equals("en")) {
					topSection.setPersonName(sec.getSectionName());
				} else {
					topSection.setPersonNameNLS(ArabicConversion.getArabicEncodedString(sec.getSectionNameNLS()));
				}

				topSection.setPersonDesc("##");
				personsList.add(0, topSection);
				mainList.addAll(personsList);

			}

			// Fetching the list of person under the directorate without having
			// any section

			List<PersonVO> sectionlessPersonsList = new ArrayList<>();
			try {
				sectionlessPersonsList = personManager.searchPersonsWithNoSection(personVO, loggedUser);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			PersonVO topElement = new PersonVO();
			topElement.setPersonName("PERSONS WITHOUT ANY SECTION");
			topElement.setPersonDesc("##");
			if (!languageBean.getLanguage().equals("en")) {

				for (PersonVO item : sectionlessPersonsList) {

					item.setPersonNameNLS(ArabicConversion.getArabicEncodedString(item.getPersonNameNLS()));
					DesignationVO dsg = new DesignationVO();
					if ((item.getDesignationVO() != null) && (item.getDesignationVO().getName() != null)) {
						dsg.setName(item.getDesignationVO().getName());
					}
					if ((item.getDesignationVO() != null) && (item.getDesignationVO().getNameNLS() != null)) {
						dsg.setNameNLS(ArabicConversion.getArabicEncodedString(item.getDesignationVO().getNameNLS()));
					}

					item.setDesignationVO(dsg);
				}

			}

			sectionlessPersonsList.add(0, topElement);
			mainList.addAll(sectionlessPersonsList);

			reportBean.setList(mainList);

			Map<String, Object> params = new HashMap<>();
			if (languageBean.getLanguage().equals("en")) {
				reportBean.setReportName(EABConstants.PERSONS_REPORT_NAME_EN);
				params.put("DIR_NAME", referenceCacheBean.getDirectorateMap().get(personVO.getEntityVO().getDirectorateVO().getId()).getDirecotrateName());
				params.put("GOV_NAME", referenceCacheBean.getGovernrateMap().get(personVO.getEntityVO().getGovernarateVO().getId()).getGovName());
			} else {
				reportBean.setReportName(EABConstants.PERSONS_REPORT_NAME_AR);
				params.put("DIR_NAME", ArabicConversion.getArabicEncodedString(referenceCacheBean.getDirectorateMap().get(personVO.getEntityVO().getDirectorateVO().getId()).getDirecotrateNameNLS()));
				params.put("GOV_NAME", ArabicConversion.getArabicEncodedString(referenceCacheBean.getGovernrateMap().get(personVO.getEntityVO().getGovernarateVO().getId()).getGovNameNLS()));
			}

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

	public void updatePersonRow(RowEditEvent event) {
		try {
			PersonVO vo = (PersonVO) event.getObject();
			// vo.setPersonNameNLS(ArabicConversion.setArabicEncodedString(vo.getPersonNameNLS()));
			vo.setModifiedTime(new Date());
			vo.setModifiedBy(loginManagedBean.getCurrentUser().getUserId());
			this.personManager.updatePersonRow(vo);
			this.userManager.addAuditInfo(new AuditVO(vo.getPersonCode().longValue(), loginManagedBean.getCurrentUser().getUserId(), EABConstants.EAB_CATOGORY_PERSON, "Person " + vo.getPersonName() + " has been Updated", getUserIP()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void savePerson() throws Exception {
		try {

			boolean validate = true;

			if (loginManagedBean.getCurrentUser().getRoleVO().getRoleId().equals(EABConstants.ROLE_ADMIN)) {
				personAddVO.getEntityVO().getGovernarateVO().setId(loginManagedBean.getCurrentUser().getGovernarateVO().getId());
				personAddVO.getEntityVO().getDirectorateVO().setId(loginManagedBean.getCurrentUser().getDirectorateVO().getId());
			} else {

				if (personAddVO.getEntityVO().getGovernarateVO() == null || personAddVO.getEntityVO().getGovernarateVO().getId().equals(-1L) || personAddVO.getEntityVO().getGovernarateVO().getId().equals(-100L)) {
					displayErrorMessageToUser("personadd.selectgov");
					validate = false;
				}

				if (personAddVO.getEntityVO().getDirectorateVO() == null || personAddVO.getEntityVO().getDirectorateVO().getId().equals(-1L) || personAddVO.getEntityVO().getDirectorateVO().getId().equals(-100L)) {
					displayErrorMessageToUser("personadd.selectdir");
					validate = false;
				}

				if (personAddVO.getEntityVO().getDepartmentVO().getId() != null && personAddVO.getEntityVO().getDepartmentVO().getId().equals(-100L)) {
					displayErrorMessageToUser("personadd.selectdept");
					validate = false;
				}

				if (personAddVO.getEntityVO().getSectionVO().getId() != null && personAddVO.getEntityVO().getSectionVO().getId().equals(-100L)) {
					displayErrorMessageToUser("personadd.selectsec");
					validate = false;
				}

			}

			if (validate) {
				if (isStaffNoExist() && validateGsmPrimary()) {
					boolean updatedFlag = false;
					personAddVO.setPersonNameNLS(ArabicConversion.setArabicEncodedString(personAddVO.getPersonNameNLS()));
					personAddVO.setPersonDescNLS(ArabicConversion.setArabicEncodedString(personAddVO.getPersonDescNLS()));
					personAddVO.setPostalAddressNLS(ArabicConversion.setArabicEncodedString(personAddVO.getPostalAddressNLS()));
					personAddVO.setSearchAbleBoolean(personAddVO.isSearchAbleBooleanCust());
					personAddVO.setImportant(personAddVO.isImportantBoolean() ? "Y" : "N");
					personAddVO.setSearchAble(personAddVO.isSearchAbleBooleanCust() ? "Y" : "N");

					if (personAddVO.getPersonCode() == null) {
						personAddVO.setCreatedTime(new Date());
						personAddVO.setCreatedBy(loginManagedBean.getCurrentUser().getUserId());

					} else {
						personAddVO.setModifiedTime(new Date());
						personAddVO.setModifiedBy(loginManagedBean.getCurrentUser().getUserId());
						updatedFlag = true;
					}
					this.personManager.savePerson(personAddVO);
					if (!updatedFlag) {
						this.userManager.addAuditInfo(new AuditVO(personAddVO.getPersonCode().longValue(), loginManagedBean.getCurrentUser().getUserId(), EABConstants.EAB_CATOGORY_PERSON, "Person " + personAddVO.getPersonName() + " has been Added", getUserIP()));
					} else {
						this.userManager.addAuditInfo(new AuditVO(personAddVO.getPersonCode().longValue(), loginManagedBean.getCurrentUser().getUserId(), EABConstants.EAB_CATOGORY_PERSON, "Person " + personAddVO.getPersonName() + " has been Updated", getUserIP()));
					}

					displayInfoMessageToUser("record.saved.successfully");

					resetPerson();

				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			displayErrorMessageToUser("common.error");
			e.printStackTrace();
		}

	}

	// public void onChangeDropDowns() {
	// try {
	// this.personBean.setEntitiesList(personManager.getEntitiesByInstituteId(this.getPersonAddVO().getEntityVO()));
	// } catch (Exception e) {
	// e.printStackTrace();
	// System.err.println("Inside searchPersons....................... " +
	// e.getMessage());
	// }
	// }

	public PersonVO getPersonVO() {
		return personVO;
	}

	public void setPersonVO(PersonVO personVO) {
		this.personVO = personVO;
	}

	public List<PersonVO> getPersons() {
		return persons;
	}

	public void setPersons(List<PersonVO> persons) {
		this.persons = persons;
	}

	public PersonManager getPersonManager() {
		return personManager;
	}

	public void setPersonManager(PersonManager personManager) {
		this.personManager = personManager;
	}

	public void reset() {
		this.personVO = new PersonVO();
		getConfigDataModel();
	}

	public void editPerson(Integer personCode) {
		try {

			personAddVO = new PersonVO();
			this.personAddVO = personManager.findPersonById(personCode);
			this.personAddVO.setSelectedGsmPrimary(personAddVO.getGsmPrimary());
			this.personAddVO.setSelectedStaffNo(personAddVO.getStaffNo());
			this.personAddVO.setPersonNameNLS(ArabicConversion.getArabicEncodedString(this.personAddVO.getPersonNameNLS()));
			this.personAddVO.setPersonDescNLS(ArabicConversion.getArabicEncodedString(this.personAddVO.getPersonDescNLS()));
			this.personAddVO.setPostalAddressNLS(ArabicConversion.getArabicEncodedString(this.personAddVO.getPostalAddressNLS()));
			this.personAddVO.setSearchAbleBooleanCust((this.personAddVO.getSearchAble() != null && this.personAddVO.getSearchAble().equals("Y")) ? true : false);
			this.personAddVO.setImportantBoolean((this.personAddVO.getImportant() != null && this.personAddVO.getImportant().equals("Y")) ? true : false);

			if (personAddVO.getEntityVO().getInstitutionVO() == null) {
				personAddVO.getEntityVO().setInstitutionVO(new InstitutionVO());
			}

			if (personAddVO.getEntityVO().getSectionVO() == null) {
				personAddVO.getEntityVO().setSectionVO(new SectionVO());
			}

			if (personAddVO.getEntityVO().getDepartmentVO() == null) {
				personAddVO.getEntityVO().setDepartmentVO(new DepartmentVO());
			}

			if (personAddVO.getDesignationVO() == null) {
				personAddVO.setDesignationVO(new DesignationVO());
			}

		} catch (Exception e) {
			// TODO: handle exception
			displayErrorMessageToUser("common.error");
			e.printStackTrace();

		}
	}

	public void resetPerson() {
		Long entityCode = personAddVO.getEntityVO().getEntityCode();
		personAddVO = new PersonVO();
		personAddVO.getEntityVO().setEntityCode(entityCode);
	}

	public PersonVO getPersonAddVO() {
		return personAddVO;
	}

	public void setPersonAddVO(PersonVO personAddVO) {
		this.personAddVO = personAddVO;
	}

	public void deletePerson(Integer deleteId) throws Exception {
		try {
			// HttpSession httpSession = getHttpSession();
			personAddVO.setPersonCode(deleteId);
			personManager.deletePerson(personAddVO);
			this.userManager.addAuditInfo(new AuditVO(personAddVO.getPersonCode().longValue(), loginManagedBean.getCurrentUser().getUserId(), EABConstants.EAB_CATOGORY_PERSON, "Person " + personAddVO.getPersonCode() + " has been deleted", getUserIP()));
			displayInfoMessageToUser("record.deleted.successfully");
			personAddVO = new PersonVO();
			personAddVO.setPersonCode(null);
		} catch (Exception e) {
			// TODO: handle exception
			displayErrorMessageToUser("common.error");
			e.printStackTrace();
		}
	}

	private List<PersonVO> getArabicConvertedList(List<PersonVO> list) {
		List<PersonVO> persons = new ArrayList<PersonVO>();
		PersonVO person = null;
		for (PersonVO personVO : list) {
			person = new PersonVO();

			if (personVO.getPersonNameNLS() != null) {
				person.setPersonNameNLS(ArabicConversion.getArabicEncodedString(personVO.getPersonNameNLS()));
			} else {
				person.setPersonNameNLS(personVO.getPersonName());
			}

			person.setPersonDescNLS(ArabicConversion.getArabicEncodedString(personVO.getPersonDescNLS()));
			person.setPostalAddressNLS(ArabicConversion.getArabicEncodedString(personVO.getPostalAddressNLS()));

			if (personVO.getDesignationVO() != null && personVO.getDesignationVO().getNameNLS() != null) {
				person.getDesignationVO().setNameNLS(ArabicConversion.getArabicEncodedString(personVO.getDesignationVO().getNameNLS()));
			}
			if (personVO.getDesignationVO() != null && personVO.getDesignationVO().getName() != null) {
				person.getDesignationVO().setName(personVO.getDesignationVO().getName());
			}

			person.getEntityVO().getDirectorateVO().setDirecotrateName(personVO.getEntityVO().getDirectorateVO().getDirecotrateName());
			person.getEntityVO().getDirectorateVO().setDirecotrateNameNLS(ArabicConversion.getArabicEncodedString(personVO.getEntityVO().getDirectorateVO().getDirecotrateNameNLS()));
			if (personVO.getEntityVO().getSectionVO() != null) {
				person.getEntityVO().getSectionVO().setSectionName(personVO.getEntityVO().getSectionVO().getSectionName());
			}
			if (personVO.getEntityVO().getSectionVO() != null && personVO.getEntityVO().getSectionVO().getSectionNameNLS() != null) {
				person.getEntityVO().getSectionVO().setSectionNameNLS(ArabicConversion.getArabicEncodedString(personVO.getEntityVO().getSectionVO().getSectionNameNLS()));
			}
			if (personVO.getEntityVO().getDepartmentVO() != null) {
				person.getEntityVO().getDepartmentVO().setDepartmentName(personVO.getEntityVO().getDepartmentVO().getDepartmentName());
				person.getEntityVO().getDepartmentVO().setDepartmentNameNLS(ArabicConversion.getArabicEncodedString(personVO.getEntityVO().getDepartmentVO().getDepartmentNameNLS()));
			}

			if (personVO.getSearchAble() != null && personVO.getSearchAble().equals("Y")) {
				person.setGsmPrimary(personVO.getGsmPrimary());
			} else {
				person.setGsmPrimary(null);
			}
			person.setPersonName(personVO.getPersonName());
			person.setEmail(personVO.getEmail());
			person.setDirectTelNo(personVO.getDirectTelNo());
			person.setGsmAddnl(personVO.getGsmAddnl());
			person.setResiTelNo(personVO.getResiTelNo());
			person.setCivilId(personVO.getCivilId());
			person.setStaffNo(personVO.getStaffNo());
			person.setStaffType(personVO.getStaffType());
			person.setExtn(personVO.getExtn());
			person.setPersonCode(personVO.getPersonCode());

			persons.add(person);

		}
		return persons;
	}

	/*
	 * public boolean validate(){ boolean result = true;
	 * 
	 * if(personAddVO.getPersonName() == null ||
	 * personAddVO.getPersonName().equals("")){
	 * displayErrorMessageToUser("Person name is required."); result = false; }
	 * if(personAddVO.getDesignation() == null ||
	 * personAddVO.getDesignation().equals("")){
	 * displayErrorMessageToUser("Please select designation from list."); result
	 * = false; } if(personAddVO.getGsmPrimary() == null ){
	 * displayErrorMessageToUser("GSM primary is required."); result = false; }
	 * if(personAddVO. getEntityVO().getEntityCode() == null ){
	 * displayErrorMessageToUser("Please select entity from list."); result =
	 * false; } return result; }
	 */

	private boolean isStaffNoExist() throws Exception {
		boolean result = false;
		try {

			if (personAddVO.getStaffNo() == null) {
				return true;
			}
			if (personAddVO.getStaffNo().equals(personAddVO.getSelectedStaffNo())) {
				result = true;
			} else if (!personManager.isStaffNoExist(personAddVO)) {
				result = true;
			} else {
				displayErrorMessageToUser("personAdd.staff.exist");
				result = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			displayErrorMessageToUser("personAdd.error.civilid");
			e.printStackTrace();

		}
		return result;
	}

	private boolean validateGsmPrimary() throws Exception {
		boolean result = false;
		try {
			if (personAddVO.getGsmPrimary() == null) {
				return true;
			}
			if (personAddVO.getGsmPrimary().equals(personAddVO.getSelectedGsmPrimary())) {
				result = true;
			} else if (!personManager.isGsmPrimaryExist(personAddVO)) {
				result = true;
			} else {
				displayErrorMessageToUser("personAdd.gsmprimary.exist");
				return false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			displayErrorMessageToUser("personAdd.error.gsmprimary");
			e.printStackTrace();
		}
		return result;
	}

	// private boolean validateGsmAddnl() throws Exception {
	// boolean result = false;
	// try {
	// if (personAddVO.getGsmAddnl() == null) {
	// return true;
	// }
	// if (personAddVO.getGsmAddnl().equals(personAddVO.getSelectedGsmAddnl()))
	// {
	// result = true;
	// } else if (!personManager.isGsmAddnlExist(personAddVO)) {
	// result = true;
	// } else {
	// displayErrorMessageToUser("personAdd.gsmaddnl.exist");
	// return false;
	// }
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	// displayErrorMessageToUser("personAdd.error.gsmprimary");
	// e.printStackTrace();
	// }
	// return result;
	// }
	//
	// private boolean validateResiTel() throws Exception {
	// boolean result = false;
	// try {
	// if (personAddVO.getResiTelNo() == null) {
	// return true;
	// }
	// if
	// (personAddVO.getResiTelNo().equals(personAddVO.getSelectedResiTelNo())) {
	// result = true;
	// } else if (!personManager.isResiTelExist(personAddVO)) {
	// result = true;
	// } else {
	// displayErrorMessageToUser("personAdd.gsmresi.exist");
	// return false;
	// }
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	// displayErrorMessageToUser("personAdd.error.gsmresi");
	// e.printStackTrace();
	// }
	// return result;
	// }
	//
	// private boolean validateDirectTel() throws Exception {
	// boolean result = false;
	// try {
	// if (personAddVO.getDirectTelNo() == null) {
	// return true;
	// }
	// if
	// (personAddVO.getDirectTelNo().equals(personAddVO.getSelectedDirectTelNo()))
	// {
	// result = true;
	// } else if (!personManager.isDirectTelExist(personAddVO)) {
	// result = true;
	// } else {
	// displayErrorMessageToUser("personAdd.gsmdirect.exist");
	// return false;
	// }
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	// displayErrorMessageToUser("personAdd.error.gsmdirect");
	// e.printStackTrace();
	// }
	// return result;
	// }

	/** HANDLING ON CHANGE EVENTS FOR DEPARTMENT **/

	private Long govId;
	private Long dirId;

	public void onChangeOfGovCombo(Long govId) {
		this.govId = govId;

		List<DirectorateVO> dirList = new ArrayList<DirectorateVO>();
		List<DepartmentVO> deptList = new ArrayList<DepartmentVO>();
		List<InstitutionVO> instList = new ArrayList<InstitutionVO>();
		List<SectionVO> secList = new ArrayList<SectionVO>();
		try {

			// List<DirectorateVO> directorateSuperList = new
			// ArrayList<DirectorateVO>(referenceCacheBean.getDirectorateMap().values());
			// for(DirectorateVO dirVO : directorateSuperList){
			// if(dirVO.getRegionCode().equals(govId)){
			// dirList.add(dirVO);
			// }
			// }

			List<DepartmentVO> deptListSuper = new ArrayList<DepartmentVO>(referenceCacheBean.getDepartmentMap().values());
			for (DepartmentVO deptVO : deptListSuper) {
				if (deptVO.getGovernarateVO() != null && (deptVO.getGovernarateVO().getId() != null && deptVO.getGovernarateVO().getId().equals(govId)) || (deptVO.getIsCommonDept() != null && deptVO.getIsCommonDept().equals("Y"))) {
					deptList.add(deptVO);
				}
			}

			GovernarateVO govVO = referenceCacheBean.getGovernrateMap().get(govId);
			List<SectionVO> secListSuper = new ArrayList<SectionVO>(referenceCacheBean.getSectionMap().values());
			for (SectionVO secVO : secListSuper) {
				if (secVO.getGovernarateVO() != null && (secVO.getGovernarateVO().getRegionCode() != null && secVO.getGovernarateVO().getRegionCode().equals(govVO.getRegionCode())) || (secVO.getIsCommonSection() != null && secVO.getIsCommonSection().equals("Y"))) {
					secList.add(secVO);
				}
			}

			List<InstitutionVO> instListSuper = new ArrayList<InstitutionVO>(referenceCacheBean.getInstituteMap().values());
			for (InstitutionVO instVO : instListSuper) {
				if (instVO.getRegCode() != null && instVO.getRegCode().equals(govVO.getRegionCode())) {
					instList.add(instVO);
				}
			}

			// dirList =
			// referenceCacheBean.getReferenceDataManager().getDirectoratesOfGov(govId);
			//
			// //Added by Sajeer to filter Department w.r.t Governerate
			// deptList =
			// referenceCacheBean.getReferenceDataManager().getDepartmentOfDirAndGovAndSec(govId,
			// null, null);
			//
			// //Added by Mujeeb to filter Section w.r.t Governerate
			// secList =
			// referenceCacheBean.getReferenceDataManager().getSectionsOfDirAndGovAndDept(govId,
			// null, null, null);
			// //Added by Sajeer to filter Health institutes w.r.t Governerate
			// instList =
			// referenceCacheBean.getReferenceDataManager().getInstitutionsOfGov(govId);
			//

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
			// this.personVO.getEntityVO().getDirectorateVO().setId(-1L);

			referenceDataBean.departmentList = new ArrayList<SelectItem>();
			referenceDataBean.departmentListNLS = new ArrayList<SelectItem>();
			for (DepartmentVO dptVO : deptList) {
				referenceDataBean.departmentList.add(new SelectItem(dptVO.getId(), dptVO.getDepartmentName()));
				referenceDataBean.departmentListNLS.add(new SelectItem(dptVO.getId(), ArabicConversion.getArabicEncodedString(dptVO.getDepartmentNameNLS())));
			}
			this.personVO.getEntityVO().getDepartmentVO().setId(-1L);

			referenceDataBean.sectionList = new ArrayList<SelectItem>();
			referenceDataBean.sectionListNLS = new ArrayList<SelectItem>();
			for (SectionVO sectionVO : secList) {
				referenceDataBean.sectionList.add(new SelectItem(sectionVO.getId(), sectionVO.getSectionName()));
				referenceDataBean.sectionListNLS.add(new SelectItem(sectionVO.getId(), ArabicConversion.getArabicEncodedString(sectionVO.getSectionNameNLS())));
			}
			this.personVO.getEntityVO().getSectionVO().setId(-1L);

			referenceDataBean.instituteList = new ArrayList<SelectItem>();
			referenceDataBean.instituteListNLS = new ArrayList<SelectItem>();
			for (InstitutionVO insVO : instList) {
				referenceDataBean.instituteList.add(new SelectItem(insVO.getInstCode(), insVO.getInstName()));

				if (insVO.getInstNameNLS() != null && !insVO.getInstNameNLS().isEmpty()) {
					referenceDataBean.instituteList.add(new SelectItem(insVO.getInstCode(), ArabicConversion.getArabicEncodedString(insVO.getInstNameNLS())));
				} else {
					referenceDataBean.instituteList.add(new SelectItem(insVO.getInstCode(), insVO.getInstName()));
				}
			}
			this.personVO.getEntityVO().getInstitutionVO().setInstCode(-1L);
		} catch (Exception e) {

			e.printStackTrace();
		}

		/*
		 * referenceDataBean.sectionList = new ArrayList<SelectItem>();
		 * referenceDataBean.sectionListNLS = new ArrayList<SelectItem>();
		 * referenceDataBean.setSectionList(referenceDataBean.sectionListSuper);
		 * referenceDataBean
		 * .setSectionListNLS(referenceDataBean.sectionListSuperNLS);
		 * this.personVO.getEntityVO().getSectionVO().setId(-1L);
		 */

	}

	public void onChangeOfDirCombo(Long dirId) {
		this.dirId = dirId;

		// IF USER DID NOT SELECT LOAD ALL
		if (!dirId.equals(-100L)) {
			// commented by Mujeeb(as per new request department loading while
			// changing the governarate)
			/*
			 * List<DepartmentVO> depList = null; try { depList =
			 * referenceCacheBean
			 * .getReferenceDataManager().getDepartmentsOfDir(dirId, govId); }
			 * catch (Exception e) { e.printStackTrace(); }
			 * 
			 * referenceDataBean.departmentList = new ArrayList<SelectItem>();
			 * referenceDataBean.departmentListNLS = new
			 * ArrayList<SelectItem>(); for (DepartmentVO deptVO : depList) {
			 * referenceDataBean.departmentList.add(new
			 * SelectItem(deptVO.getId(), deptVO.getDepartmentName()));
			 * referenceDataBean.departmentListNLS.add(new
			 * SelectItem(deptVO.getId(),
			 * ArabicConversion.getArabicEncodedString
			 * (deptVO.getDepartmentNameNLS()))); }
			 */
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
			referenceDataBean.sectionList = new ArrayList<SelectItem>();
			referenceDataBean.sectionListNLS = new ArrayList<SelectItem>();
			for (SectionVO sectionVO : secList) {
				referenceDataBean.sectionList.add(new SelectItem(sectionVO.getId(), sectionVO.getSectionName()));
				referenceDataBean.sectionListNLS.add(new SelectItem(sectionVO.getId(), ArabicConversion.getArabicEncodedString(sectionVO.getSectionNameNLS())));
			}
			this.personVO.getEntityVO().getSectionVO().setId(-1L);

			this.personVO.getEntityVO().getDepartmentVO().setId(-1L);

		} else {
			// HANDLING LOAD ALL CASE
			referenceDataBean.directorateList = new ArrayList<SelectItem>();
			referenceDataBean.directorateListNLS = new ArrayList<SelectItem>();
			referenceDataBean.setDirectorateList(referenceDataBean.directorateListSuper);
			referenceDataBean.setDirectorateListNLS(referenceDataBean.directorateListSuperNLS);
			this.personAddVO.getEntityVO().getDepartmentVO().setId(-1L);

			// commented by Mujeeb
			/*
			 * referenceDataBean.departmentList = new ArrayList<SelectItem>();
			 * referenceDataBean.departmentListNLS = new
			 * ArrayList<SelectItem>();
			 * referenceDataBean.setDepartmentList(referenceDataBean
			 * .departmentListSuper);
			 * referenceDataBean.setDepartmentListNLS(referenceDataBean
			 * .departmentListSuperNLS);
			 * this.personAddVO.getEntityVO().getDepartmentVO().setId(-1L);
			 */

			// commented by Mujeeb
			/*
			 * referenceDataBean.sectionList = new ArrayList<SelectItem>();
			 * referenceDataBean.sectionListNLS = new ArrayList<SelectItem>();
			 * referenceDataBean
			 * .setSectionList(referenceDataBean.sectionListSuper);
			 * referenceDataBean
			 * .setSectionListNLS(referenceDataBean.sectionListSuperNLS);
			 * this.personAddVO.getEntityVO().getSectionVO().setId(-1L);
			 */
		}

		// GOING TO RESET SECTION COMBO AS WELL
		// commented by Mujeeb
		/*
		 * referenceDataBean.sectionList = new ArrayList<SelectItem>();
		 * referenceDataBean.sectionListNLS = new ArrayList<SelectItem>();
		 * referenceDataBean.setSectionList(referenceDataBean.sectionListSuper);
		 * referenceDataBean
		 * .setSectionListNLS(referenceDataBean.sectionListSuperNLS);
		 */

		// this.personVO.getEntityVO().getSectionVO().setId(-1L);
		// this.personAddVO.getEntityVO().getSectionVO().setId(-1L);
	}

	public void onChangeDepartmentCombo(Long deptId) {
		Long currentRole = null;

		if (loginManagedBean != null && loginManagedBean.isAuthorized()) {
			currentRole = loginManagedBean.getCurrentUser().getRoleVO().getRoleId();
		}

		Long govId = null;
		Long dirId = null;

		if (currentRole != null && currentRole.equals(EABConstants.ROLE_SUPER_ADMIN)) {
			govId = this.govId;
			dirId = this.dirId;
		} else if (currentRole != null && currentRole.equals(EABConstants.ROLE_ADMIN)) {
			dirId = loginManagedBean.getCurrentUser().getDirectorateVO().getId();
			govId = loginManagedBean.getCurrentUser().getGovernarateVO().getId();

		} else {
			Long[] ids = referenceDataBean.getUserSelectedCookies();
			dirId = ids[0];
			govId = ids[1];
		}

		List<SectionVO> secList = new ArrayList<SectionVO>();
		List<SectionVO> sectionSuperList = new ArrayList<SectionVO>(referenceCacheBean.getSectionMap().values());
		// IN CASE USER DID NOT SELECT LOAD ALL
		// if (!deptId.equals(-200L)) {
		for (SectionVO secVO : sectionSuperList) {
			if (deptId.equals(-1L) && secVO.getGovernarateVO() != null && secVO.getGovernarateVO().getId() != null && secVO.getGovernarateVO().getId().equals(govId)) {
				secList.add(secVO);
			} else if (secVO.getDepartmentVO() != null && secVO.getDepartmentVO().getId().equals(deptId)) {
				secList.add(secVO);
			}

		}

		// } else {
		//
		// // IF USER SELECTED LOAD ALL THEN WE WILL RESET THE COMBO
		// //Changed by Sajeer to restrict the loadAll department w.r.t the
		// governerate + Common Departments
		// List<DepartmentVO> deptList = new ArrayList<DepartmentVO>();
		// List<DepartmentVO> departmentSuperList = new
		// ArrayList<DepartmentVO>(referenceCacheBean.getDepartmentMap().values());
		//
		// for(DepartmentVO dptVO:departmentSuperList){
		// if(dptVO.getGovernarateVO() != null &&
		// dptVO.getGovernarateVO().equals(govId)
		// ||(dptVO.getIsCommonDept()!=null&&dptVO.getIsCommonDept().equals("Y"))){
		// deptList.add(dptVO);
		// }
		//
		// }
		//
		// for(SectionVO secVO : sectionSuperList){
		// if(secVO.getDepartmentVO() !=null &&
		// secVO.getDepartmentVO().getId().equals(deptId)){
		// secList.add(secVO);
		// }
		// }
		//
		//
		// referenceDataBean.departmentList = new ArrayList<SelectItem>();
		// referenceDataBean.departmentListNLS = new ArrayList<SelectItem>();
		// //referenceDataBean.setDepartmentList(referenceDataBean.departmentListSuper);
		// //referenceDataBean.setDepartmentListNLS(referenceDataBean.departmentListSuperNLS);
		// for (DepartmentVO dptVO: deptList){
		// referenceDataBean.departmentList.add(new SelectItem(dptVO.getId(),
		// dptVO.getDepartmentName()));
		// referenceDataBean.departmentListNLS.add(new SelectItem(dptVO.getId(),
		// ArabicConversion.getArabicEncodedString(dptVO.getDepartmentNameNLS())));
		// }
		// this.personAddVO.getEntityVO().getDepartmentVO().setId(-1L);
		//
		// }

		referenceDataBean.sectionList = new ArrayList<SelectItem>();
		referenceDataBean.sectionListNLS = new ArrayList<SelectItem>();
		for (SectionVO sectionVO : secList) {
			referenceDataBean.sectionList.add(new SelectItem(sectionVO.getId(), sectionVO.getSectionName()));
			referenceDataBean.sectionListNLS.add(new SelectItem(sectionVO.getId(), ArabicConversion.getArabicEncodedString(sectionVO.getSectionNameNLS())));
		}
		this.personVO.getEntityVO().getSectionVO().setId(-1L);
		this.personAddVO.getEntityVO().getSectionVO().setId(-1L);

	}

	public void onChangeSectionCombo(Long secId) {
		Long currentRole = null;

		if (loginManagedBean != null && loginManagedBean.isAuthorized()) {
			currentRole = loginManagedBean.getCurrentUser().getRoleVO().getRoleId();
		}

		Long govId = null;
		Long dirId = null;
		// Filling GovId and SecId for Admin User
		if (currentRole != null && currentRole.equals(EABConstants.ROLE_SUPER_ADMIN)) {
			this.govId = govId;
			this.dirId = dirId;
		} else if (currentRole != null && currentRole.equals(EABConstants.ROLE_ADMIN)) {
			dirId = loginManagedBean.getCurrentUser().getDirectorateVO().getId();
			govId = loginManagedBean.getCurrentUser().getGovernarateVO().getId();

		} else {
			// Filling GovId and SecId for Guest User
			Long[] ids = referenceDataBean.getUserSelectedCookies();
			dirId = ids[0];
			govId = ids[1];
		}

		if (!secId.equals(-100L)) {

			// Commented by Mujeeb
			/*
			 * List<DepartmentVO> depList = null; try {
			 * 
			 * depList = referenceCacheBean.getReferenceDataManager().
			 * getDepartmentOfDirAndGovAndSec(govId, dirId, secId);
			 * 
			 * } catch (Exception e) {
			 * 
			 * e.printStackTrace(); }
			 * 
			 * referenceDataBean.departmentList = new ArrayList<SelectItem>();
			 * referenceDataBean.departmentListNLS = new
			 * ArrayList<SelectItem>(); for (DepartmentVO sectionVO : depList) {
			 * referenceDataBean.departmentList.add(new
			 * SelectItem(sectionVO.getId(), sectionVO.getDepartmentName()));
			 * referenceDataBean.departmentListNLS.add(new
			 * SelectItem(sectionVO.getId(),
			 * ArabicConversion.getArabicEncodedString
			 * (sectionVO.getDepartmentNameNLS()))); }
			 */
		} else {

			// Commented by Mujeeb
			/*
			 * referenceDataBean.sectionList = new ArrayList<SelectItem>();
			 * referenceDataBean.sectionListNLS = new ArrayList<SelectItem>();
			 * referenceDataBean
			 * .setSectionList(referenceDataBean.sectionListSuper);
			 * referenceDataBean
			 * .setSectionListNLS(referenceDataBean.sectionListSuperNLS);
			 */
			// this.personAddVO.getEntityVO().getSectionVO().setId(-1L);

			List<SectionVO> secList = null;
			try {

				secList = referenceCacheBean.getReferenceDataManager().getSectionsOfDirAndGovAndDept(govId, dirId, null, secId);
				referenceDataBean.sectionList = new ArrayList<SelectItem>();
				referenceDataBean.sectionListNLS = new ArrayList<SelectItem>();
				for (SectionVO sectionVO : secList) {
					referenceDataBean.sectionList.add(new SelectItem(sectionVO.getId(), sectionVO.getSectionName()));
					referenceDataBean.sectionListNLS.add(new SelectItem(sectionVO.getId(), ArabicConversion.getArabicEncodedString(sectionVO.getSectionNameNLS())));
				}
				// this.personAddVO.getEntityVO().getDepartmentVO().setId(-1L);

			} catch (Exception e) {

				e.printStackTrace();
			}

			// Commented by Mujeeb
			/*
			 * referenceDataBean.departmentList = new ArrayList<SelectItem>();
			 * referenceDataBean.departmentListNLS = new
			 * ArrayList<SelectItem>();
			 * referenceDataBean.setDepartmentList(referenceDataBean
			 * .departmentListSuper);
			 * referenceDataBean.setDepartmentListNLS(referenceDataBean
			 * .departmentListSuperNLS);
			 */
			// this.personAddVO.getEntityVO().getDepartmentVO().setId(-1L);
		}

	}

	public void redirectToUserSearch() {
		try {

			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

			String contextPath = ctx.getContextPath();
			FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + "/pages/user/userSearch.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void redirectToPersonSearch() {
		try {
			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String contextPath = ctx.getContextPath();
			FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + "/pages/person/home.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public UserVO getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(UserVO loggedUser) {
		this.loggedUser = loggedUser;
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

	public ReferenceDataBean getReferenceDataBean() {
		return referenceDataBean;
	}

	public void setReferenceDataBean(ReferenceDataBean referenceDataBean) {
		this.referenceDataBean = referenceDataBean;
	}

	public ReferenceCacheBean getReferenceCacheBean() {
		return referenceCacheBean;
	}

	public void setReferenceCacheBean(ReferenceCacheBean referenceCacheBean) {
		this.referenceCacheBean = referenceCacheBean;
	}

	public Long getGovId() {
		return govId;
	}

	public void setGovId(Long govId) {
		this.govId = govId;
	}

	public Long getDirId() {
		return dirId;
	}

	public void setDirId(Long dirId) {
		this.dirId = dirId;
	}

	/**
	 * @return the reportBean
	 */
	public ReportBean getReportBean() {
		return reportBean;
	}

	/**
	 * @param reportBean
	 *            the reportBean to set
	 */
	public void setReportBean(ReportBean reportBean) {
		this.reportBean = reportBean;
	}

	/**
	 * @return the exportOption
	 */
	public String getExportOption() {
		return exportOption;
	}

	/**
	 * @param exportOption
	 *            the exportOption to set
	 */
	public void setExportOption(String exportOption) {
		this.exportOption = exportOption;
	}

	/**
	 * @return the isReport
	 */
	public boolean isReport() {
		return isReport;
	}

	/**
	 * @param isReport
	 *            the isReport to set
	 */
	public void setReport(boolean isReport) {
		this.isReport = isReport;
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

	public List<PersonVO> getList() {
		return list;
	}

	public void setList(List<PersonVO> list) {
		this.list = list;
	}

	public LanguageBean getLanguageBean() {
		return languageBean;
	}

	public void setLanguageBean(LanguageBean languageBean) {
		this.languageBean = languageBean;
	}

}
