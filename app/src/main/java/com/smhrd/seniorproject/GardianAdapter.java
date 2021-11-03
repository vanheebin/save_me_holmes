package com.smhrd.seniorproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GardianAdapter extends BaseAdapter {
    private ArrayList<GardianDTO> list = new ArrayList<GardianDTO>();

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gardianlist, parent, false);
        }

        TextView tv_member_name = convertView.findViewById(R.id.tv_member_name);


        GardianDTO dto = list.get(position);

        tv_member_name.setText(dto.getName());


        return convertView;

    }

    public void addItem(String 이름) {
    }
}
