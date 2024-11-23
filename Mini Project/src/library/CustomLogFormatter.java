package library;

import java.util.logging.*;

public class CustomLogFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        StringBuilder sb = new StringBuilder();
        sb.append(new java.util.Date(record.getMillis()))
          .append(" - ")
          .append(record.getLevel())
          .append(": ")
          .append(record.getMessage())
          .append("\n");
        return sb.toString();
    }
}