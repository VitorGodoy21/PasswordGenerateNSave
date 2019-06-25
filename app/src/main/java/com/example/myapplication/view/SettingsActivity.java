package com.example.myapplication.view;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.print.PrinterId;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.constants.ActivitiesEnum;
import com.example.myapplication.constants.SharedPreferencesConstants;
import com.example.myapplication.data.SharedPreferencesPassword;
import com.example.myapplication.util.AndroidUtils;

public class SettingsActivity extends AppCompatActivity {

    private TextView tv_change_password;
    private ImageView iv_arrow_down;
    private ConstraintLayout cl_change_password;
    private TextInputEditText ed_current_password, ed_new_password, ed_confirm_password;
    private Button btn_change_password;

    SharedPreferencesPassword sharedPreferencesPassword;

    boolean changePasswordSettingsIsOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        ((MyApplication) getApplication()).setCurrentActivity(ActivitiesEnum.SETTINGS_ACTIVITY.getName());

        tv_change_password = findViewById(R.id.tv_change_password_settings);
        iv_arrow_down = findViewById(R.id.iv_arrow_down_settings);
        cl_change_password = findViewById(R.id.cl_change_password_settings);
        ed_current_password = findViewById(R.id.ed_current_password_settings);
        ed_new_password = findViewById(R.id.ed_new_password_settings);
        ed_confirm_password = findViewById(R.id.ed_confirm_password_settings);
        btn_change_password = findViewById(R.id.btn_change_password_settings);

        sharedPreferencesPassword = new SharedPreferencesPassword(getApplicationContext());

        tv_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(changePasswordSettingsIsOpen){
                    cl_change_password.setVisibility(View.GONE);
                    tv_change_password.setBackgroundResource(R.drawable.custom_buttom);
                    iv_arrow_down.setRotation(0);
                }else{
                    cl_change_password.setVisibility(View.VISIBLE);
                    tv_change_password.setBackgroundResource(R.drawable.custom_tv_settings_open);
                    iv_arrow_down.setRotation(180);
                }
                changePasswordSettingsIsOpen = !changePasswordSettingsIsOpen;
            }
        });

        btn_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String validate = validate();

                if(validate.isEmpty()){
                    sharedPreferencesPassword.setSharedPreferencesPassword(SharedPreferencesConstants.MAIN_PASSWORD_SHARED_PREFERENCE, ed_new_password.getText().toString());
                    AndroidUtils.showToast(getApplicationContext(), getResources().getString(R.string.password_changed_success_settings));
                    finish();
                    Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    AndroidUtils.showToast(getApplicationContext(), validate);
                }
            }
        });

        ((MyApplication) getApplication()).setNavigateOnApp(true);

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

    private String validate(){

        if(ed_current_password.getText().toString().isEmpty())
            return getResources().getString(R.string.type_current_password_settings);

        if(ed_new_password.getText().toString().isEmpty())
            return getResources().getString(R.string.type_new_password_settings);

        if(ed_confirm_password.getText().toString().isEmpty())
            return getResources().getString(R.string.confirm_new_password_settings);

        if(!ed_current_password.getText().toString().equals(sharedPreferencesPassword.getSharedPreferencesPassword(SharedPreferencesConstants.MAIN_PASSWORD_SHARED_PREFERENCE)))
            return getResources().getString(R.string.wrong_current_password_settings);

        if(!ed_new_password.getText().toString().equals(ed_confirm_password.getText().toString()))
            return getResources().getString(R.string.not_match_password_settings);

        return  "";
    }

}
