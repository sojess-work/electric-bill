package com.kseb.electricbillingservice.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CMResponse<T> {

    private T data;
    private boolean isBadRequest;
    private String message;

}
