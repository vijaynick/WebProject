/*
 * @(#)SystemException.java
 *
 * Copyright 2004 Ministry Of Health, Oman. All Rights Reserved.
 *
 * This software is the proprietary information of Ministry Of Health, Oman.
 * Use is subject to license terms.
 *
 */

package om.gov.moh.eab.exception;

/**
 * This is a generic exception for hims application. Any sort of generic errors
 * can be encapsulated to this exception.Add specific error code in this class
 * for getting the correct message.
 * Typical usage :-
 *      try {
 *          appointmentsDao.getAllAppointments();
 *      }catch (ResourceConnectException connectException) {
 *          SystemException exception = new SystemException(connectException);
 *          exception.setErrorCode(SystemException.DATABASE_UNAVAILABLE);
 *          throw exception;
 *      }
 *
 * @author Reji M.K.
 */

/*
 * Revision History
 * Revision		Date			Author					Description
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 *  0.1		    23Feb2004		Reji M.K.				Created the class
 *
 */

public class SystemException extends RootCarrierException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Static error code for Database unavailable
     */
    public static final String DATABASE_UNAVAILABLE = "CON001";

    /**
     * Static error code for service not accessible
     */
    public static final String SERVICE_NOT_ACCESSIBLE = "CON002";

    /**
	 * Static error code for lookup factory related error
     */
    public static final String RESOURCE_FACTORY_ERROR = "CON003";

    /**
     * Static error code for unexpected Database exception
     */
    public static final String UNEXPECTED_DB_ERROR = "CON004";

    /**
     * Static error code for unexpected server exception
     */
    public static final String UNEXPECTED_SERVER_ERROR = "CON005";

    /**
     * Static error code for access denied server exception
     */
    public static final String ACCESS_DENIED_ERROR = "CON006";

    /**
     * Static error code for sql errors
     */
    public static final String SQL_ERROR = "CON007";

    /**
     * Constructor that takes a throwable as argument. This is the preferred
     * constructor
     *
     * @param Throwable throwable
     */
    public SystemException(String errorCode) {
        super(errorCode);
    }

    /**
     * Constructor that accepts an errorCode and a throwable as argument.
     *
     * @param String errorCode
     * @param Throwable throwable
     */
    public SystemException(String errorCode,Throwable exception) {
        super(errorCode, exception);
    }
}