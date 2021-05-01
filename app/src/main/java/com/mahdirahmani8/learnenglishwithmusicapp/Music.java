package com.mahdirahmani8.learnenglishwithmusicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mahdirahmani8.learnenglishwithmusicapp.API.APIClient;
import com.mahdirahmani8.learnenglishwithmusicapp.API.APIInterface;
import com.mahdirahmani8.learnenglishwithmusicapp.DataBase.DataBase;
import com.mahdirahmani8.learnenglishwithmusicapp.Model.Create;
import com.mahdirahmani8.learnenglishwithmusicapp.Model.PlayMusic;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import ir.tapsell.plus.AdRequestCallback;
import ir.tapsell.plus.AdShowListener;
import ir.tapsell.plus.TapsellPlus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Music extends AppCompatActivity {

    private static Timer timer;
    // include play
    TextView playMusicName, timeStart, timeEnd,report;
    ImageView play;

    // activity
    TextView poem, headerMusicName, Fav;
    ImageView translate, edit, back, FavImg;
    RelativeLayout FAV_REL;
    boolean isFA = false;

    // for get and set
    int id;
    String musicName, url, artist, en, fa, username;

    Editable API_MUSIC_NAME;
    Editable API_URL;
    Editable API_ARTIST;
    Editable API_FA;
    Editable API_EN;

    int fav;
    boolean SHOW = false;

    // player
    static PlayMusic music;
    private static SeekBar seekBar;

    // API
    APIInterface request;
    String URL_link = "http://learnenmusic.pythonanywhere.com/";

    // Notification
    NotificationManager notifManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        Cast();
        getAndSet();
        playMusic(url);
        requestAd();

        // report part
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Music.this,Report.class);
                intent.putExtra("ID",id);
                startActivity(intent);

            }
        });

        DataBase db = new DataBase(Music.this);
        if (db.isLiked(id)){
            FavImg.setImageResource(R.drawable.heart);
        } else{
            FavImg.setImageResource(R.drawable.heart_empty);
        }

    }

    public void playMusic(String link) {


        if (PlayMusic.isPlayerDataSource == 1) {
            PlayMusic.Reset(link);
        } else {
            music = new PlayMusic(link);
        }


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                music.setSeekBar(seekBar);
                setRowSeekBar();
                setNotification(musicName);

                if (music.isPlaying()) {
                    music.play();
                    play.setImageResource(R.drawable.play);
                } else {
                    music.play();
                    play.setImageResource(R.drawable.pause);
                }
            }
        });

    }


    private void getAndSet() {

        // Get
        Intent intent = getIntent();
        id = intent.getExtras().getInt("ID");
        musicName = intent.getExtras().getString("MUSICNAME");
        url = intent.getExtras().getString("URL");
        artist = intent.getExtras().getString("ARTIST");
        fav = intent.getExtras().getInt("FAV");
        en = intent.getExtras().getString("EN");
        fa = intent.getExtras().getString("FA");
        username = intent.getExtras().getString("USERNAME");

        // Set
        playMusicName.setText(musicName);
        headerMusicName.setText(musicName);
        poem.setText(en + "\n");

        Fav.setText("" + fav + "");

        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isFA) {
                    poem.setText(fa + "\n");
                    isFA = !isFA;
                } else {
                    poem.setText(en + "\n");
                    isFA = !isFA;
                }

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Music.this, Edit.class);
                intent.putExtra("MUSICNAME", musicName);
                intent.putExtra("ARTIST", artist);
                intent.putExtra("URL", url);
                intent.putExtra("EN", en);
                intent.putExtra("FA", fa);
                intent.putExtra("ID", id);
                intent.putExtra("FAV",fav);
                startActivity(intent);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // FAV API
        request = APIClient.getAPIListClient(URL_link).create(APIInterface.class);

        FAV_REL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataBase db = new DataBase(Music.this);
                if (db.fav_status(id, url,fa,en,artist,musicName) == 0) {  // Check is liked

                    API_URL = new SpannableStringBuilder(url);
                    API_MUSIC_NAME = new SpannableStringBuilder(musicName);
                    API_ARTIST = new SpannableStringBuilder(artist);
                    API_FA = new SpannableStringBuilder(fa);
                    API_EN = new SpannableStringBuilder(en);


                    Call<Create> call = request.edit(id,
                            "USER LIKE",
                            API_URL,
                            API_MUSIC_NAME,
                            API_ARTIST,
                            API_FA,
                            API_EN,
                            fav - 1);



                    call.enqueue(new Callback<Create>() {
                        @Override
                        public void onResponse(Call<Create> call, Response<Create> response) {
                            Toast.makeText(Music.this, "از لیست علاقه مندی ها حذف شد", Toast.LENGTH_LONG).show();
                            Fav.setText(fav - 1 + "");
                            FavImg.setImageResource(R.drawable.heart_empty);

                            fav = fav -1;
                        }

                        @Override
                        public void onFailure(Call<Create> call, Throwable t) {
                            Toast.makeText(Music.this, "مشکلی پیش آمده", Toast.LENGTH_LONG).show();
                        }
                    });


                } else {
                    API_URL = new SpannableStringBuilder(url);
                    API_MUSIC_NAME = new SpannableStringBuilder(musicName);
                    API_ARTIST = new SpannableStringBuilder(artist);
                    API_FA = new SpannableStringBuilder(fa);
                    API_EN = new SpannableStringBuilder(en);


                    Call<Create> call = request.edit(id,
                            "USER LIKE",
                            API_URL,
                            API_MUSIC_NAME,
                            API_ARTIST,
                            API_FA,
                            API_EN,
                            fav + 1);



                    call.enqueue(new Callback<Create>() {
                        @Override
                        public void onResponse(Call<Create> call, Response<Create> response) {
                            Toast.makeText(Music.this, "به لیست علاقه مندی ها اضافه شد.", Toast.LENGTH_LONG).show();
                            Fav.setText(fav + 1 + "");
                            FavImg.setImageResource(R.drawable.heart);

                            fav = fav +1;
                        }

                        @Override
                        public void onFailure(Call<Create> call, Throwable t) {
                            Toast.makeText(Music.this, "مشکلی پیش آمده", Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }
        });

    }

    private void Cast() {
        playMusicName = (TextView) findViewById(R.id.Music_Name);
        timeStart = (TextView) findViewById(R.id.start);
        timeEnd = (TextView) findViewById(R.id.end);
        headerMusicName = (TextView) findViewById(R.id.Music_Name_play);
        poem = (TextView) findViewById(R.id.Main_txt);
        Fav = (TextView) findViewById(R.id.FAV_INT);

        play = (ImageView) findViewById(R.id.Play_music_id);
        seekBar = (SeekBar) findViewById(R.id.SeekBar);

        translate = (ImageView) findViewById(R.id.imgTr);
        edit = (ImageView) findViewById(R.id.edit);
        back = (ImageView) findViewById(R.id.back);
        FavImg = (ImageView) findViewById(R.id.FAV_IMG);
        FAV_REL = (RelativeLayout) findViewById(R.id.Rel_FAV);

        report = (TextView) findViewById(R.id.report);
    }


    @Override
    public void onBackPressed(){

        if (SHOW){
            showAd();
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("NAME", musicName);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    public static void pauseAndPlay() {
        music.play();
    }


    public void setRowSeekBar() {

        timer = new Timer();
        timer.schedule(new timer_task(), 0, 1000);

        if (PlayMusic.isPrepared) {
            timeEnd.setText(set_Timer(PlayMusic.player.getDuration()));
        }

    }


    private class timer_task extends TimerTask {

        @Override
        public void run() {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    seekBar.setProgress(PlayMusic.player.getCurrentPosition());
                    timeStart.setText(set_Timer(PlayMusic.player.getCurrentPosition()));
                }
            });
        }
    }


    public static String set_Timer(long progress) {

        int sec = (int) progress / 1000;
        int min = (int) sec / 60;
        sec %= 60;

        return String.format(Locale.ENGLISH, "%02d", min) + ":" + String.format(Locale.ENGLISH, "%02d", sec);
    }


    private void requestAd() {
        TapsellPlus.requestInterstitial(
                Music.this,
                "6076b434e07c8c0001945876",
                new AdRequestCallback() {
                    @Override
                    public void response() {
                        //ad is ready to show
                        SHOW = true;
                    }

                    @Override
                    public void error(String message) {
                        Toast.makeText(Music.this,message,Toast.LENGTH_LONG).show();
                    }

                });
    }


    // SHOW AD
    private void showAd(){
        TapsellPlus.showAd(Music.this,
                "6076b434e07c8c0001945876",
                new AdShowListener() {

                    @Override
                    public void onOpened() {
                        super.onOpened();
                    }

                    @Override
                    public void onClosed() {
                        super.onClosed();
                    }

                    @Override
                    public void onRewarded() {
                        super.onRewarded();
                    }

                    @Override
                    public void onError(String s) {
                        super.onError(s);
                    }
                });
    }


    private void setNotification( String musicName ){

//        int music_du = PlayMusic.player.getDuration();
//        NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//
//        new Handler(Looper.getMainLooper()).post(new Runnable() {
//            @Override
//            public void run() {
//                Notification notify=new Notification.Builder
//                        (getApplicationContext())
//                        .setOngoing(true)
//                        .setProgress( music_du ,seekBar.getProgress(),false)
//                        .setSmallIcon(R.drawable.bg)
//                        .build();
//
//                notify.flags |= Notification.FLAG_AUTO_CANCEL;
//                notif.notify(0, notify);
//            }
//        });
    }
}