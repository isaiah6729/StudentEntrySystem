package com.zybooks.wgu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import DialogFragments.DialogFragment_AddCourse;
import DialogFragments.DialogFragment_Update;

// update performance assessments
public class AssessmentPerformanceUpdate extends AppCompatActivity {

    SQLdatabase database;
    int courseid;
    int assessmentid;

    EditText performancemulti;

    String performancemultiassessments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_performance_update);

        // establish database
        database = new SQLdatabase(this);

        // gather values from assessment page
        assessmentid =  getIntent().getIntExtra("assessmentid", assessmentid);
        courseid = getIntent().getIntExtra("courseid", courseid);
        performancemultiassessments = getIntent().getStringExtra("assessments");

        // place previous input
        performancemulti = findViewById(R.id.performancemulti);
        performancemulti.setText(performancemultiassessments);

        // save button
        Button performancesaveButton = findViewById(R.id.performancesaveButton);

        performancesaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // new value
                performancemultiassessments = performancemulti.getText().toString();

                // place in database
               long count = database.updatePerformanceassessments(assessmentid, performancemultiassessments);

              if (count > 0 ) {

                  // clear selection
                    performancemulti.getText().clear();

                    DialogFragment_Update add = new DialogFragment_Update();
                    add.show(getSupportFragmentManager(), "add");
                }
            }
        });
    }
}