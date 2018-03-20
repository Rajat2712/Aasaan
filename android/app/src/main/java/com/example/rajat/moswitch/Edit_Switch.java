package com.example.rajat.moswitch;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

/**
 * Created by Rajat on 4/14/2017.
 */

public class Edit_Switch extends AppCompatActivity {

    EditText name,area;
    TextView photo;
    Button connect;
    String sname,sarea,ssid,bssid,pass;
    int level,state;
    boolean result1, result2, result3=false;
    private static int RESULT_LOAD_IMAGE = 1;
    byte[] imageInByte;
    Intent i;
    Wifi_list wifi_list1;
    Saved_Device_Database saved_device_database=new Saved_Device_Database(this);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_switch);

        name =(EditText)findViewById(R.id.input_name);
        area=(EditText)findViewById(R.id.input_area);
        photo=(TextView)findViewById(R.id.photo);
        connect=(Button)findViewById(R.id.connect);


        i=getIntent();
        ssid=i.getStringExtra("ssid");
        bssid=i.getStringExtra("bssid");
        pass=i.getStringExtra("pass");
        level=i.getIntExtra("level",0);







        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK);
                i.setType("image/*");

                startActivityForResult(i, RESULT_LOAD_IMAGE);

                //result3=true;

            }
        });





        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sname=name.getText().toString();
                sarea=area.getText().toString();
                state=0;


                if((sname.length() == 0)||(!sname.matches("[A-Za-z ]+")))
                {
                    // etname.requestFocus();
                    name.setError("Invalid Name");
                    result1=false;
                }
                else
                {
                    result1=true;
                }


                if((sarea.length() == 0)||(!sarea.matches("[A-Za-z ]+")))
                {
                    // etname.requestFocus();
                    area.setError("Invalid Name");
                    result2=false;
                }
                else
                {
                    result2=true;
                }



                if (result1 == true && result2==true && result3 == true  )  {

                    wifi_list1=new Wifi_list();

                    wifi_list1.setSSID(ssid);
                    wifi_list1.setBSSID(bssid);
                    wifi_list1.setPass(pass);
                    wifi_list1.setLevel(level);
                    wifi_list1.setName(sname);
                    wifi_list1.setArea(sarea);
                    wifi_list1.setPhoto(imageInByte);
                    wifi_list1.setState(state);
                    long ri=saved_device_database.addDevice(wifi_list1);
//                    if(ri != -1)
//                        Toast.makeText(getApplicationContext(), "New device added, row id: " + ri, Toast.LENGTH_SHORT).show();
//                    else
//                        Toast.makeText(getApplicationContext(), "Something wrong", Toast.LENGTH_SHORT).show();

                   // Toast.makeText(getApplicationContext(),"saved data",Toast.LENGTH_SHORT).show();
                   // Toast.makeText(getApplicationContext(),wifi_list1.getPass(),Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getApplicationContext(),Saved_Device_List.class);
                    startActivity(i);

                }

                }
        });





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            //Bitmap mBitmap = BitmapFactory.decodeFile(picturePath);
            Bitmap mBitmap = decode_File(picturePath);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imageInByte = stream.toByteArray();
            photo.setText(picturePath);
            result3=true;




            /*try {
                InputStream iStream = getContentResolver().openInputStream(selectedImage);
                inputData = Utils.getBytes(iStream);
            } catch (IOException ioe) {
                Log.e(TAG, "<saveImageInDB> Error : " + ioe.getLocalizedMessage());

            }
*/

        }


    }


    private Bitmap decode_File(String f) {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            //BitmapFactory.decodeStream(new FileInputStream(f), null, o);
            BitmapFactory.decodeFile(f,o);

            // The new size we want to scale to
            final int REQUIRED_SIZE=120;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
     //   Toast.makeText(getApplicationContext(),"Scale:-"+scale,Toast.LENGTH_SHORT).show();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeFile(f,o2);

    }









}
