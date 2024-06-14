package bg.energopro.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "users", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    public Long userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address = "";

    @Column(name = "phone")
    private String phone = "";

    @Column(name = "bio")
    private String bio = "";

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public User(String john, String doe, String mail, String s, String s1, String s2, LocalDateTime now) {
    }
}
