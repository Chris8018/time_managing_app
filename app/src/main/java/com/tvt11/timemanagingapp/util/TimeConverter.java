package com.tvt11.timemanagingapp.util;

import java.util.ArrayList;
import java.util.List;

public class TimeConverter {


    public static String toTimeStamp(long time) {

        int time_in_second = (int) time / 1000;
        int h = time_in_second / (60 * 60);
        int m = time_in_second / 60 % 60;
        int s = time_in_second % 60;

//        String hours, minutes, seconds;
//
//        hours = Integer.toString(h).length() == 1 ? "0" + h : "" + h;
//        minutes = Integer.toString(m).length() == 1 ? "0" + m : "" + m;
//        seconds = Integer.toString(s).length() == 1 ? "0" + s : "" + s;
//
//        return hours + ":" + minutes + ":" + seconds;
        String timeStr = String.format("%02d:%02d:%02d", h, m, s);
        return timeStr;
    }

    public static long fromTimeStamp(String timeStamp) {

        List<Integer> time = new ArrayList<>();
        for (String s : timeStamp.split(":")) {
            time.add(Integer.parseInt(s));
        }

        int h = time.get(0) * 60 * 60;
        int m = time.get(1) * 60;
        int s = time.get(2);
        return (h + m + s) * 1000;
    }
}
