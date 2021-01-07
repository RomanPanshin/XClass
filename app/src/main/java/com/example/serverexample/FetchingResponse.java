package com.example.serverexample;


import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;


import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class FetchingResponse extends AsyncTask<String, Void, String> {
   public static String data="",emailTxt="", passwordTxt="";
    public String response, resutltext="";

    JSONObject structure;
    private static final String url = "http://borovik.fun:8080/auth";
    @Override
    protected String doInBackground(String... strings) {

        HttpClient httpclient = new DefaultHttpClient();
        ResponseHandler<String> res = new BasicResponseHandler();
        HttpPost httppost = new HttpPost(url);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("email", emailTxt));
        nameValuePairs.add(new BasicNameValuePair("password", passwordTxt));

        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost, res);
            System.out.println(response);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject object = null;
        try {
            object = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(object.optString("code").contentEquals("Success")){

            try {
               structure = (JSONObject) object.get("result");
                Person.name = structure.get("name").toString();
                byte []bytes = Person.name.getBytes("ISO-8859-1");
                String value = new String(bytes, "UTF-8");
                Person.name = value;

                Person.key = structure.get("key").toString();
                Person.claim = structure.get("claim").toString();
                Person.uId = structure.get("uId").toString();

                Person.idclass = structure.get("classId").toString();
                byte []bytes2 = Person.idclass.getBytes("ISO-8859-1");
                String value2 = new String(bytes2, "UTF-8");
                Person.idclass = value2;


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            MainActivity.claim = Person.claim;
            return "true";
        }else{
            return "false";
        }

    }
    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);

    }

   /* public String toRussian(String str){
         byte bytes[];
        String value;
        try {
             bytes = str.getBytes("ISO-8859-1");
             value = new String(bytes, "UTF-8");
            return value;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }*/
}





