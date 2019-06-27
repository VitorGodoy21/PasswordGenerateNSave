package com.example.myapplication.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.example.myapplication.view.SettingsActivity;

import java.util.Locale;

public class AndroidUtils {

    public static void showToast(Context context, String text){
        Toast toast = Toast.makeText(context, text,Toast.LENGTH_SHORT);
        toast.show();
    }

    public static String drawableToHex(Drawable drawable){
        int color = Color.TRANSPARENT;

        if (drawable instanceof ColorDrawable)
            color = ((ColorDrawable) drawable).getColor();

        return color != Color.TRANSPARENT ? String.format("#%06X", (0xFFFFFF & color)) : "";
    }



}
