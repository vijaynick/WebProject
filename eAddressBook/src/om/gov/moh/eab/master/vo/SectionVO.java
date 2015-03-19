package om.gov.moh.eab.master.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import om.gov.moh.eab.entity.vo.EntityVO;

@Entity
@Table(name = "EAB_SECTION_MAST")
public class SectionVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String sectionName;
	private String sectionNameNLS;
	private String sectionShortName;
	private String sectionShortNameNLS;
	private Set<EntityVO> entities;
	private String isCommonSection;
	private GovernarateVO governarateVO = new GovernarateVO();
	private DirectorateVO directorateVO = new DirectorateVO();
	private DepartmentVO departmentVO = new DepartmentVO();
	private String active;
	private Long createdBy;
	private Date createdTime;
	private Long modifiedBy;
	private Date modifiedTime;
	private Long sortOrder;
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "section_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "section_name")
	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	@Column(name = "section_name_nls")
	public String getSectionNameNLS() {
		return sectionNameNLS;
	}

	
	public void setSectionNameNLS(String sectionNameNLS) {
		this.sectionNameNLS = sectionNameNLS;
	}
	
	@OneToMany(mappedBy = "sectionVO")
	public Set<EntityVO> getEntities() {
		return entities;
	}

	public void setEntities(Set<EntityVO> entities) {
		this.entities = entities;
	}
	@Column(name = "section_sh_name")
	public String getSectionShortName() {
		return sectionShortName;
	}

	public void setSectionShortName(String sectionShortName) {
		this.sectionShortName = sectionShortName;
	}
	@Column(name = "section_sh_name_nls")
	public String getSectionShortNameNLS() {
		return sectionShortNameNLS;
	}

	public void setSectionShortNameNLS(String sectionShortNameNLS) {
		this.sectionShortNameNLS = sectionShortNameNLS;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REG_CODE")
	public GovernarateVO getGovernarateVO() {
		return governarateVO;
	}

	
	public void setGovernarateVO(GovernarateVO governarateVO) {
		this.governarateVO = governarateVO;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DIRECTORATE_ID")
	public DirectorateVO getDirectorateVO() {
		return directorateVO;
	}

	
	public void setDirectorateVO(DirectorateVO directorateVO) {
		this.directorateVO = directorateVO;
	}

	
	@Column(name = "COMMON_SEC_YN")
	public String getIsCommonSection() {
		return isCommonSection;
	}

	/**
	 * @param isCommonSection the isCommonSection to set
	 */
	public void setIsCommonSection(String isCommonSection) {
		this.isCommonSection = isCommonSection;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPARTMENT_ID",nullable=true)
	public DepartmentVO getDepartmentVO() {
		return departmentVO;
	}

	public void setDepartmentVO(DepartmentVO departmentVO) {
		this.departmentVO = departmentVO;
	}

	@Column(name="active_yn")
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
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

	/**
	 * @return the sortOrder
	 */
	@Column(name = "SORT_ORDER")
	public Long getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}

	

	
}
