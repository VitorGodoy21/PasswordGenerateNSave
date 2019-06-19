package com.example.myapplication.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.MyApplication;
import com.example.myapplication.R;
import com.example.myapplication.constants.ActivitiesEnum;
import com.example.myapplication.constants.DataConstants;
import com.example.myapplication.controller.PasswordDataController;
import com.example.myapplication.model.PasswordModel;
import com.example.myapplication.util.AndroidUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SavePasswordActivity extends AppCompatActivity {

    private TextView tv_title, tv_username, tv_password, tv_description, tv_required;
    private EditText ed_title, ed_username, ed_password, ed_description;
    private Button btn_save, btn_delete;
    private String password = "";
    private String code = "";
    Cursor cursor;
    PasswordDataController crud;

    private PasswordModel passwordModel = new PasswordModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_password_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((MyApplication) getApplication()).setCurrentActivity(ActivitiesEnum.SAVE_PASSWORD_ACTIVITY.getName());
        crud = new PasswordDataController(getBaseContext());
        findViewById();

        getExtras();

        cursor = crud.loadDataById(Integer.parseInt(code));

        ed_title.setText(cursor.getString(cursor.getColumnIndexOrThrow(DataConstants.TITLE)));
        ed_username.setText(cursor.getString(cursor.getColumnIndexOrThrow(DataConstants.USERNAME)));
        ed_password.setText(cursor.getString(cursor.getColumnIndexOrThrow(DataConstants.PASSWORD)));
        ed_description.setText(cursor.getString(cursor.getColumnIndexOrThrow(DataConstants.DESCRIPTION)));

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate()) {
                    setPasswordModel();
                    crud.updatePassword(Integer.parseInt(code), passwordModel);
                    AndroidUtils.showToast(getApplicationContext(), getResources().getString(R.string.save_sucess_save_password));

                    finish();
                } else {
                    AndroidUtils.showToast(getApplicationContext(), getResources().getString(R.string.empty_entries));
                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SavePasswordActivity.this);

                builder.setTitle(getResources().getString(R.string.title_dialog_delete_password_save_password));
                builder.setMessage(getResources().getString(R.string.text_confirmation_dialog_delete_password_save_password));
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        deletePassword();
                    }
                });

                builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
                AlertDialog alert = builder.create();

                alert.show();
            }
        });

        ((MyApplication) getApplication()).setNavigateOnApp(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!((MyApplication) this.getApplication()).isValidated() && !((MyApplication) this.getApplication()).isNavigateOnApp()) {
            Intent intent = new Intent(SavePasswordActivity.this, LockScreenActivity.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        ((MyApplication) getApplication()).setValidated(false);
        ((MyApplication) getApplication()).setNavigateOnApp(false);

    }

    private void findViewById() {
        tv_title = findViewById(R.id.tv_title_save_password);
        tv_username = findViewById(R.id.tv_username_save_password);
        tv_password = findViewById(R.id.tv_password_save_password);
        tv_description = findViewById(R.id.tv_description_save_password);
        tv_required = findViewById(R.id.tv_required_save_password);

        ed_title = findViewById(R.id.ed_title_save_password);
        ed_username = findViewById(R.id.ed_username_save_password);
        ed_password = findViewById(R.id.ed_password_save_password);
        ed_description = findViewById(R.id.ed_description_save_password);

        btn_save = findViewById(R.id.btn_save_save_password);
        btn_delete = findViewById(R.id.btn_delete_save_password);
    }

    private void getExtras() {
        code = this.getIntent().getStringExtra("code");
    }

    private void setPasswordModel() {
        passwordModel.setTitle(ed_title.getText().toString());
        passwordModel.setUsername(ed_username.getText().toString().isEmpty() ? "" : ed_username.getText().toString());
        passwordModel.setPassword(ed_password.getText().toString());
        passwordModel.setDescription(ed_description.getText().toString().isEmpty() ? "" : ed_description.getText().toString());
        passwordModel.setDate(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
    }

    private boolean validate() {

        return ed_password.getText().toString().isEmpty() || ed_title.getText().toString().isEmpty() ? false : true;

    }

    private void deletePassword() {
        crud.deletePassword(Integer.parseInt(code));

        AndroidUtils.showToast(getApplicationContext(), getResources().getString(R.string.delete_sucess_save_password));

        finish();

    }

}
