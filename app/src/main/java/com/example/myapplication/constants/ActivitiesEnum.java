package com.example.myapplication.constants;

import com.example.myapplication.view.LockScreenActivity;
import com.example.myapplication.view.MainActivity;
import com.example.myapplication.view.SavePasswordActivity;
import com.example.myapplication.view.SettingsActivity;

public enum ActivitiesEnum {
    LOCK_SCREEN_ACTIVITY(0, "LockScreenActivity", LockScreenActivity.class),
    SAVE_PASSWORD_ACTIVITY(1, "SavePasswordActivity", SavePasswordActivity.class),
    MAIN_ACTIVITY(2, "MainActivity", MainActivity.class),
    SETTINGS_ACTIVITY(3,"SettingsActivity", SettingsActivity.class);

    private int value;
    private String name;
    private Class activity;

    ActivitiesEnum(int value, String name, Class activity) {
        this.value = value;
        this.name = name;
        this.activity = activity;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public Class getActivity() {
        return this.activity;
    }

    public static Class getActivity(String name) {
        for (ActivitiesEnum activity : values()) {
            if (activity.getName().equals(name))
                return activity.getActivity();
        }

        return LOCK_SCREEN_ACTIVITY.getActivity();
    }
}
