package com.zybooks.wgu;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

// details of selected assessment
public class AssessmentsDetailsPage extends AppCompatActivity {

    SQLdatabase sqLdatabase;

    int assessmentid;
    String assessments;
    int courseid;

    TextView assessmentstext1;
    TextView assessmentstext2;
    TextView assessmentstext3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments_details_page);

        // database connection
        sqLdatabase = new SQLdatabase(this);

        // gather values from previous page
        assessments = getIntent().getStringExtra("assessments");
        courseid = getIntent().getIntExtra("courseid", courseid);
        assessmentid = getIntent().getIntExtra("assessmentid", assessmentid);

        assessmentstext1 = findViewById(R.id.assessmentstext1);
        assessmentstext2 = findViewById(R.id.assessmentstext2);
        assessmentstext3 = findViewById(R.id.assessmentstext3);

        // show values of selected assessment
        assessmentstext1.setText(String.valueOf(assessmentid));
        assessmentstext2.setText(String.valueOf(courseid));
        assessmentstext3.setText(assessments);

    }
}