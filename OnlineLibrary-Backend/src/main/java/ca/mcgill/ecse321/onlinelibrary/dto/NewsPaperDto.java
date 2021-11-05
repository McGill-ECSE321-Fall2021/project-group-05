package ca.mcgill.ecse321.onlinelibrary.dto;

import ca.mcgill.ecse321.onlinelibrary.model.Newspaper;

public class NewsPaperDto extends LibraryItemDto{
	private NewsPaperInfoDto newsPaperInfo;
	
	public NewsPaperDto (int id, NewsPaperInfoDto newsPaperInfo) {
		super(id);
		this.newsPaperInfo = newsPaperInfo;
	}
	
	public static NewsPaperDto fromNewsPaper (Newspaper newspaper) {
		if (newspaper == null) {
			throw new IllegalArgumentException("There is no such newspaper.");
		}
		return new NewsPaperDto(newspaper.getId(), NewsPaperInfoDto.fromNewsPaperInfo(newspaper.getNewsPaperInfo()));
	}
	
	public NewsPaperInfoDto getNewsPaperInfo() {
		return this.newsPaperInfo;
	}
}
