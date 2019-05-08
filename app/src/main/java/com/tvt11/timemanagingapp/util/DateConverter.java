package com.tvt11.timemanagingapp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

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

    public static String dateValueToString(int year, int month, int dayOfMonth) {
        return String.format("%02d - %02d - %4d", dayOfMonth, month, year);
    }

    public static int[] StringToDateValue(String dateStr) {
        String[] dateStrArray = dateStr.split(" - ");
        int[] result = new int[3];

        int i = 0;
        for (String str: dateStrArray) {
            result[i] = Integer.parseInt(str);
            i++;
        }
        return result;
    }
}
