package com.example.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.myapplication.constants.GlobalConstants;
import com.example.myapplication.constants.SharedPreferencesConstants;
import com.example.myapplication.data.SharedPreferencesPassword;
import com.example.myapplication.util.AndroidUtils;

public class LockScreenActivity extends AppCompatActivity {

    private TextView tv_main_password, tv_intro;
    private EditText ed_main_password;
    private Button btn_one, btn_two, btn_three, btn_four, btn_five, btn_six, btn_seven, btn_eight, btn_nine, btn_cancel, btn_ok, btn_zero;
    private ImageView iv_back;
    private String firstPassword, confirmPassword;
    private SharedPreferencesPassword sharedPreferencesPassword;
    private String mainPassword_sharedPreferences;

    private boolean isPassword = false, isConfirmPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_screen_activity);
        //((MyApplication) getApplication()).setCurrentActivity(GlobalConstants.LOCK_SCREEN_ACTIVITY);

        tv_intro = findViewById(R.id.tv_intro);
        tv_main_password = findViewById(R.id.tv_main_password);
        ed_main_password = findViewById(R.id.ed_main_password);
        btn_one = findViewById(R.id.btn_one);
        btn_two = findViewById(R.id.btn_two);
        btn_three = findViewById(R.id.btn_three);
        btn_four = findViewById(R.id.btn_four);
        btn_five = findViewById(R.id.btn_five);
        btn_six = findViewById(R.id.btn_six);
        btn_seven = findViewById(R.id.btn_seven);
        btn_eight = findViewById(R.id.btn_eight);
        btn_nine = findViewById(R.id.btn_nine);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_zero = findViewById(R.id.btn_zero);
        btn_ok = findViewById(R.id.btn_ok);
        iv_back = findViewById(R.id.iv_back);

        sharedPreferencesPassword = new SharedPreferencesPassword(getApplicationContext());
        //sharedPreferencesPassword.removeSharedPreferencesPassword(SharedPreferencesConstants.MAIN_PASSWORD_SHARED_PREFERENCE);
        mainPassword_sharedPreferences = sharedPreferencesPassword.getSharedPreferencesPassword(SharedPreferencesConstants.MAIN_PASSWORD_SHARED_PREFERENCE);

        if(mainPassword_sharedPreferences.isEmpty()){
            tv_intro.setText(getResources().getString(R.string.intro_register_password_lock_screen));
            isPassword = true;
            ed_main_password.setHint(getResources().getString(R.string.type_your_password));
        }else{
            ed_main_password.setHint("");
            tv_intro.setText(getResources().getString(R.string.title_lock_screen));
        }

        ed_main_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv_main_password.setVisibility(!ed_main_password.getText().toString().isEmpty() && ( isConfirmPassword || isPassword ) ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClick("1");
            }
        });

        btn_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClick("2");
            }
        });

        btn_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClick("3");
            }
        });

        btn_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClick("4");
            }
        });

        btn_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClick("5");
            }
        });

        btn_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClick("6");
            }
        });

        btn_seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClick("7");
            }
        });

        btn_eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClick("8");
            }
        });

        btn_nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClick("9");

            }

        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPassword){
                    isPassword();
                }else if (isConfirmPassword) {
                    isConfirmPassword();
                }else{

                    iv_back.setVisibility(View.INVISIBLE);
                    if(ed_main_password.getText().toString().equals(mainPassword_sharedPreferences)){
                        AndroidUtils.showToast(getApplicationContext(), getResources().getString(R.string.access_allowed));
                        ((MyApplication) getApplication()).setValidated(true);

                        Intent intent;

                        if(((MyApplication)getApplication()).getCurrentActivity() == GlobalConstants.SAVE_PASSWORD_ACTIVITY){
                            intent = new Intent(LockScreenActivity.this, SavePasswordActivity.class);
                        }else if (((MyApplication)getApplication()).getCurrentActivity() == GlobalConstants.SETTINGS_ACTIVITY){
                            intent = new Intent(LockScreenActivity.this, SettingsActivity.class);
                        }
                        else {
                            intent = new Intent(LockScreenActivity.this, MainActivity.class);
                        }
                        finish();
                        startActivity(intent);

                    }else{
                        AndroidUtils.showToast(getApplicationContext(), getResources().getString(R.string.access_denied) );
                    }

                }
            }
        });

        btn_zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClick("0");
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ed_main_password.getText().toString().length() > 0){
                    String password = ed_main_password.getText().toString();
                    password = password.substring(0, password.length() - 1);
                    ed_main_password.setText(password);
                }
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_main_password.setHint(getResources().getString(R.string.type_your_password));
                tv_main_password.setText(getResources().getString(R.string.type_your_password));
                isPassword = true;
                ed_main_password.setText("");
                iv_back.setVisibility(View.INVISIBLE);
            }
        });

    }

    public void btnClick(String value){
        String password = ed_main_password.getText().toString();
        if(password.length() < 12 ){
            password += value;
            ed_main_password.setText(password);
        }
    }

    public void isPassword(){


        if(!ed_main_password.getText().toString().isEmpty()) {
            firstPassword = ed_main_password.getText().toString();
            ed_main_password.setHint(getResources().getString(R.string.confirm_password));
            tv_main_password.setText(getResources().getString(R.string.confirm_password));
            ed_main_password.setText("");
            isPassword = false;
            isConfirmPassword = true;
            iv_back.setVisibility(View.VISIBLE);
        }else{
            AndroidUtils.showToast(getApplicationContext(), getResources().getString(R.string.type_your_password));
        }
    }

    public void isConfirmPassword(){


        if (!ed_main_password.getText().toString().isEmpty()) {
            confirmPassword = ed_main_password.getText().toString();
            if(firstPassword.equals(confirmPassword)){
                AndroidUtils.showToast(getApplicationContext(),getResources().getString(R.string.save_sucess_save_password));
                sharedPreferencesPassword.setSharedPreferencesPassword(SharedPreferencesConstants.MAIN_PASSWORD_SHARED_PREFERENCE, ed_main_password.getText().toString());
                mainPassword_sharedPreferences = ed_main_password.getText().toString();
                ed_main_password.setText("");
                tv_main_password.setText("");
                ed_main_password.setHint("");
                isConfirmPassword = false;
                isPassword = false;
                tv_intro.setText(getResources().getString(R.string.title_lock_screen));
                iv_back.setVisibility(View.INVISIBLE);

            }
            else{
                AndroidUtils.showToast(getApplicationContext(),getResources().getString(R.string.password_not_match));
                ed_main_password.setText("");
            }
        } else {
            AndroidUtils.showToast(getApplicationContext(), getResources().getString(R.string.type_your_password));
        }
    }



}
