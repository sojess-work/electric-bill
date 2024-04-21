package com.kseb.electricbillingservice.validation.validators;

import jakarta.validation.ConstraintValidatorContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Getter
public abstract  class AbstractValidatorSupport <A extends Annotation, T> {

    public static final String REQUIRED_ERROR_MESSAGE= "{name} is required";
    public static final String REQUIRED_LENGTH_ERROR_MESSAGE = "Invalid {name}; required length[{maxSize}].";
    public static final String MAX_LENGTH_ERROR_MESSAGE = "Invalid {name}; maximum length[{maxSize}] exceeded.";
    public static final String MIN_LENGTH_ERROR_MESSAGE = "Invalid {name}; minimum length [{minSize}] validation failed.";
    public static final String INVALID_PATTERN_ERROR_MESSAGE = "Invalid data - {name}";

    private  boolean required;
    private  String name;
    private int minSize;
    private int maxSize;

    public void initialize(A annotation){
        required = getFieldValue(annotation,"required");
        name = getFieldValue(annotation, "name");
        minSize = getFieldValue(annotation,"minSize");
        maxSize = getFieldValue(annotation, "maxSize");
        init(annotation);
    }
    public boolean isValid(T value, ConstraintValidatorContext context){
        boolean isValid = valid(value,context);
        if(isValid){
            return validate(value,context);
        }else {
            return false;
        }
    }
    public boolean valid(T value, ConstraintValidatorContext context){
        if(required){
            if(value == null){
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(REQUIRED_ERROR_MESSAGE).addConstraintViolation();
                return  false;
            }
            if(value instanceof CharSequence){
                CharSequence sq  = (CharSequence) value;
                if(StringUtils.isEmpty(sq)){
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(REQUIRED_ERROR_MESSAGE).addConstraintViolation();
                    return false;
                }
            }
        }
        if(value instanceof  CharSequence && StringUtils.isNotEmpty((CharSequence) value)){
            if(maxSize != -1 && minSize == maxSize){
                if(String.valueOf(value).length() != maxSize){
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(REQUIRED_LENGTH_ERROR_MESSAGE).addConstraintViolation();
                    return  false;
                }
            }

            if(maxSize != -1){
                if(String.valueOf(value).length() > maxSize){
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(MAX_LENGTH_ERROR_MESSAGE).addConstraintViolation();
                    return  false;
                }
            }
            if (minSize != -1){
                if(String.valueOf(value).length() < minSize){
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(MIN_LENGTH_ERROR_MESSAGE).addConstraintViolation();
                    return false;
                }
            }
        }
        return true;
    }
    public abstract boolean validate(T value, ConstraintValidatorContext context);

    public abstract void init(A annotation);

    protected <T> T getFieldValue(A annotation, String annotationFieldName){
        T val = null;
        String annotationName = null;

        try{
            Method annotationMethod = annotation.annotationType().getDeclaredMethod(annotationFieldName);
            annotationName = annotation.getClass().getName();
            Object annotationVal = annotationMethod.invoke(annotation);
            val = (T) annotationVal;
        }catch (Exception e){
            log.error("Unable to get value for {}()={}, message={}",
                    annotationFieldName,annotationName,e.getMessage());
        }
        return  val;
    }


    public boolean validatePattern(CharSequence value, String regEx) throws IllegalArgumentException{

        if(value !=null && regEx !=null){
            Pattern pattern =  Pattern.compile(regEx);
            Matcher matcher =  pattern.matcher(value);
            boolean matches = matcher.matches();
            return  matches;
        }else{
            throw new IllegalArgumentException("Cannot validate [" + value + "] against pattern["+regEx+"]");

        }
    }

    public void  addConstraintValidationErrorMessage(String message, ConstraintValidatorContext context){
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }

}
