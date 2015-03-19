package om.gov.moh.eab.security.vo;

import java.util.List;

/**
 * @author farid.haq
 *
 */
public class ModuleVO {
	private Long moduleId;
	private String moduleName;
	private String moduleDescription;
	private List<ModulePermissionVO> modulePermissionList;
	
	
	public ModuleVO(){
		super();
	}
	
	public ModuleVO(Long moduleId){
		super();
		this.moduleId = moduleId;
	}
	
	/**
	 * @return the moduleId
	 */
	public Long getModuleId() {
		return moduleId;
	}
	/**
	 * @param moduleId the moduleId to set
	 */
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}
	/**
	 * @param moduleName the moduleName to set
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	/**
	 * @return the moduleDescription
	 */
	public String getModuleDescription() {
		return moduleDescription;
	}
	/**
	 * @param moduleDescription the moduleDescription to set
	 */
	public void setModuleDescription(String moduleDescription) {
		this.moduleDescription = moduleDescription;
	}
	/**
	 * @return the modulePermissionList
	 */
	public List<ModulePermissionVO> getModulePermissionList() {
		return modulePermissionList;
	}
	/**
	 * @param modulePermissionList the modulePermissionList to set
	 */
	public void setModulePermissionList(List<ModulePermissionVO> modulePermissionList) {
		this.modulePermissionList = modulePermissionList;
	}
	
	
}
