package com.mahdirahmani8.learnenglishwithmusicapp.HomeFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mahdirahmani8.learnenglishwithmusicapp.HomeActivity.AboutMe;
import com.mahdirahmani8.learnenglishwithmusicapp.HomeActivity.Donate;
import com.mahdirahmani8.learnenglishwithmusicapp.HomeActivity.Fav;
import com.mahdirahmani8.learnenglishwithmusicapp.HomeActivity.LastPlayed;
import com.mahdirahmani8.learnenglishwithmusicapp.HomeActivity.MostLikes;
import com.mahdirahmani8.learnenglishwithmusicapp.HomeActivity.MostPlay;
import com.mahdirahmani8.learnenglishwithmusicapp.HomeActivity.NewSongs;
import com.mahdirahmani8.learnenglishwithmusicapp.HomeActivity.Search;
import com.mahdirahmani8.learnenglishwithmusicapp.HomeActivity.Setting;
import com.mahdirahmani8.learnenglishwithmusicapp.R;

public class Home extends Fragment implements View.OnClickListener {


    View view;
    ImageView new_song,setting,fav,give_star,donate,about;
    RelativeLayout most_play, last_play,most_like,search;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);

        Cast();

        new_song.setOnClickListener(this);
        setting.setOnClickListener(this);
        fav.setOnClickListener(this);
        give_star.setOnClickListener(this);
        donate.setOnClickListener(this);
        about.setOnClickListener(this);

        most_like.setOnClickListener(this);
        last_play.setOnClickListener(this);
        most_play.setOnClickListener(this);
        search.setOnClickListener(this);

        return view;
    }


    private void Cast() {

        new_song = (ImageView) view.findViewById(R.id.NEW);
        setting = (ImageView) view.findViewById(R.id.Setting);
        fav = (ImageView) view.findViewById(R.id.FAV);
        give_star = (ImageView) view.findViewById(R.id.Star);
        donate = (ImageView) view.findViewById(R.id.Donate);
        about = (ImageView) view.findViewById(R.id.About);

        most_play = (RelativeLayout) view.findViewById(R.id.MostPlay);
        last_play = (RelativeLayout) view.findViewById(R.id.LastPlay);
        most_like = (RelativeLayout) view.findViewById(R.id.MostLike);
        search = (RelativeLayout) view.findViewById(R.id.Search_home_fr);
    }


    @Override
    public void onClick(View v) {
        int viewID = v.getId();

        switch (viewID){

            case R.id.NEW:
                Intent n = new Intent(getActivity(), NewSongs.class);
                startActivity(n);

                break;

            case R.id.Setting:

                Intent s = new Intent(getActivity(), Setting.class);
                startActivity(s);

                break;

            case R.id.FAV:

                Intent f = new Intent(getActivity(), Fav.class);
                startActivity(f);

                break;
            case R.id.Star:

                Intent star = new Intent(Intent.ACTION_VIEW, Uri.parse("")); // TODO set intent app store
                startActivity(star);


                break;
            case R.id.Donate:

                Intent d = new Intent(getActivity(), Donate.class);
                startActivity(d);

                break;

            case R.id.About:

                Intent a = new Intent(getActivity(), AboutMe.class);
                startActivity(a);

                break;

            case R.id.MostPlay:

                Intent MP = new Intent(getActivity(), MostPlay.class);
                startActivity(MP);

                break;

            case R.id.LastPlay:

                Intent LP = new Intent(getActivity(), LastPlayed.class);
                startActivity(LP);

                break;

            case R.id.MostLike:

                Intent ML = new Intent(getActivity(), MostLikes.class);
                startActivity(ML);

                break;

            case R.id.Search_home_fr:

                Intent search = new Intent(getActivity(), Search.class);
                startActivity(search);

                break;

        }
    }
}
















