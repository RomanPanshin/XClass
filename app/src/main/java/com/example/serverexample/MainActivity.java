package com.example.serverexample;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {
    public Button connectButton;
   public static TextView data;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectButton =  findViewById(R.id.connect);
        data =  findViewById(R.id.data);

        connectButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            FetchingData process = new FetchingData();
            process.execute();
            }
        });
    }
}
