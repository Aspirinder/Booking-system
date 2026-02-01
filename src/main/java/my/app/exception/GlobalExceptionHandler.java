package my.app.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookingConflictException.class)
    public ResponseEntity<ErrorMessage> handleBookingConflict(BookingConflictException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage());

        return ResponseEntity.status(409).body(errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorMessage errorMessage = new ErrorMessage(message);
        return ResponseEntity.status(400).body(errorMessage);
    }
}
