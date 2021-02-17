package com.example.serverexample.exerciseteacher;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.serverexample.HomeworkActivity;
import com.example.serverexample.Person;
import com.example.serverexample.R;

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


public class homework_classes_teacher extends Fragment {
    String dateStr="";
    HashMap<String, String> schedule1;
    String id="", name="", teacher="", idclass="", number="", weekday_name="", data = "";
    private static String jsonurl;
    JSONObject structure;
    ArrayList<HashMap<String, String>> scheduleList1;
    ListView lv1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dateStr = getArguments().getString("day_of_the_week");
        }
    }
    public homework_classes_teacher() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_homework_teacher, container, false);


        jsonurl = "http://borovik.fun:8080/lessons/getSchedule/teacher?teacherId=" + Person.uId + "&dayOfWeek=" + dateStr;
        scheduleList1 = new ArrayList<>();
        lv1 = v.findViewById(R.id.listviewforteacherschedule);
        lv1.setVisibility(View.VISIBLE);
        homework_classes_teacher.GetSheduleTeacherClasses getShedule = new  homework_classes_teacher.GetSheduleTeacherClasses();
        getShedule.execute();
        return  v;
    }

    public class GetSheduleTeacherClasses extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            System.out.println(jsonurl);
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
                        //teacher =jsonObject1.getString("teacher");
                        idclass =jsonObject1.getString("idclass");
                        structure = (JSONObject) jsonObject1.get("date");

                        number = structure.getString("numLesson");



                        schedule1 = new HashMap<>();
                        schedule1.put("LessonName", name);
                        schedule1.put("id", id);
                        schedule1.put("numLesson", number);
                        schedule1.put("idLesson", idclass);
                        scheduleList1.add(schedule1);

                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ListAdapter adapter = new SimpleAdapter( getActivity(),
                    scheduleList1,
                    R.layout.list2,
                    new String[]{"LessonName", "numLesson", "idLesson"},
                    new int[] {R.id.listView10, R.id.listView11, R.id.textView12});

            lv1.setAdapter(adapter);

            lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    HashMap<String,String>  value =  (HashMap<String,String>) parent.getItemAtPosition(position);

                    HomeworkActivity fragment = new HomeworkActivity();
                    Bundle args = new Bundle();
                    args.putString("id", value.get("id"));
                    fragment.setArguments(args);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.container1, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
        }
    });


}}}