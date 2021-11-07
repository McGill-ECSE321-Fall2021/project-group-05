package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinelibrary.model.LibraryItemInfo;

public interface LibraryItemInfoRespository extends CrudRepository<LibraryItemInfo, Integer> {
    LibraryItemInfo findLibraryItemInfoById(Integer id);
}
