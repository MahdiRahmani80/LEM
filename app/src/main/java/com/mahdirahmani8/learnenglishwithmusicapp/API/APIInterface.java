package com.mahdirahmani8.learnenglishwithmusicapp.API;

import android.text.Editable;

import com.mahdirahmani8.learnenglishwithmusicapp.Model.Create;
import com.mahdirahmani8.learnenglishwithmusicapp.Model.Report;
import com.mahdirahmani8.learnenglishwithmusicapp.Model.show;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIInterface {

//    GET
    @GET("list")
    Call<List<show>> getListShow();


//    Create
    @POST("create/Music/")
    @FormUrlEncoded
    Call<Create> createMusic (
            @Field("username") String username ,
            @Field("url") String url,
            @Field("musicName") String musicName,
            @Field("artist") String artist,
            @Field("fa") String fa,
            @Field("en") String en,
            @Field("fav") int fav
            );

    @POST("report/")
    @FormUrlEncoded
    Call<Report> report (
            @Field("username") String name,
            @Field("reportText") String ReportText,
            @Field("ID") int id
    );


    // Edit
    @PUT("{id}/update/")
    @FormUrlEncoded
    Call<Create> edit(
            @Path("id") int id,
            @Field("username") String username ,
            @Field("url") Editable url,
            @Field("musicName") Editable musicName,
            @Field("artist") Editable artist,
            @Field("fa") Editable fa,
            @Field("en") Editable en,
            @Field("fav") int fav
    );



    // TODO : put in retrofit for fav and update activity
}
