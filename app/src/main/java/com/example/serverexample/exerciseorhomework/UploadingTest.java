package com.example.serverexample.exerciseorhomework;

import android.os.AsyncTask;


import com.example.serverexample.Test;
import com.example.serverexample.TestTeacher;


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

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.security.KeyStore;

import java.util.ArrayList;
import java.util.List;


public class UploadingTest extends AsyncTask<String, Void, String> {
public static String data="",emailTxt="", passwordTxt="";
public String response, resutltext="";

        JSONObject structure;
private static final String url = "https://borovik.fun/test/UploadTest";
@Override
protected String doInBackground(String... strings) {


        HttpClient httpclient = getNewHttpClient();


        try {



                ResponseHandler<String> res = new BasicResponseHandler();
        HttpPost httppost = new HttpPost("https://borovik.fun/test/UploadTest");
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("description", "test"));
        nameValuePairs.add(new BasicNameValuePair("answers",TestTeacher.json));
        nameValuePairs.add(new BasicNameValuePair("lessonId", "00e6b0b5-8375-4881-975c-9e2353396945"));
        nameValuePairs.add(new BasicNameValuePair("date", TestTeacher.dateText));
        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        System.out.println("description=" + "test" + "&answers=" + TestTeacher.json +"&lessonId=004f47b3-49a4-4e56-afc6-0f7925230eaf&date=10.03.2021");
        response = httpclient.execute(httppost, res);

        System.out.println(response);
//encode
// System.out.println(URLDecoder.decode(" {\"code\":\"Success\",\"result\":{\"description\":\"test\",\"questions\":[{\"description\":\"%ED%ED\",\"answers\":{\"%F3%F3\":true,\"%E0%E0\":false,\"%F6%F6%F6\":false},\"id\":null}],\"lessonId\":\"00e6b0b5-8375-4881-975c-9e2353396945\",\"id\":\"603cf22c-69c8-4203-971b-de9cb7f32b1b\",\"date\":\"12.03.2021\"}}\n" , "CP1251"));
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

return "true";
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


