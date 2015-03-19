package om.gov.moh.eab.entity.bo;

import java.io.Serializable;
import java.util.List;

import om.gov.moh.eab.entity.dao.EntityDAO;
import om.gov.moh.eab.entity.vo.EntityVO;
import om.gov.moh.eab.security.vo.UserVO;

import org.hibernate.Hibernate;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author farid.haq
 * 
 */
@Service("entityManager")
@Transactional
public class EntityManagerImpl implements EntityManager, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityManagerImpl() {

	}

	@Autowired
	private EntityDAO entityDAO;

	
	@Override
	@Transactional(readOnly = true)
	public List<EntityVO> searchEntities(EntityVO entityVO, UserVO loggedUser, int index, int count,String sortField,SortOrder sortOrder) throws Exception {

		List<EntityVO> list = entityDAO.searchEntities(entityVO, loggedUser, index, count,sortField,sortOrder);

		for (EntityVO entityVO2 : list) {
			Hibernate.initialize(entityVO2.getDepartmentVO());
			Hibernate.initialize(entityVO2.getDirectorateVO());
			Hibernate.initialize(entityVO2.getSectionVO());
			Hibernate.initialize(entityVO2.getInstitutionVO());
			Hibernate.initialize(entityVO2.getGovernarateVO());
		}

		return list;
	}

	public EntityDAO getEntityDAO() {
		return entityDAO;
	}

	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public void saveEntity(EntityVO entityVO) throws Exception {
		entityVO.setActive("Y");
		this.entityDAO.saveEntity(entityVO);

	}

	@Override
	public EntityVO findEntityById(Long Id) throws Exception {
		// TODO Auto-generated method stub
		EntityVO entityVo = this.entityDAO.findEntityById(Id);
		
		if (entityVo.getInstitutionVO() != null && entityVo.getInstitutionVO().getInstCode() != null) {
			Hibernate.initialize(entityVo.getInstitutionVO());
		}
		
		if(entityVo.getDepartmentVO()!=null && entityVo.getDepartmentVO().getId()!=null){
			Hibernate.initialize(entityVo.getDepartmentVO());
		}
		
		if(entityVo.getSectionVO()!=null && entityVo.getSectionVO().getId()!=null){
			Hibernate.initialize(entityVo.getSectionVO());
		}
		
		
		
		
		return entityVo;
	}

	@Override
	public void deleteEntity(EntityVO entityVO) throws Exception {
		this.entityDAO.deleteEntity(entityVO);
	}

	@Override
	public boolean isSameEntityCombinationExist(EntityVO entityVO) throws Exception {

		return this.entityDAO.isSameEntityCombinationExist(entityVO);
	}

	@Override
	public int countTotalEntities(EntityVO entityVO, UserVO loggedUser) throws Exception {
		return entityDAO.countTotalEntities(entityVO, loggedUser);
	}

	

}
