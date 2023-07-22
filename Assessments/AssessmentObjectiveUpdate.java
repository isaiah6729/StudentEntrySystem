package com.zybooks.wgu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import DialogFragments.DialogFragment_AddCourse;
import DialogFragments.DialogFragment_Update;

// update objective assessments
public class AssessmentObjectiveUpdate extends AppCompatActivity {

    SQLdatabase database;
    int courseid;
    int assessmentid;

    EditText objectiveMulti;

    String objectiveMultiassessments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_objective_update);

        // database set up
        database = new SQLdatabase(this);

        // gather values from course page
        assessmentid =  getIntent().getIntExtra("assessmentid", assessmentid);
        courseid = getIntent().getIntExtra("courseid", courseid);
        objectiveMultiassessments = getIntent().getStringExtra("assessments");

        // input new assessment
        objectiveMulti = findViewById(R.id.objectiveMulti);

        // save button
        Button objectiveSavebutton = findViewById(R.id.objectiveSavebutton);

        objectiveSavebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get value
                objectiveMultiassessments = objectiveMulti.getText().toString();

                // check database
                long count = database.updateObjectiveassessments(assessmentid, objectiveMultiassessments);

                if (count > 0 ) {

                    // clear selection
                    objectiveMulti.getText().clear();

                    DialogFragment_Update add = new DialogFragment_Update();
                    add.show(getSupportFragmentManager(), "add");
                }
            }
        });

    }
}