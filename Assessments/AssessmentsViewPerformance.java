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

public class AssessmentsViewPerformance extends AppCompatActivity {

    SQLdatabase database;

    TextView assesstally;
    LinearLayout viewTermScroll;
    int tally = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments_view_performance);

        assesstally = findViewById(R.id.assesstally);

        database = new SQLdatabase(this);

        AssessmentObjectPerformance.deleteAssessmentsP();
        database.readPerformanceAssessments();

        for (int i = 0; i < AssessmentObjectPerformance.getPerformanceAssessments().size(); ++i) {

            View addView = getLayoutInflater().inflate(R.layout.add_a_new_assessment_performance, null);

            TextView assessmentid = addView.findViewById(R.id.assessmentstext1);
            TextView courseid = addView.findViewById(R.id.assessmentstext2);
            TextView assessments = addView.findViewById(R.id.assessmentstext3);
            TextView startDate = addView.findViewById(R.id.assessmentstext4);
            TextView endDate = addView.findViewById(R.id.assessmentstext5);
            TextView title = addView.findViewById(R.id.assessmentstext6);

            assessmentid.setText(String.valueOf(AssessmentObjectPerformance.getPerformanceAssessments().get(i).getPerformanceassessmentsid()));
            courseid.setText(String.valueOf(AssessmentObjectPerformance.getPerformanceAssessments().get(i).getCourseID()));
            assessments.setText(AssessmentObjectPerformance.getPerformanceAssessments().get(i).getAssessment());
             startDate.setText(AssessmentObjectPerformance.getPerformanceAssessments().get(i).getStartDate());
             endDate.setText(AssessmentObjectPerformance.getPerformanceAssessments().get(i).getEndDate());
            title.setText(AssessmentObjectPerformance.getPerformanceAssessments().get(i).getTitle());

            viewTermScroll = findViewById(R.id.viewTermScroll);

            viewTermScroll.addView(addView);

            Button deleteassessbutton = addView.findViewById(R.id.deleteassessbutton);
            Button viewassessbutton = addView.findViewById(R.id.viewassessbutton);
            Button updateassessbutton = addView.findViewById(R.id.updateassessbutton);

            int finalI = i;
            deleteassessbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int rows = database.deletePerformanceassessments(AssessmentObjectPerformance.getPerformanceAssessments().get(finalI).getPerformanceassessmentsid());

                    if (rows > 0) {

                        ((LinearLayout) addView.getParent()).removeView(addView);

                        tally = tally - 1;

                        DialogFragment_Delete delete = new DialogFragment_Delete();
                        delete.show(getSupportFragmentManager(), "delete");
                    }
                }
            });

            int finalI1 = i;
            viewassessbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent add = new Intent(getBaseContext(), AssessmentsDetailsPage.class);
                    add.putExtra("assessmentid", AssessmentObjectPerformance.getPerformanceAssessments().get(finalI1).getPerformanceassessmentsid());
                    add.putExtra("courseid", AssessmentObjectPerformance.getPerformanceAssessments().get(finalI1).getCourseID());
                    add.putExtra("assessments", AssessmentObjectPerformance.getPerformanceAssessments().get(finalI1).getAssessment());
                    add.putExtra("title", AssessmentObjectPerformance.getPerformanceAssessments().get(finalI1).getTitle());
                    startActivity(add);
                }
            });

            updateassessbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent add = new Intent(getBaseContext(), AssessmentPerformanceUpdate.class);
                    add.putExtra("assessmentid", AssessmentObjectPerformance.getPerformanceAssessments().get(finalI1).getPerformanceassessmentsid());
                    add.putExtra("courseid", AssessmentObjectPerformance.getPerformanceAssessments().get(finalI1).getCourseID());
                    add.putExtra("assessments", AssessmentObjectPerformance.getPerformanceAssessments().get(finalI1).getAssessment());
                    add.putExtra("title", AssessmentObjectPerformance.getPerformanceAssessments().get(finalI1).getTitle());
                    startActivity(add);
                }
            });

            tally = tally + 1;
        }

        assesstally.setText("You have " + tally + " Performance assessments! Add more!");


    }
}