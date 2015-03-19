package om.gov.moh.eab.report.dao;

import java.util.List;

import om.gov.moh.eab.entity.vo.EntityVO;

/**
 * @author farid.haq
 *
 */
public interface ReportDAO {

	public List<EntityVO> loadEntityReportData(Long entityCode) throws Exception;
}
