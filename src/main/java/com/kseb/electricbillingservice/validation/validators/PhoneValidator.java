package com.kseb.electricbillingservice.validation.validators;

import com.kseb.electricbillingservice.validation.constraints.Phone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;


public class PhoneValidator extends AbstractValidatorSupport<Phone,CharSequence>
        implements ConstraintValidator<Phone,CharSequence> {

    private static final String PATTERN_PHONE = "^\\+\\d{1,3}[0-9\\s-]{6,}$";
    @Override
    public boolean validate(CharSequence value, ConstraintValidatorContext context) {
        if(StringUtils.isEmpty(value)){
            return true;
        }
        boolean validPattern = validatePattern(value,PATTERN_PHONE);
        if(!validPattern){
            context.disableDefaultConstraintViolation();;
            addConstraintValidationErrorMessage(INVALID_PATTERN_ERROR_MESSAGE,context);
            return  false;
        }else{
            return true;
        }
    }

    @Override
    public void init(Phone annotation) {

    }

}
