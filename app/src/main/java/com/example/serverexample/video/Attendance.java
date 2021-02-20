package com.example.serverexample.video;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.serverexample.GettingExerciseWithUploadingHW;
import com.example.serverexample.TeacherActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.serverexample.R;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Attendance extends AppCompatActivity {

public String presentStudent="";
    JSONObject structure;
    public  static String lessonID="", jsonurl="",  result="";
    ListView lv;
Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        jsonurl = "http://borovik.fun:8080/twilio/lesson/stop?";

        lessonID = CustomDialogFragment.lessonID;
        System.out.println(lessonID);

        button = findViewById(R.id.postbutton1);

        button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(getApplication(), "Данные успешно сохранены", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Attendance.this, TeacherActivity.class);
        startActivity(intent);
    }
});

        Attendance.GetStop getStop = new Attendance.GetStop();
        getStop.execute();

    }
    public class GetStop extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {


            HttpClient httpclient = new DefaultHttpClient();
            ResponseHandler<String> res = new BasicResponseHandler();
            HttpPost httppost = new HttpPost(jsonurl);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("lessonId", lessonID));
            try {



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
try {


            JSONObject jsonObject = new JSONObject(result);
            if(jsonObject.optString("code").contentEquals("Success")) {

                JSONArray jsonArray = jsonObject.getJSONArray("result");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                    //presentStudent = jsonObject1.getString("presentStudents");

                   // System.out.println(presentStudent);

                    structure = (JSONObject) jsonObject1.get("presentStudents");


                    System.out.println(structure.toString());


                }
            }}
catch (JSONException e) {
    e.printStackTrace();
}

            return null;
      }


        @Override
        protected void onPostExecute(String s) {

        }
    }


    }