package com.mahdirahmani8.learnenglishwithmusicapp.HomeFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mahdirahmani8.learnenglishwithmusicapp.API.APIClient;
import com.mahdirahmani8.learnenglishwithmusicapp.API.APIInterface;
import com.mahdirahmani8.learnenglishwithmusicapp.Model.Create;
import com.mahdirahmani8.learnenglishwithmusicapp.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMusic extends Fragment {

    EditText musicName, artist, url, english, farsi;
    ImageView search;
    Button addSong;
    String Link = "http://learnenmusic.pythonanywhere.com/";
    APIInterface request ;
    View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_music_fragment, container, false);

        cast();


        request = APIClient.getAPIListClient(Link).create(APIInterface.class);
        addSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewMusic();

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
                        "https://www.google.com/search?q=music&oq=music..69i57j35i39l2j0l2j69i60l3.3903j0j7&sourceid=chrome&ie=UTF-8"));
                startActivity(intent);
            }
        });


        return view;
    }


    public void cast() {
        musicName = (EditText) view.findViewById(R.id.Music_Name);
        artist = (EditText) view.findViewById(R.id.Artist);
        url = (EditText) view.findViewById(R.id.URL);
        english = (EditText) view.findViewById(R.id.EN_txt);
        farsi = (EditText) view.findViewById(R.id.FA_txt);
        search = (ImageView) view.findViewById(R.id.Search);

        addSong = (Button) view.findViewById(R.id.ADD_SONG);

    }


    private void setNewMusic(){

        String userName = "USER";
        String Artist = artist.getText().toString();
        String URL = url.getText().toString();
        String en = english.getText().toString();
        String fa = farsi.getText().toString();
        String nameMusic = musicName.getText().toString();
        int Fav = 0;




        Call<Create> call = request.createMusic( userName,URL,nameMusic,Artist,fa,en,Fav );

        call.enqueue(new Callback<Create>() {
            @Override
            public void onResponse(Call<Create> call, Response<Create> response) {


                if (response.code() == 201){
                    Toast.makeText(getContext(),"موسیقی باموفقیت افزوده شد", Toast.LENGTH_LONG).show();
                    musicName.setText("");
                    url.setText("");
                    artist.setText("");
                    farsi.setText("");
                    english.setText("");

                }else if(response.code() == 400) {
                    Toast.makeText(getContext(),"مشکلی پیش آمده.فیلد ها را دوباره چک کنید ", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Create> call, Throwable t) {

            }
        });

    }



    private String isURL( String str ) {

        String regex = "^http.//.*mp3$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher( str.trim());
        while(m.find()) {
            String urlStr = m.group();
            if (urlStr.startsWith("(") && urlStr.endsWith(")"))
            {
                urlStr = urlStr.substring(1, urlStr.length() - 1);
            }
            return urlStr;
        }

        return "";
    }

}
