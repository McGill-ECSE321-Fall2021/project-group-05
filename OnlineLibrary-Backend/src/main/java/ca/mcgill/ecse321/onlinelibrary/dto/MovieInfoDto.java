package ca.mcgill.ecse321.onlinelibrary.dto;

import ca.mcgill.ecse321.onlinelibrary.model.MovieInfo;

public class MovieInfoDto extends ReservableItemInfoDto{
	public String genre;
	public String director;
	public int length;

	public MovieInfoDto(Integer id, String genre, String director, int length) {
		super(id);
		this.genre = genre;
		this.director = director;
		this.length = length;
	}

	public static MovieInfoDto fromMovieInfo(MovieInfo movieInfo) {
		if (movieInfo == null) {
			throw new IllegalArgumentException("There is no such movieInfo");
		}
		return new MovieInfoDto(movieInfo.getId(), movieInfo.getGenre(), movieInfo.getDirector(),
				movieInfo.getLength());
	}

	public String getGenre() {
		return this.genre;
	}

	public String getDirector() {
		return this.director;
	}

	public int getLength() {
		return this.length;
	}

}
