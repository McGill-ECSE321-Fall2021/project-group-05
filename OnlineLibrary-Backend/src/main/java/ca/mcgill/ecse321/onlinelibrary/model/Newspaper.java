package ca.mcgill.ecse321.onlinelibrary.model;

import ca.mcgill.ecse321.onlinelibrary.dto.LibraryItemDto;
import ca.mcgill.ecse321.onlinelibrary.dto.NewspaperDto;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Newspaper extends LibraryItem{
	@ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private NewspaperInfo newspaperInfo;

    public Newspaper(NewspaperInfo newspaperInfo) {
        this.newspaperInfo = newspaperInfo;
    }

    protected Newspaper() {

    }

    public NewspaperInfo getNewspaperInfo() {
        return newspaperInfo;
    }

    @Override
    public LibraryItemDto convertToDto() {
        return NewspaperDto.fromNewspaper(this);
    }

    @Override
    public LibraryItemInfo getItemInfo() {
        return this.getNewspaperInfo();
    }
}
