package Assessments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zybooks.wgu.R;

public class AssessmentChoose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_choose);
    }

    public void assessmentsClick(View view) {
        Intent newt = new Intent(this, AssessmentsViewObjective.class);
        startActivity(newt);
    }

    public void assessmentsClick2(View view) {
        Intent newt = new Intent(this, AssessmentsViewPerformance.class);
        startActivity(newt);
    }
}