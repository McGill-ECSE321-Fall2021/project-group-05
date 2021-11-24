package ca.mcgill.ecse321.onlinelibrary.dto;

import ca.mcgill.ecse321.onlinelibrary.model.Newspaper;

public class NewspaperDto extends LibraryItemDto{
	private NewspaperInfoDto newspaperInfo;
	
	public NewspaperDto (int id, NewspaperInfoDto newspaperInfo) {
		super(id);
		this.newspaperInfo = newspaperInfo;
	}
	
	public static NewspaperDto fromNewspaper (Newspaper newspaper) {
		if (newspaper == null) {
			throw new IllegalArgumentException("There is no such newspaper.");
		}
		return new NewspaperDto(newspaper.getId(), NewspaperInfoDto.fromNewspaperInfo(newspaper.getNewspaperInfo()));
	}
	
	public NewspaperInfoDto getNewspaperInfo() {
		return this.newspaperInfo;
	}
}
