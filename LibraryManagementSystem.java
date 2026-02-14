import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a book in the library.
 */
class Book {
    private int id;
    private String title;
    private String author;
    private boolean isIssued;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = false; // New books are available by default
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isIssued() { return isIssued; }
    public void setIssued(boolean issued) { isIssued = issued; }

    @Override
    public String toString() {
        return "ID: " + id + " | Title: " + title + " | Author: " + author +
               " | Status: " + (isIssued ? "Issued" : "Available");
    }
}

/**
 * Manages the collection of books and provides operations.
 */
class Library {
    private List<Book> books;
    private int nextId;

    public Library() {
        books = new ArrayList<>();
        nextId = 1; // Auto-increment ID starting from 1
    }

    // Add a new book
    public void addBook(String title, String author) {
        Book book = new Book(nextId++, title, author);
        books.add(book);
        System.out.println("Book added successfully: " + book);
    }

    // Display all books
    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
            return;
        }
        System.out.println("\n--- List of Books ---");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    // Issue a book by ID
    public void issueBook(int id) {
        Book book = findBookById(id);
        if (book == null) {
            System.out.println("Book with ID " + id + " not found.");
        } else if (book.isIssued()) {
            System.out.println("Book \"" + book.getTitle() + "\" is already issued.");
        } else {
            book.setIssued(true);
            System.out.println("Book \"" + book.getTitle() + "\" issued successfully.");
        }
    }

    // Return a book by ID
    public void returnBook(int id) {
        Book book = findBookById(id);
        if (book == null) {
            System.out.println("Book with ID " + id + " not found.");
        } else if (!book.isIssued()) {
            System.out.println("Book \"" + book.getTitle() + "\" was not issued.");
        } else {
            book.setIssued(false);
            System.out.println("Book \"" + book.getTitle() + "\" returned successfully.");
        }
    }

    // Helper to find a book by ID
    private Book findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }
}

/**
 * Main class with the menu-driven user interface.
 */
public class LibraryManagementSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Library library = new Library();

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            printMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    library.displayBooks();
                    break;
                case 3:
                    issueBook();
                    break;
                case 4:
                    returnBook();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Exiting... Thank you for using the Library Management System.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
        scanner.close();
    }

    // Display the menu options
    private static void printMenu() {
        System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
        System.out.println("1. Add a Book");
        System.out.println("2. View All Books");
        System.out.println("3. Issue a Book");
        System.out.println("4. Return a Book");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    // Safely read an integer choice from the user
    private static int getUserChoice() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.next(); // clear the invalid input
            return -1; // indicates invalid input
        }
    }

    // Add a new book (reads title and author)
    private static void addBook() {
        scanner.nextLine(); // consume newline
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        library.addBook(title, author);
    }

    // Issue a book (reads ID)
    private static void issueBook() {
        System.out.print("Enter the ID of the book to issue: ");
        int id = readId();
        if (id != -1) {
            library.issueBook(id);
        } else {
            System.out.println("Invalid ID format.");
        }
    }

    // Return a book (reads ID)
    private static void returnBook() {
        System.out.print("Enter the ID of the book to return: ");
        int id = readId();
        if (id != -1) {
            library.returnBook(id);
        } else {
            System.out.println("Invalid ID format.");
        }
    }

    // Helper to read an integer ID safely
    private static int readId() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.next(); // clear invalid input
            return -1;
        }
    }
}