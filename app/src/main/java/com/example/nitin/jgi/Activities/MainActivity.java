package com.example.nitin.jgi.Activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.nitin.jgi.Adapter.CustomPageAdapter;
import com.example.nitin.jgi.R;

public class MainActivity extends AppCompatActivity {
    CustomPageAdapter customPageAdapter;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout tracker = findViewById(R.id.bus_tracker);
        RelativeLayout about = findViewById(R.id.about_us);
        RelativeLayout study = findViewById(R.id.study_materials);
        RelativeLayout library = findViewById(R.id.library_books);
        RelativeLayout exam = findViewById(R.id.exam_form);

        customPageAdapter = new CustomPageAdapter(this);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(customPageAdapter);
       tracker.setOnClickListener(activityChanger);
        about.setOnClickListener(activityChanger);
        study.setOnClickListener(activityChanger);
        library.setOnClickListener(activityChanger);
        exam.setOnClickListener(activityChanger);

    }
    private View.OnClickListener activityChanger = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.about_us:
                    Intent ab = new Intent(MainActivity.this,AboutUsMain.class);
                    startActivity(ab);
                    break;
                case R.id.study_materials:
                    Intent ab1 = new Intent(MainActivity.this,StudyMaterialMain.class);
                    startActivity(ab1);
                    break;
                case R.id.bus_tracker:
                    Intent ab2 = new Intent(MainActivity.this,BusTrackerMain.class);
                    startActivity(ab2);
                    break;
                case R.id.library_books:
                    Intent ab3 = new Intent(MainActivity.this,LibraryMain.class);
                    startActivity(ab3);
                    break;
                case R.id.exam_form:
                    Intent ab4 = new Intent(MainActivity.this,ExamFormMain.class);
                    startActivity(ab4);
                    break;
                 default:break;
            }
        }
    };


}
