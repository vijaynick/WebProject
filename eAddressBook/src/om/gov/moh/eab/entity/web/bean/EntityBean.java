//package om.gov.moh.eab.entity.web.bean;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.faces.model.SelectItem;
//
//import om.gov.moh.eab.entity.vo.EntityVO;
//
//import om.gov.moh.eab.master.vo.DepartmentVO;
//import om.gov.moh.eab.master.vo.DirectorateVO;
//import om.gov.moh.eab.master.vo.GovernarateVO;
//import om.gov.moh.eab.master.vo.SectionVO;
//import om.gov.moh.eab.security.vo.InstitutionVO;
//import om.gov.moh.eab.utils.ArabicConversion;
//
//@SuppressWarnings("all")
//public class EntityBean implements Serializable {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	
//	
//	private List<EntityVO> searchedListHome = new ArrayList<EntityVO>();
//
//	public List<DirectorateVO> directorateList = new ArrayList<DirectorateVO>();
//	public List<SectionVO> sectionList = new ArrayList<SectionVO>();
//	public List<InstitutionVO> instituteList = new ArrayList<InstitutionVO>();
//	public List<DepartmentVO> departmentList = new ArrayList<DepartmentVO>();
//	public List<GovernarateVO> governrateList = new ArrayList<GovernarateVO>();
//
//	private List<DirectorateVO> directorateListNLS = new ArrayList<DirectorateVO>();
//	private List<SectionVO> sectionListNLS = new ArrayList<SectionVO>();
//	private List<InstitutionVO> instituteListNLS = new ArrayList<InstitutionVO>();
//	private List<DepartmentVO> departmentListNLS = new ArrayList<DepartmentVO>();
//	private List<GovernarateVO> governrateListNLS = new ArrayList<GovernarateVO>();
//
//	private List<GovernarateVO> cookieCmbGovList = new ArrayList<GovernarateVO>();
//	private List<DirectorateVO> cookieCmbDirList = new ArrayList<DirectorateVO>();
//
//	private List<GovernarateVO> cookieCmbGovListNLS = new ArrayList<GovernarateVO>();
//	private List<DirectorateVO> cookieCmbDirListNLS = new ArrayList<DirectorateVO>();
//
//
//
//	public List getDirectorateList() {
//		List items = new ArrayList();
//		for (DirectorateVO directorateVO : directorateList) {
//			items.add(new SelectItem(directorateVO.getId(), directorateVO.getDirecotrateName()));
//		}
//
//		return items;
//	}
//
//	public void setDirectorateList(List<DirectorateVO> directorateList) {
//		this.directorateList = directorateList;
//	}
//
//	public List<SelectItem> getSectionList() {
//		List items = new ArrayList();
//		for (SectionVO sectionVO : sectionList) {
//			items.add(new SelectItem(sectionVO.getId(), (sectionVO.getSectionName()==null ? "" : sectionVO.getSectionName())));
//		}
//
//		return items;
//
//	}
//
//	public void setSectionList(List<SectionVO> sectionList) {
//		this.sectionList = sectionList;
//	}
//
//	public List<SelectItem> getInstituteList() {
//		List items = new ArrayList();
//		for (InstitutionVO institutionVO : instituteList) {
//			items.add(new SelectItem(institutionVO.getInstCode(), institutionVO.getInstName()));
//		}
//
//		return items;
//
//	}
//
//	public void setInstituteList(List<InstitutionVO> instituteList) {
//		this.instituteList = instituteList;
//	}
//
//	public List<SelectItem> getDepartmentList() {
//		List items = new ArrayList();
//		for (DepartmentVO departmentVO : departmentList) {
//			items.add(new SelectItem(departmentVO.getId(), (departmentVO.getDepartmentName() ==null ? "" : departmentVO.getDepartmentName())));
//		}
//
//		return items;
//
//	}
//
//	public void setDepartmentListList(List<DepartmentVO> departmentList) {
//		this.departmentList = departmentList;
//	}
//
//	public List<DirectorateVO> getDirectorateListNLS() {
//		List items = new ArrayList();
//		for (DirectorateVO institutionVO : directorateList) {
//			items.add(new SelectItem(institutionVO.getId(), ArabicConversion.getArabicEncodedString(institutionVO.getDirecotrateNameNLS())));
//		}
//
//		return items;
//
//	}
//
//	public void setDirectorateListNLS(List<DirectorateVO> directorateListNLS) {
//		this.directorateListNLS = directorateListNLS;
//	}
//
//	public List<SectionVO> getSectionListNLS() {
//		List items = new ArrayList();
//		for (SectionVO institutionVO : sectionList) {
//			items.add(new SelectItem(institutionVO.getId(), ArabicConversion.getArabicEncodedString(institutionVO.getSectionNameNLS())));
//		}
//
//		return items;
//
//	}
//
//	public void setSectionListNLS(List<SectionVO> sectionListNLS) {
//		this.sectionListNLS = sectionListNLS;
//	}
//
//	public List<InstitutionVO> getInstituteListNLS() {
//		List items = new ArrayList();
//		for (InstitutionVO institutionVO : instituteList) {
//			items.add(new SelectItem(institutionVO.getInstCode(), ArabicConversion.getArabicEncodedString(institutionVO.getInstNameNLS())));
//		}
//
//		return items;
//
//	}
//
//	public void setInstituteListNLS(List<InstitutionVO> instituteListNLS) {
//		this.instituteListNLS = instituteListNLS;
//	}
//
//	public List<DepartmentVO> getDepartmentListNLS() {
//
//		List items = new ArrayList();
//		for (DepartmentVO institutionVO : departmentList) {
//			items.add(new SelectItem(institutionVO.getId(), ArabicConversion.getArabicEncodedString(institutionVO.getDepartmentNameNLS())));
//		}
//
//		return items;
//
//	}
//
//	public void setDepartmentListNLS(List<DepartmentVO> departmentListNLS) {
//		this.departmentListNLS = departmentListNLS;
//	}
//
//	public void setDepartmentList(List<DepartmentVO> departmentList) {
//		this.departmentList = departmentList;
//	}
//
//	
//
//	public List getGovernrateList() {
//		List items = new ArrayList();
//		for (GovernarateVO governarateVO : governrateList) {
//			items.add(new SelectItem(governarateVO.getId(), governarateVO.getGovName()));
//		}
//
//		return items;
//
//	}
//
//	public void setGovernrateList(List<GovernarateVO> governrateList) {
//		this.governrateList = governrateList;
//	}
//
//	public List<GovernarateVO> getGovernrateListNLS() {
//		List items = new ArrayList();
//		for (GovernarateVO governarateVO : governrateList) {
//			items.add(new SelectItem(governarateVO.getId(), ArabicConversion.getArabicEncodedString(governarateVO.getGovNameNLS())));
//		}
//
//		return items;
//	}
//
//	public void setGovernrateListNLS(List<GovernarateVO> governrateListNLS) {
//		this.governrateListNLS = governrateListNLS;
//	}
//
//	public List<GovernarateVO> getCookieCmbGovList() {
//		List items = new ArrayList();
//		for (GovernarateVO governarateVO : cookieCmbGovList) {
//			items.add(new SelectItem(governarateVO.getId(), governarateVO.getGovName()));
//		}
//
//		return items;
//
//	}
//
//	public void setCookieCmbGovList(List<GovernarateVO> cookieCmbGovList) {
//		this.cookieCmbGovList = cookieCmbGovList;
//	}
//
//	public List<DirectorateVO> getCookieCmbDirList() {
//		List items = new ArrayList();
//		for (DirectorateVO institutionVO : cookieCmbDirList) {
//			items.add(new SelectItem(institutionVO.getId(), institutionVO.getDirecotrateName()));
//		}
//
//		return items;
//	}
//
//	public void setCookieCmbDirList(List<DirectorateVO> cookieCmbDirList) {
//		this.cookieCmbDirList = cookieCmbDirList;
//	}
//
//	public List<GovernarateVO> getCookieCmbGovListNLS() {
//		List items = new ArrayList();
//		for (GovernarateVO governarateVO : cookieCmbGovList) {
//			items.add(new SelectItem(governarateVO.getId(), ArabicConversion.getArabicEncodedString(governarateVO.getGovNameNLS())));
//		}
//
//		return items;
//	}
//
//	public void setCookieCmbGovListNLS(List<GovernarateVO> cookieCmbGovListNLS) {
//		this.cookieCmbGovListNLS = cookieCmbGovListNLS;
//	}
//
//	public List<DirectorateVO> getCookieCmbDirListNLS() {
//		List items = new ArrayList();
//		for (DirectorateVO dirVO : cookieCmbDirList) {
//			items.add(new SelectItem(dirVO.getId(), ArabicConversion.getArabicEncodedString(dirVO.getDirecotrateNameNLS())));
//		}
//
//		return items;
//	}
//
//	public void setCookieCmbDirListNLS(List<DirectorateVO> cookieCmbDirListNLS) {
//		this.cookieCmbDirListNLS = cookieCmbDirListNLS;
//	}
//
//	public List<EntityVO> getSearchedListHome() {
//		return searchedListHome;
//	}
//
//	public void setSearchedListHome(List<EntityVO> searchedListHome) {
//		this.searchedListHome = searchedListHome;
//	}
//
//}
