package com.vuongkma.motel.entities;

import com.vuongkma.motel.helpers.enums.StatusContract;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "contracts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long renter_id;

    private Long room_id;
    private Date start_date;
    private Date end_date;
    @Column(name = "deposit", nullable = false, columnDefinition = "TEXT")
    private String deposit;

    private String content;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusContract status;

    private Date created_at;
    @PrePersist
    protected void onCreate() {
        created_at = new Date();
    }
    private Date update_at;
}
