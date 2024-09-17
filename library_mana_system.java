
//import java.util.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Date;
 class Book {
        private String title;
        private String author;
        private String ISBN;
        private int copiesAvailable;
    
        public Book(String title, String author, String ISBN, int copiesAvailable) {
            this.title = title;
            this.author = author;
            this.ISBN = ISBN;
            this.copiesAvailable = copiesAvailable;
        }
    
        public String getTitle() { return title; }
        public String getAuthor() { return author; }
        public String getISBN() { return ISBN; }
        public int getCopiesAvailable() { return copiesAvailable; }
    
        public void setCopiesAvailable(int copiesAvailable) {
            this.copiesAvailable = copiesAvailable;
        }
    
        @Override
        public String toString() {
            return "Book [Title=" + title + ", Author=" + author + ", ISBN=" + ISBN + ", Copies Available=" + copiesAvailable + "]";
        }
    }
    class User {
        private String userId;
        private String name;
    
        public User(String userId, String name) {
            this.userId = userId;
            this.name = name;
        }
    
        public String getUserId() { return userId; }
        public String getName() { return name; }
    
        @Override
        public String toString() {
            return "User [UserID=" + userId + ", Name=" + name + "]";
        }
    }
   

 class Transaction {
    private String transactionId;
    private String bookISBN;
    private String userId;
    private Date issueDate;
    private Date returnDate;

    public Transaction(String transactionId, String bookISBN, String userId, Date issueDate) {
        this.transactionId = transactionId;
        this.bookISBN = bookISBN;
        this.userId = userId;
        this.issueDate = issueDate;
    }

    public String getTransactionId() { return transactionId; }
    public String getBookISBN() { return bookISBN; }
    public String getUserId() { return userId; }
    public Date getIssueDate() { return issueDate; }
    public Date getReturnDate() { return returnDate; }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Transaction [TransactionID=" + transactionId + ", BookISBN=" + bookISBN + ", UserID=" + userId +
               ", IssueDate=" + issueDate + ", ReturnDate=" + (returnDate != null ? returnDate : "Not returned") + "]";
    }
}


 class Library {
    private List<Book> books;
    private List<User> users;
    private List<Transaction> transactions;

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    // Book Management
    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(String ISBN) {
        books.removeIf(book -> book.getISBN().equals(ISBN));
    }

    public Book findBookByISBN(String ISBN) {
        return books.stream().filter(book -> book.getISBN().equals(ISBN)).findFirst().orElse(null);
    }

    // User Management
    public void addUser(User user) {
        users.add(user);
    }

    public User findUserById(String userId) {
        return users.stream().filter(user -> user.getUserId().equals(userId)).findFirst().orElse(null);
    }

    // Transaction Management
    public void issueBook(String ISBN, String userId) {
        Book book = findBookByISBN(ISBN);
        User user = findUserById(userId);

        if (book != null && user != null && book.getCopiesAvailable() > 0) {
            String transactionId = UUID.randomUUID().toString();
            Transaction transaction = new Transaction(transactionId, ISBN, userId, new Date());
            transactions.add(transaction);
            book.setCopiesAvailable(book.getCopiesAvailable() - 1);
            System.out.println("Book issued successfully.");
        } else {
            System.out.println("Cannot issue book. Either book or user not found, or no copies available.");
        }
    }

    public void returnBook(String transactionId) {
        Transaction transaction = transactions.stream()
                .filter(tr -> tr.getTransactionId().equals(transactionId)).findFirst().orElse(null);

        if (transaction != null) {
            Book book = findBookByISBN(transaction.getBookISBN());
            if (book != null) {
                transaction.setReturnDate(new Date());
                book.setCopiesAvailable(book.getCopiesAvailable() + 1);
                System.out.println("Book returned successfully.");
            }
        } else {
            System.out.println("Transaction not found.");
        }
    }

    public void listBooks() {
        books.forEach(System.out::println);
    }

    public void listUsers() {
        users.forEach(System.out::println);
    }

    public void listTransactions() {
        transactions.forEach(System.out::println);
    }
}


public class library_mana_system {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Add User");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. List Books");
            System.out.println("6. List Users");
            System.out.println("7. List Transactions");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter ISBN: ");
                    String ISBN = scanner.nextLine();
                    System.out.print("Enter number of copies: ");
                    int copies = scanner.nextInt();
                    library.addBook(new Book(title, author, ISBN, copies));
                    break;

                case 2:
                    System.out.print("Enter user ID: ");
                    String userId = scanner.nextLine();
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    library.addUser(new User(userId, name));
                    break;

                case 3:
                    System.out.print("Enter ISBN: ");
                    String issueISBN = scanner.nextLine();
                    System.out.print("Enter user ID: ");
                    String issueUserId = scanner.nextLine();
                    library.issueBook(issueISBN, issueUserId);
                    break;

                case 4:
                    System.out.print("Enter transaction ID: ");
                    String transactionId = scanner.nextLine();
                    library.returnBook(transactionId);
                    break;

                case 5:
                    library.listBooks();
                    break;

                case 6:
                    library.listUsers();
                    break;

                case 7:
                    library.listTransactions();
                    break;

                case 8:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}


