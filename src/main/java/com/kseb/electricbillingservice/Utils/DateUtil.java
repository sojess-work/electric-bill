package com.kseb.electricbillingservice.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static LocalDate getDateFromString(String date){
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("ddMMyy");
        return LocalDate.parse( date, formatter);
    }
}
