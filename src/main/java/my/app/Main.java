package my.app;

import my.app.model.*;
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
            testUser.setName("Anton");
            testUser.setEmail("alex@example.com");
            testUser.setRole(UserStatus.ADMIN);
            User savedUser = userRepository.save(testUser);

            Resource testResource = new Resource(); // new Room
            testResource.setName("Room 2");
            testResource.setDescription("description");
            testResource.setType("Office");
            testResource.setPrice(100);

            Resource savedResource = resourceRepository.save(testResource);

            Booking testBooking = new Booking(); // make Booking
            testBooking.setUserID(savedUser.getUserID());
            testBooking.setResourceID(savedResource.getResourceID());
            testBooking.setStartTime(LocalDateTime.now().plusDays(1));
            testBooking.setEndTime(LocalDateTime.now().plusDays(1).plusHours(2));
            testBooking.setStatus(BookingStatus.NEW);

            bookingRepository.save(testBooking);

            System.out.println("Count of bookings: " + bookingRepository.count());*/
        };
    }
}