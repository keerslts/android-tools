package com.buaa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.buaa.activity.R;
import com.buaa.util.Globals;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/13.
 */
public class MyAdapter extends BaseAdapter {
    private Context ctx;
    private List<Map<String, Object>> values;

    public MyAdapter(Context ctx, List<Map<String, Object>> values) {
        this.ctx = ctx;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.file_list_line,null);
            //设置ListView中每一行的宽度和高度
            convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Globals.SCREEN_HEIGHT/8));
        }

        TextView fileName = (TextView)convertView.findViewById(R.id.file_name);
        TextView fileImg = (TextView)convertView.findViewById(R.id.file_img);
        Map<String, Object> map = values.get(position);
        fileName.setText(map.get("fileName").toString());
        fileImg.setBackgroundResource(Globals.imgs.get(map.get("extName")));
        fileImg.getLayoutParams().height = Globals.SCREEN_HEIGHT/8;
        return convertView;
    }
}
