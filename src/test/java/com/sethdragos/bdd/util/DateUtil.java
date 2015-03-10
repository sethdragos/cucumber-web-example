package com.sethdragos.bdd.util;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtil {

    private static DateTime myDate;

    public static String formattedDate(String dateFormat) {
        return formattedDate(0, dateFormat);
    }

    public static String formattedDate(int number, String dateFormat) {
        myDate = new DateTime().plusDays(number);
        DateTimeFormatter mdyFormat = DateTimeFormat.forPattern(dateFormat);
        return mdyFormat.print(myDate);
    }
}
