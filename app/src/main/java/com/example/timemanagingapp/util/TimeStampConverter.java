package com.example.timemanagingapp.util;

import java.util.ArrayList;
import java.util.List;

public class TimeStampConverter {

    public String toTimeStamp(long time) {

        int time_in_second = (int) time / 1000;
        int h = time_in_second / (60 * 60);
        int m = time_in_second / 60 % 60;
        int s = time_in_second % 60;

        String hours, minutes, seconds;

        hours = Integer.toString(h).length() == 1 ? "0" + h : "" + h;
        minutes = Integer.toString(m).length() == 1 ? "0" + m : "" + m;
        seconds = Integer.toString(s).length() == 1 ? "0" + s : "" + s;

        return hours + ":" + minutes + ":" + seconds;
    }

    public long fromTimeStamp(String timeStamp) {

        List<Integer> time = new ArrayList<>();
        for (String s : timeStamp.split(":")) {
            time.add(Integer.parseInt(s));
        }

        int h = time.get(0) * 60 * 60;
        int m = time.get(1) * 60;
        return (h + m + time.get(2)) * 1000;
    }
}
