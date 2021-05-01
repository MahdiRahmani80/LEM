package com.mahdirahmani8.learnenglishwithmusicapp.Model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.mahdirahmani8.learnenglishwithmusicapp.DataBase.DataBase;
import com.mahdirahmani8.learnenglishwithmusicapp.LogIn;
import com.mahdirahmani8.learnenglishwithmusicapp.MainActivity;
import com.mahdirahmani8.learnenglishwithmusicapp.R;


public class Splash extends AppCompatActivity {


    boolean connected = false;
    TextView reTry,network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // cast
        reTry = (TextView) findViewById(R.id.tvReTry);
        network = (TextView) findViewById(R.id.showNetWork);


        // network
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;


        if (connected == false){
            network.setText("اتصال با اینترنت برقرار نشد");
            reTry.setText("دوباره تلاش کنید");
        }


        reTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recreate();
            }
        });




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                DataBase db = new DataBase(Splash.this);
                if (db.loginStatus() == 1) {

                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    intent.putExtra("NAME", "");

                    if (connected) {
                        startActivity(intent);
                        finish();
                    }else{

                    }


                } else {

                    Intent intent = new Intent(Splash.this, LogIn.class);
                    if (connected) {

                        db.login(); // login
                        startActivity(intent);
                        finish();
                    }
                }


            }
        }, 3000);

    }
}