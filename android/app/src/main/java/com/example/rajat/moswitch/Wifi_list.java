package com.example.rajat.moswitch;

import java.sql.Blob;

/**
 * Created by Rajat on 3/6/2017.
 */

public class Wifi_list {

    private String SSID,BSSID,Pass,Name,Area;
    private  int Level,Id,State;
    private byte[] Photo;

    public Wifi_list(){

    }


    public Wifi_list(String SSID, String BSSID, int Level){

        this.SSID = SSID;
        this.BSSID = BSSID;
        this.Level = Level;


    }




    public Wifi_list(String SSID, String BSSID, String Pass, int Level){

        this.SSID = SSID;
        this.BSSID = BSSID;
        this.Pass = Pass;
        this.Level = Level;


    }

    public Wifi_list(String SSID, String BSSID, String Pass, int Level,String Name, String Area, byte[] Photo){

        this.SSID = SSID;
        this.BSSID = BSSID;
        this.Pass = Pass;
        this.Level = Level;
        this.Name=Name;
        this.Area=Area;
        this.Photo=Photo;


    }

    public Wifi_list(int Id,String SSID, String BSSID, String Pass, int Level,String Name, String Area, byte[] Photo){

        this.SSID = SSID;
        this.BSSID = BSSID;
        this.Pass = Pass;
        this.Level = Level;
        this.Name=Name;
        this.Area=Area;
        this.Photo=Photo;
        this.Id=Id;


    }



    public Wifi_list(int Id,String SSID, String BSSID, String Pass, int Level,String Name, String Area, byte[] Photo,int State){

        this.SSID = SSID;
        this.BSSID = BSSID;
        this.Pass = Pass;
        this.Level = Level;
        this.Name=Name;
        this.Area=Area;
        this.Photo=Photo;
        this.Id=Id;
        this.State=State;


    }





    public String getSSID(){
        return SSID;
    }

    public void  setSSID(String SSID){
        this.SSID=SSID;
    }


    public String getBSSID(){
        return BSSID;
    }

    public void  setBSSID(String BSSID){
        this.BSSID=BSSID;
    }

    public String getPass(){
        return Pass;
    }

    public void  setPass(String Pass){
        this.Pass=Pass;
    }

    public int getLevel(){
        return Level;
    }

    public void  setLevel(int Level){
        this.Level=Level;
    }


    public String getName(){
        return Name;
    }

    public void  setName(String Name){
        this.Name=Name;
    }

    public String getArea(){
        return Area;
    }

    public void  setArea(String Area){
        this.Area=Area;
    }

    public byte[] getPhoto(){
        return Photo;
    }

    public void  setPhoto(byte[] Photo){
        this.Photo=Photo;
    }

    public int getId(){
        return Id;
    }

    public void  setId(int Id){this.Id=Id;}


    public int getState(){
        return State;
    }

    public void  setState(int State){this.State=State;}



}
