package ca.mcgill.ecse321.onlinelibrary.model;

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
}
