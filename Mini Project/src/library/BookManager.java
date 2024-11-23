package library;

import java.io.*;
import java.util.*;
import javax.swing.*;
public class BookManager {
    private List<Book> books;

    public BookManager() {
        books = new ArrayList<>();
        loadBooks();
    }

    private void loadBooks() {
        try (BufferedReader br = new BufferedReader(new FileReader("resources/books.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                books.add(new Book(parts[0], parts[1]));
            }
        } catch (IOException e) {
            ExceptionDialog.show("Error loading books: " + e.getMessage());
            LoggerUtil.logSevere("Error loading books: " + e.getMessage());
        }
    }

    public boolean borrowBook(String bookId, String username) {
        for (Book book : books) {
            if (book.getId().equals(bookId) && book.isAvailable()) {
                book.setAvailable(false);
                saveBooks(); // Save the updated state
                LoggerUtil.logInfo(username + " borrowed the book: " + book.getTitle());
                return true;
            }
        }
        LoggerUtil.logWarning("Borrowing failed for book ID: " + bookId + " by user: " + username);
        return false; // Borrowing failed
    }

    public boolean returnBook(String bookId, String username) {
        for (Book book : books) {
            if (book.getId().equals(bookId)) {
                book.setAvailable(true);
                saveBooks(); // Save the updated state
                LoggerUtil.logInfo(username + " returned the book: " + book.getTitle());
                return true;
            }
        }
        LoggerUtil.logWarning("Returning failed for book ID: " + bookId + " by user: " + username);
        return false; // Returning failed
    }

    public void addBook(Book book) {
        books.add(book);
        saveBooks();
        LoggerUtil.logInfo("Added book: " + book.getTitle());
        JOptionPane.showMessageDialog(null, "Book added successfully.");
    }

    public void removeBook(String id) {
        books.removeIf(book -> book.getId().equals(id));
        saveBooks();
        LoggerUtil.logInfo("Removed book with ID: " + id);
        JOptionPane.showMessageDialog(null, "Book removed successfully.");
    }

    private void saveBooks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("resources/books.txt"))) {
            for (Book book : books) {
                bw.write(book.getId() + "," + book.getTitle() + "," + book.isAvailable());
                bw.newLine();
            }
        } catch (IOException e) {
            ExceptionDialog.show("Error saving books: " + e.getMessage());
            LoggerUtil.logSevere("Error saving books: " + e.getMessage());
        }
    }

    public List<Book> getBooks() {
        return books;
    }
}