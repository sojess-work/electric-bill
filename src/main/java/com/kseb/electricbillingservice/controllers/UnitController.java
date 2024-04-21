package com.kseb.electricbillingservice.controllers;

import com.kseb.electricbillingservice.constants.CMConstants;
import com.kseb.electricbillingservice.dto.CMResponse;
import com.kseb.electricbillingservice.services.UnitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/units")
@Slf4j
public class UnitController {
    @Autowired
    UnitService unitService;

    @GetMapping("/monthly-customer-consumption")
    public ResponseEntity<CMResponse> getCustomerConsumption(@RequestParam(name = "period") String period,
           @RequestParam(name = "customerId") String customerId){
        try {
            CMResponse response = unitService.getCustomerConsumption(customerId,period);
            if(response.isBadRequest()){
                return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }else {
                return ResponseEntity.ok().body(response);
            }
        }catch (Exception e){
            log.error("Exception in get consumer consumption",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    CMResponse.builder()
                            .isBadRequest(false)
                            .message(CMConstants.COULD_NOT_PROCCESS_ERROR)
                            .build());
        }
    }
}
