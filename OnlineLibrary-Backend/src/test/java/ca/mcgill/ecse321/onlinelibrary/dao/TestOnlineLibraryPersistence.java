package ca.mcgill.ecse321.onlinelibrary.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.onlinelibrary.model.Loan;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestOnlineLibraryPersistence {
	@Autowired
	private LoanRepository loanRepository;

	@AfterEach
	public void clearDatabase() {
		loanRepository.deleteAll();
	}
	
	@Test
	public void testPersistAndLoadLoan() {
		Integer id = 123;
		Loan aLoan = new Loan();
		aLoan.setId(id);
		loanRepository.save(aLoan);
		
		aLoan = null;
		
		aLoan = loanRepository.findLoanById(id);
		assertNotNull(aLoan);
		assertEquals(id, aLoan.getId());
	}
}