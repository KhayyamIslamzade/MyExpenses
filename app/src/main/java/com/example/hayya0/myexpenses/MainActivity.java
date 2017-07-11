package com.example.hayya0.myexpenses;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends TabActivity {

    SQLiteDatabase db = null;
    TabHost TabHostWindow;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//Screen always in portrait position

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //get fullscreen

        TabHostWindow = (TabHost) findViewById(android.R.id.tabhost);


        db = openOrCreateDatabase("MyExpenses", MODE_PRIVATE, null);
        String createDT = "Create table if not exists Expenses( expenses_id integer primary key autoincrement , cash integer , time text , date text , note text ,control integer); ";
        db.execSQL(createDT);



        TabHost.TabSpec TabMenu1 = TabHostWindow.newTabSpec("First tab");
        TabHost.TabSpec TabMenu2 = TabHostWindow.newTabSpec("Second Tab");
        TabHost.TabSpec TabMenu3 = TabHostWindow.newTabSpec("Third Tab");


        TabMenu1.setIndicator("",getResources().getDrawable(R.drawable.tabmenu_home_focus_selector));
        TabMenu1.setContent(new Intent(this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        TabMenu2.setIndicator("",getResources().getDrawable(R.drawable.tabmenu_shop_focus_selector));
        TabMenu2.setContent(new Intent(this, Add_DataActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        TabMenu3.setIndicator("",getResources().getDrawable(R.drawable.tabmenu_user_focus_selector));
        TabMenu3.setContent(new Intent(this, ArchiveActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));



        TabHostWindow.addTab(TabMenu1);
        TabHostWindow.addTab(TabMenu2);
        TabHostWindow.addTab(TabMenu3);


    }
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Back pressed",Toast.LENGTH_LONG).show();
        //showDialog(ALERT_DIALOG_ID);
    }


}

