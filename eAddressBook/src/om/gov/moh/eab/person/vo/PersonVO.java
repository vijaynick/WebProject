package om.gov.moh.eab.person.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import om.gov.moh.eab.entity.vo.EntityVO;
import om.gov.moh.eab.master.vo.DesignationVO;
import om.gov.moh.eab.security.vo.InstitutionVO;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;

/**
 * @author farid.haq
 * 
 */
@Entity
@Table(name = "EAB_PERSON")
public class PersonVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer personCode;
	private String personName;
	private String personNameNLS;
	private String personDesc;
	private String personDescNLS;
	private DesignationVO designationVO = new DesignationVO();
	
	private Integer extn;// extension of telephone number
	private Integer extn2;// extension of telephone number
	
	private String email;
	private String postalAddress;
	private String postalAddressNLS;
	private Integer staffType;
	private String staffNo;
	private Integer civilId;
	private String activeYn;
	private Date activationDate;
	private Date deactivationDate;
	private Long createdBy;
	private Date createdTime;
	private Long modifiedBy;
	private Date modifiedTime;
	private Long selectedGsmPrimary;
	private String selectedStaffNo;
	private String searchAble;
	private String important;
	private boolean searchAbleBoolean;
	private boolean searchAbleBooleanCust = true;
	private boolean importantBoolean;
	

	
	private EntityVO entityVO = new EntityVO();
	
	private Long gsmPrimary;
	private Long fax;
	private Long gsmAddnl;// GSM Additional
	private Long resiTelNo;// Residence telephone number
	private Long gsm;// This is common for all telephone no. Using only for
	private Long directTelNo;
	
	

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "PERSON_CODE")
	public Integer getPersonCode() {
		return personCode;
	}

	public void setPersonCode(Integer personCode) {
		this.personCode = personCode;
	}

	@Column(name = "PERSON_NAME")
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	@Column(name = "DIRECT_TELE_NO")
	public Long getDirectTelNo() {
		return directTelNo;
	}

	public void setDirectTelNo(Long directTelNo) {
		this.directTelNo = directTelNo;
	}

	@Column(name = "EXTN")
	public Integer getExtn() {
		return extn;
	}

	public void setExtn(Integer extn) {
		this.extn = extn;
	}

	@Column(name = "GSM_NO_PRIMARY")
	public Long getGsmPrimary() {
		return gsmPrimary;
	}

	public void setGsmPrimary(Long gsmPrimary) {
		this.gsmPrimary = gsmPrimary;
	}

	@Column(name = "GSM_NO_ADDNL")
	public Long getGsmAddnl() {
		return gsmAddnl;
	}

	public void setGsmAddnl(Long gsmAddnl) {
		this.gsmAddnl = gsmAddnl;
	}

	@Column(name = "RESI_TELE_NO")
	public Long getResiTelNo() {
		return resiTelNo;
	}

	public void setResiTelNo(Long resiTelNo) {
		this.resiTelNo = resiTelNo;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "POSTAL_ADDRESS")
	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	@Column(name = "STAFF_TYPE")
	public Integer getStaffType() {
		return staffType;
	}

	public void setStaffType(Integer staffType) {
		this.staffType = staffType;
	}

	@Column(name = "STAFF_NO")
	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	@Column(name = "CIVIL_ID")
	public Integer getCivilId() {
		return civilId;
	}

	public void setCivilId(Integer civilId) {
		this.civilId = civilId;
	}

	@Column(name = "ACTIVE_YN")
	public String getActiveYn() {
		return activeYn;
	}

	public void setActiveYn(String activeYn) {
		this.activeYn = activeYn;
	}

	@Column(name = "ACTIVATION_DATE")
	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	@Column(name = "DEACTIVATION_DATE")
	public Date getDeactivationDate() {
		return deactivationDate;
	}

	public void setDeactivationDate(Date deactivationDate) {
		this.deactivationDate = deactivationDate;
	}

	@Column(name = "CREATED_BY")
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_TIME")
	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Column(name = "MODIFIED_BY")
	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_TIME")
	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "entity_code")
	@NotFound
	public EntityVO getEntityVO() {
		return entityVO;
	}

	public void setEntityVO(EntityVO entityVO) {
		this.entityVO = entityVO;
	}

	@Column(name = "PERSON_NAME_NLS")
	public String getPersonNameNLS() {
		return personNameNLS;
	}

	public void setPersonNameNLS(String personNameNLS) {
		this.personNameNLS = personNameNLS;
	}

	@Column(name = "PERSON_DESC")
	public String getPersonDesc() {
		return personDesc;
	}

	public void setPersonDesc(String personDesc) {
		this.personDesc = personDesc;
	}

	@Column(name = "PERSON_DESC_NLS")
	public String getPersonDescNLS() {
		return personDescNLS;
	}

	public void setPersonDescNLS(String personDescNLS) {
		this.personDescNLS = personDescNLS;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DESIGNATION")
	public DesignationVO getDesignationVO() {
		return designationVO;
	}

	public void setDesignationVO(DesignationVO designationVO) {
		this.designationVO = designationVO;
	}

	@Transient
	public Long getGsm() {
		return gsm;
	}

	public void setGsm(Long gsm) {
		this.gsm = gsm;
	}

	@Transient
	public Long getSelectedGsmPrimary() {
		return selectedGsmPrimary;
	}

	public void setSelectedGsmPrimary(Long selectedGsmPrimary) {
		this.selectedGsmPrimary = selectedGsmPrimary;
	}

	

	@Column(name = "searchable")
	public String getSearchAble() {
		return searchAble;
	}

	public void setSearchAble(String searchAble) {
		this.searchAble = searchAble;
	}

	@Column(name = "important")
	public String getImportant() {
		return important;
	}

	public void setImportant(String important) {
		this.important = important;
	}

	@Transient
	public boolean isSearchAbleBoolean() {
		return searchAbleBoolean;
	}

	public void setSearchAbleBoolean(boolean searchAbleBoolean) {
		this.searchAbleBoolean = searchAbleBoolean;
	}

	@Transient
	public boolean isImportantBoolean() {
		return importantBoolean;
	}

	public void setImportantBoolean(boolean importantBoolean) {
		this.importantBoolean = importantBoolean;
	}

	
	@Column(name="POSTAL_ADDRESS_NLS")
	public String getPostalAddressNLS() {
		return postalAddressNLS;
	}

	public void setPostalAddressNLS(String postalAddressNLS) {
		this.postalAddressNLS = postalAddressNLS;
	}

	@Transient
	public boolean isSearchAbleBooleanCust() {
		return searchAbleBooleanCust;
	}

	public void setSearchAbleBooleanCust(boolean searchAbleBooleanCust) {
		this.searchAbleBooleanCust = searchAbleBooleanCust;
	}

	@Transient
	public String getSelectedStaffNo() {
		return selectedStaffNo;
	}

	public void setSelectedStaffNo(String selectedStaffNo) {
		this.selectedStaffNo = selectedStaffNo;
	}

	@Column(name="fax")
	public Long getFax() {
		return fax;
	}

	public void setFax(Long fax) {
		this.fax = fax;
	}

	@Column(name="extn_2")
	public Integer getExtn2() {
		return extn2;
	}

	public void setExtn2(Integer extn2) {
		this.extn2 = extn2;
	}

	

}
