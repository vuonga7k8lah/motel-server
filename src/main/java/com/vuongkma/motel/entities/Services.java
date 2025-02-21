package com.vuongkma.motel.entities;

import com.vuongkma.motel.helpers.enums.StatusService;
import com.vuongkma.motel.helpers.enums.TypeService;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "services")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TypeService type;

    @Column(name = "content")
    private String content;
    @Column(name = "icon")
    private String icon;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusService status;

    private Date created_at;
    @PrePersist
    protected void onCreate() {
        created_at = new Date();
    }
    private Date update_at;
}
