package com.example.serverexample;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class TestTeacher extends Fragment {

View back1, back2;
EditText qua1, ans1, qua2, ans2, ans3, ans4, ans5, ans6;;
Button homework, save, newQua;
TextView num1, num2;
CheckBox checkBox1, checkBox2, checkBox3, checkBox4,checkBox5, checkBox6;

    int i=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_test_teacher, container, false);

        qua1 = v.findViewById(R.id.qua1);
        qua2 = v.findViewById(R.id.qua2);

        ans1 = v.findViewById(R.id.ans1);
        ans2 = v.findViewById(R.id.ans2);
        ans3 = v.findViewById(R.id.ans3);
        ans4 = v.findViewById(R.id.ans4);
        ans5 = v.findViewById(R.id.ans5);
        ans6 = v.findViewById(R.id.ans6);

        back1 = v.findViewById(R.id.back1);
        back2 = v.findViewById(R.id.back2);

        num1 = v.findViewById(R.id.num1);
        num2 = v.findViewById(R.id.num2);

        checkBox1 = v.findViewById(R.id.checkBox1);
        checkBox2 = v.findViewById(R.id.checkBox2);
        checkBox3 = v.findViewById(R.id.checkBox3);
        checkBox4 = v.findViewById(R.id.checkBox4);
        checkBox5 = v.findViewById(R.id.checkBox5);
        checkBox6 = v.findViewById(R.id.checkBox6);

        homework = v.findViewById(R.id.homework);
        save = v.findViewById(R.id.addFile);
        newQua = v.findViewById(R.id.addQua);

        newQua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if(i==1){
                    qua1.setVisibility(View.VISIBLE);
                    ans1.setVisibility(View.VISIBLE);
                    ans3.setVisibility(View.VISIBLE);
                    ans2.setVisibility(View.VISIBLE);
                    checkBox1.setVisibility(View.VISIBLE);
                    checkBox2.setVisibility(View.VISIBLE);
                    checkBox3.setVisibility(View.VISIBLE);
                    back1.setVisibility(View.VISIBLE);
                    num1.setVisibility(View.VISIBLE);

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
                    num2.setVisibility(View.VISIBLE);
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Fragment homeworkFragment = new HomeworkActivity();
                getFragmentManager().beginTransaction().replace(R.id.container1, homeworkFragment).commit();

                Toast.makeText(getContext(), "Тест добавлен", Toast.LENGTH_SHORT).show();
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