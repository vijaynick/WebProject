/*
 * @(#)RootCarrierException.java
 *
 * Copyright 2004 Ministry Of Health, Oman. All Rights Reserved.
 *
 * This software is the proprietary information of Ministry Of Health, Oman.
 * Use is subject to license terms.
 *
 */

package om.gov.moh.eab.exception;

/**
 * This class is mainly used as a carrier for all Root causes for
 * Exception conditions. Subclasses should document the exact type
 * the clients can expect as the root cause so that, they can narrow
 * the root cause and then find the real cause of the exceptions.
 * Derived classes of this class are normally used to carry
 * exception causes to other tiers/layers of the system so that
 * we achieve as near as 100% tier/layer decoupling without losing
 * valuable stack traces (with the help of the immediate superclass
 * of this class) as well as to carry real root causes (irrespective
 * of the Java Type of the root cause).
 * A typical use of the root cause is like: The root cause can
 * contain an array of Strings, each element containing error reasons
 * in the form of String so that, a Thick Swing client can show them
 * in a Dialogue box, at the same time, the whole stack trace can
 * be logged into some suitable Stream.
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

public abstract class RootCarrierException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Root Cause for the Exception condition
	 */
	private Object rootCause;

	/**
	 * The Error Code for the Exception
	 */
	private String errorCode;

	/**
	 * Constructs an Exception with no specified detail message
	 */
	protected RootCarrierException() {
		super();
	}

	/**
	 * Constructs a new exception with the specified errorCode
	 *
	 * @param errorCode - the errorCode
	 */
	protected RootCarrierException(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Constructs a new exception with the specified detail message and
	 * cause
	 *
	 * @param errorCode - the errorCode
	 * @param cause - the cause, the stacktrace of which is saved for
	 * 					later printing to the stream.
	 */
	protected RootCarrierException(String errorCode, Throwable cause) {
		super(cause.getMessage(), cause);
		this.errorCode = errorCode;
	}

	/**
	 * Constructs a new exception with the specified cause
	 *
	 * @param cause - the cause, the stacktrace of which is saved for
	 * 					later printing to the stream.
	 */
	protected RootCarrierException(Throwable cause) {
		super(cause);
	}

	/**
	 * The Root Cause for the Exception condition. This can be set in a
	 * meaningful way.
	 *
	 * @param rootCause - the Root Cause for the Exception.
	 * 						The parameter should be Serializable.
	 */
	public void setRootCause(Object rootCause) {
		this.rootCause = rootCause;
	}

	/**
	 * Retrieves the Root Cause of the Exception.
	 * Subclasses should clearly document the exact type the clients
	 * can expect as the root cause.
	 *
	 * @return - the Root Cause, which has to be narrowed to the exact type.
	 */
	public Object getRootCause() {
		return rootCause;
	}

	/**
	 * The Error Code for the Exception condition.
	 *
	 * @param errorCode - This is the error code for the exception occurred.
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Retrieves the Error Code of the Exception.
	 *
	 * @return errorCode - This is the error code for the exception occurred.
	 */
	public String getErrorCode() {
		return errorCode;
	}
}