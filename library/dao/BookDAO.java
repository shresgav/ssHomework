package library.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import library.model.Book;
import library.model.Borrower;
import library.model.LibraryBranch;

public class BookDAO extends BaseDAO<Book>{

	public BookDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}
	
	public void updateBook(Book b) throws SQLException {
		update("UPDATE tbl_book SET title = ?, authId = ? WHERE bookId= ?",
				new Object[] {b.getTitle(), b.getAuthorId(), b.getBookId()});
	}
	
	public void deleteBook(Book b) throws SQLException {
		update("DELETE FROM tbl_book WHERE bookId = ?", new Object[] {b.getBookId()});
	}
	
	public Integer insertBook(Book book) throws SQLException {
		return update("INSERT INTO tbl_book (title, authId, pubId) VALUES (?, ?, ?)", new Object[] {book.getTitle(), book.getAuthorId(), book.getPubId()});
	}
	
	public List<Book> readBookByBranch(LibraryBranch lib) throws SQLException {
		 return read("SELECT * from tbl_book WHERE bookId IN (SELECT bookId from tbl_book_copies WHERE branchId = ? AND noOfCopies > 0)", new Object[] {lib.getBranchId()});
	}
	
	public List<Book> readBookByBranch(Borrower bor) throws SQLException {
		 return read("SELECT * from tbl_book WHERE bookId IN (SELECT bookId from tbl_book_loans WHERE cardNo = ?)", new Object[] {bor.getCardNo()});
		
	}
	
	public List<Book> readBookForReturn(Integer cardNo) throws SQLException {
		return read("SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_loans WHERE cardNo = ?)", new Object[] {cardNo});
	}
	
	public Book readBookByTitle(String title) throws SQLException {	
		List<Book> bookList = read("SELECT * FROM tbl_book WHERE title = ?", new Object[] {title});
		if (bookList.isEmpty()) return null;
		return bookList.get(0);
	}
	
	public List<Book> readAllBook() throws SQLException {	
		return read("SELECT * FROM tbl_book", new Object[] {});
	}

	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		while(rs.next()) {
			Book b = new Book();
			b.setTitle(rs.getString("title"));
			b.setBookId(rs.getInt("bookId"));
			b.setAuthorId(rs.getInt("authId"));
			b.setPubId(rs.getInt("pubId"));
			books.add(b);
		}
		return books;
	}

}
