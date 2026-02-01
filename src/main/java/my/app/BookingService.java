package my.app;

import my.app.exception.BookingConflictException;
import my.app.model.Booking;
import my.app.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking saveBooking(Booking newBooking) {
        if (newBooking.getEnd_time().isBefore(newBooking.getStart_time())) {
            throw new BookingConflictException("End time cannot be before start time!");
        }

        List<Booking> bookings = bookingRepository.findAll();
        for (Booking booking : bookings) {
            if(booking.getBooking_id().equals(newBooking.getBooking_id())) {
                continue;
            }
            if (booking.getResource_id().equals(newBooking.getResource_id())) {
                boolean intersects = newBooking.getStart_time().isBefore(booking.getEnd_time()) &&
                        newBooking.getEnd_time().isAfter(booking.getStart_time());

                if(intersects){
                    throw new BookingConflictException("This resource has already been booked.");
                }
            }

        }
        return bookingRepository.save(newBooking);
    }

    public Booking updateBooking(Booking updatedBooking, Long id) {
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking " + id + " not found"));

        if (updatedBooking.getStart_time() != null) {
            existingBooking.setStart_time(updatedBooking.getStart_time());
        }
        if (updatedBooking.getEnd_time() != null) {
            existingBooking.setEnd_time(updatedBooking.getEnd_time());
        }

        return saveBooking(existingBooking);
    }

    public List<Booking> getAll(){
        return bookingRepository.findAll();
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
