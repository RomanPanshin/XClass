package com.example.serverexample.materials;

import android.net.SSLCertificateSocketFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.serverexample.R;

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


public class MaterialTeacherFragment extends Fragment {




    HashMap<String, String> schedule;
    ListView listView;
    private static String jsonurl;
    String data="", name="", idClass="";
    ArrayList<HashMap<String, String>> scheduleList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_schedule_teacher, container, false);

        listView = v.findViewById(R.id.listviewallAllclasses);

        jsonurl = "https://borovik.fun/classes/getAll";

        System.out.println(jsonurl);
        scheduleList = new ArrayList<>();

        MaterialTeacherFragment.GetClasses getClasses = new MaterialTeacherFragment.GetClasses();
        getClasses.execute();
        return v;
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

            final ListAdapter adapter = new SimpleAdapter( getActivity(),
                    scheduleList,
                    R.layout.listclass,
                    new String[]{"idClass"},
                    new int[] {R.id.listViewLesson});

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    HashMap<String,String>  value =  (HashMap<String,String>) parent.getItemAtPosition(position);


                    MaterialReacherFragment fragment = new MaterialReacherFragment();
                    Bundle args = new Bundle();
                    args.putString("ALessonId",value.get("idClass"));
                    fragment.setArguments(args);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.container1, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }
    }
}