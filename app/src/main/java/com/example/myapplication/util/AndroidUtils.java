package com.example.myapplication.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

public class AndroidUtils {

    public static void showToast(Context context, String text){
        Toast toast = Toast.makeText(context, text,Toast.LENGTH_SHORT);
        toast.show();
    }

}
