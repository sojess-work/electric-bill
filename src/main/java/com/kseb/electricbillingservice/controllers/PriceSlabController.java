package com.kseb.electricbillingservice.controllers;

import com.kseb.electricbillingservice.constants.CMConstants;
import com.kseb.electricbillingservice.dto.CMResponse;
import com.kseb.electricbillingservice.dto.PriceSlabDto;
import com.kseb.electricbillingservice.services.PriceSlabService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/priceslabs")
@Slf4j
public class PriceSlabController {
    @Autowired
    PriceSlabService priceSlabService;
    @PostMapping("/add-new")
    public ResponseEntity<CMResponse> addNewPriceSlab(@RequestBody PriceSlabDto priceSlab){
        log.info("POST /priceslabs/add-new --STARTS");
        try {
            CMResponse response = priceSlabService.addNewPriceSlab(priceSlab);
            log.info("POST /priceslabs/add-new --ENDS");
            if(response.isBadRequest()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }else {
                return ResponseEntity.ok().body(response);
            }
        }catch (Exception e){
            log.error("Exception in POST /priceslabs/add-new",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CMResponse.builder()
                            .message(CMConstants.COULD_NOT_PROCCESS_ERROR)
                            .build());
        }
    }
}
