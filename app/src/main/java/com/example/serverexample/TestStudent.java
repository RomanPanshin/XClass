package com.example.serverexample;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class TestStudent extends Fragment {

Button finish;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_test_student, container, false);


        finish =v.findViewById(R.id.finish);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment testStudent = new GettingExerciseWithUploadingHW();

                getFragmentManager().beginTransaction().replace(R.id.container2, testStudent).commit();
                Toast.makeText(getContext(), "Ваш результат - 100%", Toast.LENGTH_LONG).show();


            }
        });
        return v;
    }
}