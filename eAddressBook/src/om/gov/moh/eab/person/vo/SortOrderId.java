package om.gov.moh.eab.person.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SortOrderId implements Serializable {

	/**
	 * @author Sajeer
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "ENTITY_CODE")
	private Long entityCode;
	
	@Column(name = "DESIGNATION_ID")
	private Long designationId;

	/**
	 * @return the entityCode
	 */
	public Long getEntityCode() {
		return entityCode;
	}

	public SortOrderId(Long entityCode, Long designationId) {
		super();
		this.entityCode = entityCode;
		this.designationId = designationId;
	}

	/**
	 * @param entityCode the entityCode to set
	 */
	public void setEntityCode(Long entityCode) {
		this.entityCode = entityCode;
	}

	/**
	 * @return the designationId
	 */
	public Long getDesignationId() {
		return designationId;
	}

	/**
	 * @param designationId the designationId to set
	 */
	public void setDesignationId(Long designationId) {
		this.designationId = designationId;
	}
	

}
