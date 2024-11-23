package library;
import javax.swing.*;

public class ExceptionDialog {
    public static void show(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}