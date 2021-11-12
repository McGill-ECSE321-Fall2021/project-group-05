package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.NewsPaperInfo;
import org.springframework.data.repository.CrudRepository;

public interface NewsPaperInfoRepository extends CrudRepository<NewsPaperInfo, Integer> {
    NewsPaperInfo findNewsPaperInfoById(int id);
}
