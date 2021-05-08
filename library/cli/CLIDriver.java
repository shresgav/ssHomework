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

	// Helper method reads user input
	private static int readOption(Scanner sc) {
		try {
			return Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("Invalid input");
			return -1;
		}
	}

	public static void main(String[] args) throws SQLException {
		CLIMenus cli = new CLIMenus();
		AdminServices service = new AdminServices();
		while (true) {
			cli.mainMenu();
			Integer option = readOption(sc);

			// LIBRARIAN INTERFACE
			if (option == 1) {
				do {
					cli.lib1Menu();
					option = readOption(sc);
					if (option == 1) {
						List<LibraryBranch> branches = service.getBranches();
						do {
							cli.lib2Menu(branches);
							option = readOption(sc);
							if (option != branches.size() + 1) {
								LibraryBranch lb = branches.get(option - 1);
								do {
									cli.lib3Menu();
									option = readOption(sc);

									// Update branch name/address
									if (option == 1) {
										if (cli.branchUpdate(lb, sc)) {
											System.out.println("Branch updated");
										} else {
											System.out.println("Changes canceled");
										}
									}

									// Add books to branch
									else if (option == 2) {
										List<Book> books = service.getBooks();
										cli.updateBookCopiesMenu(books);
										Integer bookOption = readOption(sc);
										if (bookOption == books.size() + 1) {
											System.out.println("Operation canceled");
										} else {
											cli.updateBookCopies(books.get(bookOption - 1), lb, sc);
										}
									}

								} while (option != 3);
							}
						} while (option != branches.size() + 1);
					}
				} while (option != 2);
			}

			// ADMINISTRATOR INTERFACE
			if (option == 2) {
				Integer adminOption = null;
				do {
					cli.adminMainMenu();
					adminOption = readOption(sc);
					// ADMIN BOOK CRUD OPERATIONS
					if (adminOption == 1) {
						cli.adminTemplateMenu(adminOption);
						Integer bookOption = readOption(sc);
						Integer bookIndex = null;
						List<Book> books = service.getBooks();
						switch (bookOption) {
						case 1:
							cli.adminAddBook(sc);
							break;
						case 2:
							cli.adminBookMenu(books);
							bookIndex = readOption(sc) - 1;
							cli.adminUpdateBook(books.get(bookIndex), sc);
							break;
						case 3:
							cli.adminBookMenu(books);
							bookIndex = readOption(sc) - 1;
							cli.adminDeleteBook(books.get(bookIndex), sc);
							break;
						default:
							break;
						}
					} 
					// ADMIN PUBLISHER CRUD OPERATIONS
					else if (adminOption == 2) {
						do {
							cli.adminTemplateMenu(adminOption);
							adminOption = readOption(sc);
							Integer publisherIndex = null;
							List<Publisher> pubs = service.getPublishers();
							switch(adminOption) {
							case 1:
								cli.adminAddPublisher(sc);
								break;
							case 2:
								cli.adminPublisherMenu(pubs);
								publisherIndex = readOption(sc) - 1;
								cli.adminUpdatePublisher(pubs.get(publisherIndex), sc);
								break;
							case 3:
								cli.adminPublisherMenu(pubs);
								publisherIndex = readOption(sc) - 1;
								cli.adminDeletePublisher(pubs.get(publisherIndex), sc);
								break;
							default:
								break;
							}
						} while (adminOption != 4);
						
					} 
					// ADMIN BRANCH CRUD OPERATIONS
					else if (adminOption == 3) {
						do {
							cli.adminTemplateMenu(adminOption);
							adminOption = readOption(sc);
							Integer libIndex = null;
							List<LibraryBranch> libs= service.getBranches();
							switch(adminOption) {
							case 1:
								cli.adminAddLibraryBranch(sc);
								break;
							case 2:
								cli.lib2Menu(libs);
								libIndex = readOption(sc) - 1;
								cli.adminUpdateLibraryBranch(libs.get(libIndex), sc);
								break;
							case 3:
								cli.lib2Menu(libs);
								libIndex = readOption(sc) - 1;
								cli.adminDeleteBranch(libs.get(libIndex), sc);
								break;
							default:
								break;
							}
						} while (adminOption != 4);
					} 
					// ADMIN BORROWER CRUD OPERATIONS
					else if (adminOption == 4) {
						do {
							cli.adminTemplateMenu(adminOption);
							adminOption = readOption(sc);
							Integer borIndex = null;
							List<Borrower> bors= service.getBorrowers();
							switch(adminOption) {
							case 1:
								cli.adminAddLibraryBranch(sc);
								break;
							case 2:
								System.out.println("Choose a branch:");
								cli.adminBorrowerMenu(bors);
								borIndex = readOption(sc) - 1;
								cli.adminUpdateBorrower(bors.get(borIndex), sc);
								break;
							case 3:
								System.out.println("Choose a branch:");
								cli.adminBorrowerMenu(bors);
								borIndex = readOption(sc) - 1;
								cli.adminDeleteBorrower(bors.get(borIndex), sc);
								break;
							default:
								break;
							}
						} while (adminOption != 4);
					} 
					// ADMIN OVER-RIDE DUE DATE OPERATIONS
					else if (adminOption == 5) {
						List<Borrower> bors= service.getBorrowers();
						cli.adminMenuChangeDueDate(bors);
						Integer borrowerOption = readOption(sc) - 1;
						List<Book> books = service.loanedBooks(bors.get(borrowerOption).getCardNo());
						cli.adminChangeDueDate(books, bors.get(borrowerOption), sc);
					}
				} while (adminOption != 6);
			}

			// BORROWER INTERFACE
			if (option == 3) {
				System.out.println("Enter your card number: ");
				Integer cardNo = readOption(sc);
				List<Borrower> borrowerList = service.listAllBorrowers();
				Set<Integer> cardNums = new HashSet<>();
				for (Borrower borrower : borrowerList) {
					cardNums.add(borrower.getCardNo());
				}
				while (!cardNums.contains(cardNo)) {
					System.out.println("Card number does not exist. Please re-enter your card number:");
					cardNo = readOption(sc);
				}
				do {
					cli.borr1Menu(sc);
					option = readOption(sc);
					if (option == 1) {
						List<LibraryBranch> branches = service.getBranches();
						do {
							cli.checkOutBranch(branches);
							Integer branchOption = readOption(sc);
							if (branchOption != branches.size() + 1) {
								LibraryBranch lib = branches.get(branchOption - 1);
								cli.checkOutBook(lib, cardNo, sc);
							}
						} while (option != branches.size() + 1);
					}
					if (option == 2) {
						List<LibraryBranch> branches = service.getBranches();
						do {
							cli.returnBranch(branches);
							Integer branchOption = readOption(sc);
							if (branchOption != branches.size() + 1) {
								LibraryBranch lib = branches.get(branchOption - 1);
								cli.returnBook(lib, cardNo, sc);
							}
						} while (option != branches.size() + 1);
					}
				} while (option != 3);
			}
		}
	}
}
