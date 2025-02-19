package com.vuongkma.motel.entities;

import com.vuongkma.motel.helpers.enums.StatusRoom;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "bookings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long user_id;
    private Long room_id;

    private Date check_in_date;
    private Date check_out_date;

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
