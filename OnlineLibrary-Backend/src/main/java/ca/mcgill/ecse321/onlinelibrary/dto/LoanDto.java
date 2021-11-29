package ca.mcgill.ecse321.onlinelibrary.dto;

import ca.mcgill.ecse321.onlinelibrary.model.Loan;

import java.sql.Date;

public class LoanDto {
    private int id;
    private Date returnDate;
    private int numberOfRenewals;
    private LibraryItemDto libraryItemDto;
    private MemberDto member;

    public LoanDto(int id, Date returnDate, int numberOfRenewals, LibraryItemDto reservableItemDto, MemberDto member) {
        this.id = id;
        this.returnDate = returnDate;
        this.numberOfRenewals = numberOfRenewals;
        this.libraryItemDto = reservableItemDto;
        this.member = member;
    }

    public static LoanDto fromLoan(Loan loan) {
        return new LoanDto(loan.getId(), loan.getReturnDate(), loan.getNumberOfRenewals(), loan.getReservableItem().convertToDto(), MemberDto.fromMember(loan.getMember()));
    }

    public int getId() {
        return id;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public int getNumberOfRenewals() {
        return numberOfRenewals;
    }

    public LibraryItemDto getReservableItem() {
        return libraryItemDto;
    }

    public MemberDto getMember() {
        return member;
    }
}
