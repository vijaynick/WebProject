package om.gov.moh.eab.person.vo;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "EAB_PERSON_SORT_ORDER")
public class SortOrderVO implements Serializable {

	/**
	 * @author Sajeer
	 */
	private static final long serialVersionUID = 1L;
	
	
	@EmbeddedId
	@AttributeOverrides({@AttributeOverride(name = "entityCode" , column = @Column(name = "ENTITY_CODE" , nullable = false)),
		@AttributeOverride(name = "designationId", column = @Column(name = "DESIGNATION_ID", nullable = false))}
			)
	private SortOrderId id;

	@Column(name = "SORT_ORDER")
	private Integer sortOrder;

		/**
	 * @return the sortOrder
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder
	 *            the sortOrder to set
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the id
	 */
	public SortOrderId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(SortOrderId id) {
		this.id = id;
	}

	public SortOrderVO(SortOrderId id, Integer sortOrder) {
		super();
		this.id = id;
		this.sortOrder = sortOrder;
	}

}
