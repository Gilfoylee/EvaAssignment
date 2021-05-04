package eva.assignment.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eva.assignment.model.Share;

@Repository
public interface ShareRepo extends CrudRepository<Share, String> {

}
