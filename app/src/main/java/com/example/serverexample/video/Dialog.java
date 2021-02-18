package com.example.serverexample.video;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.serverexample.R;

public class Dialog {

    public static AlertDialog createConnectDialog(EditText participantEditText,
                                                  DialogInterface.OnClickListener callParticipantsClickListener,
                                                  DialogInterface.OnClickListener cancelClickListener,
                                                  Context context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setIcon(R.drawable.videocall2);
        alertDialogBuilder.setTitle("Подключение к уроку");
        alertDialogBuilder.setPositiveButton("Подключиться", callParticipantsClickListener);
        alertDialogBuilder.setNegativeButton("Отменить", cancelClickListener);
        alertDialogBuilder.setCancelable(false);
        setRoomNameFieldInDialog(participantEditText, alertDialogBuilder);


        return alertDialogBuilder.create();
    }

    private static void setRoomNameFieldInDialog(EditText roomNameEditText,
                                                 AlertDialog.Builder alertDialogBuilder) {
        roomNameEditText.setHint("Название комнаты");
        alertDialogBuilder.setView(roomNameEditText);
    }

}