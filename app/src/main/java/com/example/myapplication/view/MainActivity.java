package com.example.myapplication.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
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
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    EditText ed_password, ed_size;
    ImageView iv_copy;
    TextView tv_password, tv_size, tv_letters, tv_numbers, tv_special;
    AppCompatCheckBox cb_letters, cb_numbers, cb_special;
    Button btn_generate, btn_save;

    private TextView tv_title, tv_username, tv_description;
    private EditText ed_title, ed_username, ed_description;

    ListView lv_passwords;

    private PasswordModel passwordModel = new PasswordModel();
    private PasswordDataController crud;
    private Cursor cursor;

    private FloatingActionButton fab_plus, fab_settings, fab_more;

    int flagBtnSave = 0;

    boolean moreIsOpen = false;
    boolean plusIsClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Context context = getApplicationContext();
        ((MyApplication) getApplication()).setCurrentActivity(ActivitiesEnum.MAIN_ACTIVITY.getName());

        crud = new PasswordDataController(getBaseContext());

        ed_password = findViewById(R.id.ed_password);
        ed_size = findViewById(R.id.ed_size);

        iv_copy = findViewById(R.id.iv_copy);

        tv_password = findViewById(R.id.tv_password);
        tv_size = findViewById(R.id.tv_size);
        tv_letters = findViewById(R.id.tv_letters);
        tv_numbers = findViewById(R.id.tv_numbers);
        tv_special = findViewById(R.id.tv_special);

        cb_letters = findViewById(R.id.cb_letters);
        cb_numbers = findViewById(R.id.cb_numbers);
        cb_special = findViewById(R.id.cb_special);

        btn_generate = findViewById(R.id.btn_generate);
        btn_save = findViewById(R.id.btn_save);

        tv_title = findViewById(R.id.tv_title);
        tv_username = findViewById(R.id.tv_username);
        tv_password = findViewById(R.id.tv_password);
        tv_description = findViewById(R.id.tv_description);

        ed_title = findViewById(R.id.ed_title);
        ed_username = findViewById(R.id.ed_username);
        ed_password = findViewById(R.id.ed_password);
        ed_description = findViewById(R.id.ed_description);

        fab_plus = findViewById(R.id.fab);
        fab_settings = findViewById(R.id.fab_settings);
        fab_more = findViewById(R.id.fab_more);

        loadListView();

        setVisibilityFullForm(false);

        btn_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(flagBtnSave == 0){ // INITIAL STATE
                    setVisibilityFullForm(true);
                    fab_plus.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_close));
                    lv_passwords.setVisibility(View.GONE);
                    setState(1);

                }else if(flagBtnSave == 1){ // TO SAVE
                    fab_plus.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_add));
                    savePassword();
                }else if(flagBtnSave == 2){ // PRE SAVE
                    setVisibilityFullForm(true);
                    fab_plus.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_close));
                    lv_passwords.setVisibility(View.GONE);
                    setState(1);
                }
            }
        });

        btn_generate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String error = validateGenerateRules();

                if(error.isEmpty()){
                    ed_password.setText(getRandomString());
                }
                else{
                    AndroidUtils.showToast(getApplicationContext(), error);
                }

            }
        });

        tv_letters.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cb_letters.setChecked(!cb_letters.isChecked());
            }
        });

        tv_numbers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cb_numbers.setChecked(!cb_numbers.isChecked());
            }
        });

        tv_special.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cb_special.setChecked(!cb_special.isChecked());
            }
        });

        iv_copy.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                String password = ed_password.getText().toString();

                if(!password.isEmpty()){
                    copyPassword(password);
                    AndroidUtils.showToast( getApplicationContext(), getResources().getString(R.string.password_copied));
                }
                else
                    AndroidUtils.showToast(getApplicationContext(), getResources().getString(R.string.no_password));
            }
        });

        ed_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(ed_password.getText().toString().isEmpty()){
                    iv_copy.setVisibility(View.GONE);
                    btn_save.setVisibility(View.GONE);
                    return;
                }

                iv_copy.setVisibility(View.VISIBLE);
                btn_save.setVisibility(View.VISIBLE);
                setState(2);
                fab_plus.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_close));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                plusIsClicked = true;

                fab_plus.hide();
                fab_settings.hide();

                setVisibilityFullForm(true);
                lv_passwords.setVisibility(View.GONE);
                btn_save.setVisibility(View.VISIBLE);
                setState(1);
                fab_more.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_close));

            }
        });

        fab_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);

                AndroidUtils.showToast(getApplicationContext(), "settings temporarily disabled");

            }
        });

        fab_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!moreIsOpen){
                    fab_settings.show();
                    fab_plus.show();
                    fab_more.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_close));
                    moreIsOpen = true;


                }else{
                    fab_settings.hide();
                    fab_plus.hide();
                    fab_more.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_more));
                    moreIsOpen = false;
                }

                if(plusIsClicked){

                    fab_settings.hide();
                    fab_plus.hide();

                    if(flagBtnSave == 1){  // SAVE STATE to PRE SAVE
                        setVisibilityFullForm(false);
                        fab_settings.hide();
                        lv_passwords.setVisibility(View.VISIBLE);
                        fab_more.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_close));
                        setState(2);
                    }else if(flagBtnSave == 2) { // PRE STATE to INITIAL STATE
                        setVisibilityFullForm(false);
                        lv_passwords.setVisibility(View.VISIBLE);
                        fab_more.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_more));
                        clearEditTexts();
                        setState(0);
                        fab_settings.hide();
                        fab_plus.hide();
                        moreIsOpen = false;
                        plusIsClicked = false;

                    }else{ // INITIAL STATE to SAVE STATE
                        setVisibilityFullForm(true);
                        fab_settings.hide();
                        lv_passwords.setVisibility(View.GONE);
                        fab_more.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_close));
                        btn_save.setVisibility(View.VISIBLE);
                        setState(1);

                    }
                }

            }
        });


        lv_passwords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String codigo;
                cursor.moveToPosition(position);
                codigo = cursor.getString(cursor.getColumnIndexOrThrow(DataConstants.ID));
                ((MyApplication)getApplication()).setCode(codigo);
                Intent intent = new Intent(MainActivity.this, SavePasswordActivity.class);
                intent.putExtra("code", codigo);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {

        super.onResume();
        ((MyApplication) getApplication()).setCurrentActivity(ActivitiesEnum.MAIN_ACTIVITY.getName());

        if(!((MyApplication) this.getApplication()).isValidated() && !((MyApplication) this.getApplication()).isNavigateOnApp()){
            Intent intent = new Intent(MainActivity.this, LockScreenActivity.class);
            startActivity(intent);
            ((MyApplication) getApplication()).setNavigateOnApp(false);
            return;
        }

        loadListView();
        ed_password.setText("");

    }

    @Override
    protected void onStop() {
        super.onStop();
        ((MyApplication) getApplication()).setValidated(false);
    }

    public String validateGenerateRules(){

        String error = "";

        if(!cb_numbers.isChecked() && !cb_letters.isChecked() && !cb_special.isChecked() )
            error += getResources().getString(R.string.set_the_character_types);

        if(ed_size.getText().length() == 0 || Integer.parseInt(ed_size.getText().toString()) > 30 || Integer.parseInt(ed_size.getText().toString()) <= 0){
            if(error.isEmpty())
                error += getResources().getString(R.string.set_password_size);
            else
                error += getResources().getString(R.string.and_set_password_size);
        }

        return error;
    }

    public String getAllowedCharacters(){
        String str = "";

        if(cb_special.isChecked())
            str += getResources().getString(R.string.characters_special);

        if(cb_letters.isChecked())
            str += getResources().getString(R.string.characters_letters);

        if(cb_numbers.isChecked())
            str += getResources().getString(R.string.characters_numbers);

        return str;
    }

    private String getRandomString() {
        final Random random=new Random();

        int lenght = Integer.parseInt(ed_size.getText().toString());

        final StringBuilder sb=new StringBuilder(lenght);
        for(int i=0;i<lenght;++i)
            sb.append(getAllowedCharacters().charAt(random.nextInt(getAllowedCharacters().length())));

        return sb.toString();
    }

    private void copyPassword(String password){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(getResources().getString(R.string.password_label), password);
        clipboard.setPrimaryClip(clip);
    }

    private void loadListView(){

        String[] columns = new String[] {DataConstants.ID, DataConstants.TITLE, DataConstants.PASSWORD};
        int[] idViews = new int[] {R.id.tv_id_lv, R.id.tv_title_lv, R.id.tv_password_lv};
        cursor = crud.loadData();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(),
                R.layout.list_view_passwords,cursor,columns,idViews, 0);
        lv_passwords = findViewById(R.id.lv_passwords);
        lv_passwords.setAdapter(adapter);

    }

    private void setState(int flag){
        flagBtnSave = flag;
    }

    private int getState(){
        return flagBtnSave;
    }

    private void setVisibilityFullForm(boolean visibility){

        if(visibility){
            tv_title.setVisibility(View.VISIBLE);
            ed_title.setVisibility(View.VISIBLE);
            tv_username.setVisibility(View.VISIBLE);
            ed_username.setVisibility(View.VISIBLE);
            tv_description.setVisibility(View.VISIBLE);
            ed_description.setVisibility(View.VISIBLE);
        }
        else{
            tv_title.setVisibility(View.GONE);
            ed_title.setVisibility(View.GONE);
            tv_username.setVisibility(View.GONE);
            ed_username.setVisibility(View.GONE);
            tv_description.setVisibility(View.GONE);
            ed_description.setVisibility(View.GONE);

        }
    }

    private void savePassword(){

        if(validate()){

            setVisibilityFullForm(false);

            setState(0);

            setPasswordModel();

            PasswordDataController crud = new PasswordDataController(getBaseContext());

            long result = crud.insertPassword(passwordModel);

            if(result != -1){
                AndroidUtils.showToast(getApplicationContext(),getResources().getString(R.string.save_sucess_save_password));
                clearEditTexts();

            }
            else{
                AndroidUtils.showToast(getApplicationContext(),getResources().getString(R.string.save_not_sucess_save_password));
            }

            ed_password.setText("");

            lv_passwords.setVisibility(View.VISIBLE);

            loadListView();
        }
        else{
            AndroidUtils.showToast( getApplicationContext(), getResources().getString(R.string.empty_entries));
        }
    }

    private void setPasswordModel(){
        passwordModel.setTitle(ed_title.getText().toString());
        passwordModel.setUsername(ed_username.getText().toString().isEmpty() ? "" : ed_username.getText().toString());
        passwordModel.setPassword(ed_password.getText().toString());
        passwordModel.setDescription(ed_description.getText().toString().isEmpty() ? "" : ed_description.getText().toString());
        passwordModel.setDate(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
    }

    private boolean validate(){

        return ed_password.getText().toString().isEmpty() || ed_title.getText().toString().isEmpty() ? false : true;

    }

    private void clearEditTexts(){
        ed_title.setText("");
        ed_username.setText("");
        ed_password.setText("");
        ed_description.setText("");
        ed_size.setText("");

        cb_letters.setChecked(true);
        cb_special.setChecked(true);
        cb_numbers.setChecked(true);
    }

    private void configureFabMenu(){



    }

}
