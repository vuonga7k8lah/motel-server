package com.vuongkma.motel.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phoneNumber;

    @Column(name = "refresh_token", columnDefinition = "TEXT")
    private String refreshToken;
    private Date created_at;
    @PrePersist
    protected void onCreate() {
        created_at = new Date();
    }
    private Date update_at;
}
