package om.gov.moh.eab.constants;

import java.util.HashMap;
import java.util.Map;

public interface EABConstants {

	public static final Long ROLE_SUPER_ADMIN = 1L;
	public static final Long ROLE_ADMIN = 2L;
	public static final Long ROLE_NORMAL = 3L;
	public static final Long ROLE_GUEST = 4L;
	public static final String SEARCH_TYPE_IMP = "IMP";
	public static final String SEARCH_TYPE_EMR = "EMR";
	public static final String SEARCH_TYPE_PERSON = "SEARCH_PERSON";
	public static final String SEARCH_TYPE_ENTITY = "SEARCH_ENTITY";	
	public static final String DIRECTORATE_COOKIE="DIRECOTORATE_SELECTED_VALUE";
	public static final String GOVERNORATE_COOKIE="GOVERNORATE_SELECTED_VALUE";
	public static final String PERSON_REF_DATA_CACHE_KEY="01100101";
	public static final String ENTITY_REF_DATA_CACHE_KEY="10011010";
	public static final String EAB_CATOGORY_PERSON="PERSON";
	public static final String EAB_CATOGORY_USER="USER";
	public static final String EAB_CATOGORY_DEPT="DEPARTMENT";
	public static final String EAB_CATOGORY_SECTION="SECTION";
	public static final String USERS_REPORT_NAME="users_report";
	public static final String PERSONS_REPORT_NAME_EN="persons_report_en";
	public static final String PERSONS_REPORT_NAME_AR="persons_report_ar";
	public static final String PDF="PDF";
	public static final String HTML="HTML";
	public static final String DOCX="DOCX";
	public static final Map<String, String> isdMap = new HashMap<String, String>(){{		
		
		put("968", "Oman");
		put("971", "UAE");
	}};
	
public static final Map<String, String> categoryMap = new HashMap<String, String>(){{		
		put("PERSON", "PERSON");
		put("USER", "USER");
		put("DEPARTMENT", "DEPARTMENT");
		put("SECTION", "SECTION");
	}};


}
