package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.*;
import ca.mcgill.ecse321.onlinelibrary.dto.ReservableItemInfoDto;

@Entity
public abstract class ReservableItemInfo extends LibraryItemInfo {

	@Override
	public abstract ReservableItemInfoDto convertToDto();
}
