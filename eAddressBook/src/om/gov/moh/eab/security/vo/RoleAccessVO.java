/**
 * 
 */
package om.gov.moh.eab.security.vo;

import java.io.Serializable;

/**
 * @author farid.haq
 *
 */
@SuppressWarnings("serial")
public class RoleAccessVO implements Serializable {
	private RoleVO roleVO;
	private ModuleVO moduleVO;
	private String function;
	
	public RoleAccessVO(){
		super();
	}
	
	/**
	 * @param roleVO
	 * @param moduleVO
	 */
	public RoleAccessVO(ModuleVO moduleVO,String function) {
		super();
		this.moduleVO = moduleVO;
		this.function = function;
	}
	
	/**
	 * @return the roleVO
	 */
	public RoleVO getRoleVO() {
		return roleVO;
	}
	
	/**
	 * @param roleVO the roleVO to set
	 */
	public void setRoleVO(RoleVO roleVO) {
		this.roleVO = roleVO;
	}
	/**
	 * @return the moduleVO
	 */
	public ModuleVO getModuleVO() {
		return moduleVO;
	}
	/**
	 * @param moduleVO the moduleVO to set
	 */
	public void setModuleVO(ModuleVO moduleVO) {
		this.moduleVO = moduleVO;
	}
	/**
	 * @return the function
	 */
	public String getFunction() {
		return function;
	}
	/**
	 * @param function the function to set
	 */
	public void setFunction(String function) {
		this.function = function;
	}
}
