package com.kseb.electricbillingservice.controllers;

import com.kseb.electricbillingservice.constants.CMConstants;
import com.kseb.electricbillingservice.dto.CMResponse;
import com.kseb.electricbillingservice.dto.CustomerConsumptionDto;
import com.kseb.electricbillingservice.dto.CustomerDto;
import com.kseb.electricbillingservice.services.CustomerService;
import com.kseb.electricbillingservice.services.UnitService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
@Slf4j
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    UnitService unitService;

    @PostMapping("/create-customer")
    public ResponseEntity<CMResponse> addCustomer(@RequestBody @Valid  CustomerDto customerData){
        log.info("POST /customers/create-customer --> STARTS");
        try {
            CustomerDto customer = customerService.addCustomer(customerData);
            log.info("POST /customers/create-customer --> ENDS");
            return ResponseEntity.ok().body(CMResponse.builder()
                            .data(customer)
                    .build());
        }catch (Exception e){
            log.error("error adding customer {}",customerData.getCustomerId(),e);
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CMResponse.builder()
                            .message(CMConstants.COULD_NOT_PROCCESS_ERROR)
                            .build());
        }
    }

    @PostMapping("/add-consumed-units")
    public ResponseEntity<CMResponse> addConsumedUnits(@RequestBody CustomerConsumptionDto consumption){

        log.info("POST /customers/add-consumed-units --> STARTS");
        try {
            CMResponse response = unitService.addConsumedUnits(consumption);
            log.info("POST /customers/add-consumed-units --> ENDS");
            if(response.isBadRequest()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }else {
                return ResponseEntity.ok().body(response);
            }
        }catch (Exception e){
            log.error("Exception in POST /customers/add-consumed-units",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CMResponse.builder()
                            .message(CMConstants.COULD_NOT_PROCCESS_ERROR)
                            .build());
        }
    }
}
