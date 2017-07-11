package com.example.hayya0.myexpenses;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hayya0 on 10/11/2016.
 */
public class custom_listview_productlistadapter extends BaseAdapter{

    private Context mContext;
    private List<custom_listview_product> mProductlist;

    public custom_listview_productlistadapter(Context mContext, List<custom_listview_product> mProductlist) {
        this.mContext = mContext;
        this.mProductlist = mProductlist;
    }


    @Override
    public int getCount() {
        return mProductlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=View.inflate(mContext,R.layout.custom_listview_item,null);
        TextView tv_cash= (TextView) v.findViewById(R.id.tv_cash);
        TextView tv_comment= (TextView) v.findViewById(R.id.tv_comment);
        TextView tv_time= (TextView) v.findViewById(R.id.tv_time);
        TextView tv_date= (TextView) v.findViewById(R.id.tv_date);

        tv_cash.setText(String.valueOf(mProductlist.get(position).getCash()+"$"));
        tv_comment.setText(mProductlist.get(position).getComment());

        tv_time.setText(mProductlist.get(position).getTime());
        tv_date.setText(mProductlist.get(position).getDate());

        v.setTag(mProductlist.get(position).getId());

        return v;
    }
}
