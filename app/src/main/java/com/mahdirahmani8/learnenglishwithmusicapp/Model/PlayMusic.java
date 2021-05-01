package com.mahdirahmani8.learnenglishwithmusicapp.Model;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.SeekBar;


import java.io.IOException;

public class PlayMusic {

    String url = "";
    public static int isPlayerDataSource = 0;
    public static boolean isPrepared;
    public static SeekBar seekBar;
    public static MediaPlayer player = new MediaPlayer();
    private AudioManager audioManager;

    public PlayMusic(String url) {
        this.url = url;
        try {

            player.setDataSource(url);
            player.prepareAsync();
            isPlayerDataSource = 1;

            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {

                    isPrepared = true;

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void play() {


        if (player != null && player.isPlaying()) {
            player.pause();
        } else {
            player.start();
        }

    }

    public static boolean isPlaying() {
        if (player != null && player.isPlaying()) return true;
        return false;
    }


    public static void Reset(String url) {

        player.reset();
        try {
            player.setDataSource(url);
            player.prepareAsync();

            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    player.start();
                    player.pause();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void setSeekBar(SeekBar seekBar) {

        seekBar.setMax(player.getDuration());
        player.setLooping(true);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser) {
                    player.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }




    public void onAudioFocusChange(int focusState){

        switch (focusState){
            case AudioManager.AUDIOFOCUS_LOSS:
                if (player.isPlaying()) player.stop();
                player.release();
                player = null;
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                if (player.isPlaying()) player.pause();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                if (player.isPlaying()) player.setVolume(0.1f,0.1f);
                break;
        }
    }

}




