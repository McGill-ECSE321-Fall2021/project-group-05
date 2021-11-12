package ca.mcgill.ecse321.onlinelibrary.model;

import ca.mcgill.ecse321.onlinelibrary.dto.LibraryItemDto;
import ca.mcgill.ecse321.onlinelibrary.dto.NewsPaperDto;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Newspaper extends LibraryItem{
	@ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private NewsPaperInfo newsPaperInfo;

    public Newspaper(NewsPaperInfo newsPaperInfo) {
        this.newsPaperInfo = newsPaperInfo;
    }

    protected Newspaper() {

    }

    public NewsPaperInfo getNewsPaperInfo() {
        return newsPaperInfo;
    }

    @Override
    public LibraryItemDto convertToDto() {
        return NewsPaperDto.fromNewsPaper(this);
    }

    @Override
    public LibraryItemInfo getItemInfo() {
        return this.getNewsPaperInfo();
    }
}
