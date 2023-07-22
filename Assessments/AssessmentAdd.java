package com.zybooks.wgu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import DialogFragments.DialogFragment_AddAssessment;
import DialogFragments.DialogFragment_AddCourse;
import DialogFragments.DialogFragment_IncorrectDate;
import DialogFragments.DialogFragment_MissingInput;
// adds assessments to the course
public class AssessmentAdd extends AppCompatActivity {

    SQLdatabase database;
    int courseid;

    int month1;
    int dayOfMonth1;
    int year1;

    int month2;
    int dayOfMonth2;
    int year2;

    int month3;
    int dayOfMonth3;
    int year3;

    int month4;
    int dayOfMonth4;
    int year4;

    EditText objectiveMulti;
    EditText performancemulti;

    String objectiveMultiassessments;
    String performancemultiassessments;

    CalendarView startcalendarObjective;
    CalendarView endcalendarObjective;

    CalendarView startcalendarperformance;
    CalendarView endcalendarperformance;

    TextView startobjectiveinput;
    TextView endObjectiveInput;

    TextView startperformanceinput;
    TextView endperformanceInput;

    String date1;
    String date2;
    String date3;
    String date4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);

        // establish database
        database = new SQLdatabase(this);

        // retrieve course ID
        courseid = getIntent().getIntExtra("courseid", courseid);

        //receive start calendar input 1
        startcalendarObjective = findViewById(R.id.startcalendarObjective);
        startcalendarObjective.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month1 = month+1; year1 = year; dayOfMonth1 = dayOfMonth;

                date1 = month+1 + "/" + dayOfMonth + "/" + year ;

                // see date
                startobjectiveinput.setText(date1);
            }
        });

        //receive end calendar input 1
        endcalendarObjective = findViewById(R.id.endcalendarObjective);
        endcalendarObjective.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month2 = month+1; year2 = year; dayOfMonth2 = dayOfMonth;

                date2 = month+1 + "/" + dayOfMonth + "/" + year ;

                // see date
                endObjectiveInput.setText(date2);
            }
        });


        // see objective date entries
        startobjectiveinput = findViewById(R.id.startobjectiveinput);
        endObjectiveInput = findViewById(R.id.endObjectiveInput);

        // see performance date entries
         startperformanceinput = findViewById(R.id.startperformanceinput);
         endperformanceInput = findViewById(R.id.endperformanceinput);


        objectiveMulti = findViewById(R.id.objectiveMulti);
        performancemulti = findViewById(R.id.performancemulti);

        //receive start calendar input 2
        startcalendarperformance = findViewById(R.id.startcalendarperformance);
        startcalendarperformance.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month3 = month+1; year3 = year; dayOfMonth3 = dayOfMonth;

                date3 = month+1 + "/" + dayOfMonth + "/" + year ;
                // see date chosen
                startperformanceinput.setText(date3);
            }
        });

        //receive end calendar input 2
        endcalendarperformance = findViewById(R.id.endcalendarperformance);
        endcalendarperformance.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month4 = month+1; year4 = year; dayOfMonth4 = dayOfMonth;

                date4 = month+1 + "/" + dayOfMonth + "/" + year ;
                // see date chosen
                endperformanceInput.setText(date4);
            }
        });


        // save buttons
        Button objectiveSavebutton = findViewById(R.id.objectiveSavebutton);
        Button performancesaveButton = findViewById(R.id.performancesaveButton);

        objectiveSavebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get values
                objectiveMultiassessments = objectiveMulti.getText().toString();
                date1 = startobjectiveinput.getText().toString();
                date2 = endObjectiveInput.getText().toString();

                // validate no year contradictions
                if (year1 > year2) {
                    DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
                    cant.show(getSupportFragmentManager(), "cant");
                } else {

                    // validate no month contradictions
                    if (month1 == month2) {
                        DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
                        cant.show(getSupportFragmentManager(), "cant");
                    } else {

                        // validate no month or year contradictions
                        if (month1 > month2 && year1 == year2) {
                            DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
                            cant.show(getSupportFragmentManager(), "cant");
                        } else {

                            // validate no empty spots
                            if (objectiveMultiassessments.isEmpty()) {
                                DialogFragment_MissingInput miss = new DialogFragment_MissingInput();
                                miss.show(getSupportFragmentManager(), "miss");
                            } else {

                                // retrieve info from database
                                long count = database.insertObjectiveassessments(objectiveMultiassessments, date1, date2, courseid);

                                if (count > 0) {

                                    // clear selections for next input
                                    objectiveMulti.getText().clear();
                                    startobjectiveinput.setText("");
                                    endObjectiveInput.setText("");

                                    DialogFragment_AddAssessment add = new DialogFragment_AddAssessment();
                                    add.show(getSupportFragmentManager(), "add");
                                }
                            }
                        }
                    }
                }
            }
        });

        performancesaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get values
                performancemultiassessments = performancemulti.getText().toString();
                date3 = startperformanceinput.getText().toString();
                date4 = endperformanceInput.getText().toString();

                // validate no year contradictions
                if (year3 > year4) {
                    DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
                    cant.show(getSupportFragmentManager(), "cant");
                }
                else {

                    // validate no month contradictions
                    if (month3 == month4 ) {
                        DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
                        cant.show(getSupportFragmentManager(), "cant");
                    }
                    else {

                        // validate no month of year contradictions
                        if (month3 > month4 && year3 == year4) {
                            DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
                            cant.show(getSupportFragmentManager(), "cant");
                        }
                        else {

                            // validate no empty spots
                            if (performancemultiassessments.isEmpty()) {
                                DialogFragment_MissingInput miss = new DialogFragment_MissingInput();
                                miss.show(getSupportFragmentManager(), "miss");
                            }
                            else {

                                // performance database
                                long count = database.insertPerformanceassessments(performancemultiassessments, date3, date4, courseid);

                                if (count > 0) {

                                    // clear selections
                                    performancemulti.getText().clear();
                                    startperformanceinput.setText("");
                                    endperformanceInput.setText("");

                                    DialogFragment_AddAssessment add = new DialogFragment_AddAssessment();
                                    add.show(getSupportFragmentManager(), "add");
                                }
                            }
                        }
                    }
                }
            }
        });


    }
}