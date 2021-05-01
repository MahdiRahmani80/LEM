package com.mahdirahmani8.learnenglishwithmusicapp.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mahdirahmani8.learnenglishwithmusicapp.Model.show;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {

    private Context context;

    public DataBase(Context context) {
        super(context, info_DB.DATABASE_NAME, null, info_DB.DATABASE_VERSION);
        this.context = context;
        isDataBase();
    }

    private void isDataBase() {
        File check = new File(info_DB.PACKAGE);
        if (check.exists()) {

        } else {
            check.mkdirs();
        }

        check = context.getDatabasePath(info_DB.DATABASE_NAME);
        if (check.exists()) {

        } else {

            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void copyDataBase() throws IOException {

        InputStream myInput = null;
        myInput = context.getAssets().open(info_DB.DATABASE_SOURCE);

        String outFileName = info_DB.PACKAGE + info_DB.DATABASE_NAME;

        OutputStream myOutput = null;
        myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    // SET SETTING
    // SET LOG
    // SET FAV LIKE

    int value = 0;
    int status = 0;

    public int fav_status(int id, String url, String fa, String en, String artist, String musicName) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM fav WHERE id = " + id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            value = (int) cursor.getInt(cursor.getColumnIndex(info_DB.FAV_ID));
            status = (int) cursor.getInt(cursor.getColumnIndex(info_DB.FAV_STATUS));
            url = (String) cursor.getString(cursor.getColumnIndex(info_DB.FAV_URL));
            fa = cursor.getString(cursor.getColumnIndex(info_DB.FAV_FA));
            en = cursor.getString(cursor.getColumnIndex(info_DB.FAV_EN));
            artist = cursor.getString(cursor.getColumnIndex(info_DB.FAV_ARTIST));
            musicName =cursor.getString(cursor.getColumnIndex(info_DB.FAV_NAME));

            do {

            } while (cursor.moveToNext());
        }


        int r = likeOrDislike(id, status, url, fa, en, artist, musicName);
        cursor.close();
        db.close();
        return r;

    }

    private int likeOrDislike(int id, int status, String url, String fa, String en, String artist, String musicName) {

        String query;
        SQLiteDatabase db = this.getReadableDatabase();

        if (status != 0) {
            query = "  UPDATE 'Fav' SET " + info_DB.FAV_STATUS + " = " + 0 + " WHERE " + info_DB.FAV_ID + "=" + id +
                    "; UPDATE 'Fav' SET " + info_DB.FAV_URL + " = " + url + " WHERE " + info_DB.FAV_ID + " = " + id +
                    "; UPDATE 'Fav' SET " + info_DB.FAV_FA + " = " + fa + " WHERE " + info_DB.FAV_ID + " = " + id +
                    "; UPDATE 'Fav' SET " + info_DB.FAV_EN + " = " + en + " WHERE " + info_DB.FAV_ID + " = " + id +
                    "; UPDATE 'Fav' SET " + info_DB.FAV_ARTIST + " = " + artist + " WHERE " + info_DB.FAV_ID + " = " + id +
                    "; UPDATE 'Fav' SET " + info_DB.FAV_NAME + " = " + musicName + " WHERE " + info_DB.FAV_ID + " = " + id;
            db.execSQL(query);
            db.close();
            return 0;

        } else {

            query = "  UPDATE 'Fav' SET " + info_DB.FAV_STATUS + " = " + 1 + " WHERE " + info_DB.FAV_ID + "=" + id +
                    "; UPDATE 'Fav' SET " + info_DB.FAV_URL + " = " + url + " WHERE " + info_DB.FAV_ID + " = " + id +
                    "; UPDATE 'Fav' SET " + info_DB.FAV_FA + " = " + fa + " WHERE " + info_DB.FAV_ID + " = " + id +
                    "; UPDATE 'Fav' SET " + info_DB.FAV_EN + " = " + en + " WHERE " + info_DB.FAV_ID + " = " + id +
                    "; UPDATE 'Fav' SET " + info_DB.FAV_ARTIST + " = " + artist + " WHERE " + info_DB.FAV_ID + " = " + id +
                    "; UPDATE 'Fav' SET " + info_DB.FAV_NAME + " = " + musicName + " WHERE " + info_DB.FAV_ID + " = " + id;
            db.execSQL(query);

            db.close();
            return 1;
        }


    }

    public boolean isLiked(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM fav WHERE id = " + id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            value = (int) cursor.getInt(cursor.getColumnIndex(info_DB.FAV_ID));
            status = (int) cursor.getInt(cursor.getColumnIndex(info_DB.FAV_STATUS));

            do {

            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();

        if (status == 1) {
            return true;
        } else {
            return false;
        }

    }


    public List<show> showFav() {

        SQLiteDatabase db = this.getReadableDatabase();
        List<show> data = new ArrayList<>();

        String query = "SELECT * FROM 'fav' WHERE fav = 1";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {

                show sh = new show();

                sh.setUsername("USER");
                sh.setMusicName(cursor.getString(cursor.getColumnIndex(info_DB.FAV_NAME)));
                sh.setArtist(cursor.getString(cursor.getColumnIndex(info_DB.FAV_ARTIST)));
                sh.setUrl(cursor.getString(cursor.getColumnIndex(info_DB.FAV_URL)));
                sh.setEn(cursor.getString(cursor.getColumnIndex(info_DB.FAV_EN)));
                sh.setFa(cursor.getString(cursor.getColumnIndex(info_DB.FAV_FA)));
                sh.setFav(cursor.getInt(cursor.getColumnIndex(info_DB.FAV_STATUS)));
                sh.setId(cursor.getInt(cursor.getColumnIndex(info_DB.FAV_ID)));

                data.add(sh);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return data;

    }


    // Login Status
    public int loginStatus(){

        int loginStatus = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM 'login' WHERE id=1;";

        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToNext()){

            do {

                loginStatus = cursor.getInt(cursor.getColumnIndex(info_DB.IS_LOGIN));

            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();

        return loginStatus;
    }


    // Login
    public boolean login(){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE 'login' SET "+ info_DB.IS_LOGIN +"=1 WHERE "+info_DB.LOGIN_ID+"=1;";
        db.execSQL(query);

        db.close();
        return true;
    }


}


















