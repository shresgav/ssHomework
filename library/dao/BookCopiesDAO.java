package library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import library.model.BookCopies;

public class BookCopiesDAO extends BaseDAO<BookCopies> {

	public BookCopiesDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	public void checkOutBook(Integer branchId, Integer bookId) throws SQLException {
		update("UPDATE tbl_book_copies SET noOfCopies = noOfCopies - 1 WHERE branchId = ? AND bookId = ?",
				new Object[] {branchId, bookId});
	}
	
	public void returnBookDAO(Integer branchId, Integer bookId) throws SQLException {
		update("UPDATE tbl_book_copies SET noOfCopies = noOfCopies + 1 WHERE branchId = ? AND bookId = ?",
				new Object[] {branchId, bookId});
	}
	
	public void updateBookCopies(BookCopies bc) throws SQLException {
		update("UPDATE tbl_book_copies SET noOfCopies = ? WHERE branchId = ? AND bookId = ?",
				new Object[] {bc.getNoOfCopies(), bc.getBranchId(), bc.getBookId()});
	}
	
	public void insertBookCopies(BookCopies bc) throws SQLException {
		update("INSERT INTO tbl_book_copies VALUES (?, ?, ?)",
				new Object[] {bc.getBookId(), bc.getBranchId(), bc.getNoOfCopies()});
	}
	
	
	public BookCopies readBookCopies(Integer bookId, Integer branchId) throws SQLException {
		 List<BookCopies> bcList = read("SELECT * FROM tbl_book_copies WHERE bookId = ? AND branchId = ?", new Object[] {bookId, branchId});
		 if (bcList.isEmpty()) {
			 return null;
		 }
		return bcList.get(0);
	}
	
	
	@Override
	public List<BookCopies> extractData(ResultSet rs) throws SQLException {
		List<BookCopies> bookCopies = new ArrayList<>();
		// Might need to change this logic to 
		// if (!rs.isBeforFirst) {...} else { do {...} while(rs.next()) }
		while(rs.next()) {
			BookCopies bc = new BookCopies();
			bc.setBranchId(rs.getInt("branchId"));
			bc.setBookId(rs.getInt("bookId"));
			bc.setNoOfCopies(rs.getInt("noOfCopies"));
			bookCopies.add(bc);
		}
		return bookCopies;
	}

}
