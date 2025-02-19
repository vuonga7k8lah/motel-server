package com.vuongkma.motel.entities;

import com.vuongkma.motel.helpers.enums.RoleUser;
import com.vuongkma.motel.helpers.enums.StatusRoom;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "rooms")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long user_id;
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "address", nullable = false, unique = true)
    private String address;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "price", nullable = false)
    private String price;

    @Column(name = "images", nullable = false, columnDefinition = "TEXT")
    private String images;

    @Column(name = "desc",columnDefinition = "TEXT")
    private String desc;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusRoom status;

    private Date created_at;
    @PrePersist
    protected void onCreate() {
        created_at = new Date();
    }
    private Date update_at;
}
