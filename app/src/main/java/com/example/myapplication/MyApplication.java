package com.example.myapplication;

import android.app.Application;

public class MyApplication extends Application {

    private boolean validated;
    private boolean navigateOnApp;
    private String currentActivity;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public boolean isNavigateOnApp() {
        return navigateOnApp;
    }

    public void setNavigateOnApp(boolean navigateOnApp) {
        this.navigateOnApp = navigateOnApp;
    }

    public String getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(String currentActivity) {
        this.currentActivity = currentActivity;
    }
}
