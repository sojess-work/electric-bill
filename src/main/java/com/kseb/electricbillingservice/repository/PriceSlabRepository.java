package com.kseb.electricbillingservice.repository;

import com.kseb.electricbillingservice.entity.PriceSlab;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PriceSlabRepository extends JpaRepository<PriceSlab,Integer> {

    @Cacheable("priceSlabs")
    @Query("SELECT ps FROM PriceSlab ps")
    List<PriceSlab> findAllAndCache();

    @Query("SELECT p FROM PriceSlab p WHERE " +
            "((:dateFrom BETWEEN p.dateFrom AND p.dateTo) OR (:dateTo BETWEEN p.dateFrom AND p.dateTo)) " +
            "AND ((p.unitsMinimum BETWEEN :unitsMinimum AND :unitsMaximum) OR (p.unitsMaximum BETWEEN :unitsMinimum AND :unitsMaximum)) " +
            "AND p.customerType = :customerType")
    List<PriceSlab> findByDateRangeAndUnitsAndCustomerType(
             LocalDate dateFrom,
             LocalDate dateTo,
             BigDecimal unitsMinimum,
             BigDecimal unitsMaximum,
             String customerType
    );

}
