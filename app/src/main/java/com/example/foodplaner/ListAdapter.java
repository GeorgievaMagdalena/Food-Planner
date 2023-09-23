package com.example.foodplaner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter {
    private Context context;
    private List<String> data; // Replace with your actual data type

    public ListAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_survey, parent, false);
        }

        // Bind data to the UI elements within the list item
        TextView textView = convertView.findViewById(R.id.textView);
        String item = data.get(position);
        textView.setText(item);

        // Add more bindings for other UI elements as needed

        return convertView;
    }
}
