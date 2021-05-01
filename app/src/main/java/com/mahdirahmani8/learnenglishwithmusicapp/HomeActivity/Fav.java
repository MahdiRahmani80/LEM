package com.mahdirahmani8.learnenglishwithmusicapp.HomeActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.mahdirahmani8.learnenglishwithmusicapp.API.APIInterface;
import com.mahdirahmani8.learnenglishwithmusicapp.Adopter.SongListAdopter;
import com.mahdirahmani8.learnenglishwithmusicapp.DataBase.DataBase;
import com.mahdirahmani8.learnenglishwithmusicapp.Model.show;
import com.mahdirahmani8.learnenglishwithmusicapp.R;

import java.util.ArrayList;
import java.util.List;

public class Fav extends AppCompatActivity {

    RecyclerView recyclerView;
    APIInterface request;
    String url = "http://learnenmusic.pythonanywhere.com/";
    SongListAdopter adapter;
    List<show> showList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fav);

        recyclerView = (RecyclerView) findViewById(R.id.REC_FAV);

        DataBase db = new DataBase(this);
        adapter = new SongListAdopter(this,db.showFav(),db.showFav().size());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager (new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



    }
}