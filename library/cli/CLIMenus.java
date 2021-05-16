package library.cli;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import library.model.Author;
import library.model.Book;
import library.model.BookCopies;
import library.model.BookLoans;
import library.model.Borrower;
import library.model.LibraryBranch;
import library.model.Publisher;
import library.service.AdminServices;

public class CLIMenus {
	public void mainMenu() {
		System.out.println("Welcome to the GCIT Library Management System. Which category of a user are you?");
		System.out.println("1. Librarian");
		System.out.println("2. Administrator");
		System.out.println("3. Borrower");
	}

	/////////////////// LIBRARIAN MENU OPTIONS ///////////////////

	public void lib1Menu() {
		System.out.println("1. Enter branch you manage");
		System.out.println("2. Quit to previous");
	}

	public void lib2Menu(List<LibraryBranch> libs) {
		for (int i = 0; i < libs.size(); ++i) {
			System.out.println(String.valueOf(i + 1) + ". " + libs.get(i).toString());
		}
		System.out.println(String.valueOf(libs.size() + 1) + ". Quit to previous");
	}

	public void lib3Menu() {
		System.out.println("1. Update the details of the library");
		System.out.println("2. Add copies of book to the branch");
		System.out.println("3. Quit to previous");
	}

	public boolean branchUpdate(LibraryBranch lib, Scanner sc) {
		System.out.println("You have chosen to update the branch with Branch Id: " + lib.getBranchId().toString()
				+ " and Branch Name: " + lib.getBranchName() + ". Enter quit at any prompt to cancel operation");
		System.out.println("Please enter new branch name or enter N/A for no change:");
		String name = sc.nextLine();
		if (name.equals("quit")) {
			return false;
		}

		System.out.println("Please enter new branch address or enter N/A for no change:");
		String address = sc.nextLine();
		if (address.equals("quit")) {
			return false;
		}

		LibraryBranch newLib = new LibraryBranch();
		newLib.setBranchId(lib.getBranchId());

		if (name.equalsIgnoreCase("N/A")) {
			newLib.setBranchName(lib.getBranchName());
		} else {
			newLib.setBranchName(name);
		}
		if (address.equalsIgnoreCase("N/A")) {
			newLib.setBranchAddress(lib.getBranchAddress());
		} else {
			newLib.setBranchAddress(address);
		}

		AdminServices service = new AdminServices();
		try {
			service.updateBranch(newLib);
		} catch (Exception e) {
			System.out.println("Connection to database failed");
		}
		return true;
	}

	public void updateBookCopiesMenu(List<Book> books) {
		System.out.println("Pick the book you want to add copies of to your branch:");
		for (int i = 0; i < books.size(); ++i) {
			System.out.println(String.valueOf(i + 1) + ". " + books.get(i).toString());
		}
		System.out.println(String.valueOf(books.size() + 1) + ". Quit to previous");
	}

	// Perhaps this method could be abstracted better
	public void updateBookCopies(Book b, LibraryBranch lib, Scanner sc) throws SQLException {
		AdminServices service = new AdminServices();
		BookCopies bc = service.getNumBooks(b, lib);
		Integer numCopies = (bc == null) ? 0 : bc.getNoOfCopies();
		System.out.println("Existing number of copies of book: " + numCopies.toString());
		System.out.println("Enter new number of copies of book: ");
		Integer newNumCopies = null;
		while (newNumCopies == null) {
			try {
				newNumCopies = Integer.parseInt(sc.nextLine());
			} catch (Exception e) {
				System.out.println("Invalid input. Try again.");
			}
		}
		if (bc == null) {
			bc = new BookCopies(b.getBookId(), lib.getBranchId(), newNumCopies);
			service.insertBookCopies(bc);
		} else {
			bc.setNoOfCopies(newNumCopies);
			service.updateBookCopies(bc);
		}
	}

	/////////////////// LIBRARIAN MENU OPTIONS (END) ///////////////////

	/////////////////// BORROWER MENU OPTIONS ///////////////////

	public void borr1Menu(Scanner sc) {
		System.out.println("1. Check out a book");
		System.out.println("2. Return a book");
		System.out.println("3. Quit to previous");
	}

	public void checkOutBranch(List<LibraryBranch> libs) {
		System.out.println("Pick the branch you want to check out from:");
		for (int i = 0; i < libs.size(); ++i) {
			System.out.println(String.valueOf(i + 1) + ". " + libs.get(i).toString());
		}
		System.out.println(String.valueOf(libs.size() + 1) + ". Quit to previous");
	}

	// Possible bug: Checking out a book you've already checked out
	// Could just be handled naturally with rollback but unsure
	public boolean checkOutBook(LibraryBranch lib, Integer cardNo, Scanner sc) {
		System.out.println("Pick the book you want to check out:");
		AdminServices services = new AdminServices();
		List<Book> books;
		try {
			books = services.getBooksByBranch(lib);
			for (int i = 0; i < books.size(); ++i) {
				System.out.println(String.valueOf(i + 1) + " " + books.get(i).toString());
			}
			System.out.println(String.valueOf(books.size() + 1) + ". Quit to cancel operation");
			Integer option = null;
			while (option == null) {
				try {
					option = Integer.parseInt(sc.nextLine());
				} catch (Exception e) {
					System.out.println("Invalid input. Try again.");
				}
			}
			if (option <= books.size()) {
				Book book = books.get(option - 1);
				BookLoans bl = new BookLoans();
				bl.setBookId(book.getBookId());
				bl.setBranchId(lib.getBranchId());
				bl.setCardNo(cardNo);
				bl.setDateOut(LocalDate.now());
				bl.setDueDate(LocalDate.now().plusWeeks(1));
				services.checkOutBook(bl);
			} else {
				System.out.println("Operation canceled");
				return false;
			}
			return true;
		} catch (SQLException e1) {
			System.out.println("Connection to database failed");
			return false;
		}

	}

	public void returnBranch(List<LibraryBranch> libs) {
		System.out.println("Pick the branch you want to return to:");
		for (int i = 0; i < libs.size(); ++i) {
			System.out.println(String.valueOf(i + 1) + " " + libs.get(i).toString());
		}
		System.out.println(String.valueOf(libs.size() + 1) + ". Quit to previous");
	}

	public boolean returnBook(LibraryBranch lib, Integer cardNo, Scanner sc) {
		AdminServices services = new AdminServices();
		List<Book> books;
		try {
			books = services.loanedBooks(cardNo);
			if (books.isEmpty()) {
				System.out.println("You have no books checked out!");
				return false;
			}

			System.out.println("Pick the book you want to return:");
			for (int i = 0; i < books.size(); ++i) {
				System.out.println(String.valueOf(i + 1) + " " + books.get(i).toString());
			}
			System.out.println(String.valueOf(books.size() + 1) + ". Quit to cancel operation");
			Integer option = null;
			while (option == null) {
				try {
					option = Integer.parseInt(sc.nextLine());
				} catch (Exception e) {
					System.out.println("Invalid input. Try again.");
				}
			}
			if (option <= books.size()) {
				Book book = books.get(option - 1);
				services.returnBook(cardNo, book.getBookId(), lib.getBranchId());
			} else {
				System.out.println("Operation canceled");
			}
			return true;
		} catch (SQLException e1) {
			System.out.println("Connection to database failed.");
			return false;
		}
	}

	/////////////////// BORROWER MENU OPTIONS (END) ///////////////////

	/////////////////// ADMIN MENU OPTIONS ///////////////////

	public void adminMainMenu() {
		System.out.println("1. Add/Update/Delete Book and Author");
		System.out.println("2. Add/Update/Delete Publishers");
		System.out.println("3. Add/Update/Delete Library branches");
		System.out.println("4. Add/Update/Delete Borrowers");
		System.out.println("5. Override Due Date for a Book Loan");
		System.out.println("6. Quit to previous");
	}

	public void adminTemplateMenu(Integer option) {
		String s = "";
		switch (option) {
		case 1:
			s = "Book and Author";
			break;
		case 2:
			s = "Publisher";
			break;
		case 3:
			s = "Library branches";
			break;
		case 4:
			s = "Borrowers";
			break;
		default:
			break;
		}

		System.out.println("1. Add " + s);
		System.out.println("2. Update " + s);
		System.out.println("3. Delete " + s);
		System.out.println("4. Quit to previous");
	}

	public boolean adminAddBook(Scanner sc) {
		AdminServices service = new AdminServices();
		System.out.println(
				"You have chosen to add a book to the database. Enter 'quit' at any prompt to cancel the operation.");

		System.out.println("Enter title:");
		String title = sc.nextLine();
		if (title.equals("quit"))
			return false;

		Book book;
		try {
			book = service.findBook(title);
			if (book != null) {
				System.out.println("This book already exists.");
				System.out.println("Operation canceled");
				return false;
			}

			book = new Book();

			System.out.println("Enter author name or N/A for null:");
			String authName = sc.nextLine();
			if (authName.equals("quit"))
				return false;

			if (!authName.equalsIgnoreCase("N/A")) {
				Author author = service.findAuthor(authName);
				if (author == null) {
					Integer authId = service.addAuthor(authName);
					book.setAuthorId(authId);
				} else {
					book.setAuthorId(author.getAuthorId());
				}
			}
			System.out.println("Enter publisher name or N/A for null:");
			String pubName = sc.nextLine();
			if (pubName.equals("quit"))
				return false;

			if (!pubName.equalsIgnoreCase("N/A")) {
				Publisher publisher = service.findPublisher(pubName);
				if (publisher == null) {
					publisher = new Publisher();
					publisher.setName(pubName);
					Integer pubId = service.addPublisher(publisher);
					book.setPubId(pubId);
				} else {
					book.setPubId(publisher.getId());
				}
			}
			book.setTitle(title);
			service.addBook(book);
			return true;
		} catch (SQLException e) {
			System.out.println("Connection to database failed.");
			return false;
		}

	}

	public void adminBookMenu(List<Book> books) {
		System.out.println("Choose a book:");
		for (int i = 0; i < books.size(); ++i) {
			System.out.println(String.valueOf(i + 1) + ". " + books.get(i).toString());
		}
		System.out.println(String.valueOf(books.size() + 1) + ". Quit to previous");
	}

	public void adminUpdateBook(Book b, Scanner sc) {
		AdminServices service = new AdminServices();
		System.out.println(
				"You have chosen to update " + b.toString() + ". Enter !quit at any prompt to cancel the operation");

		System.out.println("Enter new title or enter N/A for no change");
		String title = sc.nextLine();
		if (title.equals("!quit")) {
			System.out.println("Operation canceled");
			return;
		}

		System.out.println("Enter new author or enter N/A for no change");
		String author = sc.nextLine();
		if (author.equals("!quit")) {
			System.out.println("Operation canceled");
			return;
		}

		if (!title.equalsIgnoreCase("N/A")) {
			b.setTitle(title);
		}
		try {
			if (!author.equalsIgnoreCase("N/A")) {
				Author a = service.findAuthor(author);
				if (a == null) {
					Integer authId = service.addAuthor(author);
					b.setAuthorId(authId);
				} else {
					b.setAuthorId(a.getAuthorId());
				}
			}
			service.updateBook(b);
			System.out.println("Book updated");
		} catch (Exception e) {
			System.out.println("Connection to database failed.");
		}
	}

	public void adminDeleteBook(Book b, Scanner sc) {
		System.out.println("You have chosen to delete " + b.toString() + ". Enter \"" + b.toString()
				+ "\" to confirm or enter q to quit.");
		String input = sc.nextLine();
		while (!input.equals(b.toString()) && !input.equals("q")) {
			System.out.println("Invalid input. Try again.");
			input = sc.nextLine();
		}
		if (input.equals("q")) {
			System.out.println("Operation canceled.");
		} else {
			AdminServices s = new AdminServices();
			try {
				s.deleteBook(b);
				System.out.println("Book deleted");
			} catch (SQLException e) {
				System.out.println("Connection to database failed.");
			}
		}
	}
	
	////////////////// ADMIN PUBLISHER MENUS /////////////////////

	public void adminAddPublisher(Scanner sc) {
		AdminServices service = new AdminServices();
		System.out.println("You have chosen to add a publisher. Enter q at any prompt to cancel the operation");

		System.out.println("Enter publisher name:");
		String pubName = sc.nextLine();
		if (pubName.equals("q")) {
			System.out.println("Operation canceled");
			return;
		}
		System.out.println("Enter publisher address or N/A for null:");
		String pubAddress = sc.nextLine();
		if (pubAddress.equals("q")) {
			System.out.println("Operation canceled");
			return;
		}

		System.out.println("Enter publisher phone or N/A for null:");
		String pubPhone = sc.nextLine();
		if (pubAddress.equals("q")) {
			System.out.println("Operation canceled");
			return;
		}

		Publisher pub = new Publisher();
		pub.setName(pubName);
		if (!pubAddress.equalsIgnoreCase("N/A"))
			pub.setAddress(pubAddress);
		if (!pubPhone.equalsIgnoreCase("N/A"))
			pub.setPhone(pubPhone);
		try {
			service.addPublisher(pub);
			System.out.println("Publisher added");
		} catch (SQLException e) {
			System.out.println("Connection to database failed");
		}
	}

	public void adminPublisherMenu(List<Publisher> pubs) {
		System.out.println("Choose a publisher:");
		for (int i = 0; i < pubs.size(); ++i) {
			System.out.println(String.valueOf(i + 1) + ". " + pubs.get(i).getName());
		}
		System.out.println(String.valueOf(pubs.size() + 1) + ". Quit to previous");
	}

	public void adminUpdatePublisher(Publisher pub, Scanner sc) {
		AdminServices service = new AdminServices();
		System.out.println("You have chosen to update publisher " + pub.getName() + ". Enter q at any prompt to quit.");

		System.out.println("Enter new publisher name or N/A for no change:");
		String pubName = sc.nextLine();
		if (pubName.equals("q")) {
			System.out.println("Operation canceled.");
			return;
		}
		System.out.println("Enter new publisher address or N/A for no change:");
		String pubAddress = sc.nextLine();
		if (pubAddress.equals("q")) {
			System.out.println("Operation canceled.");
			return;
		}

		System.out.println("Enter new publisher phone or N/A for no change:");
		String pubPhone = sc.nextLine();
		if (pubPhone.equals("q")) {
			System.out.println("Operation canceled.");
			return;
		}

		if (!pubAddress.equalsIgnoreCase("N/A")) {
			pub.setAddress(pubAddress);
		}
		if (!pubName.equalsIgnoreCase("N/A")) {
			pub.setName(pubName);
		}
		if (!pubPhone.equalsIgnoreCase("N/A")) {
			pub.setPhone(pubPhone);
		}

		try {
			service.updatePublisher(pub);
			System.out.println("Publisher updated");
		} catch (SQLException e) {
			System.out.println("Connection to the database failed.");
		}
	}

	public void adminDeletePublisher(Publisher pub, Scanner sc) {
		System.out.println("You have chosen to delete publisher" + pub.getName() + ". Enter \"" + pub.getName()
				+ "\" to confirm or enter q to quit.");
		String input = sc.nextLine();
		while (!input.equals(pub.getName()) && !input.equals("q")) {
			System.out.println("Invalid input. Try again.");
			input = sc.nextLine();
		}
		if (input.equals("q")) {
			System.out.println("Operation canceled.");
		} else {
			AdminServices s = new AdminServices();
			try {
				s.deletePublisher(pub);
				System.out.println("Publisher deleted");
			} catch (SQLException e) {
				System.out.println("Connection to database failed");
			}
		}
	}

	/////////////// ADMIN LIBRARY BRANCH COMMANDS ///////////////
	
	public void adminAddLibraryBranch(Scanner sc) {
		System.out
				.println("You have chosen to add a new library branch. Enter q at any prompt to cancel the operation.");
		System.out.println("Enter a branch name:");
		String name = sc.nextLine();
		
		AdminServices service = new AdminServices();
		try {
			while (!(service.findBranch(name) == null)) {
				System.out.println("This branch name already exists. Enter a new name or q to quit.");
				name = sc.nextLine();
			}
		} catch (SQLException e1) {
			System.out.println("Connection to server failed.");
			return;
		}
		if (name.equals("q")) {
			System.out.println("Operation canceled");
			return;
		}
		System.out.println("Enter a branch address or enter N/A for null:");
		String address = sc.nextLine();
		if (address.equals("q")) {
			System.out.println("Operation canceled");
			return;
		}

		LibraryBranch lib = new LibraryBranch();
		if (!name.equalsIgnoreCase("N/A")) {
			lib.setBranchName(name);
		}
		if (!address.equalsIgnoreCase("N/A")) {
			lib.setBranchAddress(address);
		}

		try {
			service.insertBranch(lib);
			System.out.println("Branch inserted");
		} catch (SQLException e) {
			System.out.println("Connection to database failed");
		}

	}

	public void adminUpdateLibraryBranch(LibraryBranch lib, Scanner sc) {
		System.out
				.println("You have chosen to update a library branch. Enter q at any prompt to cancel the operation.");
		System.out.println("Enter a branch name or N/A for no change:");
		String name = sc.nextLine();
		if (name.equals("q")) {
			System.out.println("Operation canceled");
			return;
		}

		System.out.println("Enter a branch address or enter N/A for no change:");
		String address = sc.nextLine();
		if (address.equals("q")) {
			System.out.println("Operation canceled");
			return;
		}

		AdminServices service = new AdminServices();
		if (!name.equalsIgnoreCase("N/A")) {
			lib.setBranchName(name);
		}
		if (!address.equalsIgnoreCase("N/A")) {
			lib.setBranchAddress(address);
		}

		try {
			service.updateBranch(lib);
			System.out.println("Branch updated");
		} catch (SQLException e) {
			System.out.println("Connection to database failed");
		}
	}

	public void adminDeleteBranch(LibraryBranch lib, Scanner sc) {
		System.out.println("You have chosen to delete branch " + lib.getBranchName() + ". Enter \"" + lib.getBranchName()
				+ "\" to confirm or enter q to quit.");
		String input = sc.nextLine();
		while (!input.equals(lib.getBranchName()) && !input.equals("q")) {
			System.out.println("Invalid input. Try again.");
			input = sc.nextLine();
		}
		if (input.equals("q")) {
			System.out.println("Operation canceled.");
			return;
		} else {
			AdminServices s = new AdminServices();
			try {
				s.deleteBranchByName(lib.getBranchName());
				System.out.println("Library branch deleted");
			} catch (SQLException e) {
				System.out.println("Connection to database failed");
			}
		}
	}

	////////////////// ADMIN BORROWER MENUS ////////////////////

	public void adminBorrowerMenu(List<Borrower> bors) {
		System.out.println("Choose a borrower:");
		for (int i = 0; i < bors.size(); ++i) {
			System.out.println(String.valueOf(i + 1) + ". " + bors.get(i).getName());
		}
		System.out.println(String.valueOf(bors.size() + 1) + ". Quit to previous");
	}

	public void adminAddBorrower(Scanner sc) {
		System.out.println("You have chosen to add a borrower. Enter q at any prompt to cancel the operation.");
		System.out.println("Enter a borrower ID:");

		AdminServices service = new AdminServices();
		Integer id = null;
		while (id == null) {
			try {
				String s = sc.nextLine();
				if (s.equals("q")) {
					System.out.println("Operation canceled");
					return;
				}
				id = Integer.parseInt(s);
			} catch (Exception e) {
				System.out.println("Invalid input. Try again.");
			}
			try {
				if (!(service.findBorrowerByCardNo(id) == null)) {
					System.out.println("Card number already exists. Try again.");
					id = null;
				}
			} catch (SQLException e) {
				System.out.println("Connection to database failed");
			}
		}

		System.out.println("Enter a name or enter N/A for null:");
		String name = sc.nextLine();
		if (name.equals("q")) {
			System.out.println("Operation canceled");
			return;
		}

		System.out.println("Enter an address or enter N/A for null:");
		String address = sc.nextLine();
		if (address.equals("q")) {
			System.out.println("Operation canceled");
			return;
		}

		System.out.println("Enter a phone number or enter N/A for null:");
		String phone = sc.nextLine();

		Pattern pattern = Pattern.compile("^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
		Matcher matcher = pattern.matcher(phone);
		while (!matcher.matches() && !phone.equals("q") && !phone.equals("N/A")) {
			System.out.println("Invalid phone pattern. Try again. (Example pattern (123) 456-7890))");
			phone = sc.nextLine();
			matcher = pattern.matcher(phone);
		}

		if (phone.equals("q")) {
			System.out.println("Operation canceled");
			return;
		}

		Borrower bor = new Borrower();
		if (!name.equalsIgnoreCase("N/A")) {
			bor.setName(name);
		}
		if (!address.equalsIgnoreCase("N/A")) {
			bor.setAddress(address);
		}
		if (!phone.equalsIgnoreCase("N/A")) {
			bor.setPhone(phone);
		}
		bor.setCardNo(id);

		try {
			service.addBorrower(bor);
			System.out.println("Borrower added");
		} catch (SQLException e) {
			System.out.println("Connection to database failed");
		}
	}

	public void adminUpdateBorrower(Borrower bor, Scanner sc) {
		System.out.println("You have chosen to update borrower " + bor.getName()
				+ ". Enter q at any prompt to cancel the operation.");
		System.out.println("Enter a new name or N/A for no change:");
		String name = sc.nextLine();
		if (name.equals("q")) {
			System.out.println("Operation canceled");
			return;
		}

		System.out.println("Enter a new address or N/A for no change:");
		String address = sc.nextLine();
		if (address.equals("q")) {
			System.out.println("Operation canceled");
			return;
		}

		System.out.println("Enter a new phone number or N/A for no change:");
		String phone = sc.nextLine();

		Pattern pattern = Pattern.compile("^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
		Matcher matcher = pattern.matcher(phone);
		while (!matcher.matches() && !phone.equals("q") && !phone.equals("N/A")) {
			System.out.println("Invalid phone pattern. Try again. (Example pattern (123) 456-7890))");
			phone = sc.nextLine();
			matcher = pattern.matcher(phone);
		}

		if (phone.equals("q")) {
			System.out.println("Operation canceled");
			return;
		}

		AdminServices service = new AdminServices();
		if (!name.equalsIgnoreCase("N/A")) {
			bor.setName(name);
		}
		if (!address.equalsIgnoreCase("N/A")) {
			bor.setAddress(address);
		}
		if (!phone.equalsIgnoreCase("N/A")) {
			bor.setPhone(phone);
		}

		try {
			service.updateBorrower(bor);
			System.out.println("Borrower updated");
		} catch (SQLException e) {
			System.out.println("Connection to database failed");
		}
	}

	public void adminDeleteBorrower(Borrower bor, Scanner sc) {
		System.out.println("You have chosen to delete borrower " + bor.getName() + ". Enter \"" + bor.getName()
				+ "\" to confirm or enter q to quit.");

		String input = sc.nextLine();
		while (!input.equals(bor.getName()) && !input.equals("q")) {
			System.out.println("Invalid input. Try again.");
			input = sc.nextLine();
		}
		if (input.equals("q")) {
			System.out.println("Operation canceled.");
		} else {
			AdminServices s = new AdminServices();
			try {
				s.deleteBorrower(bor);
				System.out.println("Borrower deleted");
			} catch (SQLException e) {
				System.out.println("Connection to database failed");
			}
		}
	}

	////////////// ADMIN OVERRIDE DUE DATE MENUS ////////////////

	public void adminMenuChangeDueDate(List<Borrower> borList) {
		System.out.println("Choose borrower to extend due date for.");
		for (int i = 0; i < borList.size(); ++i) {
			System.out.println(String.valueOf(i + 1) + ". " + borList.get(i).getName());
		}
		System.out.println(String.valueOf(borList.size() + 1) + ". Quit to previous");
	}

	public void adminChangeDueDate(List<Book> books, Borrower bor, Scanner sc) {
		System.out.println("Choose a book. Extends due date by 1 week");
		for (int i = 0; i < books.size(); ++i) {
			System.out.println(String.valueOf(i + 1) + ". " + books.get(i).getTitle());
		}
		System.out.println(String.valueOf(books.size() + 1) + ". Quit to previous");

		AdminServices service = new AdminServices();
		Integer option = null;
		while (option == null) {
			try {
				option = Integer.parseInt(sc.nextLine());
			} catch (Exception e) {
				System.out.println("Invalid input. Try again.");
			}
		}
		if (option <= books.size()) {
			Book book = books.get(option - 1);
			BookLoans bl = new BookLoans();
			bl.setBookId(book.getBookId());
			bl.setCardNo(bor.getCardNo());
			bl.setDateOut(LocalDate.now());
			bl.setDueDate(LocalDate.now().plusWeeks(1));
			try {
				service.extendDueDate(bl);
				System.out.println("Due date extended");
			} catch (SQLException e) {
				System.out.println("Connection to database failed");
			}
		} else {
			System.out.println("Operation canceled");
		}
	}
}
