package om.gov.moh.eab.master.vo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import om.gov.moh.eab.entity.vo.EntityVO;

@Entity
@Table(name = "MST_TB_REGION_MAST")
public class GovernarateVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String govName;
	private String govNameNLS;
	private String govShortName;
	private String govShortNameNLS;
	private Long regionCode;
	
	
	@Id
	@Column(name = "REG_CODE")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "REG_NAME")
	public String getGovName() {
		return govName;
	}

	public void setGovName(String govName) {
		this.govName = govName;
	}

	@Column(name = "REG_NAME_NLS")
	public String getGovNameNLS() {
		return govNameNLS;
	}

	
	public void setGovNameNLS(String govNameNLS) {
		this.govNameNLS = govNameNLS;
	}

	@Column(name = "REG_SH_NAME")
	public String getGovShortName() {
		return govShortName;
	}

	public void setGovShortName(String govShortName) {
		this.govShortName = govShortName;
	}

	@Column(name = "REG_SH_NAME_NLS")
	public String getGovShortNameNLS() {
		return govShortNameNLS;
	}

	public void setGovShortNameNLS(String govShortNameNLS) {
		this.govShortNameNLS = govShortNameNLS;
	}
	
	@Column(name = "CMAST_REG_CODE")
	public Long getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(Long regionCode) {
		this.regionCode = regionCode;
	}
	
	
}
