package com.tvt11.timemanagingapp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    static DateFormat df2 = new SimpleDateFormat("dd-MMM-yyyy");

    public static Date toDate(String value) {
        if (value != null) {
            try {
                return df.parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return null;
        }
    }

    public static String fromDate(Date value) {
        return value == null ? null : df.format(value);
    }

    public static String fromDate(String value) {
        Date date = toDate(value);
        return value == null ? null : df2.format(value);
    }
}
