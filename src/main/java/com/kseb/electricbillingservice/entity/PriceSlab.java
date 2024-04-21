package com.kseb.electricbillingservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "price_slabs")
@Data
public class PriceSlab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "slab_name")
    private String slabName;

    @Column(name = "date_from")
    private LocalDate dateFrom;

    @Column(name = "date_to")
    private LocalDate dateTo;

    @Column(name = "units_minimum")
    private BigDecimal unitsMinimum;

    @Column(name = "units_maximum")
    private BigDecimal unitsMaximum;

    @Column(name = "price_per_unit")
    private  BigDecimal pricePerUnit;

    @Column(name = "customer_type")
    private String customerType;

    @Column(name = "unit")
    private String unit;


}
