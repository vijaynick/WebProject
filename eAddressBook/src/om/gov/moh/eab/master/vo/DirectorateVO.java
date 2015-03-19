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
@Table(name="EAB_DIRECTORATE_MAST")
public class DirectorateVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String direcotrateName;
	private String direcotrateNameNLS;
	
	private String direcotrateShortName;
	private String direcotrateShortNameNLS;
	private Long regionCode;
	

	@Id
	@Column(name = "directorate_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "directorate_name")
	public String getDirecotrateName() {
		return direcotrateName;
	}

	public void setDirecotrateName(String direcotrateName) {
		this.direcotrateName = direcotrateName;
	}
	@Column(name="directorate_name_nls")
	public String getDirecotrateNameNLS() {
		return direcotrateNameNLS;
	}

	public void setDirecotrateNameNLS(String direcotrateNameNLS) {
		this.direcotrateNameNLS = direcotrateNameNLS;
	}

	
	@Column(name = "directorate_sh_name_nls")
	public String getDirecotrateShortNameNLS() {
		return direcotrateShortNameNLS;
	}

	public void setDirecotrateShortNameNLS(String direcotrateShortNameNLS) {
		this.direcotrateShortNameNLS = direcotrateShortNameNLS;
	}

	@Column(name = "directorate_sh_name")
	public String getDirecotrateShortName() {
		return direcotrateShortName;
	}

	public void setDirecotrateShortName(String direcotrateShortName) {
		this.direcotrateShortName = direcotrateShortName;
	}

	@Column(name="REG_CODE")
	public Long getRegionCode() {
		return regionCode;
	}

	/**
	 * @param regionCode the regionCode to set
	 */
	public void setRegionCode(Long regionCode) {
		this.regionCode = regionCode;
	}

}
