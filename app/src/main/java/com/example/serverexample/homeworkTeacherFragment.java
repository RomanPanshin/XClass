package com.example.serverexample;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;

import static android.graphics.Color.BLUE;


public class homeworkTeacherFragment extends Fragment {

    CustomCalendar customCalendar, customCalendar2;
    Button askhw, seehw;
   public String date = "", day_week="";

ListView lv;
    public homeworkTeacherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_homework_teacher, container, false);
         customCalendar = v.findViewById(R.id.custom_calendar);
        customCalendar2 = v.findViewById(R.id.custom_calendar2);
        askhw = v.findViewById(R.id.ask);
        seehw = v.findViewById(R.id.seehomework);


        askhw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customCalendar.setVisibility(View.VISIBLE);
                customCalendar2.setVisibility(View.INVISIBLE);
            }
        });

        seehw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customCalendar.setVisibility(View.INVISIBLE);
                customCalendar2.setVisibility(View.VISIBLE);


            }
        });



        customCalendar.setBackgroundColor(BLUE);

        HashMap<Object, Property> descHashMap = new HashMap<>();
        Property defaultProperty = new Property();
        defaultProperty.layoutResource = R.layout.default_view_calendar;

        defaultProperty.dateTextViewResource = R.id.txtview;
        descHashMap.put("default", defaultProperty);

        Property currentProperty = new Property();
        currentProperty.layoutResource = R.layout.current_view_calendar;
        currentProperty.dateTextViewResource = R.id.txtview1;
        descHashMap.put("current", currentProperty);

        Property presentProperty = new Property();
        presentProperty.layoutResource = R.layout.present_view_calendar;
        presentProperty.dateTextViewResource = R.id.txtview2;
        descHashMap.put("present", presentProperty);

        customCalendar.setMapDescToProp(descHashMap);

        HashMap<Integer, Object> datehashmap = new HashMap<>();
        Calendar calendar = Calendar.getInstance();

        datehashmap.put(calendar.get(Calendar.DAY_OF_MONTH), "current");
        datehashmap.put(1, "present");
        datehashmap.put(2, "present");

        customCalendar.setDate(calendar, datehashmap);
        customCalendar.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(View view, Calendar selectedDate, Object desc) {

                System.out.println("JJJ");
                switch (selectedDate.get(Calendar.DAY_OF_WEEK)){

                    case 1: date = "Monday"; break;
                    case 2: date = "Tuesday"; break;
                    case 3: date = "Wednesday"; break;
                    case 4: date = "Thursday"; break;
                    case 5: date = "Friday"; break;
                    case 6: date = "Saturday"; break;
                    case 7: date = "Sunday"; break;

                }

                homework_classes_teacher fragment = new homework_classes_teacher();
                Bundle args = new Bundle();
               args.putString("day_of_the_week", date);
               //для примера будет дата 21 января
                fragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container1, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

customCalendar.setVisibility(View.INVISIBLE);

            }
        });

        HashMap<Object, Property> descHashMap2 = new HashMap<>();
        Property defaultProperty2 = new Property();


        customCalendar2.setMapDescToProp(descHashMap2);

        HashMap<Integer, Object> datehashmap2 = new HashMap<>();
        Calendar calendar2 = Calendar.getInstance();

        datehashmap.put(calendar.get(Calendar.DAY_OF_MONTH), "current");


        customCalendar2.setDate(calendar2, datehashmap2);

        customCalendar2.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(View view, Calendar selectedDate, Object desc) {

               if( selectedDate.get(Calendar.DAY_OF_MONTH)<10){
                   date = "0" + selectedDate.get(Calendar.DAY_OF_MONTH);
               }else
                   {date =  Integer.toString (selectedDate.get(Calendar.DAY_OF_MONTH));}

                if( selectedDate.get(Calendar.MONTH)<10){
                    date +=  "0" + selectedDate.get(Calendar.MONTH);
                }else {date += selectedDate.get(Calendar.MONTH);}
date+= selectedDate.get(Calendar.YEAR);

                switch (selectedDate.get(Calendar.DAY_OF_WEEK)){

                    case 1: day_week = "Monday"; break;
                    case 2: day_week = "Tuesday"; break;
                    case 3: day_week = "Wednesday"; break;
                    case 4: day_week = "Thursday"; break;
                    case 5: day_week = "Friday"; break;
                    case 6: day_week = "Saturday"; break;
                    case 7: day_week = "Sunday"; break;

                }


                homework_teacher_check fragment = new homework_teacher_check();
                Bundle args = new Bundle();
                args.putString("day_week", day_week);
                args.putString("day", date);

                fragment.setArguments(args);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container1, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                customCalendar2.setVisibility(View.INVISIBLE);

            }
        });
        return  v;
    }

}