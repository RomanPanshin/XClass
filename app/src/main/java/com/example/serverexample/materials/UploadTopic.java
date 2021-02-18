package com.example.serverexample.materials;

import android.os.AsyncTask;

import com.example.serverexample.HomeworkActivity;
import com.example.serverexample.MultipartUtility;
import com.example.serverexample.UploadMaterials;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadTopic extends AsyncTask<String, Void, String> {
    public String pathString;
    final String LOG_TAG = "myLogs";
    @Override
    protected String doInBackground(String... strings) {
        try {

            String upLoadServerUriWithFile = "http://borovik.fun:8080/lesson/topic/upload";


            try {

                MultipartUtility multipart = new MultipartUtility(upLoadServerUriWithFile, "UTF-8");

                    MediaType MEDIA_TYPE = MediaType.parse(HomeworkActivity.mimeType);
                    RequestBody requestBody=new MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart("lessonId", HomeworkActivity.id)
                            .addFormDataPart("simpleDate",HomeworkActivity.simpleDate)

                            .addFormDataPart("topic",HomeworkActivity.topic)
                            .build();
                    System.out.println(requestBody.toString());
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
