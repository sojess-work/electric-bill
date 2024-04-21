package com.kseb.electricbillingservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "customer_bills")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "billed_date")
    private LocalDate billedDate;

    @Column(name = "slab_name")
    private String slabName;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "total_dues")
    private BigDecimal totalDues;

    @Column(name = "paid")
    private boolean paid;

    @Column(name = "payment_method")
    private String paymentMethod;


}
