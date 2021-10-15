package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.Book;
import ca.mcgill.ecse321.onlinelibrary.model.ReservableItem.ItemStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestOnlineLibraryPersistence {

    @Autowired
    private LibraryItemRepository libraryItemRepository;
    @Autowired
    private ReservableItemRepository reservableItemRepository;
    @Autowired
    private BookRepository bookRepository;

    @AfterEach
    public void clearDatabase() {
        libraryItemRepository.deleteAll();
        reservableItemRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadBook() {
        Book book = new Book();
        book.setStatus(ItemStatus.CheckedOut);
        bookRepository.save(book);
        int id = book.getId();
        System.out.println(id);
        book = null;
        book = bookRepository.findBookById(id);
        assertEquals(id, book.getId());
        assertEquals(ItemStatus.CheckedOut, book.getStatus());
    }
}