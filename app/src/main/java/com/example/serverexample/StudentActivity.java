package com.example.serverexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.view.LayoutInflater;
import android.view.View;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class StudentActivity extends Fragment {
    HashMap<String, String> schedule;
    JSONObject structure;
    TextView textView, mon, tue, wed,thu, fri, sat, sun, noLes;
    ListView lv;
    String id="", name="", teacher="", idclass="", number="", weekday_name="", data = "";
    ArrayList<HashMap<String, String>> scheduleList;
    ImageView imageView;
    private static String jsonurl ;

    public StudentActivity() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.activity_student, container, false);

        textView = v.findViewById(R.id.check);
        noLes  = v.findViewById(R.id.textView13);

        mon = v.findViewById(R.id.textViewMonday);
        tue = v.findViewById(R.id.textViewTuesday);
        wed = v.findViewById(R.id.textViewWednesday);
        thu = v.findViewById(R.id.textViewThursday);
        fri = v.findViewById(R.id.textViewFriday);
        sat = v.findViewById(R.id.textViewSaturday);
        sun = v.findViewById(R.id.textViewSunday);

        imageView = v.findViewById(R.id.noLessons);
        lv = v.findViewById(R.id.listView);
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
       return v;
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

        final ListAdapter adapter = new SimpleAdapter( getActivity(),
                scheduleList,
                R.layout.list,
                new String[]{"LessonName", "TeacherName", "numLesson"},
                new int[] {R.id.listView1, R.id.listView9, R.id.textView5});

                lv.setAdapter(adapter);

               lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                HashMap<String,String>  value =  (HashMap<String,String>) parent.getItemAtPosition(position);


                    GettingExerciseWithUploadingHW fragment = new GettingExerciseWithUploadingHW();
                    Bundle args = new Bundle();
                    args.putString("lessonId", value.get("idLesson"));
                    fragment.setArguments(args);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.container2, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
            }
        });
    }
}

}