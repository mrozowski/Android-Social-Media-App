package com.example.test_store;

import com.example.test_store.list.ItemDetails;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class Helper {
    public static String getProfileDirectory(String userID){
        //return directory of profile picture in the firebase cloud
        return "users/"+userID+"/";
    }

    public static String getDefaultDateFormat(Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.ENGLISH);
        return dateFormat.format(date);
    }

    public static ArrayList<ItemDetails> sortListByDate(ArrayList<ItemDetails> postList) {
        Collections.sort(postList, new Comparator<ItemDetails>() {
            @Override
            public int compare(ItemDetails a, ItemDetails b) {
                if (a.getDate().equals(b.getDate()))
                    return 0;
                else if(a.getDate().before(b.getDate()))
                    return 1;
                else return -1;
            }
        });
        return postList;
    }
}
