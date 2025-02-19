package com.vuongkma.motel.entities;

import com.vuongkma.motel.helpers.enums.PaymentMethod;
import com.vuongkma.motel.helpers.enums.StatusBill;
import com.vuongkma.motel.helpers.enums.StatusPayment;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long renter_id;
    private Long bill_id;
    @Column(name = "amount", unique = true)
    private String amount;

    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod payment_method;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPayment status;

    private Date created_at;
    @PrePersist
    protected void onCreate() {
        created_at = new Date();
    }
    private Date update_at;
}
