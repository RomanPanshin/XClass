package com.example.serverexample;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;



public class MainActivity extends Activity {
    public Button connectButton, postButton;
   public static TextView data, jj;
   public EditText emailTxt, passwordTxt;
    public static String request="", claim="";
    public  String emailString="", passwordString="";
    static Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

       // connectButton =  findViewById(R.id.connect);
        postButton =  findViewById(R.id.postbutton);
        emailTxt =  findViewById(R.id.email);
        passwordTxt =  findViewById(R.id.password);
        jj = findViewById(R.id.textView2);
        context = getApplicationContext();
        
       /* connectButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            FetchingData process = new FetchingData();
            process.execute();
            }
        });
*/
        postButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            emailString = emailTxt.getText().toString();
            passwordString = passwordTxt.getText().toString();
            if(emailString.isEmpty()) {
                emailTxt.setError("Введите вашу почту");
                emailTxt.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
                emailTxt.setError("Введите корректную почту");
                emailTxt.requestFocus();
                    return;
                }

            FetchingResponse.emailTxt = emailString;
            FetchingResponse.passwordTxt = passwordString;
                FetchingResponse process = new FetchingResponse();
                /*process.execute();
               if(process.getStatus() == AsyncTask.Status.FINISHED){
                    System.out.println(request);
                    Intent intent = new Intent(MainActivity.this, TeacherActivity.class);
                    startActivity(intent);
                }*/
                try {
                    request= process.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(request.equals("true") && claim.equals("teacher")){
                    Intent intent = new Intent(MainActivity.this, TeacherActivity.class);
                    startActivity(intent);
                }

                if(request.equals("true") && claim.equals("student")){
                    Intent intent = new Intent(MainActivity.this, StudentActivity.class);
                    startActivity(intent);
                }

                if(request.equals("false")){
                    Toast.makeText(getApplicationContext(), "Вы не авторизированы", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
