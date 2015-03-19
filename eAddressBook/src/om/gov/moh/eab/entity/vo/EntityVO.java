package om.gov.moh.eab.entity.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import om.gov.moh.eab.master.vo.DepartmentVO;
import om.gov.moh.eab.master.vo.DirectorateVO;
import om.gov.moh.eab.master.vo.GovernarateVO;
import om.gov.moh.eab.master.vo.SectionVO;
import om.gov.moh.eab.person.vo.PersonVO;
import om.gov.moh.eab.security.vo.InstitutionVO;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "EAB_ENTITY")
public class EntityVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String entityName;
	private String entityNameNLS;
	private Long entityCode;

	private Long directTelNo = null;
	private Long extn = null;
	private Long faxNo = null;

	private String directTelNoNL;
	private String extnNL;
	private String faxNoNL;

	private String emailID;
	private Long createdBy;
	private Long modifiedBy;
	private Date createdDateTime;
	private Date modifiedDateTime;
	private String searchAble;
	private String emergency;

	private boolean searchAbleBoolean;
	private boolean searchAbleBooleanCustom=true;
	private boolean emergencyBoolean;

	private DepartmentVO departmentVO = new DepartmentVO();
	private DirectorateVO directorateVO = new DirectorateVO();
	private SectionVO sectionVO = new SectionVO();
	private InstitutionVO institutionVO = new InstitutionVO();
	private GovernarateVO governarateVO = new GovernarateVO();

	private String postalAddress;
	private String postalAddressNLS;
	private List<PersonVO> personsList = new ArrayList<PersonVO>();
	private String active;

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "ENTITY_CODE")
	public Long getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(Long entityCode) {
		this.entityCode = entityCode;
	}

	@Column(name = "DIRECT_TELE_NO")
	public Long getDirectTelNo() {
		return directTelNo;
	}

	public void setDirectTelNo(Long directTelNo) {
		this.directTelNo = directTelNo;
	}

	@Column(name = "EXTN")
	public Long getExtn() {
		return extn;
	}

	public void setExtn(Long extn) {
		this.extn = extn;
	}

	@Column(name = "FAX_NO")
	public Long getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(Long faxNo) {
		this.faxNo = faxNo;
	}

	@Column(name = "EMAIL_ID")
	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	@Column(name = "CREATED_BY")
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "MODIFIED_BY")
	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "CREATED_TIME")
	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	@Column(name = "MODIFIED_TIME")
	public Date getModifiedDateTime() {
		return modifiedDateTime;
	}

	public void setModifiedDateTime(Date modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPARTMENT_ID",nullable=true)
	public DepartmentVO getDepartmentVO() {
		return departmentVO;
	}

	public void setDepartmentVO(DepartmentVO departmentVO) {
		this.departmentVO = departmentVO;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DIRECTORATE_ID",nullable=true)
	public DirectorateVO getDirectorateVO() {
		return directorateVO;
	}

	public void setDirectorateVO(DirectorateVO directorateVO) {
		this.directorateVO = directorateVO;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SECTION_ID", nullable = true)
	public SectionVO getSectionVO() {
		return sectionVO;
	}

	public void setSectionVO(SectionVO sectionVO) {
		this.sectionVO = sectionVO;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INSTITUTE_ID",nullable=true)
	public InstitutionVO getInstitutionVO() {
		return institutionVO;
	}

	public void setInstitutionVO(InstitutionVO institutionVO) {
		this.institutionVO = institutionVO;
	}

	@Column(name = "ENTITY_NAME")
	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	@Column(name = "ENTITY_NAME_NLS")
	public String getEntityNameNLS() {
		return entityNameNLS;
	}

	public void setEntityNameNLS(String entityNameNLS) {
		this.entityNameNLS = entityNameNLS;
	}

	@Transient
	public String getDirectTelNoNL() {
		return directTelNoNL;
	}

	public void setDirectTelNoNL(String directTelNoNL) {
		this.directTelNoNL = directTelNoNL;
	}

	@Transient
	public String getExtnNL() {
		return extnNL;
	}

	public void setExtnNL(String extnNL) {
		this.extnNL = extnNL;
	}

	@Transient
	public String getFaxNoNL() {
		return faxNoNL;
	}

	public void setFaxNoNL(String faxNoNL) {
		this.faxNoNL = faxNoNL;
	}

	@Column(name = "searchable")
	public String getSearchAble() {
		return searchAble;
	}

	public void setSearchAble(String searchAble) {
		this.searchAble = searchAble;
	}

	@Column(name = "emergency")
	public String getEmergency() {
		return emergency;
	}

	public void setEmergency(String emergency) {
		this.emergency = emergency;
	}

	@Transient
	public boolean isEmergencyBoolean() {
		return emergencyBoolean;
	}

	public void setEmergencyBoolean(boolean emergencyBoolean) {
		this.emergencyBoolean = emergencyBoolean;
	}

	@Transient
	public boolean isSearchAbleBoolean() {
		return searchAbleBoolean;
	}

	public void setSearchAbleBoolean(boolean searchAbleBoolean) {
		this.searchAbleBoolean = searchAbleBoolean;
	}

	@Column(name = "POSTAL_ADDRESS")
	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GOVERNARATE_ID")
	public GovernarateVO getGovernarateVO() {
		return governarateVO;
	}

	public void setGovernarateVO(GovernarateVO governarateVO) {
		this.governarateVO = governarateVO;
	}

	@Column(name = "POSTAL_ADDRESS_NLS")
	public String getPostalAddressNLS() {
		return postalAddressNLS;
	}

	public void setPostalAddressNLS(String postalAddressNLS) {
		this.postalAddressNLS = postalAddressNLS;
	}

	@OneToMany(mappedBy="entityVO" , fetch=FetchType.LAZY)
	public List<PersonVO> getPersonsList() {
		return personsList;
	}

	public void setPersonsList(List<PersonVO> personsList) {
		this.personsList = personsList;
	}

	@Column(name="ACTIVEYN")
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Transient
	public boolean isSearchAbleBooleanCustom() {
		return searchAbleBooleanCustom;
	}

	public void setSearchAbleBooleanCustom(boolean searchAbleBooleanCustom) {
		this.searchAbleBooleanCustom = searchAbleBooleanCustom;
	}

}
