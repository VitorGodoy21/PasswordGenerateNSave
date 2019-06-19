package com.example.myapplication.view;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.constants.ActivitiesEnum;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        ((MyApplication) getApplication()).setCurrentActivity(ActivitiesEnum.SETTINGS_ACTIVITY.getName());

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!((MyApplication) this.getApplication()).isValidated() && !((MyApplication) this.getApplication()).isNavigateOnApp()){
            finish();
            Intent intent = new Intent(SettingsActivity.this, LockScreenActivity.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        ((MyApplication) getApplication()).setValidated(false);
        ((MyApplication) getApplication()).setNavigateOnApp(false);

    }

}
