package com.example.serverexample.video;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.serverexample.R;

public class InfoForAdmin extends AppCompatActivity {
Button button;
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_for_admin);

       button = findViewById(R.id.down);
       textView = findViewById(R.id.textViewHomeTask);

       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent browserIntent = new
                       Intent(Intent.ACTION_VIEW, Uri.parse("http://borovik.fun:8080/r/570b0e5b-bfcd-4cc5-96dc-f99b8794ecab.py"));
               startActivity(browserIntent);
           }
       });


    }
}