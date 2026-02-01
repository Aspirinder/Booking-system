package my.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Booking")
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingID;

    @Column(name = "userID")
    private Long userID;

    @Column(name = "resourceID")
    private Long resourceID;

    @Future(message = "The start date must be in the future")
    @Column(name = "startTime")
    private LocalDateTime startTime;

    @Future(message = "The end date must be in the future")
    @Column(name = "endTime")
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

}
