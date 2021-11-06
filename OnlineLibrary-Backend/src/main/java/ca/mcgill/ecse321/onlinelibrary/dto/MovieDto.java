package ca.mcgill.ecse321.onlinelibrary.dto;

import ca.mcgill.ecse321.onlinelibrary.model.Movie;

public class MovieDto extends ReservableItemDto{
	private MovieInfoDto movieInfo;
	public MovieDto (int id, ItemStatusDto itemStatus ,MovieInfoDto movieInfoDto) {
		super(id, itemStatus);
		this.movieInfo=movieInfoDto;
	}
	
	public static MovieDto fromMovie(Movie movie) {
		if (movie == null) {
			throw new IllegalArgumentException("There is no such movie.");
		}
		return new MovieDto(movie.getId(), fromItemStatus(movie.getStatus()),MovieInfoDto.fromMovieInfo(movie.getMovieInfo()));
	}
	
	public MovieInfoDto getMovieInfo() {
		return this.movieInfo;
	}
}
