package com.example.serverexample;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serverexample.video.InfoForAdmin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class AdminGetPastLessons extends AppCompatActivity {
    String jsonurl, data, idClass,  classId, id, jsonurl2, data2;
    ListView listView;
    ArrayList<HashMap<String, String>> scheduleList;
    HashMap<String, String> schedule;
View back1, back2;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_get_past_lessons);

        Bundle arguments = getIntent().getExtras();
        classId = arguments.get("classId").toString();


        back1 = findViewById(R.id.back1);
        back2 = findViewById(R.id.back2);

        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Intent intent = new Intent(AdminGetPastLessons.this, InfoForAdmin.class);
startActivity(intent);
            }
        });

        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        jsonurl =   "http://borovik.fun:8080/additional/lessonsByClass?classId=" + classId;

        listView = findViewById(R.id.listviewClassesAdmin);

        scheduleList = new ArrayList<>();

       // AdminGetPastLessons.GetClasses getClasses = new AdminGetPastLessons.GetClasses();
      //  getClasses.execute();

    }

    public class GetClasses extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
                HttpURLConnection httpURLConnection = null;
                try {
                    url = new URL(jsonurl);
                    httpURLConnection = (HttpURLConnection) url.openConnection();


                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    while(current != null){
                        current = bufferedReader.readLine();
                        data = data + current;
                    }
                    data = data.substring(4);
                    System.out.println(data);
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
            try {
                JSONObject jsonObject = new JSONObject(s);
                if(jsonObject.optString("code").contentEquals("Success")){

                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);




                        id = jsonObject1.getString("id");
                        schedule = new HashMap<>();
                        schedule.put("id", id);
                        scheduleList.add(schedule);

                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ListAdapter adapter = new SimpleAdapter( AdminGetPastLessons.this,
                    scheduleList,
                    R.layout.listid,
                    new String[]{"id"},
                    new int[] {R.id.listViewName});

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    HashMap<String,String>  value =  (HashMap<String,String>) parent.getItemAtPosition(position);

                    //Intent intent = new Intent(AdminHomeActivity.this, )
                    idClass = value.get("id");

                    AdminGetPastLessons.GetClassesPast getClassespast = new AdminGetPastLessons.GetClassesPast();
                    getClassespast.execute();
                }
            });
        }
    }
    public class GetClassesPast extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            jsonurl2 = "http://borovik.fun:8080/twilio/lesson/results/get?lessonId=" + idClass;

            try {
                URL url;
                HttpURLConnection httpURLConnection = null;
                try {
                    url = new URL(jsonurl2);
                    httpURLConnection = (HttpURLConnection) url.openConnection();


                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    while(current != null){
                        current = bufferedReader.readLine();
                        data2 = data2 + current;
                    }
                    data2 = data2.substring(4);
                    System.out.println(data2);
                    return data2;

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
            try {
                JSONObject jsonObject = new JSONObject(s);
                if(jsonObject.optString("code").contentEquals("Success")){

                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);




                        id = jsonObject1.getString("id");
                        schedule = new HashMap<>();
                        schedule.put("id", id);
                        scheduleList.add(schedule);

                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ListAdapter adapter = new SimpleAdapter( AdminGetPastLessons.this,
                    scheduleList,
                    R.layout.listclass,
                    new String[]{"id"},
                    new int[] {R.id.listViewLesson});

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    HashMap<String,String>  value =  (HashMap<String,String>) parent.getItemAtPosition(position);

                    //Intent intent = new Intent(AdminHomeActivity.this, )
                    idClass = value.get("id");

                }
            });
        }
    }


}
