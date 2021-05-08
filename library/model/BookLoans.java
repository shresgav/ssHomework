package library.model;

import java.time.LocalDate;

public class BookLoans {
	private int bookId;
	private int branchId;
	private int cardNo;
	private LocalDate dateOut;
	private LocalDate dueDate;
	
	
	public BookLoans() {
		this.bookId = 0;
		this.branchId = 0;
		this.cardNo = 0;
		this.dateOut = LocalDate.now();
		this.dueDate = LocalDate.now().plusWeeks(1);
	}
	
	
 	public BookLoans(int bookId, int branchId, int cardNo, LocalDate dateOut, LocalDate dueDate) {
		super();
		this.bookId = bookId;
		this.branchId = branchId;
		this.cardNo = cardNo;
		this.dateOut = dateOut;
		this.dueDate = dueDate;
	}
	public LocalDate getDateOut() {
		return dateOut;
	}
	public void setDateOut(LocalDate dateOut) {
		this.dateOut = dateOut;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public int getCardNo() {
		return cardNo;
	}
	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}
	
}
