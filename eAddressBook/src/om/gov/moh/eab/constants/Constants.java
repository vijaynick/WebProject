/*
 * Constants.java 
 * Nov 5, 2012 
 * Copyright 2011 Ministry Of Health, Oman. All Rights Reserved.
 *
 * This software is the proprietary information of Ministry Of Health, Oman.
 * Use is subject to license terms.
 */
package om.gov.moh.eab.constants;

/**
 * @author Abdul Jaleel C
 */

/*
 * Revision History Revision Date Author Description - - - - - - - - - - - - - -
 * - - - - - - - - - - - - - - - - - 0.1 Nov 5, 2012 Abdul Jaleel C. Created the
 * class
 */
public class Constants {
	
	/*
	 * session params
	 */
	
	public static final String MESSAGE_ID = "MMI";
	public static final String SESSION_PARAM_LOGGED_USER = "loggeduser";
	public static final String SESSION_PARAM_MODULE = "module";
	public static final String SESSION_PARAM_LANG = "lang";
	public static final String SESSION_PARAM_MODULE_SUB = "submodule";
	
	
	/*
	 * language constants
	 */
	public static final String LANGUAGE_ARABIC = "A";
	public static final String LANGUAGE_ENGLISH = "E";
	
	public static final String LANG_AR = "ar";
	public static final String LANG_EN = "en";
	
	/*
	 * message constants
	 */
	public static final String MESSAGE_PRIORITY_NORMAL_CODE = "N";
	public static final String MESSAGE_PRIORITY_URGENT_CODE = "U";
	public static final String MESSAGE_PRIORITY_LOW_CODE = "L";
	
	public static final String MESSAGE_PRIORITY_NORMAL_LABEL = "Normal";
	public static final String MESSAGE_PRIORITY_URGENT_LABEL = "Urgent";
	public static final String MESSAGE_PRIORITY_LOW_LABEL = "Low";
	
	public static final String MESSAGE_STATUS_SENT_CODE = "S";
	public static final String MESSAGE_STATUS_QUE_CODE = "Q";
	public static final String MESSAGE_STATUS_ERROR_CODE = "E";
	
	public static final String MESSAGE_MC_COMMAND_INVALID_PHONE = "11";
	public static final String MESSAGE_MC_COMMAND_THROTTLING_ERROR = "88";
	public static final String MESSAGE_MC_COMMAND_INVALID_PROVIDER = "8";
	public static final String MESSAGE_MC_COMMAND_SUCCESS = "0";
	
	public static final String MESSAGE_STATUS_SENT_LABEL = "Sent";
	public static final String MESSAGE_STATUS_QUE_LABEL = " Queue";
	public static final String MESSAGE_STATUS_ERROR_LABEL = "Error";
    
	public static final int MESSAGE_INST_ID = -101;
	public static final String MESSAGE_CAT_ID = "MMI";
	
	/*
	 * modles and submodules 
	 */
	public static final String MODULE_SMS = "SMS";
	public static final String MODULE_EMAIL = "EMAIL";
	public static final String MODULE_MANAGE = "MANAGE";
	public static final String MODULE_REPORT = "REPORT";
	public static final String MODULE_LOGS = "LOGS";

	public static final String MODULE_SUB_SMS_COMPOSE = "composesms";
	public static final String MODULE_SUB_SMS_OUTBOX = "outboxsms";
	public static final String MODULE_SUB_SMS_BULK_SMS = "bulksms";
	public static final String MODULE_SUB_MANAGE_CONTACTS = "contacts";
	public static final String MODULE_SUB_MANAGE_GROUPS = "groups";
	public static final String MODULE_SUB_MANAGE_USERS = "users";
	public static final String MODULE_SUB_MANAGE_ROLES = "roles";
	public static final String MODULE_SUB_REPORT_SMS_COUNT = "smscountreport";
	public static final String MODULE_SUB_LOG_EMAIL = "logemail";
	public static final String MODULE_SUB_LOG_SMS = "logsms";
	public static final String MODULE_SUB_LOG_BULK = "logbulk";
	public static final String MODULE_SUB_LOG_AUDIT= "logaudit";
	public static final String MODULE_SUB_EMAIL_COMPOSE = "composeemail";
	public static final String MODULE_SUB_EMAIL_OUTBOX = "outboxemail";
	
	/*
	 * Role Modules and Privileges
	 */
	public static final Long ROLE_MODULE_USERS_ID = 1L;
	public static final Long ROLE_MODULE_CONTACTS_ID = 2L;
	public static final Long ROLE_MODULE_GROUPS_ID = 3L;
	public static final Long ROLE_MODULE_ROLES_ID = 4L;
	public static final Long ROLE_MODULE_SMS_ID = 5L;
	public static final Long ROLE_MODULE_EMAIL_ID = 6L;
	public static final Long ROLE_MODULE_REPORTS_ID = 7L;
	public static final Long ROLE_MODULE_LOGS_ID = 8L;
	
	
	public static final String ROLE_PRIV_VIEW = "VIEW";
	public static final String ROLE_PRIV_ADD = "ADD";
	public static final String ROLE_PRIV_EDIT = "EDIT";
	public static final String ROLE_PRIV_DELETE = "DELETE";
	public static final String ROLE_PRIV_OUTBOX = "OUTBOX";
	public static final String ROLE_PRIV_COMPOSE = "COMPOSE";
	public static final String ROLE_PRIV_BULK = "BULK";
	/*
	 * struts params
	 */

	public static final String STRUTS_PARAM_LOGIN_PAGE = "login";
	public static final String STRUTS_PARAM_SMS_HOME = "smshome";
	public static final String STRUTS_PARAM_EMAIL_HOME = "emailhome";
	public static final String STRUTS_PARAM_MANAGE_HOME = "managehome";
	public static final String STRUTS_PARAM_REPORT_HOME = "reporthome";
	public static final String STRUTS_PARAM_LOGS_HOME = "logshome";

	public static final String STRUTS_PARAM_COMPOSE_PAGE = "compose";
	
	public static final String STRUTS_PARAM_EMAIL_COMPOSE_PAGE = "emailcompose";
	
	public static final String STRUTS_PARAM_LOAD_ADDRESS_POPUP = "addresspopup";
	public static final String STRUTS_PARAM_LOAD_GROUP_POPUP = "grouppopup";
	
	public static final String STRUTS_PARAM_LOAD_OUTBOX = "outboxload";
	public static final String STRUTS_PARAM_LOAD_GROUP_SEARCH = "loadgroupsearch";
	public static final String STRUTS_PARAM_LOAD_SEARCH_CONTACT="contactsearch";
	public static final String STRUTS_PARAM_LOAD_EDIT_CONTACT="contactedit";
	public static final String STRUTS_PARAM_LOAD_ADD_CONTACT="contactadd";
	public static final String STRUTS_PARAM_LOAD_ADD_GROUP = "loadaddgroup";
	public static final String STRUTS_PARAM_LOAD_ROLE_SEARCH="loadrolesearch";
	public static final String STRUTS_PARAM_LOAD_ROLE_ADD="loadroleadd";
	public static final String STRUTS_PARAM_LOAD_ADD_USER="loadadduser";
	public static final String STRUTS_PARAM_FORWARD_USER_SEARCH="forwardUserSearch";
	public static final String STRUTS_PARAM_LOAD_SEARCH_USER="loadsearchusers";
	public static final String STRUTS_PARAM_LOAD_CHANGE_PASSWORD="loadchangepwd";
	public static final String STRUTS_PARAM_LOAD_SMS_LOG ="loadsmslog";
	public static final String STRUTS_PARAM_LOAD_REPORT_SMS_COUNT="loadreportsmscount";
	public static final String STRUTS_PARAM_AFTER_ADDEDIT_FORWARD_SEARCH = "forwardsearch";
	/*
	 * date constants
	 */
	public static final String DATE_FORMAT_dd_MM_yyyy = "dd-MM-yyyy";
	public static final String DATE_FORMAT_dd_MM_yyyy_HH_mm = "dd-MM-yyyy HH:mm";
	public static final String DATE_FORMAT_ORACLE_dd_MM_yyyy_HH_mm = "DD-MM-YYYY HH24:MI";

	/*
	 * Audit constants
	 */
	public static final String AUDIT_CATEGORY_LOGIN = "LOGN";
	public static final String AUDIT_CATEGORY_USER = "USER";
	public static final String AUDIT_CATEGORY_CONTACT="CNTCT";
	public static final String AUDIT_CATEGORY_ROLE="ROLE";
	public static final String AUDIT_CATEGORY_GROUP="GROUP";
	public static final String AUDIT_CATEGORY_SMS="SMS";
	
	public static final String AUDIT_CATEGORY_LOGIN_STATUS = "Login";
	public static final String AUDIT_CATEGORY_USER_STATUS = "User";
	public static final String AUDIT_CATEGORY_CONTACT_STATUS="Contact";
	public static final String AUDIT_CATEGORY_ROLE_STATUS="Role";
	public static final String AUDIT_CATEGORY_GROUP_STATUS="Group";
	public static final String AUDIT_CATEGORY_SMS_STATUS="Sms";
	
	public static final String[][] AUDIT_STATUS_OPTIONS = {
		 {AUDIT_CATEGORY_LOGIN,AUDIT_CATEGORY_LOGIN_STATUS},
		 {AUDIT_CATEGORY_USER,AUDIT_CATEGORY_USER_STATUS},
		 {AUDIT_CATEGORY_CONTACT,AUDIT_CATEGORY_CONTACT_STATUS},
		 {AUDIT_CATEGORY_ROLE ,AUDIT_CATEGORY_ROLE_STATUS},
		 {AUDIT_CATEGORY_GROUP,AUDIT_CATEGORY_GROUP_STATUS},
		 {AUDIT_CATEGORY_SMS,AUDIT_CATEGORY_SMS_STATUS}
		 };
		

	/*
	 * User constants
	 */
	public static final String USER_ACTIVE_Y = "Y";
	public static final String USER_ACTIVE_N = "N";
	
	/*
	 * pagination params
	 */
	public static final int ROWS_PER_PAGE = 10;
	public static final int AUDIT_ROWS_PER_PAGE = 20;
	public static final int INDEX_LENGTH = 5;
	
	
	/*
	 * Report Params
	 */
	public static final String REPORT_FORMAT_PDF = "PDF";
	public static final int REPORT_FORMAT_PDF_VALUE = 1;
	public static final String REPORT_FORMAT_HTML = "HTML";
	public static final int REPORT_FORMAT_HTML_VALUE = 2;
    public static final String REPORT_FORMAT_XLS = "XLS";
	public static final int REPORT_FORMAT_XLS_VALUE = 3;
	
	/*
	 * Email constants
	 */
	public static final String TO_EMAIL_EMPTY = "TO Recipient cannot be empty.";
	public static final String TO_EMAIL_PATTERN = "The address in the TO field was not recognised. Please make sure that all addresses are properly formed.";
	public static final String CC_EMAIL_PATTERN = "The address in the CC field was not recognised. Please make sure that all addresses are properly formed.";
	public static final String BCC_EMAIL_PATTERN = "The address in the BCC field was not recognised. Please make sure that all addresses are properly formed.";
	public static final String SUBJECT_EMPTY = "Subject cannot be empty.";
	public static final String MESSAGE_EMPTY = "Message cannot be empty.";
	public  static final String RECIPIENT_EMPTY = "Atleast one recipient should be needed to send email.";
	public static final String STRUTS_PARAM_LOAD_EMAIL_OUTBOX = "emailoutboxload";

}
