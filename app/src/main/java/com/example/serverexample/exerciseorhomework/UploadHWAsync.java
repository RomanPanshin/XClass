package com.example.serverexample.exerciseorhomework;

import android.net.SSLCertificateSocketFactory;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.serverexample.GettingExerciseWithUploadingHW;
import com.example.serverexample.Multi;
import com.example.serverexample.MultipartUtility;
import com.example.serverexample.Person;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadHWAsync extends AsyncTask<String, Void, String> {
public String pathString;
final String LOG_TAG = "myLogs";
@Override
protected String doInBackground(String... strings) {
        try {

        String upLoadServerUriWithFile = "https://borovik.fun/homework/UploadHomeworkWithFile";
        String upLoadServerUriWithoutFile = "https://borovik.fun/homework/UploadHomework";

            pathString = GettingExerciseWithUploadingHW.pathStr;
            File file = file = new File(pathString);

            try {

                MultipartUtility multipart = new MultipartUtility(upLoadServerUriWithFile, "UTF-8");

                if(GettingExerciseWithUploadingHW.pathStr != null){
                    MediaType MEDIA_TYPE = MediaType.parse(GettingExerciseWithUploadingHW.mimeType);
                    RequestBody requestBody=new MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart("file", file.getName(),  RequestBody.create(MEDIA_TYPE, file))
                            .addFormDataPart("UID", Person.uId)
                            .addFormDataPart("description",GettingExerciseWithUploadingHW.description)
                            .addFormDataPart("exerciseId",GettingExerciseWithUploadingHW.idLESSON)

                            .build();
                    Request request=new Request.Builder()
                            .url(upLoadServerUriWithFile)
                            .post(requestBody)
                            .build();
                    final OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response);
            }

        });

        } else{
        RequestBody requestBody=new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("exerciseId",GettingExerciseWithUploadingHW.idLESSON)
                .addFormDataPart("description",GettingExerciseWithUploadingHW.description)
                .addFormDataPart("UID",Person.uId)


        .build();
        Request request=new Request.Builder()
        .url(upLoadServerUriWithoutFile)
        .post(requestBody)
        .build();
final OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
@Override
public void onFailure(Call call, IOException e) {
        e.printStackTrace();
        }

@Override
public void onResponse(Call call, Response response) throws IOException {

        System.out.println(response);
        }
        });
        }



        } catch (Exception e) {
        e.printStackTrace();
        }

        } catch (Exception e) {
        e.printStackTrace();
        }
        return "Executed";
        }

@Override
protected void onPostExecute(String result) {

        }

@Override
protected void onPreExecute() {
        }

@Override
protected void onProgressUpdate(Void... values) {
        }
        }
