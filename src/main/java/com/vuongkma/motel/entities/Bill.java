package com.vuongkma.motel.entities;

import com.vuongkma.motel.helpers.enums.StatusBill;
import com.vuongkma.motel.helpers.enums.StatusRoom;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "bills")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long contract_id;
    @Column(name = "amount", unique = true)
    private String amount;
    private Date due_date;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusBill status;

    private Date created_at;
    @PrePersist
    protected void onCreate() {
        created_at = new Date();
    }
    private Date update_at;
}
