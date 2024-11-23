package library;

import javax.swing.*;
import java.awt.*;

public class UserInterface {
    private final UserManager userManager;
    private final BookManager bookManager;

    public UserInterface() {
        userManager = new UserManager();
        bookManager = new BookManager();
    }

    @SuppressWarnings("unused")
    public void start() {
        JFrame frame = new JFrame("Library Management System");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(20);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        frame.add(userLabel);
        frame.add(userField);
        frame.add(passLabel);
        frame.add(passField);
        frame.add(loginButton);

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            User user = userManager.login(username, password);
            if (user != null) {
                showUserActions(user);
            } else {
                ExceptionDialog.show("Invalid username or password.");
            }
        });

        frame.setVisible(true);
    }

    @SuppressWarnings("unused")
    private void showUserActions(User user) {
        JDialog actionDialog = new JDialog();
        actionDialog.setTitle("User Actions");
        actionDialog.setSize(300, 300);
        actionDialog.setLayout(new GridLayout(0, 1));

        JButton viewBooksButton = new JButton("View Books");
        JButton borrowBookButton = new JButton("Borrow Book");
        JButton returnBookButton = new JButton("Return Book");

        actionDialog.add(viewBooksButton);
        actionDialog.add(borrowBookButton);
        actionDialog.add(returnBookButton);

        if (user instanceof Librarian) {
            JButton addBookButton = new JButton("Add Book");
            JButton removeBookButton = new JButton("Remove Book");
            JButton addUserButton = new JButton("Add User");
            JButton removeUserButton = new JButton("Remove User");
            actionDialog.add(addBookButton);
            actionDialog.add(removeBookButton);
            actionDialog.add(addUserButton);
            actionDialog.add(removeUserButton);

            addBookButton.addActionListener(e -> {
                String id = JOptionPane.showInputDialog(actionDialog, "Enter Book ID:");
                String title = JOptionPane.showInputDialog(actionDialog, "Enter Book Title:");
                if (id != null && title != null) {
                    bookManager.addBook(new Book(id, title));
                }
            });

            removeBookButton.addActionListener(e -> {
                String id = JOptionPane.showInputDialog(actionDialog, "Enter Book ID to Remove:");
                if (id != null) {
                    bookManager.removeBook(id);
                }
            });

            addUserButton.addActionListener(e -> {
                String id = JOptionPane.showInputDialog(actionDialog, "Enter User ID:");
                String name = JOptionPane.showInputDialog(actionDialog, "Enter User Name:");
                String password = JOptionPane.showInputDialog(actionDialog, "Enter User Password:");
                if (id != null && name != null && password != null) {
                    userManager.addUser(new User(id, name, password));
                }
            });

            removeUserButton.addActionListener(e -> {
                String id = JOptionPane.showInputDialog(actionDialog, "Enter User ID to Remove:");
                if (id != null) {
                    userManager.removeUser(id);
                }
            });
        }

        viewBooksButton.addActionListener(e -> {
            StringBuilder bookList = new StringBuilder("Available Books:\n");
            for (Book book : bookManager.getBooks()) {
                if (book.isAvailable()) {
                    bookList.append(book.getId()).append(": ").append(book.getTitle()).append("\n");
                }
            }
            JOptionPane.showMessageDialog(actionDialog, bookList.toString());
        });

        borrowBookButton.addActionListener(e -> {
            String bookId = JOptionPane.showInputDialog(actionDialog, "Enter Book ID to Borrow:");
            if (bookId != null) {
                boolean success = bookManager.borrowBook(bookId, user.getName());
                if (success) {
                    JOptionPane.showMessageDialog(actionDialog, user.getName() + " borrowed the book.");
                } else {
                    JOptionPane.showMessageDialog(actionDialog, "Book not available or not found.");
                }
            }
        });

        returnBookButton.addActionListener(e -> {
            String bookId = JOptionPane.showInputDialog(actionDialog, "Enter Book ID to Return:");
            if (bookId != null) {
                boolean success = bookManager.returnBook(bookId, user.getName());
                if (success) {
                    JOptionPane.showMessageDialog(actionDialog, user.getName() + " returned the book.");
                } else {
                    JOptionPane.showMessageDialog(actionDialog, "Book not found.");
                }
            }
        });

        actionDialog.setVisible(true);
    }
}