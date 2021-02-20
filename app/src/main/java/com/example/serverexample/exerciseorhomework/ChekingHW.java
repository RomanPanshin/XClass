package com.example.serverexample.exerciseorhomework;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.serverexample.R;


public class ChekingHW extends Fragment {

String name="", filename="", fileUrl="", description="";
TextView desc, txtName;
 Button download;
    Button ask, see, save;
    ImageView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString("Name");
            filename = getArguments().getString("filename");
            fileUrl = getArguments().getString("fileURL");
            description = getArguments().getString("description");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_cheking_h_w, container, false);


        //imageView = v.findViewById(R.id.imageView4);

save = v.findViewById(R.id.postbutton30);
save.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(getContext(), "Сохранено", Toast.LENGTH_SHORT).show();
    }
});


        desc = v.findViewById(R.id.textDesc);
        txtName = v.findViewById(R.id.textName);
        download = v.findViewById(R.id.downStFile);

        if(description!=null){
        desc.setText(description);
        }

        if(name!=null){
        txtName.setText(name);
        }

        if (fileUrl!=null) {
            download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new
                            Intent(Intent.ACTION_VIEW, Uri.parse(fileUrl));
                         startActivity(browserIntent);
                }
            });
        }
        else{
            download.setVisibility(View.INVISIBLE);
        }
        return v;

    }
}