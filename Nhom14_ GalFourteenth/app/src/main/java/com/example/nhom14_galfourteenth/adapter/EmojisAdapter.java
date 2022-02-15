package com.example.nhom14_galfourteenth.adapter;

import android.content.Context;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EmojisAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    ArrayList<String> unicode_list;
    public EmojisAdapter(Context context, ArrayList<String> emoji_list) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        unicode_list = emoji_list;
    }
    public int getCount() {
        return unicode_list.size();
    }
    public Object getItem(int position) {
        return null;
    }
    public long getItemId(int position) {
        return 0;
    }
    // create a new ImageView for each item referenced by the
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv = new TextView(mContext);
        tv.setText(unicode_list.get(position));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else {
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
        }
        tv.setAlpha(1.f);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,35);
        return tv;
    }
}
