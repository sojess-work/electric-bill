package com.kseb.electricbillingservice.dto;

import com.kseb.electricbillingservice.validation.constraints.CustomerId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CustomerConsumptionDto {
    @CustomerId(required = true,name = "customerId",minSize = 16,maxSize = 16)
    private String customerId;
    private BigDecimal unitsConsumed;
    private String period;
    private LocalDate fromDate;
    private LocalDate toDate;
    private BigDecimal totalUnits;
}
