package my.app;

import lombok.extern.slf4j.Slf4j;
import my.app.exception.BookingConflictException;
import my.app.model.Booking;
import my.app.model.User;
import my.app.model.UserStatus;
import my.app.repository.BookingRepository;
import my.app.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    public Page<Booking> getAll(int page, int size) {
        log.info("Fetching bookings for page {} with size {}", page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.by("bookingID").descending());

        return bookingRepository.findAll(pageable);
    }

    public Page<Booking> getBookingsByUser(Long userId, int page, int size) {
        log.info("Fetching bookings for user {} with pagination", userId);
        Pageable pageable = PageRequest.of(page, size, Sort.by("startTime").descending());
        return bookingRepository.findByUserID(userId, pageable);
    }

    public Booking saveBooking(Booking newBooking, String status) {
        log.info("Attempting to {} booking for resource ID: {}", status, newBooking.getBookingID());

        if (newBooking.getEndTime().isBefore(newBooking.getStartTime())) {
            log.warn("Date conflict detected: {} {} for resource ID: {}", newBooking.getEndTime(), newBooking.getStartTime(), newBooking.getBookingID());
            throw new BookingConflictException("End time cannot be before start time!");
        }

        List<Booking> bookings = bookingRepository.findAll();
        for (Booking booking : bookings) {
            if(booking.getBookingID().equals(newBooking.getBookingID())) continue;
            if (booking.getResourceID().equals(newBooking.getResourceID())) {
                boolean intersects = newBooking.getStartTime().isBefore(booking.getEndTime()) &&
                        newBooking.getEndTime().isAfter(booking.getStartTime());

                if(intersects){
                    log.warn("Conflict detected for resource ID: {} at requested time", newBooking.getResourceID());
                    throw new BookingConflictException("This resource has already been booked.");
                }
            }

        }
        Booking savedBooking = bookingRepository.save(newBooking);
        log.info("Booking successfully {} with ID: {}", status, savedBooking.getBookingID());
        return savedBooking;
    }

    public Booking updateBooking(Booking updatedBooking, Long id, Long userId) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking " + id + " not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User " + userId + " not found"));

        boolean isAdmin = UserStatus.ADMIN.equals(user.getRole());
        boolean isOwner = existingBooking.getUserID().equals(userId);

        if (!isAdmin && !isOwner) {
            log.warn("Access to update denied! User {} is not owner or admin", userId);
            throw new RuntimeException("You don't have permission to update this booking!");
        }

        if (updatedBooking.getStartTime() != null) existingBooking.setStartTime(updatedBooking.getStartTime());
        if (updatedBooking.getEndTime() != null) existingBooking.setEndTime(updatedBooking.getEndTime());
        if (updatedBooking.getStatus() != null) existingBooking.setStatus(updatedBooking.getStatus());

        return saveBooking(existingBooking, "UPDATE");
    }

    public void deleteBooking(Long id, Long userID) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking " + id + " not found"));
        User user = userRepository.findById(userID).orElseThrow(() -> new RuntimeException("User " + userID + " not found"));

        log.info("User {} attempts to delete booking {}", user, id);

        boolean isAdmin = UserStatus.ADMIN.equals(user.getRole());
        boolean isOwner = booking.getUserID().equals(id);

        if (isAdmin || isOwner) {
            bookingRepository.delete(booking);
            log.info("Booking {} was deleted successfully", id);
        } else {
            log.warn("Access to delete denied! User {} is not owner or admin", user);
            throw new RuntimeException("You don't have permission to delete this booking!");
        }
    }
}
