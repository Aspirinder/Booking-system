package my.app;

import jakarta.annotation.PostConstruct;
import my.app.exception.BookingConflictException;
import my.app.model.Booking;
import my.app.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking saveBooking(Booking newBooking) {

        List<Booking> bookings = bookingRepository.findAll();
        for (Booking booking : bookings) {
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

    public List<Booking> getAll(){
        return bookingRepository.findAll();
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
