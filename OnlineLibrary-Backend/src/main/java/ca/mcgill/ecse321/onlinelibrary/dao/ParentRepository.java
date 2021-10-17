package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.onlinelibrary.model.Parent;

public interface ParentRepository extends CrudRepository<Parent, Integer> {

	public Parent findParentById(int id);
}
