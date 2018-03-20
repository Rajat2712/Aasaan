package com.example.rajat.moswitch;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.victor.loading.newton.NewtonCradleLoading;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajat on 4/6/2017.
 */

public class Device_Search_List extends AppCompatActivity {

    NewtonCradleLoading newtonCradleLoading;
    AVLoadingIndicatorView avi;

    WifiManager mainWifi;
    WifiReceiver receiverWifi;
    RelativeLayout rl;


    private List<Wifi_list> wifi_lists = new ArrayList<>();
    private RecyclerView recyclerView;
    private Wifi_List_Adapter mAdapter;
    private String password = null;
    TextView tv, loading;
    int flag = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_search_list);
        newtonCradleLoading = (NewtonCradleLoading) findViewById(R.id.newton_cradle_loading);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);

        // MorphingButton btnMorph = (MorphingButton) findViewById(R.id.btnMorph);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        tv = (TextView) findViewById(R.id.tv);
        loading = (TextView) findViewById(R.id.loading);
        rl = (RelativeLayout) findViewById(R.id.rl);


        mainWifi = (WifiManager) getSystemService(WIFI_SERVICE);

        receiverWifi = new WifiReceiver();
        registerReceiver(receiverWifi, new IntentFilter(
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        if (mainWifi.isWifiEnabled() == false) {
            mainWifi.setWifiEnabled(true);
        }


        //findList();


        newtonCradleLoading.start();
        newtonCradleLoading.setLoadingColor(R.color.colorPrimaryDark);
        doInback();

        final Handler h = new Handler() {
            @Override
            public void handleMessage(Message message) {
                if (flag == 0) {
                    showAlertDialouge();
                }
            }
        };
        h.sendMessageDelayed(new Message(), 20000);



       /* if(!wifi_lists.isEmpty()){
            newtonCradleLoading.stop();
            newtonCradleLoading.setVisibility(View.GONE);
            newtonCradleLoading.clearAnimation();
            Toast.makeText(getApplicationContext(),"Enter",Toast.LENGTH_SHORT).show();

        }*/


        mAdapter = new Wifi_List_Adapter(wifi_lists);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Wifi_list wifiList = wifi_lists.get(position);
                showChangeLangDialog(wifiList);
//                Toast.makeText(getApplicationContext(), wifiList.getSSID() + " is selected!", Toast.LENGTH_SHORT).show();


                /*if(wifiList.getPass()==null) {

                    showChangeLangDialog();
                    wifiList.setPass(password);
                    ConnectToWiFi(wifiList.getSSID(),wifiList.getPass(),getApplicationContext());
                    i
                }
*/
/*
                if(password !=null){

                    wifiList.setPass(password);
                    ConnectToWiFi(wifiList.getSSID(),wifiList.getPass(),getApplicationContext());
                    WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
                    String networkId = wifiManager.getConnectionInfo().getSSID();
                    Toast.makeText(getApplicationContext(),networkId,Toast.LENGTH_SHORT).show();
                }*/


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }


    private void findList() {


        newtonCradleLoading.start();
        newtonCradleLoading.setLoadingColor(R.color.colorPrimary);

        doInback();

        if (!wifi_lists.isEmpty()) {
            //newtonCradleLoading.stop();
            newtonCradleLoading.setVisibility(View.GONE);
//            Toast.makeText(getApplicationContext(), "Enter", Toast.LENGTH_SHORT).show();


        }


    }


    private void showAlertDialouge() {


        //Toast.makeText(getApplicationContext(),"not",Toast.LENGTH_SHORT).show();


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert Dialog");

        // Setting Dialog Message
        builder.setMessage("Device not found");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.tick);

        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getApplicationContext(), Device_Search_List.class);
                startActivity(i);
            }
        });

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    void startAnim() {
        avi.setVisibility(View.VISIBLE);
//        Toast.makeText(getApplicationContext(), "play", Toast.LENGTH_SHORT).show();
        //avi.show();
        avi.smoothToShow();
    }

    void stopAnim() {
        avi.setVisibility(View.GONE);
//        Toast.makeText(getApplicationContext(), "pause", Toast.LENGTH_SHORT).show();
        //avi.hide();
        avi.smoothToHide();
    }


    public void showChangeLangDialog(final Wifi_list wifiList) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.etpass);
        final ImageButton eye = (ImageButton) dialogView.findViewById(R.id.eye);

        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    edt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    eye.setImageResource(R.drawable.ic_visibility_black_24dp);
                }
                if (edt.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    edt.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    eye.setImageResource(R.drawable.ic_visibility_off_black_24dp);
                }
            }
        });


        dialogBuilder.setTitle("Custom dialog");
        dialogBuilder.setMessage("Enter text below");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                if (!edt.getText().toString().isEmpty()) {

                    startAnim();


                    password = edt.getText().toString();
                    //Toast.makeText(getApplicationContext(),"password:-   " + password,Toast.LENGTH_SHORT).show();

                    wifiList.setPass(password);
                    //DisconnectToWiFi(wifiList.getSSID(),wifiList.getPass(),getApplicationContext());
                    ConnectToWiFi(wifiList.getSSID(), wifiList.getPass(), getApplicationContext());


                    final Handler h = new Handler() {
                        @Override
                        public void handleMessage(Message message) {

                           /* WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
                            String networkId = wifiManager.getConnectionInfo().getBSSID();
                            Toast.makeText(getApplicationContext(),networkId,Toast.LENGTH_SHORT).show();*/


                            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                            if (mWifi.isConnectedOrConnecting()) {

//                                Toast.makeText(getApplicationContext(), "enter", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), Edit_Switch.class);
                                i.putExtra("ssid", wifiList.getSSID());
                                i.putExtra("bssid", wifiList.getBSSID());
                                i.putExtra("pass", wifiList.getPass());
                                i.putExtra("level", wifiList.getLevel());
                                startActivity(i);
                                // Do whatever
                            }
                            else{
                                DisconnectToWiFi(wifiList.getSSID(), wifiList.getPass(), getApplicationContext());
//                                Toast.makeText(getApplicationContext(), "exit", Toast.LENGTH_SHORT).show();
                            }
                            stopAnim();

                        }
                    };
                    h.sendMessageDelayed(new Message(), 5000);







                    /*Intent i= new Intent(getApplicationContext(),OnOffActivity.class);
                    startActivity(i);
*/
                }
                else password = null;
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                password = null;
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }


    static public void ConnectToWiFi(String ssid, String key, Context ctx) {

        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", ssid);
        wifiConfig.preSharedKey = String.format("\"%s\"", key);
        WifiManager wifiManager = (WifiManager) ctx.getSystemService(ctx.WIFI_SERVICE);
        int networkId = wifiManager.getConnectionInfo().getNetworkId();
        wifiManager.removeNetwork(networkId);
        wifiManager.saveConfiguration();
        //remember id
        int netId = wifiManager.addNetwork(wifiConfig);

        /*
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();*/


        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration i : list) {
            if (i.SSID != null && i.SSID.equals("\"" + ssid + "\"")) {
                wifiManager.disconnect();
                wifiManager.enableNetwork(i.networkId, true);

                //wifiManager.reconnect();

                break;
            }
        }


    }


    static public void DisconnectToWiFi(String ssid, String key, Context ctx) {

        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", ssid);
        wifiConfig.preSharedKey = String.format("\"%s\"", key);
        WifiManager wifiManager = (WifiManager) ctx.getSystemService(ctx.WIFI_SERVICE);
        //int networkId = wifiManager.getConnectionInfo().getNetworkId();
        //wifiManager.removeNetwork(networkId);
        //wifiManager.saveConfiguration();
        //remember id
        int netId = wifiManager.addNetwork(wifiConfig);

        /*
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();*/


        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration i : list) {
            if (i.SSID != null && i.SSID.equals("\"" + ssid + "\"")) {
                //wifiManager.disconnect();
                wifiManager.removeNetwork(i.networkId);
                wifiManager.saveConfiguration();

                //wifiManager.reconnect();

                break;
            }
        }


    }


    public void doInback() {

        //wifi_lists.clear();


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // TODO Auto-generated method stub
                mainWifi = (WifiManager) getSystemService(WIFI_SERVICE);

                receiverWifi = new WifiReceiver();
                registerReceiver(receiverWifi, new IntentFilter(
                        WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
                mainWifi.startScan();

                //wifi_lists.clear();
                doInback();
            }


        }, 1500);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "Refresh");
        return super.onCreateOptionsMenu(menu);
    }


    /*public boolean onMenuItemSelected(int featureId, MenuItem item) {
        mainWifi.startScan();

        return super.onMenuItemSelected(featureId, item);}
*/

    @Override
    protected void onPause() {
        unregisterReceiver(receiverWifi);
        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(receiverWifi, new IntentFilter(
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }


    class WifiReceiver extends BroadcastReceiver {


        public void onReceive(Context c, Intent intent) {

            List<ScanResult> wifiList = new ArrayList<>();

            if (!wifiList.isEmpty()) {
                wifiList.clear();
            }

            wifiList = mainWifi.getScanResults();


            Wifi_list wifiList1;
            String Ssid, Bssid;
            int Level;

            List<Wifi_list> wifi_lists1 = new ArrayList<>();

            for (int i = 0; i < wifiList.size(); i++){
                ScanResult result = wifiList.get(i);

                Ssid = result.SSID;
                Bssid = result.BSSID;
                Level = result.level;
                // Log.d("ssid:-",Ssid);
                if (Ssid.matches("Aasaan")) {
                    wifiList1 = new Wifi_list(Ssid, Bssid, Level);
                    wifi_lists1.add(wifiList1);

                }
            }

            wifi_lists.clear();
            wifi_lists.addAll(wifi_lists1);

            if (!wifi_lists.isEmpty()) {
                newtonCradleLoading.stop();
                newtonCradleLoading.setVisibility(View.GONE);
                tv.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                rl.setBackgroundColor(getResources().getColor(R.color.white));
                flag = 1;
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private Device_Search_List.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final Device_Search_List.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
