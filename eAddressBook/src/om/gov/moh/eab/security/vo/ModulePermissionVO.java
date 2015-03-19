/*
 * ModulePermissionVO.java 
 * Dec 2, 2012 
 * Copyright 2011 Ministry Of Health, Oman. All Rights Reserved.
 *
 * This software is the proprietary information of Ministry Of Health, Oman.
 * Use is subject to license terms.
 */
package om.gov.moh.eab.security.vo;

import java.io.Serializable;

/**
 * @author farid.haq
 *
 */

/*
 * Revision History
 * Revision		Date			Author		       Description
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 *	0.1		    May 21, 2013		farid.haq.	   Created the class
 *
 */
public class ModulePermissionVO implements Serializable{
	
	private ModuleVO moduleVO;
	
	private String function;


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
