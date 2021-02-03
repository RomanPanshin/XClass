package com.example.serverexample;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.Calendar;
import java.util.HashMap;


public class homeworkTeacherFragment extends Fragment {

    CustomCalendar customCalendar;


    public homeworkTeacherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_homework_teacher, container, false);
         customCalendar = v.findViewById(R.id.custom_calendar);

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

                String info = "Посмотреть дз ";
                Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();
            }
        });
return  v;
    }
}