package com.mahdirahmani8.learnenglishwithmusicapp.Adopter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mahdirahmani8.learnenglishwithmusicapp.Model.show;
import com.mahdirahmani8.learnenglishwithmusicapp.Music;
import com.mahdirahmani8.learnenglishwithmusicapp.R;

import java.util.List;


public class SongListAdopter extends RecyclerView.Adapter<SongListAdopter.ViewHolder> {

    private LayoutInflater context;
    private List<show> data;
    private AdapterView.OnItemClickListener listener;
    private int size = 0;


    public SongListAdopter(Context context, List<show> showList, int size) {

        this.context = LayoutInflater.from(context);
        this.data = showList;
        this.size = size;
    }


    public void filterList(List<show> list) {
        data = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public SongListAdopter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = context.inflate(R.layout.item_music, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        String nameMusic = data.get(position).getMusicName();
        holder.MusicName.setText(nameMusic);
        String artist = data.get(position).getArtist();
        holder.Artist.setText(artist);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView MusicName, Artist;
        LinearLayout main_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            MusicName = (TextView) itemView.findViewById(R.id.Item_Music_Name);
            Artist = (TextView) itemView.findViewById(R.id.Artist);
            main_item = (LinearLayout) itemView.findViewById(R.id.Item_music);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context.getContext(), Music.class);
            intent.putExtra("ID", data.get(getPosition()).getId());
            intent.putExtra("EN", data.get(getPosition()).getEn());
            intent.putExtra("FA", data.get(getPosition()).getFa());
            intent.putExtra("FAV", data.get(getPosition()).getFav());
            intent.putExtra("ARTIST", data.get(getPosition()).getArtist());
            intent.putExtra("MUSICNAME", data.get(getPosition()).getMusicName());
            intent.putExtra("URL", data.get(getPosition()).getUrl());
            intent.putExtra("USERNAME", data.get(getPosition()).getUsername());

            context.getContext().startActivity(intent);

            ((Activity) context.getContext()).finish();
        }
    }
}
