package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.ArchiveInfo;
import org.springframework.data.repository.CrudRepository;

public interface ArchiveInfoRepository extends CrudRepository<ArchiveInfo, Integer> {
    ArchiveInfo findArchiveInfoById(int id);
}
