package om.gov.moh.eab.abstracts.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDaoImpl<E, I extends Serializable> implements AbstractDao<E, I> {

	private Class<E> entityClass;

	protected AbstractDaoImpl(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	@Autowired
	private SessionFactory sessionFactory;

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public E findById(I id) {
		return (E) getCurrentSession().get(entityClass, id);

	}

	@Override
	public void saveOrUpdate(E e) {
		getCurrentSession().saveOrUpdate(e);
	}

	@Override
	public void delete(E e) {
		getCurrentSession().delete(e);
	}

	@Override
	public List<E> findByCriteria(Criterion criterion) {
		Criteria criteria = getCurrentSession().createCriteria(entityClass);
		criteria.add(criterion);
		return criteria.list();
	}

	protected List findAll(Class clazz) {
		List objects = null;
		try {

			org.hibernate.Query query = getCurrentSession().createQuery("from " + clazz.getName());
			objects = query.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return objects;
	}
	
	@Override
	public List<Object> findByExample(Object obj) {
		try {
			return getCurrentSession().createCriteria(obj.getClass()).add(Example.create(obj)).list();
		} catch (HibernateException he) {
			return null;
		}
	}
}
