package com.example.rajat.moswitch;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends RuntimePermissionsActivity {

    Button button;

    private static final int REQUEST_PERMISSIONS = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);

      /*  int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_CALENDAR);*/

        //Toast.makeText(getApplicationContext(),permissionCheck,Toast.LENGTH_SHORT).show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MainActivity.super.requestAppPermissions(new
                                String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, R.string
                                .runtime_permissions_txt
                        , REQUEST_PERMISSIONS);


            }
        });

    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        Intent i = new Intent(getApplicationContext(), Device_Search_List.class);
        startActivity(i);
    }


}
