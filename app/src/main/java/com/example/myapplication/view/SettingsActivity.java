package com.example.myapplication.view;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.print.PrinterId;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.constants.ActivitiesEnum;
import com.example.myapplication.constants.DataConstants;
import com.example.myapplication.constants.SharedPreferencesConstants;
import com.example.myapplication.controller.PasswordDataController;
import com.example.myapplication.data.SharedPreferencesPassword;
import com.example.myapplication.util.AndroidUtils;
import com.example.myapplication.view.base.BaseActivity;

import java.util.Locale;

public class SettingsActivity extends BaseActivity {

    private TextView tv_change_password, tv_share, tv_share_info, tv_change_theme, tv_language;
    private ImageView iv_arrow_down, iv_share_arrow_down, iv_change_theme_arrow_down, iv_language_arrow_down;
    private ConstraintLayout cl_change_password, cl_share, cl_change_theme, cl_language;
    private TextInputEditText ed_current_password, ed_new_password, ed_confirm_password;
    private Button btn_change_password, btn_share, btn_confirm_language;
    private RadioButton rb_pt, rb_en, rb_es;

    SharedPreferencesPassword sharedPreferencesPassword;
    private PasswordDataController crud;
    Cursor cursor;

    String lang = "";
    boolean changePasswordSettingsIsOpen = false;
    boolean sharePasswordsSettingsIsOpen = false;
    boolean changeThemeSettingsIsOpen = false;
    boolean selectLanguageSettingsIsOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_activity);
        ((MyApplication) getApplication()).setCurrentActivity(ActivitiesEnum.SETTINGS_ACTIVITY.getName());

        tv_share = findViewById(R.id.tv_share_settings);
        iv_share_arrow_down = findViewById(R.id.iv_share_arrow_down_settings);
        cl_share = findViewById(R.id.cl_share_settings);
        tv_share_info = findViewById(R.id.tv_share_info_settings);
        btn_share = findViewById(R.id.btn_share_settings);

        tv_change_password = findViewById(R.id.tv_change_password_settings);
        iv_arrow_down = findViewById(R.id.iv_arrow_down_settings);
        cl_change_password = findViewById(R.id.cl_change_password_settings);
        ed_current_password = findViewById(R.id.ed_current_password_settings);
        ed_new_password = findViewById(R.id.ed_new_password_settings);
        ed_confirm_password = findViewById(R.id.ed_confirm_password_settings);
        btn_change_password = findViewById(R.id.btn_change_password_settings);

        tv_change_theme = findViewById(R.id.tv_change_theme_settings);
        iv_change_theme_arrow_down = findViewById(R.id.iv_change_theme_arrow_down_settings);
        cl_change_theme = findViewById(R.id.cl_change_theme_settings);

        tv_language = findViewById(R.id.tv_language_settings);
        iv_language_arrow_down = findViewById(R.id.iv_language_arrow_down_settings);
        cl_language = findViewById(R.id.cl_language_settings);
        rb_pt = findViewById(R.id.rb_pt_settings);
        rb_en = findViewById(R.id.rb_en_settings);
        rb_es = findViewById(R.id.rb_es_settings);
        btn_confirm_language = findViewById(R.id.btn_confirm_language_settings);

        sharedPreferencesPassword = new SharedPreferencesPassword(getApplicationContext());
        crud = new PasswordDataController(getBaseContext());
        cursor = crud.loadFullData();

        initializeRadioButtons();

        if(cursor.getCount() > 0){
            tv_share_info.setText(getResources().getString(R.string.info_about_share_passwords_settings));
            btn_share.setVisibility(View.VISIBLE);
        }else{
            tv_share_info.setText(getResources().getString(R.string.dont_have_password_settings));
            btn_share.setVisibility(View.GONE);
        }

        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sharePasswordsSettingsIsOpen){
                    cl_share.setVisibility(View.GONE);
                    iv_share_arrow_down.setRotation(0);
                }else{
                    cl_share.setVisibility(View.VISIBLE);
                    iv_share_arrow_down.setRotation(180);
                }
                sharePasswordsSettingsIsOpen = !sharePasswordsSettingsIsOpen;
            }
        });

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = createTextToShare();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, text);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        tv_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(changePasswordSettingsIsOpen){
                    cl_change_password.setVisibility(View.GONE);
                    iv_arrow_down.setRotation(0);
                }else{
                    cl_change_password.setVisibility(View.VISIBLE);
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

        tv_change_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(changeThemeSettingsIsOpen){
                    cl_change_theme.setVisibility(View.GONE);
                    iv_change_theme_arrow_down.setRotation(0);
                }else{
                    cl_change_theme.setVisibility(View.VISIBLE);
                    iv_change_theme_arrow_down.setRotation(180);
                }
                changeThemeSettingsIsOpen = !changeThemeSettingsIsOpen;
            }
        });

        tv_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectLanguageSettingsIsOpen){
                    cl_language.setVisibility(View.GONE);
                    iv_language_arrow_down.setRotation(0);
                }else{
                    cl_language.setVisibility(View.VISIBLE);
                    iv_language_arrow_down.setRotation(180);
                }
                selectLanguageSettingsIsOpen = !selectLanguageSettingsIsOpen;
            }
        });

        btn_confirm_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String thisLang = "";

                if(rb_es.isChecked()){
                    sharedPreferencesPassword.setSharedPreferencesPassword(SharedPreferencesConstants.LOCALE_SHARED_PREFERENCE, "es");
                    thisLang = "es";
                }else if(rb_en.isChecked()){
                    sharedPreferencesPassword.setSharedPreferencesPassword(SharedPreferencesConstants.LOCALE_SHARED_PREFERENCE, "en");
                    thisLang = "en";
                }else{
                    sharedPreferencesPassword.setSharedPreferencesPassword(SharedPreferencesConstants.LOCALE_SHARED_PREFERENCE, "pt");
                    thisLang = "pt";
                }

                if(!thisLang.equals(lang)){
                    ((MyApplication) getApplication()).setNeedRecreate(true);
                    setLocale(thisLang);
                }


                lang = thisLang;

                AndroidUtils.showToast(getApplicationContext(), getResources().getString(R.string.language_changed_success));

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        cursor = crud.loadFullData();

        if(cursor.getCount() > 0){
            tv_share_info.setText(getResources().getString(R.string.info_about_share_passwords_settings));
            btn_share.setVisibility(View.VISIBLE);
        }else{
            tv_share_info.setText(getResources().getString(R.string.dont_have_password_settings));
            btn_share.setVisibility(View.GONE);
        }


        if(!((MyApplication) this.getApplication()).isValidated() && !((MyApplication) this.getApplication()).isNavigateOnApp()){
            finish();
            Intent intent = new Intent(SettingsActivity.this, LockScreenActivity.class);
            startActivity(intent);
        }else{
            setLocale(lang);
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

    private String createTextToShare(){

        StringBuilder shareableText = new StringBuilder();

        shareableText.append(getResources().getString(R.string.passwords_backup_settings));

        int count = 0;
        while (count < cursor.getCount()) {


            String title = cursor.getString( cursor.getColumnIndex(DataConstants.TITLE) );
            shareableText.append(getResources().getString(R.string.title_to_share_setting) + title + "\n");

            String username = cursor.getString( cursor.getColumnIndex(DataConstants.USERNAME) );
            shareableText.append(getResources().getString(R.string.username_to_share_setting) + username + "\n");

            String password = cursor.getString( cursor.getColumnIndex(DataConstants.PASSWORD) );
            shareableText.append(getResources().getString(R.string.password_to_share_setting) + password + "\n");

            String description = cursor.getString( cursor.getColumnIndex(DataConstants.DESCRIPTION) );
            shareableText.append(getResources().getString(R.string.description_to_share_setting) + description + "\n\n" );

            cursor.moveToNext();
            count++;
        }


        return  shareableText.toString();
    }

    public void themeChanged(View view){

        sharedPreferencesPassword.setSharedPreferencesPassword(SharedPreferencesConstants.MAIN_THEME_SHARED_PREFERENCE, AndroidUtils.drawableToHex(view.getBackground()));
        recreate();
    }

    public void setLocale(String lang) {
        if(((MyApplication) getApplication()).isNeedRecreate()) {
            sharedPreferencesPassword.setSharedPreferencesPassword(SharedPreferencesConstants.LOCALE_SHARED_PREFERENCE, lang);
            Locale myLocale = new Locale(lang);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            Intent refresh = new Intent(this, SettingsActivity.class);
            startActivity(refresh);
            finish();
            ((MyApplication) getApplication()).setNeedRecreate(false);
        }
    }

    private void initializeRadioButtons(){
        lang = sharedPreferencesPassword.getSharedPreferencesPassword(SharedPreferencesConstants.LOCALE_SHARED_PREFERENCE, "pt");

        switch (lang){
            case "en":
                rb_en.setChecked(true);
                rb_pt.setChecked(false);
                rb_es.setChecked(false);
                break;
            case "es":
                rb_en.setChecked(false);
                rb_pt.setChecked(false);
                rb_es.setChecked(true);
                break;
            default:
                rb_en.setChecked(false);
                rb_pt.setChecked(true);
                rb_es.setChecked(false);
        }



    }

}
