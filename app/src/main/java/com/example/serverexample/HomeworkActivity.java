package com.example.serverexample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HomeworkActivity extends AppCompatActivity {
TextView nameOfHomework, path;
Button button, selbtn;
public static String pathStr="", description="", lessonID="";
EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        nameOfHomework = findViewById(R.id.nameofLessom);
        Bundle arguments = getIntent().getExtras();
        String name = arguments.get("lesson").toString();
        String lessonID = arguments.get("lessonId").toString();
        nameOfHomework.setText(name);
        editText = findViewById(R.id.editTextHw);
        path = findViewById(R.id.path);
        button = findViewById(R.id.addFile);
        selbtn = findViewById(R.id.selectFile);
        selbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 200);
                description = editText.getText().toString().trim();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UploadFileAsync uploadFileAsync = new UploadFileAsync();
                uploadFileAsync.execute();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case 200:
                if(resultCode == RESULT_OK) {
                    String path1 = data.getData().getPath();
                    path.setText(path1);
                    pathStr = path1;

                }
                break;
        }
    }
}