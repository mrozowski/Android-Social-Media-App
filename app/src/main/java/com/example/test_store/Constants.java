package com.example.test_store;

import com.example.test_store.list.ItemDetails;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class Constants {
    public final static String PROFILE_IMG_NAME = "profile.jpg";
    public final static String USER_DATA_FILE = "appUser.ser";
    public final static String USER_PROFILE_PICTURE_FILE = "profile.png";
    public final static String TAG = "MyTAG";
    public final static String EMAIL_REGEX = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public final static String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    public final static String PHONE_REGEX = "^[+0-9]{8,12}$";
    public final static String NICK_REGEX = "^[a-zA-Z]\\w*$";
    public final static int IMG_INTEND_REQUEST_CODE = 1002;

}
