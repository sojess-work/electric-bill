package com.kseb.electricbillingservice.validation.validators;


import com.kseb.electricbillingservice.validation.constraints.Alphabet;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class AlphabetValidator extends AbstractValidatorSupport<Alphabet,CharSequence>
implements ConstraintValidator<Alphabet,CharSequence> {

    private static final String PATTERN_ALPHABET ="^[a-zA-Z]+$";
    @Override
    public boolean validate(CharSequence value, ConstraintValidatorContext context) {
        if(StringUtils.isEmpty(value)){
            return true;
        }

        boolean validPattern = validatePattern(value,PATTERN_ALPHABET);
        if(!validPattern){
            context.disableDefaultConstraintViolation();
            addConstraintValidationErrorMessage(INVALID_PATTERN_ERROR_MESSAGE,context);
            return  false;
        }else{
            return  true;
        }
    }

    @Override
    public void init(Alphabet annotation) {

    }
}
