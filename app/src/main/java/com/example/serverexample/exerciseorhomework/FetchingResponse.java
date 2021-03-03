package com.example.serverexample.exerciseorhomework;

import android.os.AsyncTask;



import com.example.serverexample.MainActivity;
import com.example.serverexample.Person;


import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;

import java.io.UnsupportedEncodingException;

import java.security.KeyStore;

import java.util.ArrayList;
import java.util.List;



public class FetchingResponse extends AsyncTask<String, Void, String> {
   public static String data="",emailTxt="", passwordTxt="";
    public String response, resutltext="";

    JSONObject structure;
    private static final String url = "https://borovik.fun/auth";
    @Override
    protected String doInBackground(String... strings) {


        HttpClient httpclient = getNewHttpClient();

        try {

           ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost httppost = new HttpPost("https://borovik.fun/auth");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("email", emailTxt));
            nameValuePairs.add(new BasicNameValuePair("password", passwordTxt));

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
    public static HttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }


    }





