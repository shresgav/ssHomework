package library.cli;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import library.model.Book;
import library.model.Borrower;
import library.model.LibraryBranch;
import library.model.Publisher;
import library.service.AdminServices;

public class CLIDriver {
	public static final Scanner sc = new Scanner(System.in);
	public static final CLIMenus cli = new CLIMenus();
	public static final AdminServices service = new AdminServices();
	
	public static void main(String[] args) {
		while (true) {
			for (int i = 0; i < 10; ++i) {
				System.out.println("SAM IS GAY");
			}
			cli.mainMenu();
			Integer option = readOption(sc);
			while (option == -1) {
				option = readOption(sc);
			}
			// LIBRARIAN INTERFACE
			if (option == 1) {
				Integer librarianOption = null;
				do {
					cli.lib1Menu();
					librarianOption = readOption(sc);
					if (librarianOption == 1) {
						lib2Helper();
					}
				} while (librarianOption != 2);
			}

			// ADMINISTRATOR INTERFACE
			if (option == 2) {
				Integer adminOption = null;
				do {
					cli.adminMainMenu();
					adminOption = readOption(sc);
					while (adminOption == -1) {
						adminOption = readOption(sc);
					}
					// ADMIN BOOK CRUD OPERATIONS
					if (adminOption == 1) {
						adminBookHelper(adminOption);
					}
					// ADMIN PUBLISHER CRUD OPERATIONS
					else if (adminOption == 2) {
						adminPublisherHelper(adminOption);
					}
					// ADMIN BRANCH CRUD OPERATIONS
					else if (adminOption == 3) {
						adminBranchHelper(adminOption);
					}
					// ADMIN BORROWER CRUD OPERATIONS
					else if (adminOption == 4) {
						adminBorrowerHelper(adminOption);
					}
					// ADMIN OVER-RIDE DUE DATE OPERATIONS
					else if (adminOption == 5) {
						adminDueDateHelper();
					}
				} while (adminOption != 6);
			}

			// BORROWER INTERFACE
			if (option == 3) {
				Integer cardNo = readCardNo();
				if (cardNo == -1) continue;
				Integer borrowerOption = null;
				do {
					cli.borr1Menu(sc);
					borrowerOption = readOption(sc);
					while (borrowerOption == -1) {
						borrowerOption = readOption(sc);
					}

					// CHECK OUT A BOOK
					if (borrowerOption == 1) {
						checkOutHelper(cardNo);
					}

					// RETURN A BOOK
					if (borrowerOption == 2) {
						returnHelper(cardNo);
					}
				} while (borrowerOption != 3);
			}
		}
	}
	
	// Reads user input with some error checking
	private static int readOption(Scanner sc) {
		try {
			return Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("Invalid input");
			return -1;
		}
	}
	
	// Prints lib2Menu and interprets user input
	private static void lib2Helper() {
		List<LibraryBranch> branches;
		try {
			branches = service.getBranches();
			Integer branchOption = null;
			do {
				cli.lib2Menu(branches);
				branchOption = readOption(sc);
				if (branchOption != branches.size() + 1) {
					lib3Helper(branches.get(branchOption - 1));
				}
			} while (branchOption != branches.size() + 1);
		} catch (SQLException e) {
			System.out.println("Connection to database failed");
		}
	}
	
	// Prints lib3Menu and interprets user input
	private static void lib3Helper(LibraryBranch lb) throws SQLException {
		Integer commandOption = null;
		do {
			cli.lib3Menu();
			commandOption = readOption(sc);
			while (commandOption == -1) {
				commandOption = readOption(sc);
			}
			// Update branch name/address
			if (commandOption == 1) {
				if (cli.branchUpdate(lb, sc)) {
					System.out.println("Branch updated");
				} else {
					System.out.println("Changes canceled");
				}
			}

			// Add books to branch
			else if (commandOption == 2) {
				List<Book> books = service.getBooks();
				cli.updateBookCopiesMenu(books);
				Integer bookOption = readOption(sc);
				while (bookOption == -1) {
					bookOption = readOption(sc);
				}
				if (bookOption >= books.size() + 1) {
					System.out.println("Operation canceled");
				} else {
					cli.updateBookCopies(books.get(bookOption - 1), lb, sc);
					System.out.println("Book copies updated!");
				}
			}

		} while (commandOption != 3);
	}
	
	// Prints Admin Book CRUD options and interprets input
	private static void adminBookHelper(Integer adminOption) {
		Integer adminBookOption = null;
		do {
			cli.adminTemplateMenu(adminOption);
			adminBookOption = readOption(sc);
			while (adminBookOption == -1) {
				adminBookOption = readOption(sc);
			}
			Integer bookIndex = null;
			List<Book> books;
			try {
				books = service.getBooks();
				switch (adminBookOption) {
				case 1:
					cli.adminAddBook(sc);
					break;
				case 2:
					cli.adminBookMenu(books);
					bookIndex = readOption(sc) - 1;
					while (bookIndex == -2) {
						bookIndex = readOption(sc) - 1;
					}
					if (bookIndex < books.size()) {
						cli.adminUpdateBook(books.get(bookIndex), sc);
					}
					break;
				case 3:
					cli.adminBookMenu(books);
					bookIndex = readOption(sc) - 1;
					while (bookIndex == -2) {
						bookIndex = readOption(sc) - 1;
					}
					if (bookIndex < books.size()) {
						cli.adminDeleteBook(books.get(bookIndex), sc);
					}
					break;
				default:
					break;
				}
			} catch (SQLException e) {
				System.out.println("Connection to database failed");
			}
		} while (adminBookOption != 4);
	}
	
	// Prints Admin Publisher CRUD options and interprets input
	private static void adminPublisherHelper(Integer adminOption) {
		Integer adminPublisherOption = null;
		do {
			cli.adminTemplateMenu(adminOption);
			adminPublisherOption = readOption(sc);
			while (adminPublisherOption == -1) {
				adminPublisherOption = readOption(sc);
			}
			Integer publisherIndex = null;
			List<Publisher> pubs;
			try {
				pubs = service.getPublishers();
				switch (adminPublisherOption) {
				case 1:
					cli.adminAddPublisher(sc);
					break;
				case 2:
					cli.adminPublisherMenu(pubs);
					publisherIndex = readOption(sc) - 1;
					while (publisherIndex == -2) {
						publisherIndex = readOption(sc) - 1;
					}
					if (publisherIndex < pubs.size()) {
						cli.adminUpdatePublisher(pubs.get(publisherIndex), sc);
					}
					break;
				case 3:
					cli.adminPublisherMenu(pubs);
					publisherIndex = readOption(sc) - 1;
					while (publisherIndex == -2) {
						publisherIndex = readOption(sc) - 1;
					}
					if (publisherIndex < pubs.size()) {
						cli.adminDeletePublisher(pubs.get(publisherIndex), sc);
					}
					break;
				default:
					break;
				}
			} catch (SQLException e) {
				System.out.println("Connection to database failed");
			}
		} while (adminPublisherOption != 4);
	}
	
	// Prints Admin Branch CRUD options and interprets input
	private static void adminBranchHelper(Integer adminOption) {
		Integer adminBranchOption = null;
		do {
			cli.adminTemplateMenu(adminOption);
			adminBranchOption = readOption(sc);
			while (adminBranchOption == -1) {
				adminBranchOption = readOption(sc);
			}
			Integer libIndex = null;
			List<LibraryBranch> libs;
			try {
				libs = service.getBranches();
				switch (adminBranchOption) {
				case 1:
					cli.adminAddLibraryBranch(sc);
					break;
				case 2:
					cli.lib2Menu(libs);
					libIndex = readOption(sc) - 1;
					while (libIndex == -2) {
						libIndex = readOption(sc) - 1;
					}

					if (libIndex < libs.size()) {
						cli.adminUpdateLibraryBranch(libs.get(libIndex), sc);
					}
					break;
				case 3:
					cli.lib2Menu(libs);
					libIndex = readOption(sc) - 1;
					while (libIndex == -2) {
						libIndex = readOption(sc) - 1;
					}

					if (libIndex < libs.size()) {
						cli.adminDeleteBranch(libs.get(libIndex), sc);
					}
					break;
				default:
					break;
				}
			} catch (SQLException e) {
				System.out.println("Connection to database failed");
			}
		} while (adminBranchOption != 4);
	}
	
	// Prints Admin Book Borrower options and interprets input
	private static void adminBorrowerHelper(Integer adminOption) {
		Integer adminBorrowerOption = null;
		do {
			cli.adminTemplateMenu(adminOption);
			adminBorrowerOption = readOption(sc);
			while (adminBorrowerOption == -1) {
				adminBorrowerOption = readOption(sc);
			}
			Integer borIndex = null;
			List<Borrower> bors;
			try {
				bors = service.getBorrowers();
				switch (adminBorrowerOption) {
				case 1:
					cli.adminAddBorrower(sc);
					break;
				case 2:
					cli.adminBorrowerMenu(bors);
					borIndex = readOption(sc) - 1;
					while (borIndex == -2) {
						borIndex = readOption(sc) - 1;
					}

					if (borIndex < bors.size()) {
						cli.adminUpdateBorrower(bors.get(borIndex), sc);
					}
					break;
				case 3:
					cli.adminBorrowerMenu(bors);
					borIndex = readOption(sc) - 1;
					while (borIndex == -2) {
						borIndex = readOption(sc) - 1;
					}

					if (borIndex < bors.size()) {
						cli.adminDeleteBorrower(bors.get(borIndex), sc);
					}
					break;
				default:
					break;
				}
			} catch (SQLException e) {
				System.out.println("Connection to database failed");
			}
		} while (adminBorrowerOption != 4);
	}
	
	// Prints due date extension menu and interprets input
	private static void adminDueDateHelper() {
		List<Borrower> bors;
		try {
			bors = service.getBorrowers();
			cli.adminMenuChangeDueDate(bors);
			Integer borrowerOption = readOption(sc) - 1;
			while (borrowerOption == -2) {
				borrowerOption = readOption(sc) - 1;
			}

			if (borrowerOption < bors.size()) {
				List<Book> books = service.loanedBooks(bors.get(borrowerOption).getCardNo());
				cli.adminChangeDueDate(books, bors.get(borrowerOption), sc);
			}
		} catch (SQLException e) {
			System.out.println("Connection to database failed");
		}
	}
	
	// Read card number until 
	private static Integer readCardNo() {
		System.out.println("Enter your card number or -1 to quit: ");
		Integer cardNo = readOption(sc);
		if (cardNo == -1) return -1;
		List<Borrower> borrowerList;
		try {
			borrowerList = service.listAllBorrowers();
			Set<Integer> cardNums = new HashSet<>();
			for (Borrower borrower : borrowerList) {
				cardNums.add(borrower.getCardNo());
			}
			while (!cardNums.contains(cardNo)) {
				System.out.println("Card number does not exist. Please re-enter your card number or -1 to quit:");
				cardNo = readOption(sc);
				if (cardNo == -1) return -1;
			}
		} catch (SQLException e) {
			System.out.println("Connection to database failed");
		}
		
		return cardNo;
	}
	
	private static void checkOutHelper(Integer cardNo) {
		List<LibraryBranch> branches;
		try {
			branches = service.getBranches();
			Integer branchOption = null;
			do {
				cli.checkOutBranch(branches);
				branchOption = readOption(sc);
				while (branchOption == -1) {
					branchOption = readOption(sc);
				}

				if (branchOption != branches.size() + 1) {
					LibraryBranch lib = branches.get(branchOption - 1);
					if (!cli.checkOutBook(lib, cardNo, sc))
						break;
				}

			} while (branchOption != branches.size() + 1);
		} catch (SQLException e) {
			System.out.println("Connection to database failed");
		}
	}
	
	public static void returnHelper(Integer cardNo) {
		List<LibraryBranch> branches;
		try {
			branches = service.getBranches();
			Integer branchOption = null;
			do {
				cli.returnBranch(branches);
				branchOption = readOption(sc);
				while (branchOption == -1) {
					branchOption = readOption(sc);
				}
				if (branchOption != branches.size() + 1) {
					LibraryBranch lib = branches.get(branchOption - 1);
					if (!cli.returnBook(lib, cardNo, sc))
						break;
				}
			} while (branchOption != branches.size() + 1);
		} catch (SQLException e) {
			System.out.println("Connection to database failed");
		}
	}
}
