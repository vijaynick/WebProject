package om.gov.moh.eab.master.vo;

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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "EAB_DEPARTMENT_MAST")
public class DepartmentVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String departmentName;
	private String departmentNameNLS;
	private String departmentShortName;
	private String departmentShortSNameNLS;
	private GovernarateVO governarateVO = new GovernarateVO();
	private DirectorateVO directorateVO = new DirectorateVO(); 
	private String isCommonDept;
	private String active;
	private Long createdBy;
	private Date createdTime;
	private Long modifiedBy;
	private Date modifiedTime;
	private Long sortOrder;

	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "department_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "department_name")
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Column(name = "department_name_nls")
	public String getDepartmentNameNLS() {
		return departmentNameNLS;
	}

	public void setDepartmentNameNLS(String departmentNameNLS) {
		this.departmentNameNLS = departmentNameNLS;
	}

	@Column(name = "department_sh_name")
	public String getDepartmentShortName() {
		return departmentShortName;
	}

	public void setDepartmentShortName(String departmentShortName) {
		this.departmentShortName = departmentShortName;
	}

	@Column(name = "department_sh_name_nls")
	public String getDepartmentShortSNameNLS() {
		return departmentShortSNameNLS;
	}

	public void setDepartmentShortSNameNLS(String departmentShortSNameNLS) {
		this.departmentShortSNameNLS = departmentShortSNameNLS;
	}

	@Column(name = "COMMON_DEPT_YN")
	public String getIsCommonDept() {
		return isCommonDept;
	}

	public void setIsCommonDept(String isCommonDept) {
		this.isCommonDept = isCommonDept;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REG_CODE")
	public GovernarateVO getGovernarateVO() {
		return governarateVO;
	}

	public void setGovernarateVO(GovernarateVO governarateVO) {
		this.governarateVO = governarateVO;
	}

	@Column(name = "active_yn")
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DIRECTORATE_ID")
	public DirectorateVO getDirectorateVO() {
		return directorateVO;
	}

	public void setDirectorateVO(DirectorateVO directorateVO) {
		this.directorateVO = directorateVO;
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
