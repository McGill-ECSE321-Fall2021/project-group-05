package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Album extends ReservableItem{
	@ManyToOne
    private AlbumInfo albumInfo;

    public Album(AlbumInfo albumInfo) {
        this.albumInfo = albumInfo;
    }

    protected Album() {

    }

    public AlbumInfo getAlbumInfo() {
        return albumInfo;
    }
}
