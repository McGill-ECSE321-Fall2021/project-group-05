package ca.mcgill.ecse321.onlinelibrary.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.onlinelibrary.model.Book;
import ca.mcgill.ecse321.onlinelibrary.model.LibraryItem;
import ca.mcgill.ecse321.onlinelibrary.model.ReservableItem;
import ca.mcgill.ecse321.onlinelibrary.model.ReservableItem.ItemStatus;

//import ca.mcgill.ecse321.onlinelibrary.model.Loan;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestOnlineLibraryPersistence {
	//@Autowired
	//private LoanRepository loanRepository;
	@Autowired
	private LibraryItemRepository libraryItemRepository;
	@Autowired
	private ReservableItemRepository reservableItemRepository;
	@Autowired 
	private BookRepository bookRepository;
	@AfterEach
	public void clearDatabase() {
		//loanRepository.deleteAll();
		libraryItemRepository.deleteAll();
		reservableItemRepository.deleteAll();
		bookRepository.deleteAll();
	}
	
	//@Test
//	public void testPersistAndLoadLoan() {
//		Integer id = 123;
//		Loan aLoan = new Loan();
//		aLoan.setId(id);
//		loanRepository.save(aLoan);
//		
//		aLoan = null;
//		
//		aLoan = loanRepository.findLoanById(id);
//		assertNotNull(aLoan);
//		assertEquals(id, aLoan.getId());
//	}
	
//	@Test
//	public void testPersistAndLoadLibraryItem() {
//		Integer id = 123;
//		LibraryItem libraryItem = new LibraryItem();
//		libraryItem.setId(id);
//		libraryItemRepository.save(libraryItem);
//		libraryItem = null;
//		libraryItem = libraryItemRepository.findLibraryItemById(id);
//		assertNotNull(libraryItem);
//		assertEquals(id, libraryItem.getId());
//	}
//	@Test
//	public void testPersistAndLoadReservableItem() {
//		Integer id = 456;
//		ReservableItem reservableItem = new ReservableItem();
//		reservableItem.setId(id);
//		reservableItem.setStatus(ItemStatus.Available);
//		reservableItemRepository.save(reservableItem);
//		reservableItem = null;
//		reservableItem = reservableItemRepository.findReservableItemById(id);
//		assertNotNull(reservableItem);
//		assertEquals(id, reservableItem.getId());
//		assertEquals(ItemStatus.Available, reservableItem.getStatus());
//	}
	@Test
	public void testPersistAndLoadBook() {
		Integer id = 789;
		Book book = new Book();
		book.setId(id);
		book.setStatus(ItemStatus.CheckedOut);
		bookRepository.save(book);
		book = null;
		book = bookRepository.findBookById(id);
		assertEquals(id, book.getId());
		assertEquals(ItemStatus.CheckedOut, book.getStatus());
	}
}