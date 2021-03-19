package com.example.serverexample.video;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.SSLCertificateSocketFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.serverexample.Person;
import com.example.serverexample.R;
import com.example.serverexample.exerciseorhomework.MySSLSocketFactory;

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
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import static com.example.serverexample.exerciseorhomework.FetchingResponse.getNewHttpClient;

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

            lessonID = "c34f267d-77ce-48d2-b16e-c10ca2a8afef";

                jsonurl = "https://borovik.fun/twilio/lesson/start";
                jsonurl2 = "https://borovik.fun/token/?identity=" + "xxx" + "&room=" + lessonID;

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

                        if(token!="" && token!=null){
                       Intent intent = new Intent(getContext(), VideoActivity.class);
                        startActivity(intent);
                        }

                    }
                })
                .setNegativeButton("Нет", null)
                .create();
    }


    public class GetStart extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            HttpClient httpclient = getNewHttpClient();




            try {
                ResponseHandler<String> res = new BasicResponseHandler();
                HttpPost httppost = new HttpPost(jsonurl);

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

                    if (httpURLConnection instanceof HttpsURLConnection) {
                        HttpsURLConnection httpsConn = (HttpsURLConnection) httpURLConnection;
                        httpsConn.setSSLSocketFactory(SSLCertificateSocketFactory.getInsecure(0, null));
                        httpsConn.setHostnameVerifier(new AllowAllHostnameVerifier());
                    }
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


                        current = bufferedReader.readLine();
                        data = current;

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

               System.out.println(s);
               token = s;

        }



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