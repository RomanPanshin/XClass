package com.example.serverexample;


import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchingData extends AsyncTask<Void, Void, Void> {
    String data = "";
    String parsedData = "";
    private static final String adress = "https://borovik.fun/auth";
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL(adress);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }
            JSONObject object = new JSONObject(data);
            parsedData = object.optString("name")+ "\n" + object.optString("password");

           /* JSONArray jsonArray = object.getJSONArray("name");
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                singleData = "Name:" + jsonObject.get("name") + "\n" + "Password" + jsonObject.get("password") + "\n";

                parsedData = parsedData + singleData;
            }*/

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        MainActivity.data.setText(this.parsedData);
    }
}
