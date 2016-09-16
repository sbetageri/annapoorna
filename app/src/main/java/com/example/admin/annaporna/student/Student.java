package com.example.admin.annaporna.student;

/**
 * Created by Admin on 16-09-2016.
 */
public class Student {
    public String mId;
    public String mName;
    public String mGender;
    public String mDob;

    public String mFatherGuardianName;
    public String mMotherName;

    public String mAddress;

    public void setAddress(String address) {
        mAddress = address;
    }

    public void setDob(String dob) {
        mDob = dob;
    }

    public void setFatherGuardianName(String fatherGuardianName) {
        mFatherGuardianName = fatherGuardianName;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setMotherName(String motherName) {
        mMotherName = motherName;
    }

    public void setName(String name) {
        mName = name;
    }
}
