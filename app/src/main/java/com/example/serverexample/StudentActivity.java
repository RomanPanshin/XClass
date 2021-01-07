package com.example.serverexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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

public class StudentActivity extends AppCompatActivity {
    JSONObject structure;
    String data = "";
    TextView textView;
    ListView lv;
String id="", name="", teacher="", idclass="", number="";
    ArrayList<HashMap<String, String>> scheduleList;
    private static String jsonurl = "http://borovik.fun:8080/lessons/getSchedule?classId=" + Person.idclass + "&dayOfWeek=Monday";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        textView = findViewById(R.id.check);
        lv = findViewById(R.id.listView);
        textView.setText("Имя: "+ Person.name + "\n" + "Класс: "+ Person.idclass);

        scheduleList = new ArrayList<>();


        GetShedule getShedule = new GetShedule();
        getShedule.execute();
    }


public class GetShedule extends AsyncTask<String, String, String>{

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


                    id =jsonObject1.getString("id");
                    name =jsonObject1.getString("name");
                    teacher =jsonObject1.getString("teacher");
                    idclass =jsonObject1.getString("idclass");
                    System.out.println(teacher);
                    structure = (JSONObject) jsonObject1.get("date");

                    number = structure.getString("numLesson");
                    System.out.println(number);
                    HashMap<String, String> schedule = new HashMap<>();
                    schedule.put("LessonName", name);
                    schedule.put("TeacherName", teacher);
                    schedule.put("numLesson", number);

                    scheduleList.add(schedule);

                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter( StudentActivity.this,
                scheduleList,
                R.layout.list,
                new String[]{"LessonName", "TeacherName", "numLesson"},
                new int[] {R.id.listView1, R.id.listView9, R.id.textView5});

                lv.setAdapter(adapter);

    }
}

}