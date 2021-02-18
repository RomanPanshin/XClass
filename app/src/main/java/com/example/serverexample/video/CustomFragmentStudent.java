package com.example.serverexample.video;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.serverexample.Person;
import com.example.serverexample.R;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CustomFragmentStudent extends DialogFragment {

    String  uID="", simpleDate="", classId="",  jsonurl2="", result="", data="";
    public  static String  token="", lessonID="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
         lessonID = getArguments().getString("lessonID");

            uID = Person.uId;



            lessonID = "38eb3cbc-ec5e-4cbc-92db-4733fa16a525";
            jsonurl2 = "http://borovik.fun:8080/twilio/getAccessToken?UID=" + uID + "&lessonId=" + lessonID;

            System.out.println(lessonID + " " + uID + " " + simpleDate + " " + classId);

           // CustomFragmentStudent.GetAccessToken getAccessToken = new CustomFragmentStudent.GetAccessToken();
           // getAccessToken.execute();
        }

    }


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder.setTitle("Урок")
                .setIcon(R.drawable.videocall2)
                .setMessage("Начать видео-урок?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImN0eSI6InR3aWxpby1mcGE7dj0xIn0.eyJqdGkiOiJTSzlkNDljZGI1Y2FjYTUxMzM1YTVkYTQzMDMzODY4MmM3LTE2MTM2NjM5NDQiLCJpc3MiOiJTSzlkNDljZGI1Y2FjYTUxMzM1YTVkYTQzMDMzODY4MmM3Iiwic3ViIjoiQUNhOTQ4OWU4OWJlYTJmZDZhMTQzYjY0ZGVmMWNmMDFmNyIsImV4cCI6MTYxMzY2NzU0NCwiZ3JhbnRzIjp7ImlkZW50aXR5IjoibjdreGxTRkdwcWVaajJlOExtUEl1eDNMbkhlMiIsInZpZGVvIjp7InJvb20iOiIzOGViM2NiYy1lYzVlLTRjYmMtOTJkYi00NzMzZmExNmE1MjUifX19.9jhIbdF_aBafOHS-pYghqYwy-zsngopaHFEBFNZK768";
                        if(token!=""){
                            Intent intent = new Intent(getContext(), VideoActivityStudent.class);
                            startActivity(intent);}

                    }
                })
                .setNegativeButton("Нет", null)
                .create();
    }



    public class GetAccessToken extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            System.out.println(jsonurl2);
            try {
                URL url;
                HttpURLConnection httpURLConnection = null;
                try {
                    url = new URL(jsonurl2);
                    httpURLConnection = (HttpURLConnection) url.openConnection();


                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    while(current != null){
                        current = bufferedReader.readLine();
                        data = data + current;
                    }
                    return data;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    if(httpURLConnection !=null )
                        httpURLConnection.disconnect();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return current;
        }
        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                if(jsonObject.optString("code").contentEquals("Success")){

                    token = jsonObject.optString("result");
                    System.out.println(token);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



    }}