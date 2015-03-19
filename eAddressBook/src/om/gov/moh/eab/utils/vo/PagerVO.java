/*
 * PagerVO.java
 * Jun 14, 2011
 * Copyright 2011 Ministry Of Health, Oman. All Rights Reserved.
 *
 * This software is the proprietary information of Ministry Of Health, Oman.
 * Use is subject to license terms.
 *
 */
package om.gov.moh.eab.utils.vo;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author farid.haq
 */

/*
 * Revision History
 * Revision		Date			Author					Description
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 *	0.1		    May-21-2013		farid.haq.				Created the class
 *
 */
public class PagerVO implements Serializable {
	
	private int totalRows;
	
	private int startCount;
	
	private int endCount;
	
	private int indexLength;
	
	private int rowsPerPage; 
	
	
	public PagerVO(){
		super();
	}
	
	

	/**
	 * @param startCount
	 * @param rowsPerPage
	 */
	public PagerVO(int startCount, int rowsPerPage) {
		super();
		this.startCount = startCount;
		this.rowsPerPage = rowsPerPage;
	}

	/**
	 * @return the totalRows
	 */
	public int getTotalRows() {
		return totalRows;
	}

	/**
	 * @param totalRows the totalRows to set
	 */
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	/**
	 * @return the startCount
	 */
	public int getStartCount() {
		return startCount;
	}

	/**
	 * @param startCount the startCount to set
	 */
	public void setStartCount(int startCount) {
		this.startCount = startCount;
	}

	/**
	 * @return the endCount
	 */
	public int getEndCount() {
		return endCount;
	}

	/**
	 * @param endCount the endCount to set
	 */
	public void setEndCount(int endCount) {
		this.endCount = endCount;
	}

	/**
	 * @return the indexLength
	 */
	public int getIndexLength() {
		return indexLength;
	}

	/**
	 * @param indexLength the indexLength to set
	 */
	public void setIndexLength(int indexLength) {
		this.indexLength = indexLength;
	}

	/**
	 * @return the rowsPerPage
	 */
	public int getRowsPerPage() {
		return rowsPerPage;
	}

	/**
	 * @param rowsPerPage the rowsPerPage to set
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
	
}
