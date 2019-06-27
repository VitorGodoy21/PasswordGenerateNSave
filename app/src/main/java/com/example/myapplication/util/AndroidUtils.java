package com.example.myapplication.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

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
