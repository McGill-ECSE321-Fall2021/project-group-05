package ca.mcgill.ecse321.onlinelibrary.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.onlinelibrary.dao.BookInfoRepository;
import ca.mcgill.ecse321.onlinelibrary.model.BookInfo;


@ExtendWith(MockitoExtension.class)
public class TestOnlineLibraryService {
	@Mock
	private BookInfoRepository bookInfoDao;
	
	@InjectMocks
	private OnlineLibraryService service;
	
	@BeforeEach
	public void setMockOuput() {
		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(bookInfoDao.save(any(BookInfo.class))).thenAnswer(returnParameterAsAnswer);
	}
	
	@Test
	public void testCreateBookInfo() {
		String title = "Title";
		Integer numberOfPage = 10;
		String author = "Author";
		Integer isbn = 1;
		BookInfo bookInfo = null;
		try {
			bookInfo = service.createBookInfo(title, numberOfPage, author, isbn);
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(bookInfo);
		assertEquals(bookInfo.getTitle(), title);
		assertEquals(bookInfo.getNumberOfPage(), numberOfPage);
		assertEquals(bookInfo.getAuthor(), author);
		assertEquals(bookInfo.getIsbn(), isbn);
	}
	@Test
	public void testCreateBookInfoTitleNull() {
		String error="";
		String title = null;
		Integer numberOfPage = 10;
		String author = "Author";
		Integer isbn = 1;
		BookInfo bookInfo = null;
		try {
			bookInfo = service.createBookInfo(title, numberOfPage, author, isbn);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(bookInfo);
		assertEquals("Title can't be empty!", error);
	}
	@Test
	public void testCreateBookInfoAllNulls() {
		String error="";
		String title = null;
		int numberOfPage = 0;
		String author = null;
		Integer isbn = null;
		BookInfo bookInfo = null;
		try {
			bookInfo = service.createBookInfo(title, numberOfPage, author, isbn);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(bookInfo);
		assertEquals("Title can't be empty!Number of page can't be empty!Author can't be empty!Isbn can't be empty!", error);
	}
}




