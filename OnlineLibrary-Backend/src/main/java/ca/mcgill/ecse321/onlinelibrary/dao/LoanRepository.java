package ca.mcgill.ecse321.onlinelibrary.dao;

import ca.mcgill.ecse321.onlinelibrary.model.Loan;

import ca.mcgill.ecse321.onlinelibrary.model.Member;
import ca.mcgill.ecse321.onlinelibrary.model.ReservableItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoanRepository extends CrudRepository<Loan, Integer> {
    /**
     * Finds a library item loan using its unique ID.
     * @param id the id of the library item loan
     * @return the library item loan
     */
    Loan findLoanById(int id);

    /**
     * Finds all loans associated with a member.
     * @param member the member associated with the loans
     * @return the loans associated with the member
     */
    List<Loan> findLoanByMember(Member member);

    /**
     * Finds all loans associated with a reservable item.
     * @param reservableItem the reservable item associated with the loans
     * @return the loans associated with the reservable item
     */
    Loan findLoanByReservableItem(ReservableItem reservableItem);
}


