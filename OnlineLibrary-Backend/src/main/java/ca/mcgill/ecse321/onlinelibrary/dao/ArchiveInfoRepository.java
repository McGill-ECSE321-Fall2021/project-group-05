package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinelibrary.model.ArchiveInfo;

public interface ArchiveInfoRepository extends CrudRepository<ArchiveInfo, Integer> {
    ArchiveInfo findArchiveInfoById(int id);
}
