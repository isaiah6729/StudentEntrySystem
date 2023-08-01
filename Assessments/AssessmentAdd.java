package Assessments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zybooks.wgu.R;
import com.zybooks.wgu.SQLdatabase;

import java.util.Calendar;

import DialogFragments.DialogFragment_AddAssessment;
import DialogFragments.DialogFragment_IncorrectDate;
import DialogFragments.DialogFragment_MissingInput;
import Notifications.Assessment_Notification_Service1;
import Notifications.Assessment_Notification_Service2;
import Notifications.Assessment_Notification_Service3;
import Notifications.Assessment_Notification_Service4;

public class AssessmentAdd extends AppCompatActivity {

    Calendar calendar1;
    Calendar calendar2;
    Calendar calendar3;
    Calendar calendar4;

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
    EditText objectiveTitle;
    EditText performanceTitle;

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

    String objectivetitle;
    String performancetitle;

    String date1;
    String date2;
    String date3;
    String date4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);

        database = new SQLdatabase(this);

        courseid = getIntent().getIntExtra("courseid", courseid);

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


        objectiveTitle = findViewById(R.id.objectiveTitle);
        startobjectiveinput = findViewById(R.id.startobjectiveinput);
        endObjectiveInput = findViewById(R.id.endObjectiveInput);


        performanceTitle = findViewById(R.id.performanceTitle);
         startperformanceinput = findViewById(R.id.startperformanceinput);
         endperformanceInput = findViewById(R.id.endperformanceinput);

        objectiveMulti = findViewById(R.id.objectiveMulti);
        performancemulti = findViewById(R.id.performancemulti);

        startcalendarperformance = findViewById(R.id.startcalendarperformance);
        startcalendarperformance.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month3 = month; year3 = year; dayOfMonth3 = dayOfMonth;

                calendar3 = Calendar.getInstance();
                calendar3.set( year3, month3, dayOfMonth3, 0, 0, 0);

                date3 = month+1 + "/" + dayOfMonth + "/" + year ;
                startperformanceinput.setText(date3);
            }
        });

        endcalendarperformance = findViewById(R.id.endcalendarperformance);
        endcalendarperformance.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month4 = month; year4 = year; dayOfMonth4 = dayOfMonth;

                calendar4 = Calendar.getInstance();
                calendar4.set( year4, month4, dayOfMonth4, 0, 0, 0);

                date4 = month+1 + "/" + dayOfMonth + "/" + year ;
                endperformanceInput.setText(date4);
            }
        });


        Button objectiveSavebutton = findViewById(R.id.objectiveSavebutton);
        Button performancesaveButton = findViewById(R.id.performancesaveButton);

        objectiveSavebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                objectivetitle = objectiveTitle.getText().toString();
                objectiveMultiassessments = objectiveMulti.getText().toString();
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

                            if (objectiveMultiassessments.isEmpty() ||  objectivetitle.isEmpty() || calendar1 == null || calendar2 == null) {
                                DialogFragment_MissingInput miss = new DialogFragment_MissingInput();
                                miss.show(getSupportFragmentManager(), "miss");
                            } else {

                                long count = database.insertObjectiveassessments(objectivetitle, objectiveMultiassessments, date1, date2, courseid);

                                if (count > 0) {

                                    objectiveTitle.getText().clear();
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


                performancetitle = performanceTitle.getText().toString();
                performancemultiassessments = performancemulti.getText().toString();
                date3 = startperformanceinput.getText().toString();
                date4 = endperformanceInput.getText().toString();

                if (year3 > year4) {
                    DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
                    cant.show(getSupportFragmentManager(), "cant");
                }
                else {

                    if (month3 == month4 ) {
                        DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
                        cant.show(getSupportFragmentManager(), "cant");
                    }
                    else {
                        if (month3 > month4 && year3 == year4) {
                            DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
                            cant.show(getSupportFragmentManager(), "cant");
                        }
                        else {

                            if (performancemultiassessments.isEmpty() || performancetitle.isEmpty() || calendar3 == null || calendar4 == null) {
                                DialogFragment_MissingInput miss = new DialogFragment_MissingInput();
                                miss.show(getSupportFragmentManager(), "miss");
                            } else {
                                long count = database.insertPerformanceassessments(performancetitle, performancemultiassessments, date3, date4, courseid);

                                if (count > 0) {

                                    AlarmManager alarmManager3 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                    Intent intent23 = new Intent(getBaseContext(), Assessment_Notification_Service3.class);
                                    PendingIntent pendingIntent3 = PendingIntent.getBroadcast(getBaseContext(), 1, intent23, PendingIntent.FLAG_IMMUTABLE);

                                    alarmManager3.setExact(AlarmManager.RTC_WAKEUP, calendar3.getTimeInMillis(), pendingIntent3);

                                    AlarmManager alarmManager4 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                    Intent intent24 = new Intent(getBaseContext(), Assessment_Notification_Service4.class);
                                    PendingIntent pendingIntent4 = PendingIntent.getBroadcast(getBaseContext(), 1, intent24, PendingIntent.FLAG_IMMUTABLE);

                                    alarmManager4.setExact(AlarmManager.RTC_WAKEUP, calendar4.getTimeInMillis(), pendingIntent4);

                                    performanceTitle.getText().clear();
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