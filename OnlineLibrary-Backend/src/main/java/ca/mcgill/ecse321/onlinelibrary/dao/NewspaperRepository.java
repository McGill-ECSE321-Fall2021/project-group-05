package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinelibrary.model.Newspaper;

public interface NewspaperRepository extends CrudRepository<Newspaper, Integer>{
	Newspaper findNewspaperById(Integer id);
}
