import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Book {
    private int id;
    private String title;
    private String author;
    private double price;

    public Book(int id, String title, String author, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return id + ": " + title + " by " + author + " - $" + price;
    }
}

class ShoppingCart {
    private List<Book> items;

    public ShoppingCart() {
        items = new ArrayList<>();
    }

    public void addItem(Book book) {
        items.add(book);
    }

    public void removeItem(Book book) {
        items.remove(book);
    }

    public void displayCart() {
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Shopping Cart:");
            for (Book book : items) {
                System.out.println(book);
            }
        }
    }

    public double calculateTotalPrice() {
        double total = 0;
        for (Book book : items) {
            total += book.getPrice();
        }
        return total;
    }
}

public class Task2 {
    private List<Book> catalog;
    private ShoppingCart cart;

    public Task2() {
        catalog = new ArrayList<>();
        cart = new ShoppingCart();
    }

    public void addBook(Book book) {
        catalog.add(book);
    }

    public void removeBook(int bookId) {
        catalog.removeIf(book -> book.getId() == bookId);
        cart.removeItem(findBookById(bookId));
    }

    public Book findBookById(int bookId) {
        for (Book book : catalog) {
            if (book.getId() == bookId) {
                return book;
            }
        }
        return null;
    }

    public void displayCatalog() {
        System.out.println("Available Books:");
        for (Book book : catalog) {
            System.out.println(book);
        }
    }

    public void addToCart(int bookId) {
        Book book = findBookById(bookId);
        if (book != null) {
            cart.addItem(book);
            System.out.println("Added to cart: " + book.getTitle());
        } else {
            System.out.println("Book not found.");
        }
    }

    public void removeFromCart(int bookId) {
        Book book = findBookById(bookId);
        if (book != null) {
            cart.removeItem(book);
            System.out.println("Removed from cart: " + book.getTitle());
        } else {
            System.out.println("Book not found in cart.");
        }
    }

    public void displayCart() {
        cart.displayCart();
        System.out.println("Total Price: $" + cart.calculateTotalPrice());
    }

    public static void main(String[] args) {
    	Task2 bookstore = new Task2();

        bookstore.addBook(new Book(1, "Book 1", "Author 1", 19.99));
        bookstore.addBook(new Book(2, "Book 2", "Author 2", 14.99));
        bookstore.addBook(new Book(3, "Book 3", "Author 3", 29.99));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Display Book Catalog");
            System.out.println("2. Add to Cart");
            System.out.println("3. Remove from Cart");
            System.out.println("4. Display Cart");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    bookstore.displayCatalog();
                    break;
                case 2:
                    System.out.print("Enter the Book ID to add to cart: ");
                    int addToCartId = scanner.nextInt();
                    bookstore.addToCart(addToCartId);
                    break;
                case 3:
                    System.out.print("Enter the Book ID to remove from cart: ");
                    int removeFromCartId = scanner.nextInt();
                    bookstore.removeFromCart(removeFromCartId);
                    break;
                case 4:
                    bookstore.displayCart();
                    break;
                case 5:
                    System.out.println("Thank you for shopping!!!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}