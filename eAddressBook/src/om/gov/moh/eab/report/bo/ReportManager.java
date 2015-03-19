package om.gov.moh.eab.report.bo;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import om.gov.moh.eab.entity.vo.EntityVO;

import org.springframework.beans.factory.annotation.Autowired;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * @author farid.haq
 *
 */
public interface ReportManager {
	
	
	public Boolean compileReport(ServletContext scontext,String compileDir,String reportFileName) throws Exception;
	
	public JasperPrint fillReport(String sourceFileName, Map<String, Object> params, JRDataSource dataSource) throws JRException;
	
	public void exportReport(JasperPrint jasperPrint, String destFileName,String Format) throws Exception;
	
	public List<EntityVO> loadEntitiesForReport(Long entityCode) throws Exception;
	
}
