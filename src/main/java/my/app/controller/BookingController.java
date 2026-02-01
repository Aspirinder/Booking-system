package my.app.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import my.app.BookingService;
import my.app.model.Booking;
import my.app.repository.BookingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;


    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public Page<Booking> getBookings(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return bookingService.getAll(page, size);
    }

    @GetMapping("/user/{userId}")
    public Page<Booking> getByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return bookingService.getBookingsByUser(userId, page, size);
    }

    @PostMapping
    public Booking createBooking(@Valid @RequestBody Booking booking) {
        return bookingService.saveBooking(booking, "SAVE");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id, @RequestHeader Long userID) {
        bookingService.deleteBooking(id, userID);
        return ResponseEntity.ok("Deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> update(@PathVariable Long id, @RequestBody Booking booking, @RequestHeader Long currentUserId) {

        Booking updated = bookingService.updateBooking(booking, id, currentUserId);
        return ResponseEntity.ok(updated);
    }
}
