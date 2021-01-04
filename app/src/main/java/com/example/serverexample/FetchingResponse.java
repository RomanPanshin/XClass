package com.example.serverexample;


import android.os.AsyncTask;



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


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class FetchingResponse extends AsyncTask<Void, Void, Void> {
   public static String data="",emailTxt="", passwordTxt="";
    public String response, resutltext="";
    private static final String url = "http://borovik.fun:8080/auth";
    @Override
    protected Void doInBackground(Void... voids) {

        HttpClient httpclient = new DefaultHttpClient();
        ResponseHandler<String> res = new BasicResponseHandler();
        HttpPost httppost = new HttpPost(url);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("email", emailTxt));
        nameValuePairs.add(new BasicNameValuePair("password", passwordTxt));

        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
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
        resutltext = object.optString("name")+ "\n" + object.optString("key");
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.jj.setText(this.resutltext);
    }
}





