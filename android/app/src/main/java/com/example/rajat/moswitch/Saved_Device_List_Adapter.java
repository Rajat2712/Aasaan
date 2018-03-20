package com.example.rajat.moswitch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Rajat on 4/14/2017.
 */

public class Saved_Device_List_Adapter extends RecyclerView.Adapter<Saved_Device_List_Adapter.MyViewHolder>  {

    private Context mContext;
    private List<Wifi_list> wifi_list;
    private Saved_Device_List_AdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            cardView=(CardView) view.findViewById(R.id.card_view);

        }
    }

    public Saved_Device_List_Adapter(Context mContext, List<Wifi_list> wifi_list, Saved_Device_List_AdapterListener listener) {
        this.mContext = mContext;
        this.wifi_list = wifi_list;
        this.listener=listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saved_device_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Wifi_list wl = wifi_list.get(position);
        holder.title.setText(wl.getName());
        holder.count.setText(wl.getArea());

        // loading album cover using Glide library
       /* if(pgContact.getPhoto()!=null)
            holder.photo.setImageBitmap(BitmapFactory.decodeByteArray(pgContact.getPhoto(),0, pgContact.getPhoto().length));
       */

        if(wl.getPhoto()!=null) {

            Glide.with(mContext).load(wl.getPhoto()).into(holder.thumbnail);


        }

        if ((wl.getState()==1)){
            holder.cardView.setBackgroundColor(Color.GREEN);
        } else {
            holder.cardView.setBackgroundColor(Color.WHITE);

        }



       /* holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow, position);

            }
        });*/

        applyClickEvents(holder,position);

    }



    private void applyClickEvents(final MyViewHolder holder, final int position) {
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPopupClicked(holder.overflow,position);
            }
        });

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onIconClicked(position);
            }
        });

    }






    /**
     * Showing popup menu when tapping on 3 dots
     */




   /* private void showPopupMenu(View view, int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_saved_list, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.show();
    }*/

    /**
     * Click listener for popup menu items
     */


   /* class MyMenuItemClickListener  implements PopupMenu.OnMenuItemClickListener {

        int p;
        public MyMenuItemClickListener() {
        }

        public MyMenuItemClickListener(int pos) {
            this.p=pos;
        }


        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:


                    Toast.makeText(mContext, "Add to favourite" + p  , Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getApplicationContext(),Saved_Device_info.class);
                    i.putExtra("position",p);
                    startActivity(i);
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next" + p, Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }*/

    @Override
    public int getItemCount() {
        return wifi_list.size();
    }




    public interface Saved_Device_List_AdapterListener {
        void onIconClicked(int position);

        void onPopupClicked(ImageView view,int position);


    }

}
