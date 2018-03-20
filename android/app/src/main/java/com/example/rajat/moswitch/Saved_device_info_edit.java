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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kbeanie.multipicker.api.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajat on 4/17/2017.
 */

public class Saved_device_info_edit extends AppCompatActivity {

    EditText name,area;
    TextView photo;
    ImageView icon;
    Button connect;
    String sname,sarea;
    int id,p;
    boolean result1, result2, result3=false;
    private static int RESULT_LOAD_IMAGE = 1;
    byte[] imageInByte;
    final int CAMERA_CAPTURE = 1;
    final int CROP_PIC = 2;
    Intent i;
    Wifi_list wifi_list;
    private List<Wifi_list> wifi_listList;
    Saved_Device_Database savedDeviceDatabase;

    ImagePicker imagePicker;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.saved_device_info_edit);
        setContentView(R.layout.saved_device_info_edit);
        name=(EditText) findViewById(R.id.input_name);
        area=(EditText) findViewById(R.id.input_area);
        photo=(TextView)findViewById(R.id.tvphoto);
        icon=(ImageView)findViewById(R.id.icon);
        connect=(Button)findViewById(R.id.connect);

        savedDeviceDatabase=new Saved_Device_Database(getApplicationContext());
        i=getIntent();
        p=i.getIntExtra("position",0);


        wifi_listList = new ArrayList<>();
        wifi_listList=savedDeviceDatabase.getAllDevice();
        wifi_list=wifi_listList.get(p);

        name.setText(wifi_list.getName());
        area.setText(wifi_list.getArea());
        id=wifi_list.getId();
        Glide.with(getApplicationContext()).load(wifi_list.getPhoto()).into(icon);

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


                    wifi_list.setName(sname);
                    wifi_list.setArea(sarea);
                    wifi_list.setPhoto(imageInByte);
                    savedDeviceDatabase.updateDevice(wifi_list,id);
                    Intent i=new Intent(getApplicationContext(),Saved_Device_List.class);
                    startActivity(i);

                }

            }
        });





















    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
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
            Glide.with(getApplicationContext()).load(imageInByte).into(icon);
            result3=true;
            cursor.close();

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
//        Toast.makeText(getApplicationContext(),"Scale:-"+scale,Toast.LENGTH_SHORT).show();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeFile(f,o2);

    }


























}
