package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.LibraryItemInfo;
import org.springframework.data.repository.CrudRepository;

public interface LibraryItemInfoRepository extends CrudRepository<LibraryItemInfo, Integer> {
    LibraryItemInfo findLibraryItemInfoById(Integer id);
}
