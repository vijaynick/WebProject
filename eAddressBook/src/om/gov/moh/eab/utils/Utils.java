/*
 * Util.java 
 * Nov 5, 2012 
 * Copyright 2011 Ministry Of Health, Oman. All Rights Reserved.
 *
 * This software is the proprietary information of Ministry Of Health, Oman.
 * Use is subject to license terms.
 */
package om.gov.moh.eab.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import om.gov.moh.eab.constants.Constants;
import om.gov.moh.eab.security.vo.RoleAccessVO;
import om.gov.moh.eab.security.vo.RoleVO;
import om.gov.moh.eab.security.vo.UserVO;
import om.gov.moh.eab.utils.vo.SelectionVO;


/**
 * @author farid.haq
 */

/*
 * Revision History
 * Revision		Date			Author		       Description
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 *	0.1		    May 21, 2013		farid.haq.	   Created the class
 *
 */
public class Utils {
	
	
	
	public static String generalizedToString(Object target) {
		if(target==null) return "";
		StringBuilder b = new StringBuilder(target.getClass().getName() + "{");
		for (Field f : target.getClass().getDeclaredFields()) {
			try {
				f.setAccessible(true);
				b.append(f.getName() + "=" + f.get(target) + " ");
			} catch (IllegalAccessException e) {
				System.out.print("Error in to String" + target.getClass());
			}

		}
		b.append('}');
		return b.toString();
	}
	
	public static String getUserIP(){
		//HttpServletRequest request = ServletActionContext.getRequest();	
		HttpServletRequest request = null;
		String clientIP=request.getHeader("x-forwarded-for");
		if(Utils.trim(clientIP).equals(""))clientIP=request.getRemoteAddr();
		
		return clientIP;
	}
	
	public static void setSessionAttribute(String param,Object value){
		//ServletActionContext.getRequest().getSession().setAttribute(param,value);
	}
	
	public static void removeSessionAttribute(String param){
       // ServletActionContext.getRequest().getSession().removeAttribute(param);
    }
	
	public static String trim(String value){
		return value==null?"":value.trim();
	}
	
	public static boolean isEmpty(String value){
		if(value==null)return true;
		return "".equals(value.trim());
	}
	public static boolean isEmpty(List<Object> list){
		if(list==null||list.size()==0)return true;
		return false;
	}
	
	public static boolean validateGsm(String value){
		
		if(isEmpty(value)||  value.length()!=8) return false;
		try{Integer.parseInt(value);}
		catch(NumberFormatException nfe) {return false;}
		//List<String> invalidStartingDigits = Arrays.asList("1", "2", "4", "0", "8"); 
		//if (invalidStartingDigits.contains(value.substring(0, 1)))  return false;
		return true;
	}
	
	
	public static boolean validateEmail(String value){
		if(isEmpty(value) ||  value.length()<2)
			return false;
		return true;
	}
	
	public static boolean validateEmailPattern(String value){
		if(isEmpty(value))return false;
		String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		return Pattern.compile(EMAIL_PATTERN).matcher(value).matches();
	}
	
	public static String getStackTraceAsString(Exception exception) {

		if (exception == null)
			return "";
		String res = "";
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try {
			pw.print(" [ ");
			pw.print(exception.getClass().getName());
			pw.print(" ] ");
			pw.print(exception.getMessage());
			exception.printStackTrace(pw);
			res = sw.toString();
			pw.close();
			sw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	public static boolean hasModulePermission(String moduleName){
		if(Constants.MODULE_SMS.equals(moduleName))//SMS
			return hasPagePermission(Constants.MODULE_SUB_SMS_COMPOSE)||hasPagePermission(Constants.MODULE_SUB_SMS_OUTBOX)||hasPagePermission(Constants.MODULE_SUB_SMS_BULK_SMS); 
		if(Constants.MODULE_EMAIL.equals(moduleName))//EMAIL
			return hasPagePermission(Constants.MODULE_SUB_SMS_COMPOSE)||hasPagePermission(Constants.MODULE_SUB_SMS_OUTBOX);
		else if(Constants.MODULE_MANAGE.equals(moduleName))//Manage
			return hasPagePermission(Constants.MODULE_SUB_MANAGE_CONTACTS)||hasPagePermission(Constants.MODULE_SUB_MANAGE_GROUPS)
				||hasPagePermission(Constants.MODULE_SUB_MANAGE_USERS)||hasPagePermission(Constants.MODULE_SUB_MANAGE_ROLES);
		else if(Constants.MODULE_REPORT.equals(moduleName))//Report
			return hasPagePermission(Constants.MODULE_SUB_REPORT_SMS_COUNT);
		else if(Constants.MODULE_LOGS.equals(moduleName))//Log
			return hasPagePermission(Constants.MODULE_SUB_LOG_SMS);
		
		return false;
	}
	
	public static boolean hasPagePermission(String pageId){
		if(Constants.MODULE_SUB_SMS_COMPOSE.equals(pageId))
			return isUserPrivileged(Constants.ROLE_MODULE_SMS_ID,Constants.ROLE_PRIV_COMPOSE);
		else if(Constants.MODULE_SUB_SMS_OUTBOX.equals(pageId))
			return isUserPrivileged(Constants.ROLE_MODULE_SMS_ID,Constants.ROLE_PRIV_OUTBOX);
		else if(Constants.MODULE_SUB_SMS_BULK_SMS.equals(pageId))
			return isUserPrivileged(Constants.ROLE_MODULE_SMS_ID,Constants.ROLE_PRIV_BULK);
		else if(Constants.MODULE_SUB_MANAGE_CONTACTS.equals(pageId))
			return isUserPrivileged(Constants.ROLE_MODULE_CONTACTS_ID,Constants.ROLE_PRIV_VIEW);
		else if(Constants.MODULE_SUB_MANAGE_GROUPS.equals(pageId))
			return isUserPrivileged(Constants.ROLE_MODULE_GROUPS_ID,Constants.ROLE_PRIV_VIEW);
		else if(Constants.MODULE_SUB_MANAGE_USERS.equals(pageId))
			return isUserPrivileged(Constants.ROLE_MODULE_USERS_ID,Constants.ROLE_PRIV_VIEW)||isUserPrivileged(Constants.ROLE_MODULE_USERS_ID,Constants.ROLE_PRIV_ADD)
			||isUserPrivileged(Constants.ROLE_MODULE_USERS_ID,Constants.ROLE_PRIV_EDIT)||isUserPrivileged(Constants.ROLE_MODULE_USERS_ID,Constants.ROLE_PRIV_DELETE);
		else if(Constants.MODULE_SUB_MANAGE_ROLES.equals(pageId))
			return isUserPrivileged(Constants.ROLE_MODULE_ROLES_ID,Constants.ROLE_PRIV_VIEW)||isUserPrivileged(Constants.ROLE_MODULE_ROLES_ID,Constants.ROLE_PRIV_ADD)
			||isUserPrivileged(Constants.ROLE_MODULE_ROLES_ID,Constants.ROLE_PRIV_EDIT)||isUserPrivileged(Constants.ROLE_MODULE_ROLES_ID,Constants.ROLE_PRIV_DELETE);
		else if(Constants.MODULE_SUB_REPORT_SMS_COUNT.equals(pageId))
			return isUserPrivileged(Constants.ROLE_MODULE_REPORTS_ID,Constants.ROLE_PRIV_VIEW);
		else if(Constants.MODULE_SUB_LOG_SMS.equals(pageId))
			return isUserPrivileged(Constants.ROLE_MODULE_LOGS_ID,Constants.ROLE_PRIV_VIEW);
		else if(Constants.MODULE_SUB_EMAIL_COMPOSE.equals(pageId))
			return isUserPrivileged(Constants.ROLE_MODULE_EMAIL_ID, Constants.ROLE_PRIV_COMPOSE);
		else if(Constants.MODULE_SUB_EMAIL_OUTBOX.equals(pageId))
			return isUserPrivileged(Constants.ROLE_MODULE_EMAIL_ID, Constants.ROLE_PRIV_OUTBOX);
		
		return false;
	}
	
	public static boolean isUserPrivileged(Long moduleId,String privConstant){
		//UserVO userVO =  getSessionUser();
		/*UserVO userVO =  null;
		
		if(userVO==null || userVO.getRoleList()==null || privConstant==null)return false;
		
		for(RoleVO roleVO:userVO.getRoleList())
			if(roleVO.getRoleAccessList()!=null)
				for(RoleAccessVO roleAccessVO:roleVO.getRoleAccessList()){
					if(privConstant.equals(roleAccessVO.getFunction()) && moduleId.equals(roleAccessVO.getModuleVO().getModuleId())) 
						return true;
				}
		*/
		return false;
	}
	
	//it-samsudeen
	public static List<SelectionVO> getCategoryList() {
		List<SelectionVO> auditStatusList = new ArrayList<SelectionVO>();
		SelectionVO selectionVO = null;
		String[][] availableStatus = Constants.AUDIT_STATUS_OPTIONS;
		for (int i = 0; i < availableStatus.length; i++) {
			selectionVO = new SelectionVO();
			selectionVO.setId(availableStatus[i][0]);
			selectionVO.setValue(availableStatus[i][1]);
			auditStatusList.add(selectionVO);
		}
		return auditStatusList;
	}
	
	public static String setArabicEncodedString(String msgText) {
        try {
            return new String(msgText.getBytes("windows-1256"), "iso8859-1");
        } catch (UnsupportedEncodingException e) {
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }
	
	/**
	 *
	 * @author it-ismail
	 */
    public static String getArabicEncodedString(String arg) {
	        try {
	            return new String(arg.getBytes("iso8859-1"), "windows-1256");
	        } catch (UnsupportedEncodingException
	                e) {
	        } catch (Exception e) {
	            // TODO: handle exception
	        }
	        return null;
	    }


	//it-sikandar
	
	public static String getTerminalName(HttpServletRequest request) throws Exception {  
        String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        
        String hostName = InetAddress.getByName(ip).getHostName();
        return hostName;  
    }  

}
