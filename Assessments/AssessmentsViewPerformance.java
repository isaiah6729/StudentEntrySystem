package com.zybooks.wgu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import DialogFragments.DialogFragment_CantDelete;
import DialogFragments.DialogFragment_Delete;

// view all the performance assessments programmatically
public class AssessmentsViewPerformance extends AppCompatActivity {

    SQLdatabase database;

    TextView assesstally;
    LinearLayout viewTermScroll;
    int tally = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments_view_performance);

        // tally of assessments
        assesstally = findViewById(R.id.assesstally);

        // start database
        database = new SQLdatabase(this);

        // delete object to take new objects
        AssessmentObjectPerformance.deleteAssessmentsP();

        // get info from database
        database.readPerformanceAssessments();

        // automatically take objects' info from list and place them on the screen
        for (int i = 0; i < AssessmentObjectPerformance.getPerformanceAssessments().size(); ++i) {

            // create child view
            View addView = getLayoutInflater().inflate(R.layout.add_a_new_assessment_performance, null);

            // create textviews
            TextView assessmentid = addView.findViewById(R.id.assessmentstext1);
            TextView courseid = addView.findViewById(R.id.assessmentstext2);
            TextView assessments = addView.findViewById(R.id.assessmentstext3);
            TextView startDate = addView.findViewById(R.id.assessmentstext4);
            TextView endDate = addView.findViewById(R.id.assessmentstext5);

            // set text for views from objects
            assessmentid.setText(String.valueOf(AssessmentObjectPerformance.getPerformanceAssessments().get(i).getPerformanceassessmentsid()));
            courseid.setText(String.valueOf(AssessmentObjectPerformance.getPerformanceAssessments().get(i).getCourseID()));
            assessments.setText(AssessmentObjectPerformance.getPerformanceAssessments().get(i).getAssessment());
             startDate.setText(AssessmentObjectPerformance.getPerformanceAssessments().get(i).getStartDate());
             endDate.setText(AssessmentObjectPerformance.getPerformanceAssessments().get(i).getEndDate());

            // parent view
            viewTermScroll = findViewById(R.id.viewTermScroll);

            // add child to parent view
            viewTermScroll.addView(addView);

            // buttons
            Button deleteassessbutton = addView.findViewById(R.id.deleteassessbutton);
            Button viewassessbutton = addView.findViewById(R.id.viewassessbutton);
            Button updateassessbutton = addView.findViewById(R.id.updateassessbutton);

            // delete listener
            int finalI = i;
            deleteassessbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // delete database
                    int rows = database.deletePerformanceassessments(AssessmentObjectPerformance.getPerformanceAssessments().get(finalI).getPerformanceassessmentsid());

                    if (rows > 0) {

                        // delete from view
                        ((LinearLayout) addView.getParent()).removeView(addView);

                        DialogFragment_Delete delete = new DialogFragment_Delete();
                        delete.show(getSupportFragmentManager(), "delete");
                    }
                }
            });

            // view details listener
            int finalI1 = i;
            viewassessbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // send info to next page
                    Intent add = new Intent(getBaseContext(), AssessmentsDetailsPage.class);
                    add.putExtra("assessmentid", AssessmentObjectPerformance.getPerformanceAssessments().get(finalI1).getPerformanceassessmentsid());
                    add.putExtra("courseid", AssessmentObjectPerformance.getPerformanceAssessments().get(finalI1).getCourseID());
                    add.putExtra("assessments", AssessmentObjectPerformance.getPerformanceAssessments().get(finalI1).getAssessment());
                    startActivity(add);
                }
            });

            // update listener
            updateassessbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // send info to next page
                    Intent add = new Intent(getBaseContext(), AssessmentPerformanceUpdate.class);
                    add.putExtra("assessmentid", AssessmentObjectPerformance.getPerformanceAssessments().get(finalI1).getPerformanceassessmentsid());
                    add.putExtra("courseid", AssessmentObjectPerformance.getPerformanceAssessments().get(finalI1).getCourseID());
                    add.putExtra("assessments", AssessmentObjectPerformance.getPerformanceAssessments().get(finalI1).getAssessment());
                    startActivity(add);
                }
            });

            tally = tally + 1;
        }

        // tally total
        assesstally.setText("You have " + tally + " assessments! Add more!");


    }
}