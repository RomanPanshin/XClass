package com.example.serverexample;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
    public Button connectButton, postButton;
   public static TextView data, jj;
   public EditText emailTxt, passwordTxt;
   public  String emailString="", passwordString="";
   public static String answer="";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectButton =  findViewById(R.id.connect);
        postButton =  findViewById(R.id.post);
        emailTxt =  findViewById(R.id.email);
        passwordTxt =  findViewById(R.id.password);
        data =  findViewById(R.id.data);
        jj =  findViewById(R.id.txt3);

        connectButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            FetchingData process = new FetchingData();
            process.execute();
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            emailString = emailTxt.getText().toString();
            passwordString = passwordTxt.getText().toString();
            FetchingResponse.emailTxt = emailString;
            FetchingResponse.passwordTxt = passwordString;
                FetchingResponse process = new FetchingResponse();
                process.execute();

             //   Toast toast = Toast.makeText(getApplicationContext(), answer, Toast.LENGTH_LONG);
                //toast.show();
            }
        });

    }
}
