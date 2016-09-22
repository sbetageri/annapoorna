package com.example.admin.annaporna;

import java.util.Calendar;

/**
 * Created by Admin on 20-09-2016.
 */
public class Utils {
    public static int getAge(String[] date) {
        // date format
        // YYYY/MM/DD
        int curYear = Calendar.getInstance().get(Calendar.YEAR);
        int curMonth = Calendar.getInstance().get(Calendar.MONTH);
        int curDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int day = Integer.parseInt(date[2]);
        int month = Integer.parseInt(date[1]);
        int age = curYear - Integer.parseInt(date[0]);
        if(month > curMonth) {
            age -= 1;
        } else if(month == curMonth) {
            if(curDay < day) {
                age -= 1;
            }
        }
        return age;
    }
}
