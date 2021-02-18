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

public class CustomDialogFragment extends DialogFragment {

String  uID="", simpleDate="", classId="", jsonurl="", jsonurl2="", result="", data="";
public  static String  token="", lessonID="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            lessonID = getArguments().getString("lessonID");
            classId = getArguments().getString("idLesson");


            uID = Person.uId;

            Date currentDate = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
           simpleDate = dateFormat.format(currentDate);


                jsonurl = "http://borovik.fun:8080/twilio/lesson/start";
                jsonurl2 = "http://borovik.fun:8080/twilio/getAccessToken?UID=" + uID + "&lessonId=" + lessonID;

            System.out.println(lessonID + " " + uID + " " + simpleDate + " " + classId);

            CustomDialogFragment.GetAccessToken getAccessToken = new CustomDialogFragment.GetAccessToken();
            getAccessToken.execute();
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
                       GetStart getStart = new GetStart();
                        getStart.execute();

token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImN0eSI6InR3aWxpby1mcGE7dj0xIn0.eyJqdGkiOiJTSzlkNDljZGI1Y2FjYTUxMzM1YTVkYTQzMDMzODY4MmM3LTE2MTM2NjQ2NjYiLCJpc3MiOiJTSzlkNDljZGI1Y2FjYTUxMzM1YTVkYTQzMDMzODY4MmM3Iiwic3ViIjoiQUNhOTQ4OWU4OWJlYTJmZDZhMTQzYjY0ZGVmMWNmMDFmNyIsImV4cCI6MTYxMzY2ODI2NiwiZ3JhbnRzIjp7ImlkZW50aXR5IjoiMEFjekZpVjVVZFFoZjNFWUpCTTZSMmI1VjNoMiIsInZpZGVvIjp7InJvb20iOiIzOGViM2NiYy1lYzVlLTRjYmMtOTJkYi00NzMzZmExNmE1MjUifX19.tv831ds92fwhNZCOLxCb6JYyh-gg_2prt2r2UuM31SY";

                        if(token!=""){
                        Intent intent = new Intent(getContext(), VideoActivity.class);
                        startActivity(intent);}

                    }
                })
                .setNegativeButton("Нет", null)
                .create();
    }


    public class GetStart extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpClient httpclient = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost httppost = new HttpPost(jsonurl);


            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("lessonId", lessonID));
                nameValuePairs.add(new BasicNameValuePair("UID", uID));
                nameValuePairs.add(new BasicNameValuePair("simpleDate", simpleDate));
                nameValuePairs.add(new BasicNameValuePair("classId", URLEncoder.encode(classId, "UTF-8")));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                result = httpclient.execute(httppost, res);
                System.out.println(result);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
            }
        @Override
        protected void onPostExecute(String s) {

        }
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