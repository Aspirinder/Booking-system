package my.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Booking")
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long booking_id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "resource_id")
    private Long resource_id;

    @Future(message = "The start date must be in the future")
    @Column(name = "start_time")
    private LocalDateTime start_time;

    @Future(message = "The end date must be in the future")
    @Column(name = "end_time")
    private LocalDateTime end_time;

    public String status;
}
