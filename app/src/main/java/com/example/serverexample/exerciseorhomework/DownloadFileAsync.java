package com.example.serverexample.exerciseorhomework;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import java.net.URL;


public class DownloadFileAsync extends AsyncTask<String, Integer, String> {

    Context mContext;
    String url;

    public DownloadFileAsync(Context mContext, String url) {
        this.mContext = mContext;
        this.url = url;
    }


    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... f_url) {
        DownloadManager downloadmanager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle("Домашняя работа");
        request.setDescription("Downloading");//request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"game-of-life");
        downloadmanager.enqueue(request);
        return null;
    }

    protected void onProgressUpdate(Integer... progress) {

    }

    @Override
    protected void onPostExecute(String file_url) {

    } }