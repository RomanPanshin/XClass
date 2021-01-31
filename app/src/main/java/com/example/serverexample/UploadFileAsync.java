package com.example.serverexample;


import android.os.AsyncTask;



import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class UploadFileAsync extends AsyncTask<String, Void, String> {
    public String response1;
    @Override
    protected String doInBackground(String... strings) {
        try {


            Date currentDate = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            String dateText = dateFormat.format(currentDate);



                try {
                    String upLoadServerUriWithFile = "http://borovik.fun:8080/UploadExerciseWithFile";
                    String upLoadServerUriWithoutFile = "http://borovik.fun:8080/UploadExercise";

                    if(HomeworkActivity.pathStr != null){
                    RequestBody requestBody=new MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart("file", HomeworkActivity.pathStr, RequestBody.create(MediaType.parse("image/*"), HomeworkActivity.pathStr))
                            .addFormDataPart("lessonId",HomeworkActivity.lessonID)
                            .addFormDataPart("simpleDate",dateText)
                            .addFormDataPart("description",HomeworkActivity.description)
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
                                .addFormDataPart("lessonId",HomeworkActivity.lessonID)
                                .addFormDataPart("simpleDate",dateText)
                                .addFormDataPart("description",HomeworkActivity.description)
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
