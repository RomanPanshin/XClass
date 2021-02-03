package com.example.serverexample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

import static com.example.serverexample.MainActivity.context;

public class HomeworkActivity extends AppCompatActivity {
TextView nameOfHomework, path;
Button button, selbtn;
    public static Uri pathstr;
public static String pathStr="", description="", lessonID="", filePath="";
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
                intent.setType("image/*");
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

                    Uri path2 = data.getData();
                    path.setText(path1);

                    //filePath=getRealPathFromUri(data.getData());

                    pathstr = path2;
                    pathStr = path2.getPath();
                    //File file=new File(filePath);
                   // File file = new File(path1);
                    //System.out.println(filePath);
                }
                break;
        }
    }
    public String getRealPathFromUri(Uri uri){
        String[]  data = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(context, uri, data, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}