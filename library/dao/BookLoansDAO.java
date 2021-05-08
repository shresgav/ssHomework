package library.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import library.model.BookLoans;

public class BookLoansDAO extends BaseDAO<BookLoans> {

	public BookLoansDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	public void updateBookLoans(BookLoans bl) throws SQLException {
		update("UPDATE tbl_book_loans SET dueDate = ? WHERE cardNo = ? AND bookId = ?",
				new Object[] {bl.getDueDate(), bl.getCardNo(), bl.getBookId()});
	}
	
	public void insertBookLoans(BookLoans bl) throws SQLException {
		update("INSERT INTO tbl_book_loans VALUES (?, ?, ?, ?, ?)",
				new Object[] {bl.getBookId(), bl.getBranchId(), bl.getCardNo(), Date.valueOf(bl.getDateOut()), Date.valueOf(bl.getDueDate())});
	}
	
	public void deleteBookLoan(Integer cardNo, Integer bookId) throws SQLException {
		update("DELETE FROM tbl_book_loans WHERE cardNo = ? AND bookId = ?", new Object[] {cardNo, bookId});
	}
	
	
	public List<BookLoans> readBookLoans() throws SQLException {
		return read("SELECT * tbl_book_loans", new Object[] {});
	}
	
	@Override
	public List<BookLoans> extractData(ResultSet rs) throws SQLException {
		List<BookLoans> bookLoans = new ArrayList<>();
		// Might need to change this logic to 
		// if (!rs.isBeforFirst) {...} else { do {...} while(rs.next()) }
		while(rs.next()) {
			BookLoans bl = new BookLoans();
			bl.setBranchId(rs.getInt("branchId"));
			bl.setBookId(rs.getInt("bookId"));
			bl.setCardNo(rs.getInt("cardNo"));
			bl.setDateOut(rs.getDate("dateOut").toLocalDate());
			bl.setDueDate(rs.getDate("dueDate").toLocalDate());
			bookLoans.add(bl);
		}
		return bookLoans;
	}

}
