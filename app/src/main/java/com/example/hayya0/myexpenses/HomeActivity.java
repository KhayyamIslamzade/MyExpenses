package com.example.hayya0.myexpenses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {
    SQLiteDatabase db = null;

    TabHost TabHostWindow;
    private static final int ALERT_DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TabHostWindow = (TabHost) findViewById(android.R.id.tabhost);

          /*InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);  SHOW KEYBOARD */

     /*   harcama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (Integer.getInteger(harcama.getText().toString()) != null) {
                    btn_onay.setVisibility(View.INVISIBLE);
                } else {
                    btn_onay.setVisibility(View.VISIBLE);
                }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

    }


/*
    public void addData(int hrcm_deger) {
        db = openOrCreateDatabase("MyExpenses", MODE_PRIVATE, null);
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat TimerFormat = new SimpleDateFormat("HH:mm ");
        SimpleDateFormat DateFormat = new SimpleDateFormat("dd.MM.yy ");

        String time = DateFormat.format(calendar.getTime());
        String date = DateFormat.format(calendar.getTime());

        *//*String insertData = "insert into HARCAMALAR(harcama , harcama_tarih) values('";
        insertData += hrcm_deger + "' , '" + hrcm_tarih + "')";
        db.execSQL(insertData);*//*

    }*/

   /* public void ekleOnClick(View v) {

            int hrcm_deger = Integer.parseInt(harcama.getText().toString());
            addData(hrcm_deger);
            Toast.makeText(this, "Veri Eklendi", Toast.LENGTH_LONG).show();
            harcama.setText("");
            btn_onay.setVisibility(View.INVISIBLE);

    }*/

    @Override
    public void onBackPressed() {
        showDialog(ALERT_DIALOG_ID);
    }
    public  void do_shopOnClick(View v)
    {
        TabActivity tabs = (TabActivity) getParent();
        tabs.getTabHost().setCurrentTab(1);

      /*  Intent intent =new Intent(getApplicationContext(),Add_DataActivity.class);
        startActivity(intent);
        this.closeContextMenu();*/

    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case ALERT_DIALOG_ID:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Warning!").setMessage("Do you want to exit from app?")
                        .setIcon(android.R.drawable.ic_dialog_alert);


                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);

                    }
                });


                AlertDialog dialog = builder.create();
                return dialog;

            default:
                return super.onCreateDialog(id);
        }
    }


    /*public void verileriSilOnClick(View v) {
        db = openOrCreateDatabase("MyExpenses", MODE_PRIVATE, null);
        String deleteDT = "Delete from HARCAMALAR";
        db.execSQL(deleteDT);
        Toast.makeText(this, "Veriler Silindi", Toast.LENGTH_LONG).show();
    }*/

}
