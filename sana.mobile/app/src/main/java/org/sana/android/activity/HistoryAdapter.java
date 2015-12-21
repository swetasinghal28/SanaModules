package org.sana.android.activity;

/**
 * Created by arpitjaiswal on 11/21/15.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import org.sana.R;

import java.util.ArrayList;


public class HistoryAdapter extends BaseAdapter {

    Context context;
    ArrayList<City> listData;

    public HistoryAdapter(Context context,ArrayList<City> listData){
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class ViewHolder {
        private TextView textViewDiscription;
        private TextView textViewEncounterID;


    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.historylayout,null);
            viewHolder = new ViewHolder();
            viewHolder.textViewDiscription = (TextView) view.findViewById(R.id.txtViewDiscription);
            view.setTag(viewHolder);
            viewHolder.textViewEncounterID = (TextView) view.findViewById(R.id.txtViewencounetrID);
            view.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        City city = listData.get(position);
        String discription = city.getName();
        String  encounterID= city.getState();
        viewHolder.textViewDiscription.setText(discription);
        viewHolder.textViewEncounterID.setText(encounterID);

        return view;
    }
}
