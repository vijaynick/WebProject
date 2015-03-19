//package om.gov.moh.eab.security.web.bean;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.faces.model.SelectItem;
//
//import om.gov.moh.eab.entity.vo.EntityVO;
//import om.gov.moh.eab.master.vo.DirectorateVO;
//import om.gov.moh.eab.master.vo.GovernarateVO;
//import om.gov.moh.eab.security.vo.InstitutionVO;
//import om.gov.moh.eab.security.vo.RoleVO;
//import om.gov.moh.eab.security.vo.UserVO;
//import om.gov.moh.eab.utils.ArabicConversion;
//
//public class UserBean implements Serializable {
//	private static final long serialVersionUID = -3446873621025909487L;
//	private List<UserVO> searchedList = new ArrayList<UserVO>();
//	private List<RoleVO> roleList = new ArrayList<RoleVO>();
//	private List<RoleVO> roleListNLS = new ArrayList<RoleVO>();
//
//	private List<DirectorateVO> directorateList = new ArrayList<DirectorateVO>();
//	private List<DirectorateVO> directorateNLS = new ArrayList<DirectorateVO>();
//	private List<InstitutionVO> institutionList = new ArrayList<InstitutionVO>();
//	private List<GovernarateVO> governrateListNLS=new ArrayList<GovernarateVO>();
//	private List<GovernarateVO> governrateList=new ArrayList<GovernarateVO>();
//
//	public List<UserVO> getSearchedList() {
//		return searchedList;
//	}
//
//	public void setSearchedList(List<UserVO> searchedList) {
//		this.searchedList = searchedList;
//	}
//
//	public List<RoleVO> getRoleList() {
//		List items = new ArrayList<RoleVO>();
//		for (RoleVO entry : roleList) {
//			items.add(entry);
//		}
//		return items;
//	}
//
//	public List<InstitutionVO> getInstitutionList() {
//		return institutionList;
//	}
//
//	public void setInstitutionList(List<InstitutionVO> institutionList) {
//		this.institutionList = institutionList;
//	}
//
//	public void setRoleList(List<RoleVO> roleList) {
//		this.roleList = roleList;
//	}
//
//	public List<DirectorateVO> getDirectorateList() {
//		List items = new ArrayList();
//		for (DirectorateVO entityVO : directorateList) {
//			items.add(new SelectItem(entityVO.getId(), entityVO.getDirecotrateName()));
//		}
//
//		return items;
//	}
//
//	public void setDirectorateList(List<DirectorateVO> directorateList) {
//		this.directorateList = directorateList;
//	}
//
//	public List<DirectorateVO> getDirectorateNLS() {
//		List items = new ArrayList();
//		for (DirectorateVO entityVO : directorateList) {
//			items.add(new SelectItem(entityVO.getId(), ArabicConversion.getArabicEncodedString(entityVO.getDirecotrateNameNLS())));
//		}
//
//		return items;
//	}
//
//	public void setDirectorateNLS(List<DirectorateVO> directorateNLS) {
//		this.directorateNLS = directorateNLS;
//	}
//
//	public List<RoleVO> getRoleListNLS() {
//		List items = new ArrayList<RoleVO>();
//		for (RoleVO entry : roleList) {
//			entry.setRoleNameNLS(ArabicConversion.getArabicEncodedString(entry.getRoleNameNLS()));
//			items.add(entry);
//		}
//		return items;
//	}
//
//	public void setRoleListNLS(List<RoleVO> roleListNLS) {
//		this.roleListNLS = roleListNLS;
//	}
//	
//	public List<GovernarateVO> getGovernrateList() {
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
//
//}
