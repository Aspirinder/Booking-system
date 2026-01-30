package my.app;

import my.app.model.Booking;
import my.app.model.Resource;
import my.app.model.User;
import my.app.repository.BookingRepository;
import my.app.repository.ResourceRepository;
import my.app.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner testDB(UserRepository userRepository, ResourceRepository resourceRepository, BookingRepository bookingRepository) {
        return (args) -> {
            /*User testUser = new User(); // new User
            testUser.setName("Alex");
            testUser.setEmail("alex@example.com");
            testUser.setRole("ADMIN");
            User savedUser = userRepository.save(testUser);

            Resource testResource = new Resource(); // new Room
            testResource.setName("Room 1");
            testResource.setDescription("description");
            testResource.setType("Office");
            testResource.setPrice(100);

            Resource savedResource = resourceRepository.save(testResource);

            Booking testBooking = new Booking(); // make Booking
            testBooking.setUser_id(savedUser.getUser_id());
            testBooking.setResource_id(savedResource.getResource_id());
            testBooking.setStart_time(LocalDateTime.now());
            testBooking.setEnd_time(LocalDateTime.now().plusHours(2));
            testBooking.setStatus("COMPLETED");

            bookingRepository.save(testBooking);

            System.out.println("Count of bookings: " + bookingRepository.count());*/
        };
    }
}