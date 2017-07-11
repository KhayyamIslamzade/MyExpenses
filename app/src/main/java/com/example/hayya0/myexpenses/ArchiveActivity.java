package com.example.hayya0.myexpenses;

import android.app.TabActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ArchiveActivity extends AppCompatActivity {
    private ActionMode actionmode;
    SQLiteDatabase db = null;
    ListView listview;
    private custom_listview_productlistadapter adapter;
    private List<custom_listview_product> mProductList;
    final List<Integer> positionsList = new ArrayList<>();//declare this as a member variable--outside of any method


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);

        listview = (ListView) findViewById(R.id.arsivler);
        mProductList = new ArrayList<>();
        SelectData();
        adapter = new custom_listview_productlistadapter(getApplicationContext(), mProductList);
        listview.setAdapter(adapter);



        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        listview.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(android.view.ActionMode mode, int position, long id, boolean checked) {
                mode.setTitle(listview.getCheckedItemCount()+ " Selected");
                if(checked)
                    positionsList.add(position);
                else
                    positionsList.remove(position);
            }

            @Override
            public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
                // Inflate the menu for the CAB
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        deleteScreenedMessageFromInbox();
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(android.view.ActionMode mode) {

            }
        });


    }

    public void deleteScreenedMessageFromInbox() {

        db = openOrCreateDatabase("MyExpenses", MODE_PRIVATE, null);
        for(int position : positionsList) {
            Integer id = positionsList.get(position);

            String deleteScreenedMessage = "delete from " + "Expenses" + " where " + "id" + " = " + id;
            db.execSQL(deleteScreenedMessage);
        }
        db.close();
    }


    /*public void btn_delete_OnClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_delete:
                db = openOrCreateDatabase("MyExpenses", MODE_PRIVATE, null);
                String deleteDT = "Delete from Expenses";
                db.execSQL(deleteDT);
                Toast.makeText(this, "Veriler Silindi", Toast.LENGTH_LONG).show();

               break;
        }
    }*/


   /* public void addDataOnClick(View v)
    {
        Intent intent = new Intent(getApplication(),Add_DataActivity.class);
        startActivity(intent);
    }*/

    @Override
    public void onBackPressed() {
     /*   Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);*/
        //finish();
        TabActivity tabs = (TabActivity) getParent();
        tabs.getTabHost().setCurrentTab(0);
    }


    public void SelectData() {
        db = openOrCreateDatabase("MyExpenses", MODE_PRIVATE, null);
        String selectDT = "Select * from Expenses order by date desc";
        Cursor cursor = db.rawQuery(selectDT, null);
        cursor.moveToFirst();
        String dbString = "";

        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex("cash")) != null) {
                dbString += cursor.getString(cursor.getColumnIndex("cash")) + "\n";

                int id = cursor.getInt(cursor.getColumnIndex("expenses_id"));
                int cash = cursor.getInt(cursor.getColumnIndex("cash"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String comment = cursor.getString(cursor.getColumnIndex("note"));


                mProductList.add(new custom_listview_product(id, cash, comment, time, date));
            }
            cursor.moveToNext();

        }
        db.close();
    }


    private String spaces_on_string(String text) {
        if (text.length() > 4) {
            text = text.substring(0, 4).toString() + "...  ";
            return text;
        } else {
            String bosluk = "";
            for (int i = 0; i < 11 - text.length(); i++) {
                bosluk += " ";
            }
            text = text + bosluk;
            return text;
        }
    }


    private ActionMode.Callback modeCallBack = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.context_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.edit:
                    Toast.makeText(getApplicationContext(), "Edit", Toast.LENGTH_SHORT).show();

                    mode.finish();
                    return true;


                case R.id.delete:
                    /*Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_SHORT).show();*/

                    Toast.makeText(getApplicationContext(), "Item id :" + item.getItemId(), Toast.LENGTH_LONG).show();
                    mode.finish();
                    return true;

                default:
                    return false;
            }

        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionmode = null;
        }
    };

    private void actionmode()
    {

    }
}
