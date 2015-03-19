package om.gov.moh.eab.master.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EAB_TB_DESIG_MAST")
public class DesignationVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String nameNLS;
	private String guestViewYN;

	@Id
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "NAME_NLS")
	public String getNameNLS() {
		return nameNLS;
	}

	public void setNameNLS(String nameNLS) {
		this.nameNLS = nameNLS;
	}

	@Column(name = "GUEST_VIEW_YN")
	public String getGuestViewYN() {
		return guestViewYN;
	}

	public void setGuestViewYN(String guestViewYN) {
		this.guestViewYN = guestViewYN;
	}
}
