package com.example.zorahu.networktest;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {

    private ConnectivityManager mgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = mgr.getActiveNetworkInfo();
        //Check the Network Connection
        if (info != null && info.isConnected()){
            try{
                //Returns all the interfaces on this machine.
                Enumeration<NetworkInterface> ifs = NetworkInterface.getNetworkInterfaces();
                while (ifs.hasMoreElements()){
                    NetworkInterface ip = ifs.nextElement();
                    Enumeration<InetAddress> ips = ip.getInetAddresses();
                    while (ips.hasMoreElements()){
                        InetAddress ia = ips.nextElement();
                        Log.d("test",ia.getHostAddress());
                    }
                }
            }catch (Exception e){
                Log.d("test","Oops!");

            }

        }else {
            Log.d("test","Not connected");
        }
    }
    public void test1(View v){
        /*
        連上網路的方法，必須要包在執行緒當中
        如果沒有會跳出 NetworkOnMainThreadException
         */
//        new Thread(){
//            @Override
//            public void run(){
//                try{
//                    URL url = new URL("http://www.google.com");
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    conn.connect();
//                    InputStream in = conn.getInputStream();
//                    int c;
//                    StringBuffer sb = new StringBuffer();
//                    while ((c = in.read()) != -1){
//                        sb.append((char)c);
//                    }
//                    in.close();
//                    Log.d("test",sb.toString());
//                }catch (Exception ee){
//                    Log.d("test",ee.toString());
//                }
//
//            }
//        }.start();
        MyTread mt1 = new MyTread();
        mt1.start();
    }
    private class MyTread extends Thread{
        @Override
        public void run(){
            try {
                URL url = new URL("http://data.coa.gov.tw/Service/OpenData/EzgoTravelFoodStay.aspx");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(conn.getInputStream()));

                reader.close();
            }catch (Exception ee){
                Log.d("test",ee.toString());
            }
        }
    }
}
