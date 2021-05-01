package com.mahdirahmani8.learnenglishwithmusicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mahdirahmani8.learnenglishwithmusicapp.API.APIClient;
import com.mahdirahmani8.learnenglishwithmusicapp.API.APIInterface;
import com.mahdirahmani8.learnenglishwithmusicapp.Model.Create;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit extends AppCompatActivity {

    String musicname, url_str, fa, en, artist_str;
    int id;
    int fav;
    EditText musicName, artist, url, english, farsi;
    ImageView search;
    Button addSong;
    String Link = "http://learnenmusic.pythonanywhere.com/";
    APIInterface request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        cast();
        getAndSet();
        editSong();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/search?q=music&oq=music..69i57j35i39l2j0l2j69i60l3.3903j0j7&sourceid=chrome&ie=UTF-8"));
                startActivity(intent);
            }
        });

    }

    private void editSong() {
        request = APIClient.getAPIListClient(Link).create(APIInterface.class);
        addSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewMusic();

            }
        });
    }


    private void setNewMusic() {

        Call<Create> call = request.edit(id,
                "USER" + "&" + "USER",
                url.getText(),
                musicName.getText(),
                artist.getText(),
                farsi.getText(),
                english.getText(),
                fav);

        call.enqueue(new Callback<Create>() {
            @Override
            public void onResponse(Call<Create> call, Response<Create> response) {

                if (response.code() == 200) {
                    Toast.makeText(Edit.this, "تغییرات با موفقیت ذخیره شد", Toast.LENGTH_LONG).show();
                    url.setText("");
                    musicName.setText("");
                    artist.setText("");
                    farsi.setText("");
                    english.setText("");

                } else {

                    Toast.makeText(Edit.this, "مشکلی پیش آمده . لطفا فیلد های خود را چک کنید", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Create> call, Throwable t) {

                Toast.makeText(Edit.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void cast() {

        musicName = (EditText) findViewById(R.id.Music_Name);
        artist = (EditText) findViewById(R.id.Artist);
        url = (EditText) findViewById(R.id.URL);
        english = (EditText) findViewById(R.id.EN_txt);
        farsi = (EditText) findViewById(R.id.FA_txt);
        search = (ImageView) findViewById(R.id.Search);

        addSong = (Button) findViewById(R.id.ADD_SONG);
    }

    public void getAndSet() {


        // Get
        musicname = getIntent().getExtras().getString("MUSICNAME");
        url_str = getIntent().getExtras().getString("URL");
        fa = getIntent().getExtras().getString("FA");
        en = getIntent().getExtras().getString("EN");
        artist_str = getIntent().getExtras().getString("ARTIST");
        id = getIntent().getExtras().getInt("ID");
        fav = getIntent().getExtras().getInt("FAV");

        // SET
        musicName.setText(musicname);
        url.setText(url_str);
        farsi.setText(fa);
        english.setText(en);
        artist.setText(artist_str);

    }
}