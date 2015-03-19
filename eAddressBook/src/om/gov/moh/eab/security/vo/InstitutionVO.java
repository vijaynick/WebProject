package om.gov.moh.eab.security.vo;

/**
 * @author farid.haq
 *
 */

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import om.gov.moh.eab.entity.vo.EntityVO;
import om.gov.moh.eab.utils.Utils;

@Entity
@Table(name = "MST_TB_ESTABLISHMENT_MAST")
public class InstitutionVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long instCode;
	private String instName;
	private String instNameNLS;
	private String instshName;
	private Integer instTypeCode;
	private String activeInst;
	private Integer walCode;
	private Long regCode;
	private Integer authorityType;
	private Set<EntityVO> entities;
	private String active;
	private Long authorotyType;
	private Long estTypeCode;
	

	/**
	 * @return the instCode
	 */
	@Id
	@Column(name = "EST_CODE")
	public Long getInstCode() {
		return instCode;
	}

	/**
	 * @param instCode
	 *            the instCode to set
	 */
	public void setInstCode(Long instCode) {
		this.instCode = instCode;
	}

	/**
	 * @return the instName
	 */
	@Column(name = "EST_NAME")
	public String getInstName() {
		return instName;
	}

	/**
	 * @param instName
	 *            the instName to set
	 */
	public void setInstName(String instName) {
		this.instName = instName;
	}

	/**
	 * @return the instshName
	 */
	@Transient
	public String getInstshName() {
		return instshName;
	}

	/**
	 * @param instshName
	 *            the instshName to set
	 */
	public void setInstshName(String instshName) {
		this.instshName = instshName;
	}

	/**
	 * @return the instTypeCode
	 */
	@Transient
	public Integer getInstTypeCode() {
		return instTypeCode;
	}

	/**
	 * @param instTypeCode
	 *            the instTypeCode to set
	 */
	public void setInstTypeCode(Integer instTypeCode) {
		this.instTypeCode = instTypeCode;
	}

	/**
	 * @return the activeInst
	 */
	@Column(name = "ACTIVE_YN")
	public String isActiveInst() {
		return activeInst;
	}

	/**
	 * @param activeInst
	 *            the activeInst to set
	 */
	public void setActiveInst(String activeInst) {
		this.activeInst = activeInst;
	}

	/**
	 * @return the walCode
	 */
	@Transient
	public Integer getWalCode() {
		return walCode;
	}

	/**
	 * @param walCode
	 *            the walCode to set
	 */
	public void setWalCode(Integer walCode) {
		this.walCode = walCode;
	}

	/**
	 * @return the regCode
	 */
	@Column(name = "REG_CODE")
	public Long getRegCode() {
		return regCode;
	}

	/**
	 * @param regCode
	 *            the regCode to set
	 */
	public void setRegCode(Long regCode) {
		this.regCode = regCode;
	}

	/**
	 * overriding existing toString Method and calling the generalised Method
	 */
	@Override
	public String toString() {
		return Utils.generalizedToString(this);
	}

	/**
	 * @return the authorityType
	 */
	@Transient
	public Integer getAuthorityType() {
		return authorityType;
	}

	/**
	 * @param authorityType
	 *            the authorityType to set
	 */
	public void setAuthorityType(Integer authorityType) {
		this.authorityType = authorityType;
	}

	@OneToMany(mappedBy = "institutionVO")
	public Set<EntityVO> getEntities() {
		return entities;
	}

	public void setEntities(Set<EntityVO> entities) {
		this.entities = entities;
	}

	@Column(name = "EST_NAME_NLS")
	public String getInstNameNLS() {
		return instNameNLS;
	}

	public void setInstNameNLS(String instNameNLS) {
		this.instNameNLS = instNameNLS;
	}

	@Transient
	public String getActiveInst() {
		return activeInst;
	}
	@Column(name="active_yn")
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	

	@Column(name="AUTHORITY_TYPE")
	public Long getAuthorotyType() {
		return authorotyType;
	}

	public void setAuthorotyType(Long authorotyType) {
		this.authorotyType = authorotyType;
	}

	@Column(name="ESTTYPE_CODE")
	public Long getEstTypeCode() {
		return estTypeCode;
	}

	public void setEstTypeCode(Long estTypeCode) {
		this.estTypeCode = estTypeCode;
	}

}
