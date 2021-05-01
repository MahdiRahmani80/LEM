package com.mahdirahmani8.learnenglishwithmusicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.mahdirahmani8.learnenglishwithmusicapp.Adopter.HomeTab;
import com.mahdirahmani8.learnenglishwithmusicapp.HomeFragments.AddMusic;
import com.mahdirahmani8.learnenglishwithmusicapp.HomeFragments.Search;
import com.mahdirahmani8.learnenglishwithmusicapp.HomeFragments.Songs;
import com.mahdirahmani8.learnenglishwithmusicapp.Model.PlayMusic;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import ir.tapsell.plus.TapsellPlus;

public class MainActivity extends AppCompatActivity {

    private HomeTab adopter;
    private TabLayout tabLayout;
    private ViewPager pager;
    private ImageView play;
    private TextView musicName, timeStart, timeEnd;
    private RelativeLayout playBottom;
    private SeekBar seekBar;



    private Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cast();
        setupView();
        setAdapter();
        MusicSet();
        setPlayBG();

        // Initial Tapsell
        TapsellPlus.initialize(this,"rrraegidsiprfeeibilkbcsecbpfocmrgrhnbatmjntjphoqtqfpogsjjtkctajkkjnapa");
       

    }

    private void MusicSet() {

        String songName = getIntent().getExtras().getString("NAME");
        if (songName.hashCode() != 0) {

            timeEnd.setText(Music.set_Timer(PlayMusic.player.getDuration()));
            // start
            timer = new Timer();
            timer.schedule(new timer_task_main(), 0, 1000);
            PlayMusic.setSeekBar(seekBar);

            musicName.setText(songName);
        } else {
            playBottom.setVisibility(View.GONE);
        }

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Music.pauseAndPlay();
                setPlayBG();

                Timer timer = new Timer();
                timer.schedule(new timer_task_main(), 0, 1000);

                timeEnd.setText(set_Timer(PlayMusic.player.getDuration()));

            }
        });

    }

    private void setPlayBG() {
        // SET BG
        if (PlayMusic.isPlaying()) {
            play.setImageResource(R.drawable.pause);
        } else {
            play.setImageResource(R.drawable.play);
        }
    }

    private void Cast() {

        playBottom = (RelativeLayout) findViewById(R.id.Music);
        play = (ImageView) findViewById(R.id.Play_music_id);
        musicName = (TextView) findViewById(R.id.Music_Name);
        seekBar = (SeekBar) findViewById(R.id.SeekBar);
        timeStart = (TextView) findViewById(R.id.start);
        timeEnd = (TextView) findViewById(R.id.end);
    }

    private void setAdapter() {
        adopter = new HomeTab(getSupportFragmentManager());

        Songs song = new Songs();
        Search search = new Search();
        AddMusic add = new AddMusic();

        adopter.addFragment(search, "Search");
        adopter.addFragment(song, "Songs");
        adopter.addFragment(add, "Add Music");

        int[] tabIcons = {
                R.drawable.search,
                R.drawable.add_song,
                R.drawable.songs
        };
        pager.setAdapter(adopter);
        tabLayout.setupWithViewPager(pager);

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[2]);
        tabLayout.getTabAt(2).setIcon(tabIcons[1]);
        tabLayout.getTabAt(1).select();

    }


    private void setupView() {
        tabLayout = (TabLayout) findViewById(R.id.TabLayout);
        pager = (ViewPager) findViewById(R.id.pager);

    }


    private String set_Timer(long progress) {

        int sec = (int) progress / 1000;
        int min = (int) sec / 60;
        sec %= 60;

        return String.format(Locale.ENGLISH, "%02d", min) + ":" + String.format(Locale.ENGLISH, "%02d", sec);
    }


    public class timer_task_main extends TimerTask {

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

}
