package om.gov.moh.eab.entity.bo;

import java.util.List;

import om.gov.moh.eab.entity.vo.EntityVO;
import om.gov.moh.eab.security.vo.UserVO;

import org.primefaces.model.SortOrder;

/**
 * @author farid.haq
 * 
 */
public interface EntityManager {
	

	public List<EntityVO> searchEntities(EntityVO entityVO, UserVO loggedUser, int start, int count,String sortField,SortOrder sortOrder) throws Exception;

	public void saveEntity(EntityVO entityVO) throws Exception;

	public EntityVO findEntityById(Long Id) throws Exception;

	public void deleteEntity(EntityVO entityVO) throws Exception;

	public boolean isSameEntityCombinationExist(EntityVO entityVO) throws Exception;

	public int countTotalEntities(EntityVO entityVO, UserVO loggedUser) throws Exception;	
		
}
