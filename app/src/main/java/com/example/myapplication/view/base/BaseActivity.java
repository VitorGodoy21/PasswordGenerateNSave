package com.example.myapplication.view.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.constants.SharedPreferencesConstants;
import com.example.myapplication.data.SharedPreferencesPassword;

public class BaseActivity extends AppCompatActivity {

    SharedPreferencesPassword sharedPreferencesPassword;
    String currentTheme;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferencesPassword = new SharedPreferencesPassword(getApplicationContext());
        currentTheme = sharedPreferencesPassword.getSharedPreferencesPassword(SharedPreferencesConstants.MAIN_THEME_SHARED_PREFERENCE, "#6C534E");
        setMainTheme(currentTheme);
    }

    @Override
    protected void onResume() {
        String theme = sharedPreferencesPassword.getSharedPreferencesPassword(SharedPreferencesConstants.MAIN_THEME_SHARED_PREFERENCE, "#6C534E");
        if(!currentTheme.equals(theme))
            recreate();

        super.onResume();
    }

    private void setMainTheme(String currentTheme)
    {
        switch (currentTheme.toUpperCase()) {
            case "#FF6363":
                setTheme(R.style.AppThemeLightRed);
                break;
            case "#CECECE":
                setTheme(R.style.AppThemeGray);
                break;
            case "#677DB7":
                setTheme(R.style.AppThemeBlue);
                break;
            case "#783C6F":
                setTheme(R.style.AppThemePurple);
                break;
            case "#80D39B":
                setTheme(R.style.AppThemeDarkGreen);
                break;
            case "#2E2E2E":
                setTheme(R.style.AppThemeBlack);
                break;
            default:
                setTheme(R.style.AppTheme);

        }

    }


}
