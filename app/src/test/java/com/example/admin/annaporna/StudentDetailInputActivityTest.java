package com.example.admin.annaporna;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.admin.annaporna.service.DatabaseService;
import com.example.admin.annaporna.student.StudentDetailInputActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

/**
 * Created by Admin on 16-12-2016.
 */

@Config(constants = BuildConfig.class, sdk = 23)
@RunWith(RobolectricTestRunner.class)
public class StudentDetailInputActivityTest {

    @Test
    public void testValidStudentInputFabClick() {
        StudentDetailInputActivity activity = Robolectric.setupActivity(StudentDetailInputActivity.class);
        FloatingActionButton fab = (FloatingActionButton)activity.findViewById(R.id.fab_student_added);
        TextInputEditText name = (TextInputEditText) activity.findViewById(R.id.student_name_input);
        TextInputEditText dob = (TextInputEditText) activity.findViewById(R.id.date_dob_input);
        TextInputEditText fatherGuardianName = (TextInputEditText) activity.findViewById(R.id.father_guardian_input);
        TextInputEditText motherName = (TextInputEditText) activity.findViewById(R.id.mother_input);
        TextInputEditText address = (TextInputEditText) activity.findViewById(R.id.student_address_input);
        RadioButton boy = (RadioButton) activity.findViewById(R.id.radio_btn_boys);

        name.setText("Sathya");
        dob.setText("23/11/1926");
        fatherGuardianName.setText("Sai");
        motherName.setText("Eswaramma");
        address.setText("Prashanti Nilayam");
        boy.performClick();

        fab.performClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent expectedIntent = new Intent(activity, DatabaseService.class);

        Intent actualIntent = shadowActivity.peekNextStartedService();
        Assert.assertTrue(expectedIntent.filterEquals(actualIntent));
    }

}
