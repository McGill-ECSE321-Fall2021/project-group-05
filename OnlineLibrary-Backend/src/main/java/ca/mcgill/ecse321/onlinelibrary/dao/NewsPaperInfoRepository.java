package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinelibrary.model.NewsPaperInfo;

public interface NewsPaperInfoRepository extends CrudRepository<NewsPaperInfo, Integer> {
    NewsPaperInfo findNewsPaperInfoById(int id);
}
