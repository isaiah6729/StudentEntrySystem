package Assessments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.zybooks.wgu.R;
import com.zybooks.wgu.SQLdatabase;

import java.util.Calendar;

import DialogFragments.DialogFragment_AddCourse;
import DialogFragments.DialogFragment_IncorrectDate;
import DialogFragments.DialogFragment_Update;
import DialogFragments.DialogFragment_Update_Assessment;

public class AssessmentPerformanceUpdate extends AppCompatActivity {

    SQLdatabase database;
    int courseid;
    int assessmentid;

    EditText performancemulti;

    String performancemultiassessments;

    EditText objectiveTitle;
    String title;

    Calendar calendar1;
    Calendar calendar2;

    int month1;
    int dayOfMonth1;
    int year1;

    int month2;
    int dayOfMonth2;
    int year2;

    CalendarView startcalendarObjective;
    CalendarView endcalendarObjective;

    TextView startobjectiveinput;
    TextView endObjectiveInput;

    String date1;
    String date2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_performance_update);

        database = new SQLdatabase(this);

        assessmentid =  getIntent().getIntExtra("assessmentid", assessmentid);
        courseid = getIntent().getIntExtra("courseid", courseid);
        performancemultiassessments = getIntent().getStringExtra("assessments");
        title = getIntent().getStringExtra("title");

        TextView textView = findViewById(R.id.textView4);
        textView.setText("Performance Assessment ID " + assessmentid);

        performancemulti = findViewById(R.id.performancemulti);
        performancemulti.setText(performancemultiassessments);

        objectiveTitle = findViewById(R.id.objectiveTitle);
        objectiveTitle.setText(title);

        startcalendarObjective = findViewById(R.id.startcalendarObjective);
        startcalendarObjective.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month1 = month; year1 = year; dayOfMonth1 = dayOfMonth;

                calendar1 = Calendar.getInstance();
                calendar1.set( year1, month1, dayOfMonth1, 0, 0, 0);

                date1 = month+1 + "/" + dayOfMonth + "/" + year ;
                startobjectiveinput.setText(date1);
            }
        });

        endcalendarObjective = findViewById(R.id.endcalendarObjective);
        endcalendarObjective.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month2 = month; year2 = year; dayOfMonth2 = dayOfMonth;

                calendar2 = Calendar.getInstance();
                calendar2.set( year2, month2, dayOfMonth2, 0, 0, 0);

                date2 = month+1 + "/" + dayOfMonth + "/" + year ;
                endObjectiveInput.setText(date2);
            }
        });

        startobjectiveinput = findViewById(R.id.startobjectiveinput);
        endObjectiveInput = findViewById(R.id.endObjectiveInput);

        Button performancesaveButton = findViewById(R.id.performancesaveButton);
        performancesaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title = objectiveTitle.getText().toString();
                performancemultiassessments = performancemulti.getText().toString();
                date1 = startobjectiveinput.getText().toString();
                date2 = endObjectiveInput.getText().toString();

                if (year1 > year2) {
                    DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
                    cant.show(getSupportFragmentManager(), "cant");
                } else {

                    if (month1 == month2) {
                        DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
                        cant.show(getSupportFragmentManager(), "cant");
                    } else {

                        if (month1 > month2 && year1 == year2) {
                            DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
                            cant.show(getSupportFragmentManager(), "cant");
                        } else {

                            long count = database.updatePerformanceassessments(assessmentid, title, performancemultiassessments, date1, date2);

                            if (count > 0) {

                                objectiveTitle.getText().clear();
                                performancemulti.getText().clear();

                                DialogFragment_Update_Assessment add = new DialogFragment_Update_Assessment();
                                add.show(getSupportFragmentManager(), "add");
                            }
                        }
                    }
                }
            }
        });
    }
}