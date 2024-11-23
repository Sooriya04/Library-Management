package library;

public class Book {
    private String id;
    private String title;
    private boolean isAvailable;

    public Book(String id, String title) {
        this.id = id;
        this.title = title;
        this.isAvailable = true;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}