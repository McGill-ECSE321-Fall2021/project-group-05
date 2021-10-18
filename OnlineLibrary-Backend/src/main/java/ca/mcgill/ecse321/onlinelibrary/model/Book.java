package ca.mcgill.ecse321.onlinelibrary.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Book extends ReservableItem {
    @ManyToOne
    private BookInfo bookInfo;

    protected Book() {

    }

    public BookInfo getBookInfo() {
        return bookInfo;
    }

    public Book(BookInfo bookInfo) {
        this.bookInfo = bookInfo;
    }
}
