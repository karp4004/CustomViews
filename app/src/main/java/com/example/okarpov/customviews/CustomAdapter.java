package com.example.okarpov.customviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by okarpov on 7/25/2016.
 */
public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> data;
    protected LayoutInflater inflater = null;

    public CustomAdapter(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = new ArrayList<>();
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public String getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View inflateView()
    {
        return inflater.inflate(R.layout.customlist_item, null);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.customlist_item, parent, false);

        return vi;
    }

    public void addItem(String addr)
    {
        data.add(addr);
    }
}
