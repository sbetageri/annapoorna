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
}
