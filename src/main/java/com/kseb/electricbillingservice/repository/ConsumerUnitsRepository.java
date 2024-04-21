package com.kseb.electricbillingservice.repository;

import com.kseb.electricbillingservice.entity.ConsumerUnits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ConsumerUnitsRepository extends JpaRepository<ConsumerUnits,Integer> {

    Optional<ConsumerUnits> findByFromDateAndToDateAndCustomerId(LocalDate fromDate, LocalDate toDate,String customerId);

    List<ConsumerUnits> findByBilledFalse();
}
