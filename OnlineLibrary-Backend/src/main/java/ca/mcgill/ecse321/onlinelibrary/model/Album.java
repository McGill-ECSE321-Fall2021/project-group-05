package ca.mcgill.ecse321.onlinelibrary.model;

import ca.mcgill.ecse321.onlinelibrary.dto.AlbumDto;
import ca.mcgill.ecse321.onlinelibrary.dto.LibraryItemDto;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Album extends ReservableItem{
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AlbumInfo albumInfo;

    public Album(AlbumInfo albumInfo) {
        this.albumInfo = albumInfo;
    }

    protected Album() {

    }

    public AlbumInfo getAlbumInfo() {
        return albumInfo;
    }

    public LibraryItemDto convertToDto() {
        return AlbumDto.fromAlbum(this);
    }
}
