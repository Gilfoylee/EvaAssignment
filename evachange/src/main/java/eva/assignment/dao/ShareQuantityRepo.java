package eva.assignment.dao;

import eva.assignment.model.ShareQuantity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareQuantityRepo extends CrudRepository<ShareQuantity, String> {
	
	
}
