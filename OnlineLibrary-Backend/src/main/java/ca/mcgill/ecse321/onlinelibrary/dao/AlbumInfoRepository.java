package ca.mcgill.ecse321.onlinelibrary.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.onlinelibrary.model.AlbumInfo;

public interface AlbumInfoRepository extends CrudRepository<AlbumInfo, Integer> {
    AlbumInfo findAlbumInfoById(int id);
}
