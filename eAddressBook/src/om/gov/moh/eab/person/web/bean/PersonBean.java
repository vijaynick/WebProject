//package om.gov.moh.eab.person.web.bean;
//
///**
// * author Mohamed zulfihar
// * Created Date: 11-June-2013
// * Copyright 2013 MOH. All rights reserved to DGIT
// */
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import javax.faces.model.SelectItem;
//
//import om.gov.moh.eab.entity.vo.EntityVO;
//import om.gov.moh.eab.master.vo.DesignationVO;
//import om.gov.moh.eab.person.vo.PersonVO;
//import om.gov.moh.eab.security.vo.InstitutionVO;
//import om.gov.moh.eab.utils.ArabicConversion;
//
//@SuppressWarnings("all")
//public class PersonBean implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//	
//	
//	private List<EntityVO> entitiesList = new ArrayList<EntityVO>();
//	private List<EntityVO> entitiesListNLS = new ArrayList<EntityVO>();
//	private List<om.gov.moh.eab.master.vo.DesignationVO> designations = new ArrayList<DesignationVO>();
//	private List<DesignationVO> designationsNLS = new ArrayList<DesignationVO>();
//	private List<String> alphabets = new ArrayList<String>();
//	private List<String> alphabetsNLS = new ArrayList<String>();
//	private List<SelectItem> isdSelectItems = new ArrayList<SelectItem>();
//	private Map<String, String> isd = new HashMap<String, String>();
//	private List<InstitutionVO> instituteList = new ArrayList<InstitutionVO>();
//	private List<InstitutionVO> instituteListNLS = new ArrayList<InstitutionVO>();
//
//	public List<EntityVO> getEntitiesList() {
//		List items = new ArrayList();
//		for (EntityVO entityVO : entitiesList) {
//			items.add(new SelectItem(entityVO.getEntityCode(), entityVO.getEntityName()));
//		}
//
//		return items;
//
//	}
//
//	public void setEntitiesList(List<EntityVO> entitiesList) {
//		this.entitiesList = entitiesList;
//	}
//
//	public List<EntityVO> getEntitiesListNLS() {
//		List items = new ArrayList();
//		for (EntityVO entityVO : entitiesList) {
//			items.add(new SelectItem(entityVO.getEntityCode(), ArabicConversion.getArabicEncodedString(entityVO.getEntityNameNLS())));
//		}
//
//		return items;
//
//	}
//
//	public void setEntitiesListNLS(List<EntityVO> entitiesListNLS) {
//		this.entitiesListNLS = entitiesListNLS;
//	}
//
//	public List<DesignationVO> getDesignations() {
//		List items = new ArrayList();
//		for (DesignationVO designationVO : designations) {
//			if (designationVO != null && designationVO.getId() != null && designationVO.getName() != null) {
//				items.add(new SelectItem(designationVO.getId(), designationVO.getName()));
//			}else{
//				System.out.println(designationVO);
//			}
//		}
//
//		return items;
//
//	}
//
//	public void setDesignations(List<DesignationVO> designations) {
//		this.designations = designations;
//	}
//
//	public List<DesignationVO> getDesignationsNLS() {
//		List items = new ArrayList();
//		for (DesignationVO designationVO : designations) {
//			items.add(new SelectItem(designationVO.getId(), ArabicConversion.getArabicEncodedString(designationVO.getNameNLS())));
//		}
//
//		return items;
//
//	}
//
//	public void setDesignationsNLS(List<DesignationVO> designationsNLS) {
//		this.designationsNLS = designationsNLS;
//	}
//
//	public List getAlphabets() {
//
//		List items = new ArrayList();
//		for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
//			items.add(alphabet);
//		}
//
//		return items;
//	}
//
//	public List<String> getAlphabetsNLS() {
//
//		List items = new ArrayList();
//
//		for (String alphabet : ArabicConversion.getArabicLetters()) {
//			items.add(alphabet);
//		}
//
//		return items;
//	}
//
//	public void setAlphabetsNLS(List<String> alphabetsNLS) {
//		this.alphabetsNLS = alphabetsNLS;
//	}
//
//	
//
//	public void setAlphabets(List<String> alphabets) {
//		this.alphabets = alphabets;
//	}
//
//	public List<SelectItem> getIsdSelectItems() {
//		List items = new ArrayList();
//
//		for (Entry<String, String> entry : isd.entrySet()) {
//
//			items.add(new SelectItem(entry.getKey(), entry.getValue()));
//		}
//
//		return items;
//	}
//
//	public void setIsdSelectItems(List<SelectItem> isdSelectItems) {
//		this.isdSelectItems = isdSelectItems;
//	}
//
//	public Map<String, String> getIsd() {
//		return isd;
//	}
//
//	public void setIsd(Map<String, String> isd) {
//		this.isd = isd;
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
//}
