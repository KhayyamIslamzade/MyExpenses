package com.example.hayya0.myexpenses;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Add_DataActivity extends AppCompatActivity {

    SQLiteDatabase db = null;
    ImageButton btn_clear, btn_add_data;
    Button btn_taken, btn_list;
    ImageView btn_taken_or_list;
    EditText et_time, et_date, et_cash, et_currency, et_notes;
    Dialog time_dialog, date_dialog;
    LinearLayout ll_cash, ll_currency, ll_datetime, ll_notes;
    int control_value;
    DatePicker datepicker;

    int year_x,month_x,day_x,hour_x,minute_x;

    String currency[];

    private static final int ET_TIME_ALERT_DIALOG_ID = 0;
    private static final int ET_DATE_ALERT_DIALOG_ID = 1;
    private static final int ET_CURRENCY_ALERT_DIALOG_ID = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__data);

        control_value = 1;


        currency = new String[]{"Dollar", "AZN", "YTL", "Frank", "RBL"};


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        btn_taken = (Button) findViewById(R.id.btn_taken);
        btn_list = (Button) findViewById(R.id.btn_list);
        btn_taken_or_list = (ImageView) findViewById(R.id.btn_taken_or_list);
        btn_clear = (ImageButton) findViewById(R.id.btn_clear);
        btn_add_data = (ImageButton) findViewById(R.id.btn_add_db_data);

        et_time = (EditText) findViewById(R.id.et_time);
        et_date = (EditText) findViewById(R.id.et_date);
        et_cash = (EditText) findViewById(R.id.et_cash);
        et_currency = (EditText) findViewById(R.id.et_currency);
        et_notes = (EditText) findViewById(R.id.et_notes);

        ll_cash = (LinearLayout) findViewById(R.id.LL_cash);
        ll_currency = (LinearLayout) findViewById(R.id.LL_currency);
        ll_datetime = (LinearLayout) findViewById(R.id.ll_datetime);
        ll_notes = (LinearLayout) findViewById(R.id.ll_notes);

        datepicker= (DatePicker) findViewById(R.id.datePicker);

        control();
        getTimeDate();
        set_datetime_start_value();


        et_time.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showDialog(ET_TIME_ALERT_DIALOG_ID);
                return true;
            }
        });

        et_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showDialog(ET_DATE_ALERT_DIALOG_ID);
                return true;
            }
        });
        et_currency.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showDialog(ET_CURRENCY_ALERT_DIALOG_ID);
                return true;
            }
        });

    }


    private void getTimeDate() {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat TimeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat DateFormat = new SimpleDateFormat("dd.MM.yyyy");

        String time = TimeFormat.format(calendar.getTime());
        String date = DateFormat.format(calendar.getTime());

        et_time.setText(time.toString());
        et_date.setText(date.toString());

    }
    private void set_datetime_start_value()
    {
        final Calendar cal=Calendar.getInstance();
        year_x=cal.get(Calendar.YEAR);
        month_x=cal.get(Calendar.MONTH);
        day_x=cal.get(Calendar.DAY_OF_MONTH);
        hour_x=cal.get(Calendar.HOUR);
        minute_x=cal.get(Calendar.MINUTE);
    }


    public void db_data_add_OnClick(View v) {


        if (control_value == 1)//buy
        {

            if (TextUtils.isEmpty(et_cash.getText())) {
                et_cash.requestFocus();
                openKeyboard();
                ll_cash.setBackgroundResource(R.drawable.theme_red_background);
                Toast.makeText(getApplicationContext(), "Cash can't be empty on Buy option", Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(et_notes.getText())) {
                et_notes.requestFocus();
                openKeyboard();
                ll_notes.setBackgroundResource(R.drawable.theme_red_background);
                Toast.makeText(getApplicationContext(), "Notes can't be empty on Buy option", Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(et_time.getText()) && TextUtils.isEmpty(et_date.getText())) {
                getTimeDate();
            } else {

                successfuly_db_added();
            }

        }
        if (control_value == 0)//shopping list
        {
            if (TextUtils.isEmpty(et_time.getText())) {
                et_time.requestFocus();
                ll_datetime.setBackgroundResource(R.drawable.theme_red_background);
                Toast.makeText(getApplicationContext(), "Time can't be empty on Shopping List option", Toast.LENGTH_LONG).show();

            } else if (TextUtils.isEmpty(et_date.getText())) {
                et_date.requestFocus();
                ll_datetime.setBackgroundResource(R.drawable.theme_red_background);
                Toast.makeText(getApplicationContext(), "Date can't be empty on Shopping List option", Toast.LENGTH_LONG).show();

            } else if (TextUtils.isEmpty(et_notes.getText())) {
                et_notes.requestFocus();
                openKeyboard();
                ll_notes.setBackgroundResource(R.drawable.theme_red_background);
                Toast.makeText(getApplicationContext(), "Notes can't be empty on Shopping List option", Toast.LENGTH_LONG).show();

            } else {
                et_cash.setText("0");
                successfuly_db_added();
            }


        }


    }

    private void successfuly_db_added() {
        try {


            db = openOrCreateDatabase("MyExpenses", MODE_PRIVATE, null);

            String insertData = "insert into Expenses(cash , time ,date , note , control) values('";
            insertData += Integer.parseInt(et_cash.getText().toString()) + "' , '" + et_time.getText().toString() + "' , '" + et_date.getText().toString() + "' , '" + et_notes.getText().toString().replaceAll("\\s"," ")
                    + "' , '" + control_value + "')";
            Toast.makeText(this, "Data added", Toast.LENGTH_LONG).show();
            db.execSQL(insertData);
            clearData();
            db.close();
            control();
        } catch (Exception ex) {

        };

    }

    private void control() {
        if (control_value == 1) {
            btn_taken.setBackgroundResource(R.drawable.button_green_selector);
            btn_list.setBackgroundResource(R.drawable.button_blue_selector);
            ll_cash.setBackgroundResource(R.drawable.theme_black_background);
            ll_currency.setBackgroundResource(R.drawable.theme_black_background);
            ll_notes.setBackgroundResource(R.drawable.theme_black_background);
            btn_taken_or_list.setBackgroundResource(R.drawable.taken);

            et_cash.setFocusableInTouchMode(true);
            et_cash.setEnabled(true);
            et_currency.setFocusableInTouchMode(true);
            et_currency.setEnabled(true);


        } else {
            btn_taken.setBackgroundResource(R.drawable.button_blue_selector);
            btn_list.setBackgroundResource(R.drawable.button_green_selector);
            ll_cash.setBackgroundResource(R.drawable.theme_yellow_background);
            ll_currency.setBackgroundResource(R.drawable.theme_yellow_background);
            ll_notes.setBackgroundResource(R.drawable.theme_black_background);
            btn_taken_or_list.setBackgroundResource(R.drawable.shoppinglist);

            et_cash.setText(null);
            et_currency.setText(null);
            et_cash.setFocusable(false);
            et_cash.setEnabled(false);
            et_currency.setFocusable(false);
            et_currency.setEnabled(false);

        }
    }


    private void clearData() {
       /* finish();
        TabActivity tabs = (TabActivity) getParent();
        tabs.getTabHost().setCurrentTab(1);*/

        et_cash.setText("");
        et_currency.setText("");
        getTimeDate();
        et_notes.setText("");
    }

    private void openKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    @Override
    public void onBackPressed() {
     /*   Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);*/
        //finish();
        TabActivity tabs = (TabActivity) getParent();
        tabs.getTabHost().setCurrentTab(0);
    }




    public void adddata_buttons_OnClick(View v) {
        switch (v.getId()) {


            case R.id.btn_add_data_timeadd:
                time_dialog.dismiss();
                break;
            case R.id.btn_add_data_dateadd:
                date_dialog.dismiss();
                break;

            case R.id.btn_taken:
                control_value = 1;
                control();
                break;

            case R.id.btn_list:
                control_value = 0;
                control();
                break;


            case R.id.et_currency:
                showDialog(ET_CURRENCY_ALERT_DIALOG_ID);
                break;

            case R.id.et_time:
                showDialog(ET_TIME_ALERT_DIALOG_ID);
                break;

            case R.id.et_date:
                showDialog(ET_DATE_ALERT_DIALOG_ID);
                break;


            case R.id.btn_clear:
                clearData();
                break;

        }
    }





    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {


            case ET_DATE_ALERT_DIALOG_ID:
               /* time_dialog = new Dialog(this);
                time_dialog.setTitle("Set Time");
                time_dialog.setContentView(R.layout.acivity_add_data_time_alert);
                return time_dialog;*/
                return new DatePickerDialog(this,dpickerListener,year_x,month_x,day_x);

            case ET_TIME_ALERT_DIALOG_ID:
                /*date_dialog = new Dialog(this);
                date_dialog.setTitle("Set Date");
                date_dialog.setContentView(R.layout.acivity_add_data_date_alert);
                return date_dialog;*/

                return new TimePickerDialog(this,tpickerListener,hour_x,minute_x,true);

            case ET_CURRENCY_ALERT_DIALOG_ID:
                AlertDialog.Builder currency_list_builder = new AlertDialog.Builder(this);
                currency_list_builder.setTitle("Currency List");
                currency_list_builder.setItems(currency, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        et_currency.setText("  " + currency[which].toString());
                    }
                });
                return currency_list_builder.create();


            default:
                return super.onCreateDialog(id);
        }

    }


    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x=year;
            month_x=monthOfYear+1;
            day_x=dayOfMonth;
            et_date.setText(day_x+"."+month_x+"."+year);
        }
    };
    TimePickerDialog.OnTimeSetListener tpickerListener=new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour_x=hourOfDay;
            minute_x=minute;
            et_time.setText(hour_x+":"+minute_x);
        }
    };




}
