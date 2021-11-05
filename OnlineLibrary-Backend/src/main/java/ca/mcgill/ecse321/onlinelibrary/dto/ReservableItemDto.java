package ca.mcgill.ecse321.onlinelibrary.dto;

import ca.mcgill.ecse321.onlinelibrary.model.ReservableItem.ItemStatus;

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

	static ItemStatusDto fromItemStatus(ItemStatus itemStatus) {
		return switch (itemStatus) {
			case Available -> ItemStatusDto.Available;
			case CheckedOut -> ItemStatusDto.CheckedOut;
			case Reserved -> ItemStatusDto.Reserved;
		};
	}
}
