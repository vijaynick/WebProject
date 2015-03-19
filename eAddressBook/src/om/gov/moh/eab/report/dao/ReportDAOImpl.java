package om.gov.moh.eab.report.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import om.gov.moh.eab.entity.vo.EntityVO;
import om.gov.moh.eab.person.vo.PersonVO;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author farid.haq
 * 
 */
@Repository
@Transactional
public class ReportDAOImpl implements ReportDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<EntityVO> loadEntityReportData(Long directorateId) {

		// List<EntityUserReportVO> entityUsersReportList = new
		// ArrayList<EntityUserReportVO>();

		List<EntityVO> entityList = getCurrentSession().createQuery("from EntityVO  ent join fetch ent.directorateVO where ent.directorateVO.id=:dirId").setParameter("dirId", directorateId).list();

		Iterator<EntityVO> it = entityList.iterator();
		while (it.hasNext()) {
			EntityVO vo = (EntityVO) it.next();
			if (vo.getPersonsList() == null || vo.getPersonsList().size() <= 0) {
				it.remove();
			}
		}
     System.out.println(entityList.size());
		return entityList;
	}
}
