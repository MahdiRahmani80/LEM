package com.mahdirahmani8.learnenglishwithmusicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mahdirahmani8.learnenglishwithmusicapp.API.APIClient;
import com.mahdirahmani8.learnenglishwithmusicapp.API.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Report extends AppCompatActivity {

    EditText report,user_name;
    Button btn;
    int id ;

    // API
    APIInterface request;
    String URL_link = "http://learnenmusic.pythonanywhere.com/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        cast();
        id = getIntent().getExtras().getInt("ID");

        request = APIClient.getAPIListClient(URL_link).create(APIInterface.class);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<com.mahdirahmani8.learnenglishwithmusicapp.Model.Report> call = request.report(
                        user_name.getText().toString(),
                        report.getText().toString(),
                        id);

                if (report.getText().toString().trim() != ""){

                    call.enqueue(new Callback<com.mahdirahmani8.learnenglishwithmusicapp.Model.Report>() {
                        @Override
                        public void onResponse(Call call, Response response) {

                            report.setText("");
                            user_name.setText("");
                            Toast.makeText(Report.this,"با تشکر. در سریع ترین زمان ممکن پیگیری خواهیم کرد",Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {

                        }
                    });

                }


            }
        });


    }

    private void cast() {

        report = (EditText) findViewById(R.id.Report);
        user_name = (EditText) findViewById(R.id.Name);
        btn = (Button) findViewById(R.id.btn_Report);
    }
}