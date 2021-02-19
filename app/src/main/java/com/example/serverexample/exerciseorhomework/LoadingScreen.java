package com.example.serverexample.exerciseorhomework;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serverexample.MainActivity;
import com.example.serverexample.R;

public class LoadingScreen extends AppCompatActivity {
    private int sleeptimer = 5;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_activity);

        Logo logo = new Logo();
        logo.start();
    }
    private class Logo extends Thread{
        public void run(){
            try {
                sleep(1000 * sleeptimer);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }

            Intent intent = new Intent (LoadingScreen.this, MainActivity.class);
            startActivity(intent);
            LoadingScreen.this.finish();
        }
    }
}
