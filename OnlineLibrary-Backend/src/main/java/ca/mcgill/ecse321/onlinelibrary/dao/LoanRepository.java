package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.Loan;

import org.springframework.data.repository.CrudRepository;

public interface LoanRepository extends CrudRepository<Loan, Integer> {
    /**
     * Finds a library item loan using its unique ID.
     * @param id the id of the library item loan
     * @return the library item loan
     */
    Loan findLoanById(int id);
}


