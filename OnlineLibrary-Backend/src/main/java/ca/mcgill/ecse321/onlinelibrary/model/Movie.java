package ca.mcgill.ecse321.onlinelibrary.model;

import ca.mcgill.ecse321.onlinelibrary.dto.LibraryItemDto;
import ca.mcgill.ecse321.onlinelibrary.dto.MovieDto;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Movie extends ReservableItem{
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MovieInfo movieInfo;

    public Movie(MovieInfo movieInfo) {
        this.movieInfo = movieInfo;
    }

    protected Movie() {

    }

    public MovieInfo getMovieInfo() {
        return movieInfo;
    }

    public LibraryItemDto convertToDto() {
        return MovieDto.fromMovie(this);
    }

    @Override
    public LibraryItemInfo getItemInfo() {
        return this.getMovieInfo();
    }
}
