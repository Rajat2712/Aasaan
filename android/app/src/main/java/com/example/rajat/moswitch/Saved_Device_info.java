package com.example.rajat.moswitch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajat on 4/16/2017.
 */

public class Saved_Device_info extends AppCompatActivity {

    private List<Wifi_list> wifi_listList;
    Wifi_list wifi_list;
    public static Saved_Device_Database savedDeviceDatabase;
    TextView name,loc,ssid,pass,photo;
    ImageView icon;
    Button edit,delete;
    int p,id;
    Intent i;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_device_info);

        name=(TextView)findViewById(R.id.iname);
        loc=(TextView)findViewById(R.id.iloc);
        ssid=(TextView)findViewById(R.id.issid);
        pass=(TextView)findViewById(R.id.ipass);
        photo=(TextView)findViewById(R.id.iphoto);
        icon=(ImageView)findViewById(R.id.icon);
        edit=(Button)findViewById(R.id.edit);
        delete=(Button)findViewById(R.id.delete);

        savedDeviceDatabase=new Saved_Device_Database(getApplicationContext());
        i=getIntent();
        p=i.getIntExtra("position",0);


        wifi_listList = new ArrayList<>();
        wifi_listList=savedDeviceDatabase.getAllDevice();
        wifi_list=wifi_listList.get(p);

        name.setText(wifi_list.getName());
        loc.setText(wifi_list.getArea());
        ssid.setText(wifi_list.getSSID());
        pass.setText(wifi_list.getPass());
        id=wifi_list.getId();
        Glide.with(getApplicationContext()).load(wifi_list.getPhoto()).into(icon);

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(icon.isShown()){
                    icon.setVisibility(View.GONE);
                }else icon.setVisibility(View.VISIBLE);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Saved_device_info_edit.class);
                i.putExtra("position",p);
                startActivity(i);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedDeviceDatabase.deleteDevice(id);
                Intent j=new Intent(getApplicationContext(),Saved_Device_List.class);
                startActivity(j);

            }
        });




    }
}
