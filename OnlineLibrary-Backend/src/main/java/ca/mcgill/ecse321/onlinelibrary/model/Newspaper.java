package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Newspaper extends LibraryItem{
	@ManyToOne
    private NewsPaperInfo newsPaperInfo;

    public Newspaper(NewsPaperInfo newsPaperInfo) {
        this.newsPaperInfo = newsPaperInfo;
    }

    protected Newspaper() {

    }

    public NewsPaperInfo getNewsPaperInfo() {
        return newsPaperInfo;
    }
}
