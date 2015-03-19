package om.gov.moh.eab.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

/**
 * 
 * @author farid.haq
 */
@ManagedBean
@SessionScoped
public class ReportBean {
	private List<?> list;
	private String reportName;

	JasperPrint jasperPrint;

	public void init(Map<String, Object> params) throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(list);
		ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		 
		params.put("LOGO_PATH", context.getRealPath("/resources/img/top_banner.jpg"));

		String path = context.getRealPath("/reports/") + "/";
		String jasperFileName = context.getRealPath(path + reportName + ".jasper");
		File jasperFile = new File(jasperFileName);

		// if jasper file is not there , only then we will comiple jrxml to
		// japser file
		if (!jasperFile.exists()) {
			JasperCompileManager.compileReportToFile(path + reportName + ".jrxml", path + reportName + ".jasper");
		}

		jasperPrint = JasperFillManager.fillReport(path + reportName + ".jasper", params, beanCollectionDataSource);
	}

	public void exportAsPDF(Map<String, Object> params) throws JRException, IOException {
		init(params);
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + reportName + ".pdf");
		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
		FacesContext.getCurrentInstance().responseComplete();
	}

	public void exportAsDOCX(Map<String, Object> params) throws JRException, IOException {
		init(params);
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + reportName + ".docx");
		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		JRDocxExporter docxExporter = new JRDocxExporter();
		docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
		docxExporter.setParameter(JRDocxExporterParameter.OUTPUT_STREAM, servletOutputStream);
		docxExporter.exportReport();
		FacesContext.getCurrentInstance().responseComplete();
	}

	public void exportAsXLSX(Map<String, Object> params) throws JRException, IOException {
		init(params);
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + reportName + ".xlsx");
		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		JRXlsxExporter docxExporter = new JRXlsxExporter();
		docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
		docxExporter.exportReport();
		FacesContext.getCurrentInstance().responseComplete();
	}

	public void exportAsODT(Map<String, Object> params) throws JRException, IOException {
		init(params);
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + reportName + ".odt");
		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		JROdtExporter docxExporter = new JROdtExporter();
		docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
		docxExporter.exportReport();
		FacesContext.getCurrentInstance().responseComplete();
	}

	public void exportAsPPT(Map<String, Object> params) throws JRException, IOException {
		init(params);
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + reportName + ".pptx");
		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		JRPptxExporter docxExporter = new JRPptxExporter();
		docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
		docxExporter.exportReport();
		FacesContext.getCurrentInstance().responseComplete();
	}

	public void exportAsHTML(Map<String, Object> params) throws JRException, IOException {
		init(params);
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		JRHtmlExporter exporter = new JRHtmlExporter();
		exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, httpServletResponse.getWriter());
		exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING, "ISO-8859-9");
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, httpServletResponse.getWriter());
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "/eAddressBook/servlets/image?image=");
		session.setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
		exporter.exportReport();
		FacesContext.getCurrentInstance().responseComplete();
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public JasperPrint getJasperPrint() {
		return jasperPrint;
	}

	public void setJasperPrint(JasperPrint jasperPrint) {
		this.jasperPrint = jasperPrint;
	}

}
