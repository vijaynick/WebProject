package om.gov.moh.eab.report.web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import om.gov.moh.eab.entity.vo.EntityVO;
import om.gov.moh.eab.person.web.bean.PersonManagedBean;
import om.gov.moh.eab.report.bo.ReportManager;
import om.gov.moh.eab.security.bo.UserManager;
import om.gov.moh.eab.security.vo.UserVO;
import om.gov.moh.eab.utils.JSFMessageUtil;

/**
 * 
 * @author farid.haq
 * 
 */

@ManagedBean(name = "reportManagedBean")
@ViewScoped
public class ReportManagedBean extends JSFMessageUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	private UserVO userVO = null;

	@ManagedProperty(value = "#{reportManager}")
	private ReportManager reportManager;

	@ManagedProperty(value = "#{userManager}")
	private UserManager userManager;

	@ManagedProperty(value = "#{personManagedBean}")
	private PersonManagedBean personManagedBean;

	public void generateUserReport() {

		if (personManagedBean.getPersonVO().getEntityVO().getDirectorateVO().getId() == null || personManagedBean.getPersonVO().getEntityVO().getDirectorateVO().getId().equals(-1L)) {
			sendErrorMessageToUser("msg.report.dir");
			return;
		}

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		ServletContext scontext = (ServletContext) externalContext.getContext();
		userVO = new UserVO();
		List<EntityVO> entitiesList = new ArrayList<EntityVO>();
		String COMPILE_DIR = "\\reports\\";
		String reportFileName = "EntityUserReport";
		String subReportFileName = "EntityUser_subReport";
		Map<String, Object> reportParameters = new HashMap<String, Object>();

		String imagePath = scontext.getRealPath("\\resources\\img\\mohlogo.gif");

		String subReportPath = scontext.getRealPath(COMPILE_DIR);

		reportParameters.put("IMAGE_PATH", imagePath);

		reportParameters.put("SUBREPORT_DIR", subReportPath);

		System.out.println("*(*(*)*(((((((((" + imagePath);

		try {

			entitiesList = reportManager.loadEntitiesForReport(personManagedBean.getPersonVO().getEntityVO().getDirectorateVO().getId());
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(entitiesList);

			reportManager.compileReport(scontext, COMPILE_DIR, subReportFileName);

			reportManager.compileReport(scontext, COMPILE_DIR, reportFileName);

			JasperPrint jasperPrint = null;
			jasperPrint = reportManager.fillReport(scontext.getRealPath(COMPILE_DIR + reportFileName + ".jasper"), reportParameters, dataSource);

			reportManager.exportReport(jasperPrint, "D:\\reports\\entityUserReport.pdf", "PDF");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sendInfoMessageToUser("msg.report.success");
	}

	public ReportManager getReportManager() {
		return reportManager;
	}

	public void setReportManager(ReportManager reportManager) {
		this.reportManager = reportManager;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public PersonManagedBean getPersonManagedBean() {
		return personManagedBean;
	}

	public void setPersonManagedBean(PersonManagedBean personManagedBean) {
		this.personManagedBean = personManagedBean;
	}

}
