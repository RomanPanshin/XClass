package com.example.serverexample;

import android.content.Intent;
import android.net.SSLCertificateSocketFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.serverexample.materials.MaterialReacherFragment;
import com.example.serverexample.materials.MaterialTeacherFragment;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class AdminHomeActivity extends AppCompatActivity {
    String jsonurl, data, idClass, classId;
    ListView listView;
    ArrayList<HashMap<String, String>> scheduleList;
    HashMap<String, String> schedule;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminhome);

        jsonurl = "https://borovik.fun/classes/getAll";



        listView = findViewById(R.id.listviewClassesAdmin);

        scheduleList = new ArrayList<>();

        AdminHomeActivity.GetClasses getClasses = new AdminHomeActivity.GetClasses();
        getClasses.execute();

    }

    public class GetClasses extends AsyncTask<String, String, String> {

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
                    data = data.substring(4);
                    System.out.println(data);
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
                if(jsonObject.optString("code").contentEquals("Success")){

                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);




                        idClass = jsonObject1.getString("id");
                        schedule = new HashMap<>();
                        schedule.put("idClass", idClass);

                        scheduleList.add(schedule);

                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ListAdapter adapter = new SimpleAdapter( AdminHomeActivity.this,
                    scheduleList,
                    R.layout.listclass,
                    new String[]{"idClass"},
                    new int[] {R.id.listViewLesson});

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    HashMap<String,String>  value =  (HashMap<String,String>) parent.getItemAtPosition(position);

                    //Intent intent = new Intent(AdminHomeActivity.this, )
                    classId = value.get("idClass");

                    Intent intent = new Intent(AdminHomeActivity.this, AdminGetPastLessons.class);
                    intent.putExtra("classId", classId);
                    startActivity(intent);


                }
            });
        }
    }
}
