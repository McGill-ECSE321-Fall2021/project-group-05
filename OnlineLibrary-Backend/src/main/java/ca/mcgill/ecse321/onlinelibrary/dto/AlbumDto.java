package ca.mcgill.ecse321.onlinelibrary.dto;

import ca.mcgill.ecse321.onlinelibrary.model.Album;

public class AlbumDto extends ReservableItemDto{
	private AlbumInfoDto albumInfo;
	public AlbumDto (int id, ItemStatusDto status, AlbumInfoDto albumInfoDto) {
		super(id, status);
		this.albumInfo=albumInfoDto;
	}
	
	public static AlbumDto fromAlbum(Album album) {
		if (album == null) {
			throw new IllegalArgumentException("There is no such album.");
		}
		return new AlbumDto(album.getId(), fromItemStatus(album.getStatus()),AlbumInfoDto.fromAlbumInfo(album.getAlbumInfo()));
	}
	
	public AlbumInfoDto getAlbumInfo() {
		return this.albumInfo;
	}
}
