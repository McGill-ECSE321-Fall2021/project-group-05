package ca.mcgill.ecse321.onlinelibrary.dto;

import ca.mcgill.ecse321.onlinelibrary.dto.ReservableItemDto.ItemStatusDto;

public abstract class ReservableItemDto extends LibraryItemDto{
	private ItemStatusDto itemStatus;
	
	public ReservableItemDto(int id, ItemStatusDto itemStatus) {
		super(id);
		this.itemStatus = itemStatus;
	}
	
	public ItemStatusDto getItemStatus() {
		return this.itemStatus;
	}
	
	public enum ItemStatusDto {Available, CheckedOut, Reserved}
}
