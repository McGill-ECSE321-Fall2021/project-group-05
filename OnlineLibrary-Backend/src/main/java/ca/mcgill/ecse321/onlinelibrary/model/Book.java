package ca.mcgill.ecse321.onlinelibrary.model;

import ca.mcgill.ecse321.onlinelibrary.dto.BookDto;
import ca.mcgill.ecse321.onlinelibrary.dto.LibraryItemDto;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Book extends ReservableItem {
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BookInfo bookInfo;

    public Book(BookInfo bookInfo) {
        this.bookInfo = bookInfo;
    }

    protected Book() {
        
    }

    public BookInfo getBookInfo() {
        return bookInfo;
    }

    public LibraryItemDto convertToDto() {
        return BookDto.fromBook(this);
    }
}
