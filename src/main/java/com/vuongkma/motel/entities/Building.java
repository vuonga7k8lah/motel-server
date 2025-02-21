package com.vuongkma.motel.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "buildings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    private Long user_id;

    @Column(name = "address", nullable = false, unique = true)
    private String address;

    @Column(name = "images", nullable = false)
    private String images;

    @Column(name = "desc", nullable = false,columnDefinition = "TEXT NOT NULL")
    private String desc;

    private Date created_at;
    @PrePersist
    protected void onCreate() {
        created_at = new Date();
    }
    private Date update_at;
}
