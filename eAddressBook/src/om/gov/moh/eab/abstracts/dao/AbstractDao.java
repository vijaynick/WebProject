package om.gov.moh.eab.abstracts.dao;



import om.gov.moh.eab.entity.vo.EntityVO;

import org.hibernate.criterion.Criterion;

import java.io.Serializable;
import java.util.List;

public interface AbstractDao<E, I extends Serializable> {

    E findById(I id);
    void saveOrUpdate(E e);
    void delete(E e);
    List<E> findByCriteria(Criterion criterion);
    public List<Object> findByExample(Object obj);    
}
