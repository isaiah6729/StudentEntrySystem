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

// view all the objective assessments programmatically
public class AssessmentsViewObjective extends AppCompatActivity {

    SQLdatabase database;

    TextView assesstally;
    LinearLayout viewTermScroll;
    int tally = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments_view);

        // tally of assessments
        assesstally = findViewById(R.id.assesstally);

        // start database
        database = new SQLdatabase(this);

        // delete object to take new objects
        AssessmentObjectObjective.deleteAssessmentsO();

        // get info from database
        database.readObjectiveAssessments();

        // automatically take objects' info from list and place them on the screen
        for (int i = 0; i < AssessmentObjectObjective.getObjectiveAssessments().size(); ++i) {

            // create child view
            View addView = getLayoutInflater().inflate(R.layout.add_a_new_assessment_objective, null);

            // create textviews
            TextView assessmentid = addView.findViewById(R.id.assessmentstext1);
            TextView courseid = addView.findViewById(R.id.assessmentstext2);
            TextView assessments = addView.findViewById(R.id.assessmentstext3);
            TextView datestart = addView.findViewById(R.id.assessmentstext5);
            TextView dateend = addView.findViewById(R.id.assessmentstext4);

            // set text for views from objects
            assessmentid.setText(String.valueOf(AssessmentObjectObjective.getObjectiveAssessments().get(i).getObjectiveassessmentsid()));
            courseid.setText(String.valueOf(AssessmentObjectObjective.getObjectiveAssessments().get(i).getObjectivecourseID()));
            assessments.setText(AssessmentObjectObjective.getObjectiveAssessments().get(i).getObjectiveassessments());
            datestart.setText(AssessmentObjectObjective.getObjectiveAssessments().get(i).getStartDate());
            dateend.setText(AssessmentObjectObjective.getObjectiveAssessments().get(i).getEndDate());

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
                   int rows = database.deleteObjectiveassessments(AssessmentObjectObjective.getObjectiveAssessments().get(finalI).getObjectiveassessmentsid());

                    if (rows > 0) {

                        // delete from view
                    ((LinearLayout) addView.getParent()).removeView(addView);

                        DialogFragment_Delete delete = new DialogFragment_Delete();
                        delete.show(getSupportFragmentManager(), "delete");

                        tally = tally - 1;
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
                    add.putExtra("assessmentid", AssessmentObjectObjective.getObjectiveAssessments().get(finalI1).getObjectiveassessmentsid());
                    add.putExtra("courseid", AssessmentObjectObjective.getObjectiveAssessments().get(finalI1).getObjectivecourseID());
                    add.putExtra("assessments", AssessmentObjectObjective.getObjectiveAssessments().get(finalI1).getObjectiveassessments());
                    startActivity(add);
                }
            });

            // update listener
            updateassessbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // send info to next page
                    Intent add = new Intent(getBaseContext(), AssessmentObjectiveUpdate.class);
                    add.putExtra("assessmentid", AssessmentObjectObjective.getObjectiveAssessments().get(finalI1).getObjectiveassessmentsid());
                    add.putExtra("courseid", AssessmentObjectObjective.getObjectiveAssessments().get(finalI1).getObjectivecourseID());
                    add.putExtra("assessments", AssessmentObjectObjective.getObjectiveAssessments().get(finalI1).getObjectiveassessments());
                    startActivity(add);
                }
            });

            tally = tally + 1;
        }

        // tally total
        assesstally.setText("You have " + tally + " assessments! Add more!");

    }
}