package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.NewspaperInfo;
import org.springframework.data.repository.CrudRepository;

public interface NewspaperInfoRepository extends CrudRepository<NewspaperInfo, Integer> {
    NewspaperInfo findNewspaperInfoById(int id);
}
