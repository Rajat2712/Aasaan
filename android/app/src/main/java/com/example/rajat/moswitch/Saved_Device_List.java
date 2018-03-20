package com.example.rajat.moswitch;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajat on 4/14/2017.
 */

public class Saved_Device_List extends AppCompatActivity implements Saved_Device_List_Adapter.Saved_Device_List_AdapterListener{


    private RecyclerView recyclerView;
    private Saved_Device_List_Adapter adapter;
    private List<Wifi_list> wifi_listList;
    Wifi_list wifi_list;
    AVLoadingIndicatorView avi;
    WebView wv;
    public static Saved_Device_Database savedDeviceDatabase;
    FloatingActionButton fab;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_device_list);
        avi=(AVLoadingIndicatorView)findViewById(R.id.avi);
        wv=(WebView)findViewById(R.id.wv);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        wv.setWebViewClient(new WebViewClient());
//        setSupportActionBar(toolbar);

       // initCollapsingToolbar();
        savedDeviceDatabase=new Saved_Device_Database(getApplicationContext());

      //  Toast.makeText(getApplicationContext(),"count" + savedDeviceDatabase.getDeviceCount(),Toast.LENGTH_SHORT).show();


       // Toast.makeText(getApplicationContext(),"Ccount" + savedDeviceDatabase.getCoumnCount(),Toast.LENGTH_SHORT).show();


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        wifi_listList = new ArrayList<>();
        wifi_listList=savedDeviceDatabase.getAllDevice();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent i = new Intent(Saved_Device_List.this, GraphActivity.class);
                startActivity(i);
            }
        });



        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if(wifi_listList!=null) {
            adapter = new Saved_Device_List_Adapter(this, wifi_listList,this);
            recyclerView.setAdapter(adapter);
         //   Toast.makeText(getApplicationContext(),"enter mar liya",Toast.LENGTH_SHORT).show();

        }


        //prepareAlbums();



    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */


    @Override
    public void onIconClicked(final int position) {

        startAnim();

        On_Off(position);

        stopAnim();

        //Toast.makeText(getApplicationContext(),"icon" + position,Toast.LENGTH_SHORT).show();


/*
        ConnectToWiFi(wifi_listList.get(position).getSSID(),wifi_listList.get(position).getPass(),getApplicationContext());


        final Handler h = new Handler() {
            @Override
            public void handleMessage(Message message) {

                           *//* WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
                            String networkId = wifiManager.getConnectionInfo().getBSSID();
                            Toast.makeText(getApplicationContext(),networkId,Toast.LENGTH_SHORT).show();*//*



                ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                 if (mWifi.isAvailable()) {

                Toast.makeText(getApplicationContext(),"enter",Toast.LENGTH_SHORT).show();

                     On_Off(position);




                            }
                            else {
                                DisconnectToWiFi(wifi_listList.get(position).getSSID(),wifi_listList.get(position).getPass(),getApplicationContext());
                                Toast.makeText(getApplicationContext(),"Device not found",Toast.LENGTH_SHORT).show();
                            }
                stopAnim();

            }
        };
        h.sendMessageDelayed(new Message(), 4000);



        //toggleSelection(position);

        */
    }


    void startAnim(){
        avi.setVisibility(View.VISIBLE);
       // Toast.makeText(getApplicationContext(),"play",Toast.LENGTH_SHORT).show();
        //avi.show();
        avi.smoothToShow();
    }

    void stopAnim(){
        avi.setVisibility(View.GONE);
       // Toast.makeText(getApplicationContext(),"pause",Toast.LENGTH_SHORT).show();
        //avi.hide();
        avi.smoothToHide();
    }

    public void On_Off(int position){

        if(wifi_listList.get(position).getState()==0){

            wv.getSettings().setJavaScriptEnabled(true);
            wv.getSettings().setLoadsImagesAutomatically(true);
            wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            wv.loadUrl("http://192.168.1.1/H");

            wifi_listList.get(position).setState(1);

            savedDeviceDatabase.updateDevice(wifi_listList.get(position),wifi_listList.get(position).getId());

            adapter.notifyItemChanged(position);




        } else {

            wv.getSettings().setJavaScriptEnabled(true);
            wv.getSettings().setLoadsImagesAutomatically(true);
            wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            wv.loadUrl("http://192.168.1.1/L");

            wifi_listList.get(position).setState(0);

            savedDeviceDatabase.updateDevice(wifi_listList.get(position),wifi_listList.get(position).getId());

            adapter.notifyItemChanged(position);


        }





    }



    static public void ConnectToWiFi(String ssid ,String key,Context ctx) {

        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", ssid);
        wifiConfig.preSharedKey = String.format("\"%s\"", key);
        WifiManager wifiManager = (WifiManager) ctx.getSystemService(ctx.WIFI_SERVICE);
        int networkId = wifiManager.getConnectionInfo().getNetworkId();
        //wifiManager.disconnect();
        wifiManager.removeNetwork(networkId);
        wifiManager.saveConfiguration();
        //remember id
        int netId = wifiManager.addNetwork(wifiConfig);

        /*
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();*/


        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for( WifiConfiguration i : list ) {
            if(i.SSID != null && i.SSID.equals("\"" + ssid + "\"")) {
                //wifiManager.disconnect();
                wifiManager.enableNetwork(i.networkId, true);

              //  wifiManager.reconnect();

                break;
            }
        }


    }



    static public void DisconnectToWiFi(String ssid ,String key,Context ctx) {

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
        for( WifiConfiguration i : list ) {
            if(i.SSID != null && i.SSID.equals("\"" + ssid + "\"")) {
                //wifiManager.disconnect();
                wifiManager.removeNetwork(i.networkId);
                wifiManager.saveConfiguration();

                //wifiManager.reconnect();

                break;
            }
        }


    }







    @Override
    public void onPopupClicked(ImageView v,int position) {

        //Toast.makeText(getApplicationContext(),"overflow" + position,Toast.LENGTH_SHORT).show();

        Intent i=new Intent(getApplicationContext(),Saved_Device_info.class);
        i.putExtra("position",position);
        startActivity(i);
        //showPopupMenu(v,position);

    }


    private void showPopupMenu(ImageView view, int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(getApplicationContext(),view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_saved_list, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.show();
    }


    class MyMenuItemClickListener  implements PopupMenu.OnMenuItemClickListener {

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


//                    Toast.makeText(getApplicationContext(), "Add to favourite" + p  , Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getApplicationContext(),Saved_Device_info.class);
                    i.putExtra("position",p);
                    startActivity(i);
                    return true;
                case R.id.action_play_next:
//                    Toast.makeText(getApplicationContext(), "Play next" + p, Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }













    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
