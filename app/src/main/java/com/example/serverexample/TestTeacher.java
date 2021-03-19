package com.example.serverexample;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.serverexample.exerciseorhomework.UploadingTest;
import com.google.gson.Gson;

import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class TestTeacher extends Fragment {
    View back1, back2;
    Button homework, save, newQua;
    ListView lv, lv1;
    ArrayAdapter<String> arrayAdapter;
    HashMap<String, String> schedule, ans;
    ArrayList<HashMap<String, String>> scheduleList, ansList;
    public static  String dateText, description="hhgh", lessonId="", questionString="", json="", answer1, answer2, answer3;
    public static Test test;
    public static ArrayList arrayList;
    public static Gson gson;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4,checkBox5, checkBox6;
    EditText qua1, ans1, qua2, ans2, ans3, ans4, ans5, ans6;

    int i=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_test_teacher, container, false);

        lessonId = HomeworkActivity.lessonID;

        /*qua1 = v.findViewById(R.id.qua12);
        qua2 = v.findViewById(R.id.qua22);

        ans1 = v.findViewById(R.id.ans1);
        ans2 = v.findViewById(R.id.ans2);
        ans3 = v.findViewById(R.id.ans3);
        ans4 = v.findViewById(R.id.ans4);
        ans5 = v.findViewById(R.id.ans5);
        ans6 = v.findViewById(R.id.ans6);
        back1 = v.findViewById(R.id.back1);
        back2 = v.findViewById(R.id.back2);

        checkBox1 = v.findViewById(R.id.checkBox4);
        checkBox2 = v.findViewById(R.id.checkBox41);
        checkBox3 = v.findViewById(R.id.checkBox42);
        checkBox4 = v.findViewById(R.id.checkBox1);
        checkBox5 = v.findViewById(R.id.checkBox5);
        checkBox6 = v.findViewById(R.id.checkBox6);*/

        lv =  v.findViewById(R.id.listViewTEST);
       // lv1 =  v.findViewById(R.id.listViewAns);
        homework = v.findViewById(R.id.homework);
        save = v.findViewById(R.id.addFile);
        newQua = v.findViewById(R.id.addQua);
        scheduleList = new ArrayList<>();
        ansList = new ArrayList<>();
        schedule = new HashMap<>();
        ans = new HashMap<>();

        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        dateText = dateFormat.format(currentDate);


        newQua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
              /*  if(i==1){
                    qua1.setVisibility(View.VISIBLE);
                    ans1.setVisibility(View.VISIBLE);
                    ans3.setVisibility(View.VISIBLE);
                    ans2.setVisibility(View.VISIBLE);
                    checkBox1.setVisibility(View.VISIBLE);
                    checkBox2.setVisibility(View.VISIBLE);
                    checkBox3.setVisibility(View.VISIBLE);
                    back1.setVisibility(View.VISIBLE);


                }
                if(i==2){
                    qua2.setVisibility(View.VISIBLE);
                    ans4.setVisibility(View.VISIBLE);
                    ans5.setVisibility(View.VISIBLE);
                    ans6.setVisibility(View.VISIBLE);
                    checkBox4.setVisibility(View.VISIBLE);
                    checkBox5.setVisibility(View.VISIBLE);
                    checkBox6.setVisibility(View.VISIBLE);
                    back2.setVisibility(View.VISIBLE);
                }*/
                schedule.put("Question", "question");
                schedule.put("Answer1", "K");
                schedule.put("Answer2", "k");
                schedule.put("Answer3", "j");
                scheduleList.add(schedule);
                    ListAdapter adapter = new SimpleAdapter( getActivity(),
                            scheduleList,
                            R.layout.testlist,
                            new String[]{},
                            new int[]{ });
              //  lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    lv.setAdapter(adapter);

                 /*   ansList.add(ans);
                ListAdapter adapter1 = new SimpleAdapter( getActivity(),
                        ansList,
                        R.layout.answers,
                        new String[]{},
                        new int[]{ });
                //lv1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                lv1.setAdapter(adapter1);*/
        }});

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {





                 /* questionString =  URLEncoder.encode(qua1.getText().toString(), "CP1251");
                    System.out.println(questionString);


                    answer1  =  URLEncoder.encode(ans1.getText().toString(), "CP1251");

                    answer2  =  URLEncoder.encode(ans2.getText().toString(), "CP1251");

                    answer3  =  URLEncoder.encode(ans3.getText().toString(), "CP1251");


                    HashMap<String, Boolean> answers = new HashMap<>();
                    answers.put(answer1,true );
                    answers.put(answer2,false );
                    answers.put(answer3,false );

                    // Question question = new Question(questionString, answers);
                    arrayList = new ArrayList();
                    arrayList.add( new Question(questionString, answers));

                    test = new Test("TEST",arrayList, "45445", dateText, "82722b86-4cba-4178-97ac-9a34f50867a2" );

                    //System.out.println(arrayList.toString());
                    gson = new Gson();
                    json = gson.toJson(arrayList);
                    System.out.println(json);
                    //test = new Test(questionString, arrayList, lessonId, dateText,  lessonId);
                    //System.out.println(test.toString());


                    UploadingTest uploadingTest = new UploadingTest();
                    uploadingTest.execute();*/

                    Toast.makeText(getContext(), "Тест добавлен", Toast.LENGTH_SHORT).show();
                    Fragment homeworkFragment = new HomeworkActivity();
                    getFragmentManager().beginTransaction().replace(R.id.container1, homeworkFragment).commit();





                } catch (Exception e) {
                    e.printStackTrace();
                }






                /*int cntChoice = lv.getCount();

                String checked = "";
                String unchecked = "";

                SparseBooleanArray sparseBooleanArray = lv.getCheckedItemPositions();

                for (int i = 0; i < cntChoice; i++) {

                    if (sparseBooleanArray.get(i) == true) {
                        checked += lv.getItemAtPosition(i).toString()
                                + "\n";
                        // выводим список выбранных элементов
                        System.out.println(checked);
                        //selection.setText(checked);
                    } else if (sparseBooleanArray.get(i) == false) {
                        unchecked += lv.getItemAtPosition(i).toString()
                                + "\n";
                        // выводим список невыбранных элементов
                       // System.out.println(unchecked);
                       // selection.setText(unchecked);
                    }
                }*/

            }
        });

        homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment homeworkFragment = new HomeworkActivity();
                getFragmentManager().beginTransaction().replace(R.id.container1, homeworkFragment).commit();
            }
        });

        return  v;
    }


}