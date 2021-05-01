package com.mahdirahmani8.learnenglishwithmusicapp.HomeFragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Search extends Fragment {
    
    View view;
    RecyclerView recyclerView;
    EditText editText;
    SongListAdopter adopter;
    APIInterface request;
    String url = "http://learnenmusic.pythonanywhere.com/";
    Context context = getContext();
    SongListAdopter adapter;
    public static List<show> showList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search,container,false);

        // Cast
        recyclerView = (RecyclerView) view.findViewById(R.id.rec_Search);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        editText = (EditText) view.findViewById(R.id.ed_Search);
        // end Cast

        getList();


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().trim().isEmpty()){
                    recyclerView.setVisibility(GONE);
                }else {

                    filter(s.toString().trim());
                    recyclerView.setVisibility(VISIBLE);
                }


            }
        });



        
        return (view);
    }

    private void getList() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                request = APIClient.getAPIListClient(url).create(APIInterface.class);

                request.getListShow().enqueue(new Callback<List<show>>() {
                    @Override
                    public void onResponse(Call<List<show>> call, Response<List<show>> response) {

                        showList = response.body();
                        // set Adopter
                        adopter = new SongListAdopter(getActivity(), showList , showList.size());
                        recyclerView.setAdapter(adopter);
                        recyclerView.setVisibility(GONE);

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

    }

    // search
    private void filter(String text) {

        List<show> list = new ArrayList<>();
        for (show sh : Songs.showList){
            if (sh.getMusicName().toLowerCase().contains(text.toLowerCase())  && sh.getMusicName() != null
                    || sh.getArtist().toLowerCase().contains(text.toLowerCase()) && sh.getArtist() != null ){

                list.add(sh);
            }
        }

        adopter.filterList(list);

    }
}
