package com.kseb.electricbillingservice.enums;

import lombok.Getter;
@Getter
public enum CustomerType {
    RESIDENTIAL("RES"),
    INDUSTRIAL("IND");

    private final String value;
     CustomerType(String value){
        this.value = value;
    }

}
