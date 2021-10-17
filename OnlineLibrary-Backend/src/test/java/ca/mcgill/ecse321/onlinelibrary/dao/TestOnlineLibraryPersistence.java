// package ca.mcgill.ecse321.onlinelibrary.dao;
//
// import ca.mcgill.ecse321.onlinelibrary.model.*;
// import ca.mcgill.ecse321.onlinelibrary.model.ReservableItem.ItemStatus;
// import java.sql.Date;
// import java.sql.Time;
// import java.time.LocalDate;
// import java.time.LocalTime;
// import java.time.Month;
//
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.TestPropertySource;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
//
// @ExtendWith(SpringExtension.class)
// @SpringBootTest
// @TestPropertySource(locations = "classpath:application-test.properties")
// public class TestOnlineLibraryPersistence {
//
// @Autowired
// private BookRepository bookRepository;
// @Autowired
// private MovieRepository movieRepository;
// @Autowired
// private AlbumRepository albumRepository;
// @Autowired
// private ArchiveRepository archiveRepository;
// @Autowired
// private NewspaperRepository newspaperRepository;
// @Autowired
// private MemberRepository memberRepository;
// @Autowired
// private OnlineAccountRepository onlineAccountRepository;
// @Autowired
// private LoanRepository loanRepository;
// @Autowired
// private LibraryOpeningHoursRepository libraryOpeningHoursRepository;
// @Autowired
// private HolidayRepository holidayRepository;
//
// @AfterEach
// public void clearDatabase() {
// Iterable<Member> m = memberRepository.findAll();
// Iterable<OnlineAccount> oa = onlineAccountRepository.findAll();
//
// loanRepository.deleteAll();
// onlineAccountRepository.deleteAll();
//
// m = memberRepository.findAll();
// oa = onlineAccountRepository.findAll();
//
// memberRepository.deleteAll();
// bookRepository.deleteAll();
// movieRepository.deleteAll();
// albumRepository.deleteAll();
// archiveRepository.deleteAll();
// newspaperRepository.deleteAll();
// libraryOpeningHoursRepository.deleteAll();
// holidayRepository.deleteAll();
// }
//
// @Test
// public void testPersistAndLoadBook() {
// Book book = new Book();
// book.setStatus(ItemStatus.CheckedOut);
// bookRepository.save(book);
// int id = book.getId();
// book = null;
// book = bookRepository.findBookById(id);
// assertEquals(id, book.getId());
// assertEquals(ItemStatus.CheckedOut, book.getStatus());
// }
//
// @Test
// public void testPersistAndLoadMovie() {
// Movie movie = new Movie();
// movie.setStatus(ItemStatus.Available);
// movieRepository.save(movie);
// int id = movie.getId();
// movie = null;
// movie = movieRepository.findMovieById(id);
// assertEquals(id, movie.getId());
// assertEquals(ItemStatus.Available, movie.getStatus());
// }
//
// @Test
// public void testPersistAndLoadAlbum() {
// Album album = new Album();
// album.setStatus(ItemStatus.Reserved);
// albumRepository.save(album);
// int id = album.getId();
// album = null;
// album = albumRepository.findAlbumById(id);
// assertEquals(id, album.getId());
// assertEquals(ItemStatus.Reserved, album.getStatus());
// }
//
// @Test
// public void testPersistAndLoadArchive() {
// Archive archive = new Archive();
// archiveRepository.save(archive);
// int id = archive.getId();
// archive = null;
// archive = archiveRepository.findArchiveById(id);
// assertNotNull(archive);
// assertEquals(id, archive.getId());
// }
//
// @Test
// public void testPersistAndLoadNewsPaper() {
// Newspaper newspaper = new Newspaper();
// newspaperRepository.save(newspaper);
// int id = newspaper.getId();
// newspaper = null;
// newspaper = newspaperRepository.findNewspaperById(id);
// assertNotNull(newspaper);
// assertEquals(id, newspaper.getId());
// }
//
// @Test
// public void testPersistAndLoadLoan() {
//
// Member member = new Member("123 McGill Street", "Luke Skywalker");
// memberRepository.save(member);
// Book reservableItem = new Book();
// bookRepository.save(reservableItem);
// Date date = Date.valueOf(LocalDate.of(2021, 10, 16));
// Loan loan = new Loan(date, reservableItem, member);
// int numberOfRenewals = 2;
// loan.setNumberOfRenewals(numberOfRenewals);
// loanRepository.save(loan);
//
// int loanId = loan.getId();
// int bookId = reservableItem.getId();
// int memberId = member.getId();
// loan = null;
//
// loan = loanRepository.findLoanById(loanId);
// assertNotNull(loan);
// assertEquals(loanId, loan.getId());
// assertEquals(reservableItem.getId(), loan.getReservableItem().getId());
// assertEquals(numberOfRenewals, loan.getNumberOfRenewals());
// assertEquals(date.toString(), loan.getReturnDate().toString());
//
// // Check associations
// ReservableItem retrievedItem = loan.getReservableItem();
// assertNotNull(retrievedItem);
// assertEquals(bookId, retrievedItem.getId());
//
// Member retrievedMember = loan.getMember();
// assertNotNull(retrievedMember);
// assertEquals(memberId, retrievedMember.getId());
// }
//
// @Test
// public void testPersistAndLoadLibraryOpeningHours() {
// Date date = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
// Time startTime = java.sql.Time.valueOf(LocalTime.of(11, 35));
// Time endTime = java.sql.Time.valueOf(LocalTime.of(13, 25));
//
// LibraryOpeningHours libraryOpeningHours = new LibraryOpeningHours();
//
// libraryOpeningHours.setDate(date);
// libraryOpeningHours.setStartTime(startTime);
// libraryOpeningHours.setEndTime(endTime);
//
// libraryOpeningHoursRepository.save(libraryOpeningHours);
// int id = libraryOpeningHours.getId();
//
// libraryOpeningHours = null;
// libraryOpeningHours =
// libraryOpeningHoursRepository.findLibraryOpeningHoursById(id);
//
// assertNotNull(libraryOpeningHours);
// assertEquals(id, libraryOpeningHours.getId());
// assertEquals(date, libraryOpeningHours.getDate());
// assertEquals(startTime, libraryOpeningHours.getStartTime());
// assertEquals(endTime, libraryOpeningHours.getEndTime());
// }
//
// @Test
// public void testPersistAndLoadHoliday() {
// Date startDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY,
// 30));
// Date endDate = java.sql.Date.valueOf(LocalDate.of(2020, Month.JANUARY, 31));
//
// Holiday holiday = new Holiday();
//
// holiday.setStartDate(startDate);
// holiday.setEndDate(endDate);
//
// holidayRepository.save(holiday);
// int id = holiday.getId();
//
// holiday = null;
// holiday = holidayRepository.findHolidayById(id);
//
// assertNotNull(holiday);
// assertEquals(id, holiday.getId());
// assertEquals(startDate, holiday.getStartDate());
// assertEquals(endDate, holiday.getEndDate());
// }
//
// @Test
// public void testPersistAndLoadMember() {
// // Create and persist member with online account and 2 loans
// Member originalMember = new Member("212 McGill Street", "Obi-Wan Kenobi");
// OnlineAccount originalAccount = new OnlineAccount("212", "obi1kenobi",
// "obi-wan.kenobi@mail.mcgill.ca", originalMember);
// originalMember.setOnlineAccount(originalAccount);
// originalMember = memberRepository.save(originalMember);
//
// // Get ID and drop references
// int memberId = originalMember.getId();
// int accountId = originalAccount.getId();
// originalMember = null;
// originalAccount = null;
//
// Member retrievedMember = memberRepository.findMemberById(memberId);
// assertNotNull(retrievedMember);
//
// // Check attributes
// assertEquals("212 McGill Street", retrievedMember.getAddress());
// assertEquals("Obi-Wan Kenobi", retrievedMember.getFullName());
//
// // Check association
// OnlineAccount retrievedAccount = retrievedMember.getOnlineAccount();
// assertNotNull(retrievedAccount);
// assertEquals(accountId, retrievedAccount.getId());
// }
//
// @Test
// public void testPersistAndLoadOnlineAccount() {
// // Create and persist member with online account
// Member originalMember = new Member("501 McGill Street", "Anakin Skywalker");
// OnlineAccount originalAccount = new OnlineAccount("501", "chosen1",
// "anakin.skywalker@mail.mcgill.ca",
// originalMember);
// originalMember.setOnlineAccount(originalAccount);
// originalMember = memberRepository.save(originalMember);
//
// // Get ID and drop reference
// int memberId = originalMember.getId();
// int accountId = originalAccount.getId();
// originalAccount = null;
//
// OnlineAccount retrievedAccount =
// onlineAccountRepository.findOnlineAccountById(accountId);
// assertNotNull(retrievedAccount);
//
// // Check attributes
// assertEquals("501", retrievedAccount.getPasswordHash());
// assertEquals("chosen1", retrievedAccount.getUsername());
// assertEquals("anakin.skywalker@mail.mcgill.ca",
// retrievedAccount.getEmailAddress());
//
// // Check association
// Member retrievedMember = retrievedAccount.getAccountOwner();
// assertNotNull(retrievedMember);
// assertEquals(memberId, retrievedMember.getId());
// }
// }
