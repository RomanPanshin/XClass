package com.example.serverexample;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.serverexample.materials.MAterialStudentFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StudentActivityHome extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_home);

        bottomNavigationView = findViewById(R.id.bottomNav2);
        getSupportFragmentManager().beginTransaction().replace(R.id.container2, new StudentActivity()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId())
                {
                    case R.id.scheduleBTNSTD:
                        fragment = new StudentActivity();
                        break;

                    case R.id.materialBTNSTD:
                        fragment = new MAterialStudentFragment();
                        break;

                    case R.id.homeworkBTNSTD:
                        fragment = new StudentActivity();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container2, fragment).commit();
                return true;
            }
        });
    }


}
