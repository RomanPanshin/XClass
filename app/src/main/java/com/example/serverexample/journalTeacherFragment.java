package com.example.serverexample;

import android.content.Context;
import android.net.SSLCertificateSocketFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import com.example.serverexample.video.CustomDialogFragment;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
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
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;


public class journalTeacherFragment extends Fragment {

Context cn;
    HashMap<String, String> schedule;
    JSONObject structure;
    TextView textView, mon, tue, wed,thu, fri, sat, sun, noLes, textMonth;
    ListView lv;
    String id="", name="", teacher="", idclass="", number="", weekday_name="", data = "";
    ArrayList<HashMap<String, String>> scheduleList;
    ImageView imageView;
    private static String jsonurl ;
    Button left, right;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_journal_teacher, container, false);

        textView = v.findViewById(R.id.check);
        noLes  = v.findViewById(R.id.textView13);

cn = v.getContext();

        mon = v.findViewById(R.id.textViewMonday);
        tue = v.findViewById(R.id.textViewTuesday);
        wed = v.findViewById(R.id.textViewWednesday);
        thu = v.findViewById(R.id.textViewThursday);
        fri = v.findViewById(R.id.textViewFriday);
        sat = v.findViewById(R.id.textViewSaturday);
        sun = v.findViewById(R.id.textViewSunday);

        right = v.findViewById(R.id.buttonRight);
        left = v.findViewById(R.id.but_left);

        imageView = v.findViewById(R.id.noLessons);
        lv = v.findViewById(R.id.listView2);
        textView.setText(Person.name);

        Calendar calendar = Calendar.getInstance();
        String month = calendar.getDisplayName(Calendar.MONTH,
                Calendar.LONG_FORMAT, new Locale("ru"));



        scheduleList = new ArrayList<>();
        weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
        jsonurl = "https://borovik.fun/lessons/getSchedule/teacher?teacherId=" + Person.uId + "&dayOfWeek=" + weekday_name;

        System.out.println(jsonurl);

        if(!weekday_name.equals("Saturday") && !weekday_name.equals("Sunday")){
            journalTeacherFragment.GetShedule getShedule = new journalTeacherFragment.GetShedule();
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


    public class GetShedule extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;

                try {
                    url = new URL(jsonurl);
                    URLConnection httpURLConnection = url.openConnection();
                    if (httpURLConnection instanceof HttpsURLConnection) {
                        HttpsURLConnection httpsConn = (HttpsURLConnection) httpURLConnection;
                        httpsConn.setSSLSocketFactory(SSLCertificateSocketFactory.getInsecure(0, null));
                        httpsConn.setHostnameVerifier(new AllowAllHostnameVerifier());
                    }


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



                        schedule = new HashMap<>();
                        schedule.put("LessonName", name);
                        schedule.put("id", id);
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
                    R.layout.scheduleteacherlist,
                    new String[]{"LessonName", "idLesson", "numLesson", "id"},
                    new int[] {R.id.listView10, R.id.listView11, R.id.textView12, R.id.textViewID});

            lv.setAdapter(adapter);


           lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    HashMap<String,String>  value =  (HashMap<String,String>) parent.getItemAtPosition(position);

                   /* HomeworkActivity fragment = new HomeworkActivity();
                    Bundle args = new Bundle();
                    args.putString("id", value.get("id"));
                    fragment.setArguments(args);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.container1, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();*/
                    CustomDialogFragment dialog = new CustomDialogFragment();

                  Bundle args = new Bundle();
                  args.putString("lessonID", value.get("id"));
                    args.putString("idLesson", value.get("idLesson"));



                    dialog.setArguments(args);

                    dialog.show(getActivity().getSupportFragmentManager(), "custom");
                }
            });


        }
        }}

