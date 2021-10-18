package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Movie extends ReservableItem{
	@ManyToOne
    private MovieInfo movieInfo;

    public Movie(MovieInfo movieInfo) {
        this.movieInfo = movieInfo;
    }

    protected Movie() {

    }

    public MovieInfo getMovieInfo() {
        return movieInfo;
    }
}
