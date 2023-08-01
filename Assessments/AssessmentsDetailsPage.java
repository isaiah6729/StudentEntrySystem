package Assessments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.zybooks.wgu.AlertsObject;
import com.zybooks.wgu.R;
import com.zybooks.wgu.SQLdatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import DialogFragments.DialogFragment_AddAssessment;
import DialogFragments.DialogFragment_IncorrectDate;
import DialogFragments.DialogFragment_MissingInput;
import Notifications.Assessment_Notification_Service1;
import Notifications.Assessment_Notification_Service2;

public class AssessmentsDetailsPage extends AppCompatActivity {


    private static AssessmentObjectObjective TheAlert;

    public static AssessmentObjectObjective getAlert() {    return TheAlert;}

    int code = 1;
    int code2 = 1;

    String Alert;
    EditText alert;

    Calendar calendar1;
    Calendar calendar2;

    CalendarView startcalendarObjective;
    CalendarView endcalendarObjective;

    TextView startobjectiveinput;
    TextView endObjectiveInput;

    int month1;
    int dayOfMonth1;
    int year1;

    int month2;
    int dayOfMonth2;
    int year2;

    String date1;
    String date2;

    SQLdatabase sqLdatabase;

    int assessmentid;
    String assessments;
    int courseid;

    TextView assessmentstext1;
    TextView assessmentstext2;
    TextView assessmentstext3;
    TextView assessmentstext6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments_details_page);

        sqLdatabase = new SQLdatabase(this);

        alert = findViewById(R.id.Alert);

        assessments = getIntent().getStringExtra("assessments");
        courseid = getIntent().getIntExtra("courseid", courseid);
        assessmentid = getIntent().getIntExtra("assessmentid", assessmentid);
        String title = getIntent().getStringExtra("title");

        assessmentstext1 = findViewById(R.id.assessmentstext1);
        assessmentstext2 = findViewById(R.id.assessmentstext2);
        assessmentstext3 = findViewById(R.id.assessmentstext3);
        assessmentstext6 = findViewById(R.id.assessmentstext6);

        assessmentstext1.setText(String.valueOf(assessmentid));
        assessmentstext2.setText(String.valueOf(courseid));
        assessmentstext3.setText(assessments);
        assessmentstext6.setText(title);

        startobjectiveinput = findViewById(R.id.startobjectiveinput);
        endObjectiveInput = findViewById(R.id.endObjectiveInput);

        startcalendarObjective = findViewById(R.id.startcalendarObjective);
        startcalendarObjective.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month1 = month;
                year1 = year;
                dayOfMonth1 = dayOfMonth;

                calendar1 = Calendar.getInstance();
                calendar1.set(year1, month1, dayOfMonth1, 0, 0, 0);

                date1 = month + 1 + "/" + dayOfMonth + "/" + year;
                startobjectiveinput.setText(date1);
            }
        });

        endcalendarObjective = findViewById(R.id.endcalendarObjective);
        endcalendarObjective.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month2 = month;
                year2 = year;
                dayOfMonth2 = dayOfMonth;

                calendar2 = Calendar.getInstance();
                calendar2.set(year2, month2, dayOfMonth2, 0, 0, 0);

                date2 = month + 1 + "/" + dayOfMonth + "/" + year;
                endObjectiveInput.setText(date2);
            }
        });

        Button objectiveSavebutton = findViewById(R.id.objectiveSavebutton);
        objectiveSavebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Alert = alert.getText().toString();
                date1 = startobjectiveinput.getText().toString();
                date2 = endObjectiveInput.getText().toString();

                TheAlert = new AssessmentObjectObjective(0, 0, null, null, null,null, Alert);

                if (year1 > year2) {
                    DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
                    cant.show(getSupportFragmentManager(), "cant");
                }
                else {

                    if (month1 == month2) {
                        DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
                        cant.show(getSupportFragmentManager(), "cant");
                    } else {

                        if (month1 > month2 && year1 == year2) {
                            DialogFragment_IncorrectDate cant = new DialogFragment_IncorrectDate();
                            cant.show(getSupportFragmentManager(), "cant");
                        } else {


                           if (Alert.isEmpty() || calendar1 == null || calendar2 == null) {
                               DialogFragment_MissingInput miss = new DialogFragment_MissingInput();
                               miss.show(getSupportFragmentManager(), "miss");
                            }
                            else {

                               sqLdatabase.insertAlerts(Alert, date1, date2);

                               AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                               Intent intent2 = new Intent(getBaseContext(), Assessment_Notification_Service1.class);
                               PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), code, intent2, PendingIntent.FLAG_MUTABLE);

                               alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent);

                               AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                               Intent intent22 = new Intent(getBaseContext(), Assessment_Notification_Service2.class);
                               PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getBaseContext(), code2, intent22, PendingIntent.FLAG_MUTABLE);

                               alarmManager2.setExact(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pendingIntent2);

                               code = code + 1;
                               code2 = code2 + 1;

                           }
                                alert.getText().clear();
                                startobjectiveinput.setText("");
                                endObjectiveInput.setText("");

                                toast();
                            }
                        }
                    }
                }

        });

    }

    public void toast () {
        Toast.makeText(this, "Your alert has been created "  + code + code2 + " " + Alert + " " + calendar1.getTime(), Toast.LENGTH_LONG).show();
    }
}

