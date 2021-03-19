package com.example.serverexample;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.SSLCertificateSocketFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.serverexample.exerciseorhomework.UploadHWAsync;
import com.example.serverexample.video.CustomFragmentStudent;
import com.example.serverexample.video.VideoActivity;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import static android.app.Activity.RESULT_OK;
import static com.example.serverexample.MainActivity.context;
import static com.example.serverexample.R.drawable.bluerect;

public class GettingExerciseWithUploadingHW extends Fragment {
    Context mc;
    TextView homeTask, path, theme, lesson;
    Button buttonUP, selbtn, download, con, createTest, homework;
    public static Uri pathstr;
    public static String pathStr="", description="", idLESSON="", mimeType = "", jsonurl="",jsonurl2="", LessonName="", topic="";
    EditText editText;
    String data, fileurl, data2;
    //TextView nameOfLesson;
    JSONObject structure;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idLESSON = getArguments().getString("lessonId");
            LessonName = getArguments().getString("LessonName");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_getting_exercise_with_uploading_h_w, container, false);
        mc= v.getContext();

        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);


       // jsonurl = "http://borovik.fun:8080/GetExerciseBySimpleDateAndLessonId?simpleDate=" + dateText + "&lessonId=" + idLESSON;
        jsonurl = "https://borovik.fun/GetExerciseBySimpleDateAndLessonId?simpleDate=21.01.2021&lessonId=701b0192-bc36-410f-b911-c27de261397e" ;
        //jsonurl2 = "http://borovik.fun:8080/lessons/topic/get?lessonId=" + idLESSON + "&simpleDate=" + dateText;
        jsonurl2 = "https://borovik.fun/lessons/topic/get?lessonId=91898941-cc42-4275-ad3b-dbdcd51db21a&simpleDate=15.02.2021";

        System.out.println(jsonurl2);
        homeTask = v.findViewById(R.id.textViewHomeTask);

        download = v.findViewById(R.id.down);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://borovik.fun/r/c63f485e-7cb0-4129-a47b-0f992d3cedf6.png"));
                startActivity(browserIntent);
            }
        });

        homework = v.findViewById(R.id.homework);

        createTest = v.findViewById(R.id.createTest);

        createTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment testStudent = new TestStudent();


                getFragmentManager().beginTransaction().replace(R.id.container2, testStudent).commit();
            }
        });

        homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        con = v.findViewById(R.id.buttonCon);
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CustomFragmentStudent customFragmentStudent = new CustomFragmentStudent();
                customFragmentStudent.show(getActivity().getSupportFragmentManager(), "custom");


            }
        });
        lesson = v.findViewById(R.id.textView6);
        lesson.setText(LessonName);
        theme = v.findViewById(R.id.theme);

        editText = v.findViewById(R.id.editTexthWTask);
        path = v.findViewById(R.id.path);
        buttonUP = v.findViewById(R.id.uploadhw);
        selbtn = v.findViewById(R.id.selectHWFile);


        selbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 200);
                selbtn.setBackgroundResource(bluerect);
                selbtn.setText("");
            }
        });
        buttonUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description = editText.getText().toString().trim();

System.out.println(idLESSON + " " +  description);

                UploadHWAsync uploadHWAsync = new UploadHWAsync();

                uploadHWAsync.execute();
            }
        });
       /*downBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadFileAsync downloadFileAsync = new DownloadFileAsync(HomeworkActivity.this, "http://borovik.fun:8080/r/f6576fd5-7bd9-47a2-bf7a-79ee7b3eb261.jpg");

                downloadFileAsync.execute();
            }
        });*/


        GettingExerciseWithUploadingHW.GetExercise getExercise = new GettingExerciseWithUploadingHW.GetExercise();
        getExercise.execute();
        GettingExerciseWithUploadingHW.GetTheme getTopic = new GettingExerciseWithUploadingHW.GetTheme();
        getTopic.execute();


        return  v;
    }
    @Override
    public  void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case 200:
                if(resultCode == RESULT_OK) {


                    try {
                        Uri uri = data.getData();

                        mimeType = mc.getContentResolver().getType(uri);
                        if (mimeType == null) {
                            String path = getPath(mc, uri);
                            if (path == null) {
                                //pathStr  = FilenameUtils.getName(uri.toString());
                            } else {
                                File file = new File(path);
                                pathStr  = file.getName();
                            }
                        } else {
                            Uri returnUri = data.getData();
                            Cursor returnCursor = mc.getContentResolver().query(returnUri, null, null, null, null);
                            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                            int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                            returnCursor.moveToFirst();
                            pathStr  = returnCursor.getString(nameIndex);
                            String size = Long.toString(returnCursor.getLong(sizeIndex));
                        }
                        File fileSave = mc.getExternalFilesDir(null);
                        String sourcePath = mc.getExternalFilesDir(null).toString();
                        pathStr = sourcePath + "/" + pathStr;
                        try {
                            copyFileStream(new File(pathStr), uri,mc);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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


    public static String getPath(Context context, Uri uri) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // DocumentProvider
            if (DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                    // TODO handle non-primary volumes
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    return getDataColumn(context, contentUri, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};
                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }


    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }


    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
    private void copyFileStream(File dest, Uri uri, Context context)
            throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = context.getContentResolver().openInputStream(uri);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;

            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            is.close();
            os.close();
        }
    }

    public class GetExercise extends AsyncTask<String, String, String> {

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
                    data=data.substring(4);
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
                    if (jsonObject.optString("code").contentEquals("Success")) {

                        structure = (JSONObject) jsonObject.get("result");
                        description = structure.get("description").toString();
                        homeTask.setText(description);

                        if (structure.getString("fileURL").equals("null")) {
                            download.setVisibility(View.INVISIBLE);
                        } else {
                            fileurl = structure.getString("fileURL");
                        }





                    }
                    if (jsonObject.optString("code").contentEquals("Error")) {

                        download.setVisibility(View.INVISIBLE);
                        homeTask.setText("Не задано");

                    }
                } catch(JSONException e){
                    e.printStackTrace();
                }


        }
    }
    public class GetTheme extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
                HttpURLConnection httpURLConnection = null;
                try {
                    url = new URL(jsonurl2);
                    httpURLConnection = (HttpURLConnection) url.openConnection();

                    if (httpURLConnection instanceof HttpsURLConnection) {
                        HttpsURLConnection httpsConn = (HttpsURLConnection) httpURLConnection;
                        httpsConn.setSSLSocketFactory(SSLCertificateSocketFactory.getInsecure(0, null));
                        httpsConn.setHostnameVerifier(new AllowAllHostnameVerifier());
                    }

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    while(current != null){
                        current = bufferedReader.readLine();
                        data2 = data2 + current;
                    }
                    data2=data2.substring(4);
                    return data2;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    if(httpURLConnection !=null )
                        httpURLConnection.disconnect();
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
                if (jsonObject.optString("code").contentEquals("Success")) {

                    JSONObject jsonObject2 = new JSONObject(jsonObject.optString("result"));

                        topic = jsonObject2.getString("topic");
                        theme.setText(topic);

                }

            } catch(JSONException e){
               theme.setText("Тема не назначена");
                e.printStackTrace();
            }



        }
    }
}
