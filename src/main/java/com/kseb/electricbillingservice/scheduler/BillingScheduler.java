package com.kseb.electricbillingservice.scheduler;

import com.kseb.electricbillingservice.entity.ConsumerUnits;
import com.kseb.electricbillingservice.entity.CustomerBill;
import com.kseb.electricbillingservice.entity.PriceSlab;
import com.kseb.electricbillingservice.repository.ConsumerUnitsRepository;
import com.kseb.electricbillingservice.repository.CustomerBillRepository;
import com.kseb.electricbillingservice.repository.PriceSlabRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class BillingScheduler {
    @Autowired
    ConsumerUnitsRepository consumerUnitsRepository;

    @Autowired
    PriceSlabRepository priceSlabRepository;

    @Autowired
    CustomerBillRepository customerBillRepository;


    /**
     * Scheduler that run every 28th of every months with 30 minutes interval to calculate bill of a customer
     */
    @Scheduled(cron = "${scheduler.customerBillCron}")
    @Transactional
    public  void calculateBill(){
        log.info("Scheduler for calculating bill amount for each customer started at {}", LocalDateTime.now());
        List<ConsumerUnits> consumerUnits = consumerUnitsRepository.findByBilledFalse();
        List<CustomerBill> bills = new ArrayList<>();
        log.info("Found n = {}, no of records", consumerUnits.size());
        List<PriceSlab> priceSlabs = priceSlabRepository.findAllAndCache();
        log.info("Fetched {} price slab records from cache",priceSlabs.size());
        consumerUnits.forEach(consumerUnit -> {
          try{
              LocalDate unitUpdatedDate = consumerUnit.getToDate();
              Optional<PriceSlab> priceSlabOpt = priceSlabs.stream().filter(priceSlab ->
                              unitUpdatedDate.isAfter(priceSlab.getDateFrom())
                                      && unitUpdatedDate.isBefore(priceSlab.getDateTo())
                                      && (consumerUnit.getUnitsConsumed().compareTo(priceSlab.getUnitsMinimum()) > 0)
                                      && (consumerUnit.getUnitsConsumed().compareTo(priceSlab.getUnitsMaximum()) <0)
                                      && consumerUnit.getCustomerType().equals(priceSlab.getCustomerType()))
                      .findFirst();
              if(priceSlabOpt.isPresent()){
                  PriceSlab priceSlab = priceSlabOpt.get();
                  BigDecimal totalPrice = consumerUnit.getUnitsConsumed().multiply(priceSlab.getPricePerUnit());
                  CustomerBill bill = CustomerBill.builder()
                          .customerId(consumerUnit.getCustomerId())
                          .slabName(priceSlab.getSlabName())
                          .billedDate(LocalDate.now())
                          .totalAmount(totalPrice)
                          .paid(false)
                          .build();
                  bills.add(bill);
                  log.info("customer id {} pays {}",consumerUnit.getCustomerId(), totalPrice);
                  consumerUnit.setBilled(true);
              }
          }catch (Exception e){
              log.error("Exception while processing bill for customer {}",consumerUnit.getCustomerId(),e);
          }
        });
        log.info("saving all bills to db");
        customerBillRepository.saveAll(bills);
        log.info("Scheduler completed at {}", LocalDateTime.now());
    }

}
