package om.gov.moh.eab.entity.dao;

import java.util.List;

import om.gov.moh.eab.entity.vo.EntityVO;
import om.gov.moh.eab.master.vo.DesignationVO;
import om.gov.moh.eab.security.vo.UserVO;

import org.primefaces.model.SortOrder;

/**
 * @author farid.haq
 * 
 */
public interface EntityDAO {
	

	public List<EntityVO> searchEntities(EntityVO entityVO, UserVO loggedUser, int index, int count,String sortField,SortOrder sortOrder) throws Exception;

	public void saveEntity(EntityVO entityVO) throws Exception;

	public EntityVO findEntityById(Long Id) throws Exception;

	public void deleteEntity(EntityVO entityVO) throws Exception;

	public boolean isSameEntityCombinationExist(EntityVO entityVO) throws Exception;

	public int countTotalEntities(EntityVO entityVO, UserVO loggedUser) throws Exception;
	
	 /**
     * This method is used to find the EntityVO based on the combination of parameter values 
     * Eg:- If any of the following parameter say secId is null, it will fetch that entity code which has values for govId,dirId and depId and secId is null
     * @param govId
     * @param dirId
     * @param depId
     * @param secId
     * @return
     * @throws Exception
     * @author Sajeer
     */
	public EntityVO findEntityByCombination(Long govId,Long dirId,Long depId,Long secId) throws Exception;
	
	/**
	 * This method will list the designations of the persons who belongs to this entityCode
	 * @param entityCode
	 * @return
	 * @throws Exception
	 * @author Sajeer
	 */
	public List<DesignationVO> findDesignationsByEntityCode(Long entityCode) throws Exception;
	
	/**
	 * This method is used to save the sorter order of designations with respect to an entity code
	 * Here @param designationList is a List<String> since the primefaces control p:orderList internally convert List<DesignationVO> to  List<String>
	 * The sort order will be the position of that designation in designationList.
	 * @param entityCode
	 * @param designationList
	 * @throws Exception
	 * @author Sajeer
	 */
	public void saveDesignationSortOrder(Long entityCode, List<String> designationList) throws Exception;


	
	
}
