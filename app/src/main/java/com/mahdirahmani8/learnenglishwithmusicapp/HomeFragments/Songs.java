package com.mahdirahmani8.learnenglishwithmusicapp.HomeFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mahdirahmani8.learnenglishwithmusicapp.API.APIClient;
import com.mahdirahmani8.learnenglishwithmusicapp.API.APIInterface;
import com.mahdirahmani8.learnenglishwithmusicapp.Adopter.SongListAdopter;
import com.mahdirahmani8.learnenglishwithmusicapp.Model.show;
import com.mahdirahmani8.learnenglishwithmusicapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Songs extends Fragment {

    RecyclerView recyclerView;
    View view;
    SongListAdopter adapter;
    public static List<show> showList = new ArrayList<>();
    APIInterface request;
    String url = "http://learnenmusic.pythonanywhere.com/";
    Context context = getContext();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.songs, container, false);


        Cast();


        // GET API

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                request = APIClient.getAPIListClient(url).create(APIInterface.class);

                request.getListShow().enqueue(new Callback<List<show>>() {
                    @Override
                    public void onResponse(Call<List<show>> call, Response<List<show>> response) {

                        showList = response.body();

                        // For Reverse ArrayList
                        Collections.reverse(showList);
                        adapter = new SongListAdopter(getContext(), showList, showList.size());
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<show>> call, Throwable t) {

                        Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();

                    }
                });

            }
        };

        try {
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(runnable);
            
        }catch(Exception e){

        }


        // end get

        return view;
    }

    private void Cast() {
        recyclerView = (RecyclerView) view.findViewById(R.id.Recycler_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

}