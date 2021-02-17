package com.example.serverexample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CustomDialogFragment extends DialogFragment {

String lessonID="", uID="", simpleDate="", classId="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            lessonID = getArguments().getString("lessonID");
            classId = getArguments().getString("idLesson");


            uID = Person.uId;

            Date currentDate = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
           simpleDate = dateFormat.format(currentDate);

           System.out.println(lessonID + " " + uID + " " + simpleDate + " " + classId);
        }
    }


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder.setTitle("Урок")
                .setIcon(R.drawable.videocall2)
                .setMessage("Начать видео-урок?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Нет", null)
                .create();
    }
}