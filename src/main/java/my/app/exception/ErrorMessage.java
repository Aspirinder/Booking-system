package my.app.exception;

import java.time.LocalDateTime;

public class ErrorMessage {
    private String message;
    private LocalDateTime date;

    public ErrorMessage(String message) {
        this.message = message;
        this.date = LocalDateTime.now();
    }

    public String getMessage() { return message; }
    public LocalDateTime getDate() { return date; }
}
