package com.example.kr2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NameRankAdapter extends BaseAdapter {
    private final ArrayList<Player> list;
    private final Context context;

    public NameRankAdapter(Context context, ArrayList<Player> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return (list.get(i)).scope;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, null);
        }
        TextView name = view.findViewById(R.id.name);
        name.setText(list.get(i).name);
        TextView rank = view.findViewById(R.id.rank);
        rank.setText(String.valueOf(list.get(i).scope));
        return view;
    }
}
