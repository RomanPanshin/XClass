package com.example.serverexample.exerciseorhomework;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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


public class hw_teacher_ extends Fragment {

    String dateStr="", exerciseID="", name="";
    HashMap<String, String> schedule1;
    String id="", data1="", data = "",data3="", uid="", uid2="", filename="", fileURL="", description="";
    private static String jsonurl, jsonurl2, jsonurl3;
    JSONObject structure;
    ArrayList<HashMap<String, String>> scheduleList1;
    ListView lv1;
    ArrayList<String> students,students2 ;
    HashMap<String, Integer> hashMap;
    TextView nohw;
    Button ask, see;
    ImageView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString("idLesson");
            dateStr = getArguments().getString("date");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_homework_teacher, container, false);
        ask = v.findViewById(R.id.ask);
        see = v.findViewById(R.id.seehomework);
        imageView = v.findViewById(R.id.imageView4);

        ask.setVisibility(View.INVISIBLE);
        see.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.INVISIBLE);
nohw = v.findViewById(R.id.textViewNoHW);
       jsonurl = "http://borovik.fun:8080/GetExerciseBySimpleDateAndLessonId?simpleDate=" + dateStr + "&lessonId=" + id;
        //jsonurl = "http://borovik.fun:8080/GetExerciseBySimpleDateAndLessonId?simpleDate=21.01.2021&lessonId=701b0192-bc36-410f-b911-c27de261397e";
System.out.println(jsonurl);
        scheduleList1 = new ArrayList<>();
        hashMap = new HashMap<>();
        lv1 = v.findViewById(R.id.listviewforteacherschedule);
        lv1.setVisibility(View.VISIBLE);
        hw_teacher_.GetSheduleTeacherClasses getShedule = new  hw_teacher_.GetSheduleTeacherClasses();
        getShedule.execute();
        return  v;
    }

    class GetSheduleTeacherClasses extends AsyncTask<String, String, String> {

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

                    JSONObject result = jsonObject.getJSONObject("result") ;
                    exerciseID = result.optString ("id");
                    System.out.println(exerciseID);

                     jsonurl2 ="http://borovik.fun:8080/homework/getByExerciseId?exerciseId=" + exerciseID;
                    //jsonurl2 ="http://borovik.fun:8080/homework/getByExerciseId?exerciseId=2b065ca7-6dc0-4ea7-bb68-a0b46d2baaac";

                    hw_teacher_.GetHWFromUsers getShedule = new  hw_teacher_.GetHWFromUsers();
                    getShedule.execute();

                    }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }}

    class GetHWFromUsers extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            System.out.println(jsonurl2);
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
                        data1 = data1 + current;
                    }
                    return data1;

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



                        name = jsonObject1.getString("name");
                        filename= jsonObject1.getString("filename");
                        fileURL= jsonObject1.getString("fileURL");
                        description= jsonObject1.getString("description");


                        students  = new ArrayList<String>();
                        students.add(uid);
                        schedule1 = new HashMap<>();
                        schedule1.put("Name",name);
                        schedule1.put("filename",filename);
                        schedule1.put("fileURL",fileURL);
                        schedule1.put("description",description);
                        scheduleList1.add(schedule1);

                    }


                }   if(jsonObject.optString("code").contentEquals("Error")){
                    nohw.setVisibility(View.VISIBLE);
                    lv1.setVisibility(View.INVISIBLE);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ListAdapter adapter = new SimpleAdapter( getActivity(),
                    scheduleList1,
                    R.layout.liststudents,
                    new String[]{"Name","filename", "fileURL", "description"},
                    new int[] {R.id.listViewName, R.id.textView8, R.id.textView9, R.id.textView10});

            lv1.setAdapter(adapter);

            lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    HashMap<String,String>  value =  (HashMap<String,String>) parent.getItemAtPosition(position);

                    ChekingHW fragment = new ChekingHW();
                    Bundle args = new Bundle();
                    args.putString("Name", value.get("Name"));
                    args.putString("filename", value.get("filename"));
                    args.putString("fileURL", value.get("fileURL"));
                    args.putString("description", value.get("description"));
                    fragment.setArguments(args);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.container1, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });

        }}


}
