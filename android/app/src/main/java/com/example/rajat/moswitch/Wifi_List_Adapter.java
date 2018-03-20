package com.example.rajat.moswitch;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * Created by Rajat on 3/6/2017.
 */

public class Wifi_List_Adapter extends RecyclerView.Adapter<Wifi_List_Adapter.MyViewHolder> {

    private List<Wifi_list> wifi_lists;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView SSID;

        public MyViewHolder(View view) {
            super(view);
            SSID = (TextView) view.findViewById(R.id.title);
            //genre = (TextView) view.findViewById(R.id.genre);
            //year = (TextView) view.findViewById(R.id.year);
        }
    }


    public Wifi_List_Adapter(List<Wifi_list> wifi_lists) {
        this.wifi_lists = wifi_lists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wifi_list_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Wifi_list wifiList = wifi_lists.get(position);
        holder.SSID.setText(wifiList.getSSID());
        //holder.genre.setText(movie.getGenre());
        //holder.year.setText(movie.getYear());
    }

    @Override
    public int getItemCount() {
        return wifi_lists.size();
    }
}
