package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.AlbumInfo;
import org.springframework.data.repository.CrudRepository;

public interface AlbumInfoRepository extends CrudRepository<AlbumInfo, Integer> {
    AlbumInfo findAlbumInfoById(int id);
}
