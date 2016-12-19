package com.example.admin.annaporna;

import android.util.Log;

import com.example.admin.annaporna.school.SchoolDetailsInputActivity_ViewBinding;

import java.util.Calendar;

/**
 * Created by Admin on 20-09-2016.
 */
public class Utils {

    public static final String _TAG = "Annaporna_utils";

    public static int getAge(String[] date) {
        // date format
        // YYYY/MM/DD
        int curYear = Calendar.getInstance().get(Calendar.YEAR);
        int curMonth = Calendar.getInstance().get(Calendar.MONTH);
        int curDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int day = Integer.parseInt(date[2]);
        int month = Integer.parseInt(date[1]);
        int age = curYear - Integer.parseInt(date[0]);
        if (month > curMonth) {
            age -= 1;
        } else if (month == curMonth) {
            if (curDay < day) {
                age -= 1;
            }
        }
        return age;
    }

    public static boolean isLeapYear(int year) {
        // If divisible by 4, 100 and 400. Then it is a leap year
        // If divisible by only 4, then leap year.
        if (year % 4 != 0) {
            return false;
        } else {
            if (year % 100 == 0) {
                if (year % 400 == 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
    }

    public static boolean isValidDate(String input) {
        Log.e(_TAG, "date : " + input);
        // date is supposed to be dd/mm/yyyy
        if(input == null || input.length() == 0) {
            return false;
        }
        final int MONTH_MIN_DATE = 1;

        final int LEAP_YEAR_MAX_DATE = 29;

        int[] MAX_DAYS_IN_MONTHS = {
                31, // Jan
                28, // Feb, varies
                31, // March
                30, // April
                31, // May
                30, // June
                31, // July
                31, // Aug
                30, // Sept
                31, // Oct
                30, // Nov
                31  // Dec
        };

        final int MIN_MONTH = 1;
        final int MAX_MONTH = 12;
        String[] date = input.split("/");
        int day = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[2]);
        Log.e(_TAG, "day : " + day);
        Log.e(_TAG, "month : " + month);
        Log.e(_TAG, "year : " + year);
        if(date[2].length() != 4) {
            Log.e(_TAG, "length of year insufficient");
            return false;
        }
        if(month >= MIN_MONTH && month <= MAX_MONTH) {
            if(isLeapYear(year)) {
                MAX_DAYS_IN_MONTHS[1] = LEAP_YEAR_MAX_DATE;
            }

            if(day >= MONTH_MIN_DATE && day <= MAX_DAYS_IN_MONTHS[month - 1]) {
                return true;
            } else {
                Log.e(_TAG, "invalid day");
                return false;
            }
        } else {
            Log.e(_TAG, "invalid month");
            return false;
        }
    }

    public static String convertDateToYYYYMMDDFormat(String date) {
        // input is of the form DD/MM/YYYY
        String[] dob = date.split("/");
        StringBuilder sb = new StringBuilder();
        sb.append(dob[2]);
        sb.append("/");
        sb.append(dob[1]);
        sb.append("/");
        sb.append(dob[0]);
        return sb.toString();
    }
}
