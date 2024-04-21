package com.kseb.electricbillingservice.services;

import com.kseb.electricbillingservice.Utils.DateUtil;
import com.kseb.electricbillingservice.constants.CMConstants;
import com.kseb.electricbillingservice.dto.CMResponse;
import com.kseb.electricbillingservice.dto.CustomerConsumptionDto;
import com.kseb.electricbillingservice.entity.ConsumerUnits;
import com.kseb.electricbillingservice.exceptions.OperationFailedException;
import com.kseb.electricbillingservice.repository.ConsumerUnitsRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
public class UnitService {

    @Value("${custMgmtConfig.units.updateDate}")
    private Integer unitsUpdateDate;
    @Autowired
    private ConsumerUnitsRepository consumerUnitsRepository;

    /**
     * This method will get units consumed by customer in a month
     * @param customerId
     * @param period
     * @return
     * @throws OperationFailedException
     */
    public CMResponse getCustomerConsumption(String customerId, String period) throws OperationFailedException {
        LocalDate fromDate;
        LocalDate toDate;
        CustomerConsumptionDto response = new CustomerConsumptionDto();

        if(StringUtils.isNotBlank(period)){
            toDate = DateUtil.getDateFromString("27"+period);
            fromDate = toDate.minusMonths(1).plusDays(1);
        }else {
            log.warn("Invalid request for customer {}", customerId);
            return CMResponse.builder()
                    .isBadRequest(true)
                    .message(CMConstants.INVALID_PERIOD)
                    .build();
        }
        if(toDate.isAfter(LocalDate.now())){
            log.warn("Invalid request for customer {}", customerId);
            return CMResponse.builder()
                    .message(CMConstants.INVALID_PERIOD)
                    .isBadRequest(true)
                    .build();
        }else {
            ConsumerUnits consumerUnits = fetchUnitConsumption(fromDate,toDate,customerId);
            response.setPeriod(period);
            BeanUtils.copyProperties(consumerUnits,response);
            return CMResponse.builder()
                    .data(response)
                    .build();
        }

    }

    /**
     * Method to update consumed units of a customer
     * @param consumption
     * @return
     */
    @Transactional
    public CMResponse addConsumedUnits(CustomerConsumptionDto consumption){
        log.info("saving units detail for customer in database");
        ConsumerUnits units = new ConsumerUnits();
        Optional<ConsumerUnits> previousConsumptionOpt = consumerUnitsRepository.findByFromDateAndToDateAndCustomerId(
                consumption.getFromDate().minusMonths(1),consumption.getToDate().minusMonths(1),consumption.getCustomerId());
        if(previousConsumptionOpt.isPresent()){
            ConsumerUnits previousConsumption = previousConsumptionOpt.get();
            if(consumption.getTotalUnits().compareTo(previousConsumption.getTotalUnits()) < 0){
                return CMResponse.builder()
                        .isBadRequest(true)
                        .message(CMConstants.INVALID_TOTAL_UNITS)
                        .build();
            }
            BigDecimal unitsConsumed = consumption.getTotalUnits().subtract(previousConsumption.getTotalUnits());
            BeanUtils.copyProperties(consumption,units);
            units.setUnitsConsumed(unitsConsumed);
            consumption.setUnitsConsumed(unitsConsumed);
        }else {
            BeanUtils.copyProperties(consumption,units);
            units.setUnitsConsumed(consumption.getTotalUnits());
            consumption.setUnitsConsumed(consumption.getTotalUnits());
        }
        consumerUnitsRepository.save(units);
        log.info("saved units detail for customer in database");
        return CMResponse.builder()
                .isBadRequest(false)
                .data(consumption)
                .build();
    }

    /**
     * Method to fetch unit consumption details from db
     * @param fromDate
     * @param toDate
     * @param customerId
     * @return
     * @throws OperationFailedException
     */
    private ConsumerUnits fetchUnitConsumption(LocalDate fromDate,LocalDate toDate,String customerId) throws OperationFailedException {

        Optional<ConsumerUnits> consumerUnits = consumerUnitsRepository.findByFromDateAndToDateAndCustomerId(fromDate,toDate,customerId);
        if(consumerUnits.isPresent()){
            return consumerUnits.get();
        }else{
            log.error("No records found for customer {} in unit consumption",customerId);
            throw new OperationFailedException("No record found for customer " + customerId);
        }
    }
}
