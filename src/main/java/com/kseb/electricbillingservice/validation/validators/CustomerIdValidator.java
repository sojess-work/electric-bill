package com.kseb.electricbillingservice.validation.validators;

import com.kseb.electricbillingservice.validation.constraints.CustomerId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class CustomerIdValidator extends  AbstractValidatorSupport<CustomerId,CharSequence>
implements ConstraintValidator<CustomerId,CharSequence> {
    private static final String PATTERN_CUSTOMER_ID ="^[a-zA-Z0-9]*$";
    @Override
    public boolean validate(CharSequence value, ConstraintValidatorContext context) {
        if(StringUtils.isEmpty(value)){
            return true;
        }

        boolean validPattern = validatePattern(value,PATTERN_CUSTOMER_ID);
        if(!validPattern){
            context.disableDefaultConstraintViolation();
            addConstraintValidationErrorMessage(INVALID_PATTERN_ERROR_MESSAGE,context);
            return  false;
        }else{
            return  true;
        }
    }

    @Override
    public void init(CustomerId annotation) {

    }
}
