package com.kseb.electricbillingservice.constants;

public interface CMConstants {

    String COULD_NOT_PROCCESS_ERROR = "Could not process request. Please try again later.";
    String UNITS_NOT_UPDATED = "Units not updated for this month. Please try again after 28th of this month.";
    String INVALID_PERIOD = "Invalid period provided. Please provide a valid period";
    String OVERLAPPING_DATE_RANGE ="The providing date range/unit range is overlapping with existing records, either edit the existing records" +
            " or add a new record without overlapping date ranges.";
    String INVALID_TOTAL_UNITS = "Total units provided is less than previously recorded units. Please verify and update.";
}
