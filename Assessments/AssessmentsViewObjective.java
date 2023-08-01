package Assessments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zybooks.wgu.R;
import com.zybooks.wgu.SQLdatabase;

import DialogFragments.DialogFragment_Delete;

public class AssessmentsViewObjective extends AppCompatActivity {

    SQLdatabase database;

    TextView assesstally;
    LinearLayout viewTermScroll;
    int tally = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments_view);

        assesstally = findViewById(R.id.assesstally);

        database = new SQLdatabase(this);

        AssessmentObjectObjective.deleteAssessmentsO();
        database.readObjectiveAssessments();

        for (int i = 0; i < AssessmentObjectObjective.getObjectiveAssessments().size(); ++i) {

            View addView = getLayoutInflater().inflate(R.layout.add_a_new_assessment_objective, null);

            TextView assessmentid = addView.findViewById(R.id.assessmentstext1);
            TextView courseid = addView.findViewById(R.id.assessmentstext2);
            TextView assessments = addView.findViewById(R.id.assessmentstext3);
            TextView datestart = addView.findViewById(R.id.assessmentstext5);
            TextView dateend = addView.findViewById(R.id.assessmentstext4);
            TextView title = addView.findViewById(R.id.assessmentstext6);

            assessmentid.setText(String.valueOf(AssessmentObjectObjective.getObjectiveAssessments().get(i).getObjectiveassessmentsid()));
            courseid.setText(String.valueOf(AssessmentObjectObjective.getObjectiveAssessments().get(i).getObjectivecourseID()));
            assessments.setText(AssessmentObjectObjective.getObjectiveAssessments().get(i).getObjectiveassessments());
            datestart.setText(AssessmentObjectObjective.getObjectiveAssessments().get(i).getStartDate());
            dateend.setText(AssessmentObjectObjective.getObjectiveAssessments().get(i).getEndDate());
            title.setText(AssessmentObjectObjective.getObjectiveAssessments().get(i).getTitle());

            viewTermScroll = findViewById(R.id.viewTermScroll);

            viewTermScroll.addView(addView);

            Button deleteassessbutton = addView.findViewById(R.id.deleteassessbutton);
            Button viewassessbutton = addView.findViewById(R.id.viewassessbutton);
            Button updateassessbutton = addView.findViewById(R.id.updateassessbutton);

            int finalI = i;
            deleteassessbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   int rows = database.deleteObjectiveassessments(AssessmentObjectObjective.getObjectiveAssessments().get(finalI).getObjectiveassessmentsid());

                    if (rows > 0) {

                    ((LinearLayout) addView.getParent()).removeView(addView);

                        DialogFragment_Delete delete = new DialogFragment_Delete();
                        delete.show(getSupportFragmentManager(), "delete");

                        tally = tally - 1;
                    }
                }
            });

            int finalI1 = i;
            viewassessbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent add = new Intent(getBaseContext(), AssessmentsDetailsPage.class);
                    add.putExtra("assessmentid", AssessmentObjectObjective.getObjectiveAssessments().get(finalI1).getObjectiveassessmentsid());
                    add.putExtra("courseid", AssessmentObjectObjective.getObjectiveAssessments().get(finalI1).getObjectivecourseID());
                    add.putExtra("assessments", AssessmentObjectObjective.getObjectiveAssessments().get(finalI1).getObjectiveassessments());
                    add.putExtra("title", AssessmentObjectObjective.getObjectiveAssessments().get(finalI1).getTitle());
                    startActivity(add);
                }
            });

            updateassessbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent add = new Intent(getBaseContext(), AssessmentObjectiveUpdate.class);
                    add.putExtra("assessmentid", AssessmentObjectObjective.getObjectiveAssessments().get(finalI1).getObjectiveassessmentsid());
                    add.putExtra("courseid", AssessmentObjectObjective.getObjectiveAssessments().get(finalI1).getObjectivecourseID());
                    add.putExtra("assessments", AssessmentObjectObjective.getObjectiveAssessments().get(finalI1).getObjectiveassessments());
                    add.putExtra("title", AssessmentObjectObjective.getObjectiveAssessments().get(finalI1).getTitle());
                    startActivity(add);
                }
            });

            tally = tally + 1;
        }

        assesstally.setText("You have " + tally + " Objective assessments! Add more!");

    }
}