package com.example.serverexample.materials;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.serverexample.R;

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
import java.util.ArrayList;
import java.util.HashMap;


public class materialforLessomStudent extends Fragment {



    HashMap<String, String> schedule;
    ListView listView;
    private static String jsonurl;
    String data="", name="", ALessonId="", description="", fileName="", fileUrl="";
    ArrayList<HashMap<String, String>> scheduleList;
    ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ALessonId = getArguments().getString("ALessonId");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_materialfor_lessom_student, container, false);

        listView = v.findViewById(R.id.listviewMaterial);
        imageView =  v.findViewById(R.id.noMaterial);

       // jsonurl = "http://borovik.fun:8080/additional/Files?ALessonId=" + ALessonId;
       jsonurl = "http://borovik.fun:8080/additional/Files?ALessonId=0015badc-c252-4fa5-840e-1a93df9976a5";

        System.out.println(jsonurl);
        scheduleList = new ArrayList<>();

        materialforLessomStudent.GetClasses getClasses = new materialforLessomStudent.GetClasses();
        getClasses.execute();
        return v;
    }

    public class GetClasses extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
                HttpURLConnection httpURLConnection = null;
                try {
                    url = new URL(jsonurl);
                    httpURLConnection = (HttpURLConnection) url.openConnection();


                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    while(current != null){
                        current = bufferedReader.readLine();
                        data = data + current;
                    }

                    return data;

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
                if(jsonObject.optString("code").contentEquals("Success")) {

                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    if (jsonArray.length() == 0) {
                    imageView.setVisibility(View.VISIBLE);
                    } else {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                            description = jsonObject1.getString("description");
                            fileName = jsonObject1.getString("fileName");
                            fileUrl = jsonObject1.getString("fileUrl");
                            schedule = new HashMap<>();
                            schedule.put("description", description);
                            schedule.put("fileName", fileName);
                            schedule.put("fileUrl", fileUrl);

                            scheduleList.add(schedule);

                        }

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ListAdapter adapter = new SimpleAdapter( getActivity(),
                    scheduleList,
                    R.layout.listdownloadmaterial,
                    new String[]{"description", "fileUrl"},
                    new int[] {R.id.descriptiomMaterial, R.id.downMaterial});

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    HashMap<String,String>  value =  (HashMap<String,String>) parent.getItemAtPosition(position);

                    Intent browserIntent = new
                           // Intent(Intent.ACTION_VIEW, Uri.parse(value.get("fileUrl")));
                            Intent(Intent.ACTION_VIEW, Uri.parse("http://borovik.fun:8080/r/c63f485e-7cb0-4129-a47b-0f992d3cedf6.png"));
                    startActivity(browserIntent);
                }
            });
        }
    }
}