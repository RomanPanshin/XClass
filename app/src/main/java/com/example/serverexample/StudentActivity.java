package com.example.serverexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
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
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class StudentActivity extends AppCompatActivity {
    HashMap<String, String> schedule;
    JSONObject structure;
    TextView textView, mon, tue, wed,thu, fri, sat, sun, noLes;
    ListView lv;
    String id="", name="", teacher="", idclass="", number="", weekday_name="", data = "";
    ArrayList<HashMap<String, String>> scheduleList;
    ImageView imageView;
    private static String jsonurl ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        textView = findViewById(R.id.check);
        noLes  = findViewById(R.id.textView13);

        mon = findViewById(R.id.textViewMonday);
        tue = findViewById(R.id.textViewTuesday);
        wed = findViewById(R.id.textViewWednesday);
        thu = findViewById(R.id.textViewThursday);
        fri = findViewById(R.id.textViewFriday);
        sat = findViewById(R.id.textViewSaturday);
        sun = findViewById(R.id.textViewSunday);

        imageView = findViewById(R.id.noLessons);
        lv = findViewById(R.id.listView);
        textView.setText(Person.name + "\n" + Person.idclass.replace('_', ' '));

        scheduleList = new ArrayList<>();
        weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
        jsonurl = "http://borovik.fun:8080/lessons/getSchedule?classId=" + Person.idclass + "&dayOfWeek=" + weekday_name;

       if(!weekday_name.equals("Saturday") && !weekday_name.equals("Sunday")){
        GetShedule getShedule = new GetShedule();
        getShedule.execute();
       }else{
           lv.setEnabled(false);
            imageView.setVisibility(View.VISIBLE);
            noLes.setVisibility(View.VISIBLE);
       }

        switch (weekday_name){
            case ("Monday"): mon.setBackgroundResource(R.drawable.blueround); break;
            case ("Tuesday"): tue.setBackgroundResource(R.drawable.blueround); break;
            case ("Wednesday"): wed.setBackgroundResource(R.drawable.blueround); break;
            case ("Thursday"): thu.setBackgroundResource(R.drawable.blueround); break;
            case ("Friday"): fri.setBackgroundResource(R.drawable.blueround); break;
            case ("Saturday"): sat.setBackgroundResource(R.drawable.blueround); break;
            case ("Sunday"): sun.setBackgroundResource(R.drawable.blueround); break;
        }
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
                    schedule = new HashMap<>();
                    schedule.put("LessonName", name);
                    schedule.put("TeacherName", teacher);
                    schedule.put("numLesson", number);
                    schedule.put("idLesson", idclass);
                    scheduleList.add(schedule);

                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final ListAdapter adapter = new SimpleAdapter( StudentActivity.this,
                scheduleList,
                R.layout.list,
                new String[]{"LessonName", "TeacherName", "numLesson"},
                new int[] {R.id.listView1, R.id.listView9, R.id.textView5});

                lv.setAdapter(adapter);

                lv.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                HashMap<String,String>  value =  (HashMap<String,String>) parent.getItemAtPosition(position);
                Intent intent = new Intent(StudentActivity.this, HomeworkActivity.class);
                intent.putExtra("lesson", value.get("LessonName"));
                intent.putExtra("lessonId", value.get("idLesson"));
                startActivity(intent);
            }
        });
    }
}

}