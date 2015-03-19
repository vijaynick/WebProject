/*
 * SelectionVO.java
 * July 3, 2011
 * Copyright 2011 Ministry Of Health, Oman. All Rights Reserved.
 *
 * This software is the proprietary information of Ministry Of Health, Oman.
 * Use is subject to license terms.
 *
 */
package om.gov.moh.eab.utils.vo;

import java.io.Serializable;

import om.gov.moh.eab.utils.Utils;

/**
 * @author farid.haq
 *
 */

/*
 * Revision History
 * Revision		Date			Author					Description
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 *	0.1		    May 21, 2013		    farid.haq.				Created the class
 *
 */
public class SelectionVO implements Serializable {

	/**
	 * General Id attribute
	 */
	private String id;

	/**
	 * General value for the id attribute. This is displayed in the screen
	 */
	private String value;

	/**
	 * Default constructor
	 */
	public SelectionVO() {
	}

	/**
	 * Constructor which takes id and value
	 */
	public SelectionVO(String id, String value) {
		this.id = id;
		this.value = value;
	}
		
	
	/**
	 * Get method for id attribute
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Get method for value attribute
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Set method for id attribute
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Set method for value attribute
	 * @param string
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * overriding existing toString Method and calling the generalized Method
	 */
	@Override
	public String toString()
	{
		return Utils.generalizedToString(this);
	}
}
