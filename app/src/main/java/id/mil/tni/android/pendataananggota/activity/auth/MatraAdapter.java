package id.mil.tni.android.pendataananggota.activity.auth;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.mil.tni.android.pendataananggota.R;
import id.mil.tni.android.pendataananggota.data.Matra;

/**
 * Created by Aprilian Nur on 11/8/2017.
 */

public class MatraAdapter extends ArrayAdapter<Matra>{

    private Context context;
    private ArrayList<Matra> values;

    public MatraAdapter(Context context, int textViewResourceId,
                       ArrayList<Matra> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount(){
        return values.size();
    }

    @Override
    public Matra getItem(int position){
        return values.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        label.setPadding(0, 5, 0, 5);
        label.setText(values.get(position).getNama());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        if(position == 0){
            label.setPadding(16, 10, 0, 5);
        } else if(position == values.size()-1){
            label.setPadding(16, 5, 0, 10);
        } else {
            label.setPadding(16, 5, 0, 5);
        }
        label.setText(values.get(position).getNama());
        return label;
    }
}