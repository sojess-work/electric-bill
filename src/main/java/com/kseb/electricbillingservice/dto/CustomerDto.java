package com.kseb.electricbillingservice.dto;

import com.kseb.electricbillingservice.enums.CustomerType;
import com.kseb.electricbillingservice.validation.constraints.Alphabet;
import com.kseb.electricbillingservice.validation.constraints.CustomerId;
import com.kseb.electricbillingservice.validation.constraints.Phone;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class CustomerDto {
    @Phone(name = "phone",required = true,minSize = 10,maxSize = 13)
    private String phone;
    @Email
    private String email;
    @Alphabet(name = "firstName",required = true,minSize = 4,maxSize = 10)
    private String firstName;
    @Alphabet(name = "lastName",required = true,minSize = 4,maxSize = 10)
    private String lastName;
    @CustomerId(name = "customerId",required = true,minSize = 16,maxSize = 16)
    private String customerId;
    @Alphabet(required = true,name = "customerType",minSize = 3,maxSize = 5)
    private String customerType;
}
