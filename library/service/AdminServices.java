package library.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import library.dao.AuthorDAO;
import library.dao.BookCopiesDAO;
import library.dao.BookDAO;
import library.dao.BookLoansDAO;
import library.dao.BorrowerDAO;
import library.dao.LibraryBranchDAO;
import library.dao.PublisherDAO;
import library.model.Author;
import library.model.Book;
import library.model.BookCopies;
import library.model.BookLoans;
import library.model.Borrower;
import library.model.LibraryBranch;
import library.model.Publisher;

public class AdminServices {
	private Utility util = new Utility();
	
	/////////////////// LIBRARY BRANCH REQUESTS ///////////////////
	
	public void updateBranch(LibraryBranch lb) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			LibraryBranchDAO lbDAO = new LibraryBranchDAO(conn);
			lbDAO.updateBranch(lb);
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Failed to connect to server");
		} finally {
			conn.close();
		}
	}
	
	public void createBranch(LibraryBranch lb) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			LibraryBranchDAO lbDAO = new LibraryBranchDAO(conn);
			lbDAO.insertBranch(lb);
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Failed to connect to server");
		} finally {
			conn.close();
		}
	}
	
	public List<LibraryBranch> getBranches() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			LibraryBranchDAO lbDAO = new LibraryBranchDAO(conn);
			return lbDAO.readAllLibrary();
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return null;
	}
	
	public List<Book> getBooks() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookDAO bDAO = new BookDAO(conn);
			return bDAO.readAllBook();
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return null;
	}
	
	public void deleteBranchById(Integer id) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			LibraryBranchDAO lbDAO = new LibraryBranchDAO(conn);
			LibraryBranch lb = lbDAO.readLibraryByCode(id);
			lbDAO.deleteBranch(lb);
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Failed to connect to server");
		} finally {
			conn.close();
		}
	}
	
	public void deleteBranchByName(String name) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			LibraryBranchDAO lbDAO = new LibraryBranchDAO(conn);
			LibraryBranch lb = lbDAO.readLibraryByName(name);
			lbDAO.deleteBranch(lb);
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Failed to connect to server");
		} finally {
			conn.close();
		}
	}
	
	public void insertBranch(LibraryBranch lib) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			LibraryBranchDAO lbDAO = new LibraryBranchDAO(conn);
			lbDAO.insertBranch(lib);
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Failed to connect to server");
		} finally {
			conn.close();
		}
	}
	
	/////////////////// LIBRARY BRANCH REQUESTS (END) ////////////////////
	
	
	/////////////////// BOOK CHECKOUT/RETURN REQUESTS ///////////////////
	
	public List<Book> getBooksByBranch(LibraryBranch lib) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookDAO bDAO = new BookDAO(conn);
			return bDAO.readBookByBranch(lib);
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Failed to connect to server");
		} finally {
			conn.close();
		}
		return null;
	}
	
	public BookCopies getNumBooks(Book b, LibraryBranch lib) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookCopiesDAO bcDAO = new BookCopiesDAO(conn);
			return bcDAO.readBookCopies(b.getBookId(), lib.getBranchId());
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Failed to connect to server");
		} finally {
			conn.close();
		}
		return null;
	}
	
	public void updateBookCopies(BookCopies bc) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookCopiesDAO bcDAO = new BookCopiesDAO(conn);
			bcDAO.updateBookCopies(bc);
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Failed to connect to server");
		} finally {
			conn.close();
		}
	}
	
	public void insertBookCopies(BookCopies bc) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookCopiesDAO bcDAO = new BookCopiesDAO(conn);
			bcDAO.insertBookCopies(bc);
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Failed to connect to server");
		} finally {
			conn.close();
		}
	}
	
	public void checkOutBook(BookLoans bl) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookCopiesDAO bcDAO = new BookCopiesDAO(conn);
			BookLoansDAO blDAO = new BookLoansDAO(conn);
			bcDAO.checkOutBook(bl.getBranchId(), bl.getBookId());
			blDAO.insertBookLoans(bl);
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Failed to connect to server");
		} finally {
			conn.close();
		}
	}
	
	public List<Book> loanedBooks(Integer cardNo) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookDAO bDAO = new BookDAO(conn);
			return bDAO.readBookForReturn(cardNo);
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Failed to connect to server");
		} finally {
			conn.close();
		}
		return null;
	}
	
	public void returnBook(Integer cardNo, Integer bookId, Integer branchId) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookCopiesDAO bcDAO = new BookCopiesDAO(conn);
			BookLoansDAO blDAO = new BookLoansDAO(conn);
			blDAO.deleteBookLoan(cardNo, bookId);
			BookCopies bc = bcDAO.readBookCopies(bookId, branchId);
			
			// If we return to a branch that never had a copy of this book, INSERT INTO instead of UPDATE
			if (bc == null) {
				bc = new BookCopies();
				bc.setBookId(bookId);
				bc.setBranchId(branchId);
				bc.setNoOfCopies(1);
				bcDAO.insertBookCopies(bc);
			} else {
				bcDAO.returnBookDAO(branchId, bookId);
			}
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Failed to connect to server");
		} finally {
			conn.close();
		}
	}
	
	public List<Borrower> listAllBorrowers() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BorrowerDAO bDAO = new BorrowerDAO(conn);
			return bDAO.readAllBorrowers();
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Failed to connect to server");
		} finally {
			conn.close();
		}
		return null;
	}
	
	public Author findAuthor(String name) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			AuthorDAO aDAO = new AuthorDAO(conn);
			return aDAO.readAuthorByName(name);
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Failed to connect to server");
		} finally {
			conn.close();
		}
		return null;
	}
	
	public Book findBook(String title) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookDAO bDAO = new BookDAO(conn);
			return bDAO.readBookByTitle(title);
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Failed to connect to server");
		} finally {
			conn.close();
		}
		return null;
	}
	
	/////////////////// BOOK CHECKOUT/RETURN REQUESTS (END) ///////////////////////
	
	public Integer addAuthor(String name) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			AuthorDAO aDAO = new AuthorDAO(conn);
			Integer id = aDAO.insertAuthor(name);
			conn.commit();
			return id;
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Failed to connect to server");
		} finally {
			conn.close();
		}
		return null;
	}
	
	public Publisher findPublisher(String name) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			PublisherDAO pDAO = new PublisherDAO(conn);
			return pDAO.readPublisherByName(name);
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Failed to connect to server");
		} finally {
			conn.close();
		}
		return null;
	}
	
	public Integer addPublisher(Publisher pub) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			PublisherDAO pDAO = new PublisherDAO(conn);
			Integer id = pDAO.insertPublisher(pub);
			conn.commit();
			return id;
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Failed to connect to server");
		} finally {
			conn.close();
		}
		return null;
	}
	
	public Integer addBook(Book book) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookDAO bDAO = new BookDAO(conn);
			Integer id = bDAO.insertBook(book);
			conn.commit();
			return id;
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Failed to connect to server");
		} finally {
			conn.close();
		}
		return null;
	}
	
	public void updateBook(Book book) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookDAO bDAO = new BookDAO(conn);
			bDAO.updateBook(book);
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Failed to connect to server");
		} finally {
			conn.close();
		}
	}
	
	public void deleteBook(Book book) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookDAO bDAO = new BookDAO(conn);
			bDAO.deleteBook(book);
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Failed to connect to server");
		} finally {
			conn.close();
		}
	}
	
	public List<Publisher> getPublishers() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			PublisherDAO pDAO = new PublisherDAO(conn);
			return pDAO.readAllPublishers();
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return null;
	}
	
	public void updatePublisher(Publisher pub) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			PublisherDAO pDAO = new PublisherDAO(conn);
			pDAO.updatePublisher(pub);
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}
	
	public void deletePublisher(Publisher pub) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			PublisherDAO pDAO = new PublisherDAO(conn);
			pDAO.deletePublisher(pub);
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}
	
	public Borrower findBorrowerByCardNo(Integer cardNo) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BorrowerDAO bDAO = new BorrowerDAO(conn);
			return bDAO.readBorrowerByCardNo(cardNo);
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return null;
	}
	public List<Borrower> getBorrowers() throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BorrowerDAO bDAO = new BorrowerDAO(conn);
			return bDAO.readAllBorrowers();
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
		return null;
	}
	
	public void addBorrower(Borrower bor) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BorrowerDAO bDAO = new BorrowerDAO(conn);
			bDAO.insertBorrower(bor);
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Failed to connect to server");
		} finally {
			conn.close();
		}
	}
	
	public void updateBorrower(Borrower bor) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BorrowerDAO bDAO = new BorrowerDAO(conn);
			bDAO.updateBorrower(bor);
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Failed to connect to server");
		} finally {
			conn.close();
		}
	}
	
	public void deleteBorrower(Borrower bor) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BorrowerDAO bDAO = new BorrowerDAO(conn);
			bDAO.deleteBorrower(bor);
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}
	
	public void extendDueDate(BookLoans bl) throws SQLException {
		Connection conn = null;
		try {
			conn = util.getConnection();
			BookLoansDAO blDAO = new BookLoansDAO(conn);
			
			blDAO.updateBookLoans(bl);
			conn.commit();
		} catch(Exception e) {
			e.printStackTrace();
			conn.rollback();
			System.out.println("Failed to connect to server");
		} finally {
			conn.close();
		}
	}
}
