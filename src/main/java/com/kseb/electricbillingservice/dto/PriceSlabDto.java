package com.kseb.electricbillingservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PriceSlabDto {
    private String slabName;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private BigDecimal unitsMinimum;
    private BigDecimal unitsMaximum;
    private BigDecimal pricePerUnit;
    private String CustomerType;
    private String unit;
}
