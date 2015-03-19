package om.gov.moh.eab.report.bo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTML;

import om.gov.moh.eab.entity.vo.EntityVO;
import om.gov.moh.eab.report.dao.ReportDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.pdf.codec.Base64.OutputStream;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;

/**
 * @author farid.haq
 * 
 */
@Service(value = "reportManager")
public class ReportManagerImpl implements ReportManager {

	@Autowired
	private ReportDAO reportDao;

	public ReportDAO getReportDao() {
		return reportDao;
	}

	public void setReportDao(ReportDAO reportDao) {
		this.reportDao = reportDao;
	}

	@Override
	public Boolean compileReport(ServletContext scontext, String compileDir, String reportFileName) throws Exception {

		// String jasperFileName = servletContext.getRealPath("/");
		String jasperFileName = scontext.getRealPath(compileDir + reportFileName + ".jasper");
		File jasperFile = new File(jasperFileName);

		if (jasperFile.exists()) {
			return true; // jasper file already exists, do not compile again
		}
		try {
			// jasper file has not been constructed yet, so compile the xml file
			System.setProperty("jasper.reports.compile.temp", scontext.getRealPath(compileDir));

			String xmlFileName = jasperFileName.substring(0, jasperFileName.indexOf(".jasper")) + ".jrxml";
			// JasperCompileManager.compileReportToFile(xmlFileName);
			JasperCompileManager.compileReportToFile(xmlFileName, jasperFileName);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public JasperPrint fillReport(String sourceFileName, Map<String, Object> reportParameters, JRDataSource dataSource) throws JRException {

		JasperPrint jasperPrint = null;
		// jasperPrint =
		// JasperFillManager.fillReport(scontext.getRealPath(COMPILE_DIR +
		// "userReport" + ".jasper"), reportParameters, datasource);
		jasperPrint = JasperFillManager.fillReport(sourceFileName, reportParameters, dataSource);
		return jasperPrint;
	}

	@Override
	public void exportReport(JasperPrint jasperPrint, String destFileName, String format) throws Exception {

		

		if (format == "HTML") {

			// ExternalContext externalContext =
			// FacesContext.getCurrentInstance().getExternalContext();
			// HttpServletResponse response = (HttpServletResponse)
			// externalContext.getResponse();
			ByteArrayOutputStream htmlStream = new ByteArrayOutputStream();
			JRHtmlExporter htmlExporter = new JRHtmlExporter();
			// htmlExporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
			// Boolean.FALSE);
			// htmlExporter.setParameter(JRHtmlExporterParameter.OUTPUT_WRITER,
			// response.getOutputStream());
			htmlExporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			htmlExporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING, "ISO-8859-9");
			// htmlExporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
			// "/SampleReportJSF/servlets/image?image=");//SampleReportJSF is
			// the name of the project

			htmlExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			htmlExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, htmlStream);
			htmlExporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
			htmlExporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME, "/img/");
			// htmlExporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
			// "/Images/");
			htmlExporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
			try {
				// htmlExporter.exportReport();
				System.out.println("destFileName:***" + destFileName);
				JasperExportManager.exportReportToHtmlFile(jasperPrint, "D:\\reports\\userReport.html");

			} catch (Exception e) {
				e.printStackTrace();
			}

			/*
			 * ServletOutputStream servletOutputStream = null;
			 * ByteArrayOutputStream htmlStream1 =new ByteArrayOutputStream();
			 * byte[] report=htmlStream1.toByteArray(); FacesContext context =
			 * FacesContext.getCurrentInstance(); HttpServletResponse response =
			 * (HttpServletResponse)context.getExternalContext().getResponse();
			 * response.reset(); response.setContentType("application/html");
			 * response.setHeader("Content-disposition","inline=" +
			 * "jasperReportName" + ".html"); servletOutputStream =
			 * response.getOutputStream(); servletOutputStream.write(report);
			 * response.setContentLength(report.length);
			 */

		} else if (format == "CSV") {

		} else {

			// JasperExportManager.exportReportToPdfFile(jasperPrint,
			// scontext.getRealPath(COMPILE_DIR + "test" + ".pdf"));
			try {
				// OutputStream output = new FileOutputStream(new
				// File(scontext.getRealPath(COMPILE_DIR + "test") + ".pdf"));
				// JasperExportManager.exportReportToPdfStream(jasperPrint,
				// output);

				// File file=new File("D:\\reports\\first.html");

				// OutputStream output = new FileOutputStream(new
				// File(“jrxml/catalog.pdf”));
				// JasperExportManager.exportReportToPdfStream(jasperPrint,
				// output);

				// JasperExportManager.exportReportToHtmlFile(jasperPrint,
				// "D:\\reports\\first.html");
				// JasperExportManager.exportReportToPdf(jasperPrint);

				File file = new File("D:\\reports\\userReport.pdf");
				if (!file.exists()) {
					file.createNewFile();
				}

				/*
				 * bytes = JasperExportManager.exportReportToPdf(jasperPrint);
				 * response.reset(); response.setContentType("application/pdf");
				 * response.setContentLength(bytes.length); servletoutputstream
				 * = response.getOutputStream();
				 * servletoutputstream.write(bytes, 0, bytes.length);
				 * servletoutputstream.flush(); servletoutputstream.close();
				 * 
				 * JRPdfExporter exporter = new JRPdfExporter();
				 */

				JasperExportManager.exportReportToPdfFile(jasperPrint, destFileName); // working

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public List<EntityVO> loadEntitiesForReport(Long entityCode) throws Exception {
		// TODO Auto-generated method stub
		return reportDao.loadEntityReportData(entityCode);
	}

}
