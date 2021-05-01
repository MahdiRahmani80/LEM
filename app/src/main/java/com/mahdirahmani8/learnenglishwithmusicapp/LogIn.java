package com.mahdirahmani8.learnenglishwithmusicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LogIn extends AppCompatActivity {

    TextView txt ;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        txt = (TextView) findViewById(R.id.text);
        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LogIn.this, MainActivity.class);
                intent.putExtra("NAME","");
                startActivity(intent);
                finish();
            }
        });


        txt.setText("سلام\n" +
                "هدف این برنامه یادگیری زبان انگیسی با استفاده از موسیقی هست.\n" +
                "شما علاوه بر گوش دادن موسیقی های انگلیسی و دیدن متن و ترجمه آن ها \n" +
                "میتوانید موسیقی هایی که دوست دارید به برنامه اضافه کنید و همه آن را ببیند و\n" +
                "لایک کنند . و جالب تر این جاست که دیگران هم میتوانند آن را ویرایش کنند.\n" +
                "امیدوارم از برنامه لذت ببرید.");

    }
}