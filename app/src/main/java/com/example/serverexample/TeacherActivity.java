package com.example.serverexample;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TeacherActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        bottomNavigationView = findViewById(R.id.bottomNav);
        getSupportFragmentManager().beginTransaction().replace(R.id.container1, new homeworkTeacherFragment()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId())
                {
                    case R.id.homeworkBTN:
                        fragment = new homeworkTeacherFragment();
                        break;

                    case R.id.journalBTN:
                        fragment = new journalTeacherFragment();
                        break;

                    case R.id.scheduleBTN:
                        fragment = new ScheduleTeacherFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container1, fragment).commit();
                return true;
            }
        });
    }


}
