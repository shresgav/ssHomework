package library.model;

public class BookCopies {
	private int bookId;
	private int branchId;
	private int noOfCopies;
	
	public BookCopies() {
		super();
		this.bookId = 0;
		this.branchId = 0;
		this.noOfCopies = 0;
	}
	public BookCopies(int bookId, int branchId, int noOfCopies) {
		super();
		this.bookId = bookId;
		this.branchId = branchId;
		this.noOfCopies = noOfCopies;
	}
	public int getNoOfCopies() {
		return noOfCopies;
	}
	public void setNoOfCopies(int noOfCopies) {
		this.noOfCopies = noOfCopies;
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
	
	
}
