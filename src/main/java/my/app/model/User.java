package my.app.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "Users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserStatus role;
}