package com.example.admin.annaporna;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;


/**
 * Created by Admin on 16-12-2016.
 */

public class UtilsTest {
    @Test
    public void correctAge() {
        String[] date = "2000/01/01".split("/");
        int age = Utils.getAge(date);
        int yearAfter2000 = Calendar.getInstance().get(Calendar.YEAR) % 100;
        Assert.assertEquals(Integer.toString(age), Integer.toString(yearAfter2000));
    }

    @Test
    public void checkValidDate() {
        String[] invalidDates = {
                "0/11/2016",
                "32/11/2016",
                "29/2/2013",
                "29/2/1900",
                "10/0/2016",
                "10/13/2016",
                "10/10/200",
                "10/10/20000",
                "0/0/2016",
                "32/0/2016",
                "0/10/200",
                "0/10/2000",
                "0/0/200",
                "32/13/2000"
        };
        for(String date : invalidDates) {
            Assert.assertFalse(date + " Invalid date is true", Utils.isValidDate(date));
        }

        String[] validDates = {
                "23/11/1926",
                "29/2/2016"
        };
        for(String date : validDates) {
            Assert.assertTrue(date + " Valid date is false", Utils.isValidDate(date));
        }
    }

    @Test
    public void checkDateConversion() {
        String date = "23/11/1926";
        String expDate = "1926/11/23";
        Assert.assertEquals(expDate, Utils.convertDateToYYYYMMDDFormat(date));
    }


}
